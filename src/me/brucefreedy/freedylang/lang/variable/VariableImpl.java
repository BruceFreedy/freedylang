package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.abst.*;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;
import net.jafama.FastMath;

import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * method, variable, class
 */
@Processable(alias = "class")
public class VariableImpl extends ProcessImpl<Object> implements Variable<Object>, ScopeSupplier, Method, ScopeChild {

    protected String string;
    protected AbstractFront body;
    protected AbstractFront params;
    protected Process<?> assignment;
    protected List<List<String>> args = new List<>();
    protected Object result = new Null();
    protected List<String> nodes = new List<>();
    protected Scope scope;
    protected List<ScopeChild> declaration;
    protected boolean initialized = false;
    protected Scope parent;
    protected VariableImpl nextFunc;
    protected ScopeSupplier beforeScope;

    @Override
    public void setParent(ProcessUnit processUnit, Scope scope) {
        processUnit.getVariableRegister().setVariable(processUnit, nodes, this);
        parent = scope;
        if (body != null && params == null) run(processUnit);
    }

    public Scope getScope() {
        if (result instanceof ScopeSupplier) return ((ScopeSupplier) result).getScope();
        return scope;
    }

    public VariableImpl() {
        super();
    }

    public VariableImpl(String string) {
        this.string = string;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public String getString() {
        return string;
    }

    public void setVariable(ProcessUnit processUnit, VariableRegister register, List<String> nodes, Object process) {
        if (beforeScope == null) register.setVariable(processUnit, nodes, process);
        else register.setVariable(processUnit, beforeScope.getScope(), nodes, process);
    }

    public Object getVariable(ProcessUnit processUnit, VariableRegister register, List<String> nodes) {
        if (beforeScope == null) return register.getVariable(processUnit, nodes);
        else return register.getVariable(processUnit, beforeScope.getScope(), nodes);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        if (string == null) {
            List<ScopeChild> peek = parseUnit.getDeclaration().peek();
            if (peek != null) peek.add(this);
            parseUnit.getDeclaration().add(declaration = new List<>());
        }
        nodes.add(string);
        super.parse(parseUnit);
        while (process instanceof Member) {  //member
            parseUnit.popPeek();
            process = ((Stacker<?>) process).getProcess();
            if (process instanceof VariableImpl) {
                VariableImpl variable = (VariableImpl) this.process;
                nodes.addAll(variable.getNodes());
                body = variable.body;
                assignment = variable.assignment;
                params = variable.params;
                process = variable.getProcess();
                nextFunc = variable.nextFunc;
            } else break;
        }
        if (process instanceof AbstractFront) {  //method
            params = ((AbstractFront) process);
            process = params.getProcess();
            if (nodes.size() == 1) {
                while (process instanceof Member) {  //next func
                    parseUnit.popPeek();
                    process = ((Stacker<?>) process).getProcess();
                    if (process instanceof VariableImpl) {
                        nextFunc = (VariableImpl) this.process;
                    } else break;
                }
            }
            if (process instanceof AbstractFront) {  //method body
                body = ((AbstractFront) process);
                body.setScopeSupplier(() -> new Scope(Scope.ScopeType.METHOD));
                process = body.getProcess();
                args = new List<>(params.getProcesses().stream().filter(p -> p instanceof VariableImpl)
                        .map(p -> ((VariableImpl) p).nodes).collect(Collectors.toList()));
                parseUnit.getDeclaration().peek().add(this);
            }  //else method calling
        } else if (string == null && process instanceof VariableImpl) {  //class
            scope = new Scope(Scope.ScopeType.CLASS);
            VariableImpl variable = (VariableImpl) this.process;
            string = variable.getString();
            nodes = variable.nodes;
            body = variable.params;
            body.setScopeSupplier(this::getScope);
            process = variable.getProcess();
            parseUnit.getDeclaration().popPeek();
        } else if (process instanceof Assignment) {  //assignment
            assignment = Process.parsing(parseUnit);
            if (assignment instanceof Stacker) process = ((Stacker<?>) assignment).getProcess();
            else process = new Breaker();
            parseUnit.steal(p -> assignment = p, () -> assignment);
        }
    }

    @Override
    public void run(ProcessUnit processUnit) {
        VariableRegister scope = processUnit.getVariableRegister();
        if (params != null && body != null) {  //method body
        } else if (params == null && body != null && assignment == null) {  //class
            if (initialized) {
                process.run(processUnit);
            } else {
                initialized = true;
                scope.setVariable(processUnit, nodes, this);
                body.setBeforeRun(() -> declaration.forEach(p -> p.setParent(processUnit, scope.peek())));
                body.run(processUnit);
            }
        } else if (params != null) {  //method call
            Object variable = getVariable(processUnit, scope, nodes);
            if (variable instanceof Method) {
                List<Process<?>> paramsList = params.getProcesses();
                boolean isRunAfter = variable instanceof MethodRunAfter;
                if (!isRunAfter) paramsList.forEach(p -> p.run(processUnit));
                VariableRegister register = new VariableRegister();
                if (scope.first() != null) register.add(scope.first());
                Object result = ((Method) variable).run(new ProcessUnit(isRunAfter ? scope : register),
                        isRunAfter ? new List<>(paramsList) :
                        new List<>(paramsList.stream().map(Supplier::get).collect(Collectors.toList())));
                if (nextFunc != null && result instanceof ScopeSupplier) {
                    nextFunc.beforeScope = ((ScopeSupplier) result);
                    nextFunc.run(processUnit);
                    this.result = nextFunc.result;
                    return;
                } else this.result = result;
            }
        } else if (assignment != null) {  //assignment
            Object variable = getVariable(processUnit, scope, nodes);
            boolean isRunAfter = variable instanceof MethodRunAfter;
            if (!isRunAfter) assignment.run(processUnit);
            boolean isVarImpl = variable instanceof VariableImpl;
            if (variable instanceof Method && (!isVarImpl || ((VariableImpl) variable).isMethod())) {
                VariableRegister register = new VariableRegister();
                if (scope.first() != null) register.add(scope.first());
                if (assignment instanceof AbstractFront) {
                    result = ((Method) variable).run(new ProcessUnit(isRunAfter ? scope : register), ((AbstractFront) assignment).getProcesses());
                } else if (assignment instanceof Method) {
                    result = ((Method) variable).run(new ProcessUnit(isRunAfter ? scope : register),
                            new List<>(Collections.singletonList(isRunAfter ? assignment : assignment.get())));
                } else result = ((Method) variable).run(new ProcessUnit(isRunAfter ? scope : register), new List<>(Collections.singletonList(assignment)));
            } else if (assignment instanceof Method) {
                result = isRunAfter ? assignment : assignment.get();
                setVariable(processUnit, scope, nodes, result);
            } else if (assignment instanceof AbstractFront) {
                result = assignment;
                setVariable(processUnit, scope, nodes, result);
            } else {
                result = isRunAfter ? assignment : assignment.get();
                setVariable(processUnit, scope, nodes, result);
            }
        } else {  //variable
            Object variable = getVariable(processUnit, scope, nodes);
            if (variable == null) result = new Null();
            else {
                if (variable instanceof Method) {
                    VariableRegister register = new VariableRegister();
                    if (scope.first() != null) register.add(scope.first());
                    result = ((Method) variable).run(new ProcessUnit(register), new List<>());
                } else result = variable;
            }
        }
        super.run(processUnit);
    }

    public boolean isMethod() {
        return body != null && params != null && assignment == null;
    }

    @Override
    public Object get() {
        if (params != null && body != null) return this;
        return result;
    }

    @Override
    public String toString() {
        if (body != null) return string;
        return result.toString();
    }

    @Override
    public Object run(ProcessUnit processUnit, List<?> params) {
        if (this.body != null && this.params == null && assignment == null) {
            return this;
        }
        VariableRegister scope = processUnit.getVariableRegister();
        body.setBeforeRun(() -> IntStream.range(0, FastMath.min(params.size(), args.size())).forEach(i -> scope.setVariable(processUnit, args.get(i), params.get(i))));
        scope.add(parent);
        body.run(processUnit);
        scope.popPeek();
        return body.get();

    }

}
