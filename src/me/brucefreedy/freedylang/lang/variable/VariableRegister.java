package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;

import java.util.Iterator;

public class VariableRegister extends List<Scope> {


    public Object getVariable(String name) {
        return getVariable(peek(), name);
    }

    public void setVariable(String name, Object variable) {
        setVariable(peek(), name, variable);
    }

    public Object getVariable(List<String> nameList) {
        if (nameList.size() == 1) return getVariable(nameList.get(0));
        return getVariable(first(), nameList);
    }

    public void setVariable(List<String> nameList, Object process) {
        if (nameList.size() == 1) setVariable(nameList.get(0), process);
        else setVariable(first(), nameList, process);
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

    public void setVariable(Scope scope, List<String> nameList, Object variable) {
        Iterator<String> iterator = nameList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Object process = scope.getRegistry(name);
            if (!iterator.hasNext()) {
                scope.register(name, variable);
            }
            if (process instanceof ScopeSupplier) {
                scope = ((ScopeSupplier) process).getScope();
                if (scope == null) break;
            }

        }
    }

    public Object getVariable(Scope scope, List<String> nameList) {
        Iterator<String> iterator = nameList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Object process = scope.getRegistry(name);
            if (!iterator.hasNext()) {
                return process;
            }
            if (process instanceof ScopeSupplier) {
                scope = ((ScopeSupplier) process).getScope();
                if (scope == null) break;
            }

        }
        return null;
    }

}