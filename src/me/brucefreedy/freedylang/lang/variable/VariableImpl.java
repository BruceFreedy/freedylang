package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;

import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * method, variable, class
 */
@Processable(alias = "class")
public class VariableImpl extends ProcessImpl<Object> implements Variable<Object>, ScopeSupplier, Method {

    protected String string;
    protected AbstractFront body;
    protected AbstractFront params;
    protected Process<?> assignment;
    protected List<List<String>> args = new List<>();
    protected Object result = new Null();
    protected List<String> nodes = new List<>();
    protected Scope scope;
    protected List<Process<?>> declaration;
    protected boolean initialized = false;
    protected Scope parent;
    protected VariableImpl nextFunc;
    protected ScopeSupplier beforeScope;

    public Scope getScope() {
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

    protected void setVariable(VariableRegister register, List<String> nodes, Object o) {
        if (beforeScope == null) register.setVariable(nodes, process);
        else register.setVariable(beforeScope.getScope(), nodes, process);
    }

    protected Object getVariable(VariableRegister register, List<String> nodes) {
        if (beforeScope == null) return register.getVariable(nodes);
        else return register.getVariable(beforeScope.getScope(), nodes);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        if (string == null) parseUnit.getDeclaration().add(declaration = new List<>());
        nodes.add(string);
        super.parse(parseUnit);
        while (process instanceof Member) {  //member
            process = Process.parsing(parseUnit);
            if (process instanceof VariableImpl) {
                VariableImpl variable = (VariableImpl) this.process;
                nodes.addAll(variable.getNodes());
                body = variable.body;
                assignment = variable.assignment;
                params = variable.params;
                process = variable.getProcess();
            } else break;
        }
        if (process instanceof AbstractFront) {  //method
            params = ((AbstractFront) process);
            process = params.getProcess();
            while (process instanceof Member) {  //next func
                process = Process.parsing(parseUnit);
                if (process instanceof VariableImpl) {
                    nextFunc = (VariableImpl) this.process;
                } else break;
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
            process = new Breaker();
            parseUnit.popPeek(stealer -> {
                stealer.setProcess(assignment);
                assignment = stealer;
            });
        }
    }

    @Override
    public void run(ProcessUnit processUnit) {
        VariableRegister scope = processUnit.getVariableRegister();
        if (params != null && body != null) {  //method body
            scope.setVariable(nodes, this);
            parent = scope.peek();
        } else if (params == null && body != null && assignment == null) {  //class
            if (initialized) {
                process.run(processUnit);
            } else {
                initialized = true;
                scope.setVariable(nodes, this);
                body.setBeforeRun(() -> declaration.forEach(p -> p.run(processUnit)));
                body.run(processUnit);
            }
        } else if (params != null) {  //method call
            Object variable = getVariable(scope, nodes);
            if (variable instanceof Method) {
                List<Process<?>> paramsList = params.getProcesses();
                Object result = ((Method) variable).run(processUnit, paramsList);
                if (nextFunc != null && result instanceof ScopeSupplier) {
                    nextFunc.beforeScope = ((ScopeSupplier) result);
                    nextFunc.run(processUnit);
                    this.result = nextFunc.result;
                }
                this.result = result;
            }
        } else if (assignment != null) {  //assignment
            Object variable = getVariable(scope, nodes);
            assignment.run(processUnit);
            if (variable instanceof Method && !(variable instanceof VariableImpl)) {
                if (assignment instanceof AbstractFront) {
                    ((Method) variable).run(processUnit, ((AbstractFront) assignment).getProcesses());
                } else ((Method) variable).run(processUnit, new List<>(Collections.singletonList(assignment)));
            } else {
                result = assignment;
                setVariable(scope, nodes, result);
            }
        } else {  //variable
            Object variable = getVariable(scope, nodes);
            if (variable == null) result = new Null();
            else {
                if (variable instanceof Method && !(variable instanceof VariableImpl)) {
                    result = ((Method) variable).run(processUnit, new List<>());
                } else result = variable;
            }
        }
        super.run(processUnit);
    }

    @Override
    public Object get() {
        if (scope != null || params != null && body != null) return this;
        return result;
    }

    @Override
    public String toString() {
        if (body != null) return string;
        return result.toString();
    }

    @Override
    public Object run(ProcessUnit processUnit, List<Process<?>> params) {
        VariableRegister scope = processUnit.getVariableRegister();
        params.forEach(p -> p.run(processUnit));
        if (args.size() <= params.size()) {
            body.setBeforeRun(
                    () -> IntStream.range(0, args.size()).forEach(i -> scope.setVariable(args.get(i), params.get(i))));
        }
        scope.add(parent);
        body.run(processUnit);
        scope.popPeek();
        return body.get();

    }

}
