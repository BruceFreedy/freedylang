package me.brucefreedy.freedylang.lang.variable.bool;

public class False extends Bool {
    @Override
    public boolean getValue() {
        return false;
    }

    @Override
    public String toString() {
        return "false";
    }
}
