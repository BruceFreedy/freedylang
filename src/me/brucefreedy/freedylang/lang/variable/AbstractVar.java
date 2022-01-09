package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;

public class AbstractVar<T> extends Null implements ScopeSupplier {
    protected final Scope scope = new Scope();
    protected T object;

    public AbstractVar(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        if (object == null) return super.toString();
        return object.toString();
    }
    @Override
    public Scope getScope() {
        return scope;
    }

    public Scope register(String name, Object var) {
        Scope scope = getScope();
        scope.register(name, var);
        return scope;
    }

    public Scope registerMethod(String name, Method method) {
        return register(name, method);
    }

}
