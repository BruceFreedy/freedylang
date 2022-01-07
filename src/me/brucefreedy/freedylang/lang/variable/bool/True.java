package me.brucefreedy.freedylang.lang.variable.bool;

public class True extends Bool {
    @Override
    public boolean getValue() {
        return true;
    }

    @Override
    public String toString() {
        return "true";
    }
}
