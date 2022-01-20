package me.brucefreedy.freedylang.lang.arithmetic.increase;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.StealerImpl;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

public abstract class AbstractIncrease extends StealerImpl<Object> {

    Number number;

    @Override
    public void run(ProcessUnit processUnit) {
        Process<?> p = process == null ? a : process;
        if (p == null) return;
        Object o = p.get();
        if (o instanceof Number) {
            number = increase((Number) o);
        }
    }

    public abstract Number increase(Number number);

    @Override
    public Object get() {
        if (number == null) return new SimpleNumber(0);
        return number.get();
    }
}
