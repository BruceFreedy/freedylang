package me.brucefreedy.freedylang.lang.scope;

import me.brucefreedy.common.RegistryImpl;

public class Scope extends RegistryImpl<String, Object> {

    public enum ScopeType {
        BODY, METHOD, CLASS
    }
    public Scope(Scope scope) {
        super(scope);
        this.type = scope.type;
    }

    private final ScopeType type;

    public ScopeType getType() {
        return type;
    }

    public Scope() {
        this(ScopeType.BODY);
    }

    public Scope(ScopeType type) {
        this.type = type;
    }

    public boolean isBody() {
        return type == ScopeType.BODY;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "isMethod=" + type.name() +", " + super.toString() +
                '}';
    }
}