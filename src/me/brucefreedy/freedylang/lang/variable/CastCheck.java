package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;

@FunctionalInterface
public interface CastCheck<T extends AbstractVar<?>> {
    boolean instof(Object o);
    default T cast(List<?> params) {
        Object o = params.first();
        if (instof(o)) return (T) o;
        else return null;
    }
}
