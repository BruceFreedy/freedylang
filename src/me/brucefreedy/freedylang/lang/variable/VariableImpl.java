package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * method, variable, class
 */
@Processable(alias = "class")
public class VariableImpl extends ProcessImpl<Object> implements Variable<Object>, ScopeSupplier {

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

    public Scope getScope() {
        return scope;
    }

    public VariableImpl() {}

    public VariableImpl(String string) {
        this.string = string;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public String getString() {
        return string;
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
            Object variable = scope.getVariable(nodes);
            if (variable instanceof VariableImpl) {
                VariableImpl func = (VariableImpl) variable;
                List<Process<?>> paramsList = params.getProcesses();
                paramsList.forEach(p -> p.run(processUnit));
                if (func.args.size() <= paramsList.size()) {
                    func.body.setBeforeRun(
                            () -> IntStream.range(0, func.args.size()).forEach(i -> scope.setVariable(func.args.get(i), paramsList.get(i))));
                }
                func.body.run(processUnit);
                this.result = func.body.get();
            }
        } else if (assignment != null) {  //assignment
            assignment.run(processUnit);
            result = assignment.get();
            scope.setVariable(nodes, result);
        } else {  //variable
            Object variable = scope.getVariable(nodes);
            if (variable == null) result = new Null();
            else {
                result = variable;
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
}
