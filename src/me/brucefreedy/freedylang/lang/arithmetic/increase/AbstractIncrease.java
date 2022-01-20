package me.brucefreedy.freedylang.lang.arithmetic.increase;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.StealerImpl;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

public abstract class AbstractIncrease extends StealerImpl<Object> {

    Number number;
    boolean running;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        addStealer(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (running) return;
        running = true;
        number = null;
        if (!workIncrease(processUnit, a)) workIncrease(processUnit, process);
        else process.run(processUnit);
        running = false;
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

    @Override
    public String toString() {
        if (number == null) return super.toString();
        else return number.toString();
    }

}
