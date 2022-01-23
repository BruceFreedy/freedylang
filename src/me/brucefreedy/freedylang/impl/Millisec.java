package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

@Processable(alias = "millisec")
public class Millisec extends ProcessImpl<Number> {
    @Override
    public Number get() {
        return getTime();
    }
    @Override
    public String toString() {
        return getTime().toString();
    }

    protected SimpleNumber getTime() {
        return new SimpleNumber(System.currentTimeMillis());
    }
}
