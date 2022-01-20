package me.brucefreedy.freedylang.lang.arithmetic.increase;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.StealerImpl;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

public abstract class AbstractIncrease extends StealerImpl<Object> {

    Number number;

    @Override
    public void parse(ParseUnit parseUnit) {
        addStealer(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (!workIncrease(processUnit, a)) workIncrease(processUnit, process);
        else process.run(processUnit);
    }

    protected boolean workIncrease(ProcessUnit unit, Process<?> p) {
        if (p == null) return false;
        p.run(unit);
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
