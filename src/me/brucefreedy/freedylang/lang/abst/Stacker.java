package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.Process;

import java.util.function.Predicate;

/**
 * for stack process
 */
public interface Stacker<T> extends Process<T> {
    Process<?> getProcess();

    /**
     * get peek stacker till not stacker
     */
    default Process<?> getPeek() {
        Process<?> p = getProcess();
        while (p instanceof Stacker) {
            p = ((Stacker<?>) p).getProcess();
        }
        return p;
    }

    /**
     * find matching filter in stacker
     */
    @Deprecated
    static Process<?> getFirst(Process<?> p, Predicate<Process<?>> filter) {
        if (filter.test(p)) return p;
        while (p instanceof Stacker) {
            p = ((Stacker<?>) p).getProcess();
            if (filter.test(p)) return p;
        }
        return null;
    }

}
