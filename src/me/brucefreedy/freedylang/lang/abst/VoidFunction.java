package me.brucefreedy.freedylang.lang.abst;

/**
 * return this instance value for process
 */
public abstract class VoidFunction extends ProcessImpl<VoidFunction> {
    @Override
    public VoidFunction get() {
        return this;
    }
}
