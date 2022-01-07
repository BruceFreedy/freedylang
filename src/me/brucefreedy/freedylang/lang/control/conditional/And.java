package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

@Processable(alias = {"&&", "&"}, regex = true)
public class And extends EmptyImpl<And> {
    @Override
    public And get() {
        return this;
    }
}
