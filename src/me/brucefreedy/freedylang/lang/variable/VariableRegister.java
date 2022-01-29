package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class VariableRegister extends List<Scope> {

    public VariableRegister() {}

    public VariableRegister(Collection<? extends Scope> c) {
        super(c.stream().map(Scope::new).collect(Collectors.toList()));
    }

    public Object getVariable(String name) {
        return getVariable(peek(), name);
    }

    public void setVariable(String name, Object variable) {
        setVariable(peek(), name, variable);
    }

    public Object getVariable(ProcessUnit processUnit, List<String> nameList) {
        if (nameList.size() == 1) return getVariable(nameList.get(0));
        else {
            List<Scope> scopes = new List<>(this);
            for (int i = scopes.size() - 1; i >= 0; i--) {
                Scope scope = scopes.get(i);
                Object variable = getVariable(processUnit, scope, nameList);
                if (variable != null) return variable;
            }
        }
        return null;
    }

    public void setVariable(ProcessUnit processUnit, List<String> nameList, Object process) {
        if (nameList.size() == 1) setVariable(nameList.get(0), process);
        else {
            List<Scope> scopes = new List<>(this);
            for (int i = scopes.size() - 1; i >= 0; i--) {
                Scope scope = scopes.get(i);
                if (setVariable(processUnit, scope, nameList, process)) return;
            }
        }
    }

    public Object getVariable(Scope scope, String name) {
        List<Scope> subScope = subScope(indexOf(scope));
        if (subScope.isEmpty()) return null;
        for (int i = subScope.size() - 1; i >= 0; i--) {
            Scope sco = subScope.get(i);
            if (sco.getRegistry(name) != null) {
                return sco.getRegistry(name);
            }
        }
        return null;
    }

    private List<Scope> subScope(int index) {
        return new List<>(subList(0, index + 1));
    }

    public void setVariable(Scope scope, String name, Object variable) {
        List<Scope> subScope = subScope(indexOf(scope));
        if (subScope.isEmpty()) return;
        for (int i = subScope.size() - 1; i >= 0; i--) {
            Scope sco = subScope.get(i);
            if (sco.getRegistry(name) != null) {
                sco.register(name, variable);
                return;
            }
        }
        peek().register(name, variable);
    }

    public boolean setVariable(ProcessUnit processUnit, Scope scope, List<String> nameList, Object variable) {
        if (scope == null) return false;
        Iterator<String> iterator = nameList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Object process = scope.getRegistry(name);
            if (process == null) return false;
            if (!iterator.hasNext()) {
                scope.register(name, variable);
                return true;
            }
            if (process instanceof Method) {
                process = ((Method) process).run(processUnit, new List<>());
            }
            if (process instanceof ScopeSupplier) {
                scope = ((ScopeSupplier) process).getScope();
                if (scope == null) break;
            }
        }
        return false;
    }

    public Object getVariable(ProcessUnit processUnit, Scope scope, List<String> nameList) {
        if (scope == null) return null;
        Iterator<String> iterator = nameList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Object process = scope.getRegistry(name);
            if (process == null) return null;
            if (!iterator.hasNext()) {
                return process;
            }
            if (process instanceof Method) {
                process = ((Method) process).run(processUnit, new List<>());
            }
            if (process instanceof ScopeSupplier) {
                scope = ((ScopeSupplier) process).getScope();
                if (scope == null) break;
            }

        }
        return null;
    }

    protected Object workMethod(ProcessUnit unit, Object o) {
        if (o instanceof Method) return ((Method) o).run(unit, new List<>());
        else return o;
    }

}