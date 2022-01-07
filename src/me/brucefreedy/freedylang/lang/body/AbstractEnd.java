package me.brucefreedy.freedylang.lang.body;

import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

public abstract class AbstractEnd extends EmptyImpl<AbstractEnd> {
    @Override
    public AbstractEnd get() {
        return this;
    }
}
