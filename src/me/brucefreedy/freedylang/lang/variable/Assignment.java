package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

@Processable(alias = "=", regex = true)
public class Assignment extends EmptyImpl<Assignment> {
    @Override
    public Assignment get() {
        return this;
    }
}
