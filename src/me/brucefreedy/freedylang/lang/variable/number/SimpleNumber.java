package me.brucefreedy.freedylang.lang.variable.number;

public class SimpleNumber extends Number {
    public SimpleNumber(java.lang.Number number) {
        setNumber(number);
    }

    @Override
    public Number get() {
        return new SimpleNumber(number);
    }
}
