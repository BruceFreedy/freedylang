package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;

@FunctionalInterface
public interface CastCheck<T extends AbstractVar<?>> {
    boolean instof(Object o);
    default T cast(List<Process<?>> params) {
        Process<?> first = params.first();
        if (instof(first)) return (T) first;
        if (first == null) return null;
        Object o = first.get();
        if (instof(o)) return (T) o;
        else return null;
    }
}
