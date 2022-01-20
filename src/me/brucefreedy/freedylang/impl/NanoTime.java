package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

@Processable(alias = "nanotime")
public class NanoTime extends ProcessImpl<Number> {
    @Override
    public Number get() {
        return getTime();
    }
    @Override
    public String toString() {
        return getTime().toString();
    }

    protected SimpleNumber getTime() {
        return new SimpleNumber(System.nanoTime());
    }
}
