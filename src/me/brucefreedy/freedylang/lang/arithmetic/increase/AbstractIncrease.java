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
        if (!workIncrease(a)) workIncrease(process);
    }

    protected boolean workIncrease(Process<?> p) {
        if (p == null) return false;
        Object o = p.get();
        if (o instanceof Number) {
            number = increase((Number) o);
            return true;
        }
        return false;
    }

    public abstract Number increase(Number number);

    @Override
    public Object get() {
        if (number == null) return new SimpleNumber(0);
        return number.get();
    }
}
