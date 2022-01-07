package me.brucefreedy.freedylang.lang.variable;


import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;

@Processable(alias = "null")
public class SimpleNull extends ProcessImpl<SimpleNull> {
    @Override
    public SimpleNull get() {
        return this;
    }

}
