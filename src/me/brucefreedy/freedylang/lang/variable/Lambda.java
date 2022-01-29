package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.*;

@Processable(alias = ":", regex = true)
public class Lambda extends ProcessImpl<Process<?>> {
    @Override
    public Process<?> get() {
        return process;
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
