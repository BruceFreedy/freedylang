package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;

public class SimpleVar<T> extends AbstractVar<T> implements Process<Object> {
    public SimpleVar(T object) {
        super(object);
    }

    @Override
    public void parse(ParseUnit parseUnit) {

    }

    @Override
    public void run(ProcessUnit processUnit) {

    }

    @Override
    public Object get() {
        return this;
    }
}
