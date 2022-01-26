package me.brucefreedy.freedylang.lang.variable.bool;

import me.brucefreedy.freedylang.lang.variable.SimpleVar;

import java.util.function.Predicate;

public abstract class Bool extends SimpleVar<Boolean> implements Predicate<Object> {

    public Bool() {
        super(false);
    }

    @Override
    public Boolean getObject() {
        return getValue();
    }

    public Bool(Boolean object) {
        super(object);
    }

    public static Bool get(boolean bool) {
        if (bool) return new True();
        else return new False();
    }

    public abstract boolean getValue();

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        return test(obj);
    }

    @Override
    public boolean test(Object o) {
        return toString().equals(o.toString());
    }

}
