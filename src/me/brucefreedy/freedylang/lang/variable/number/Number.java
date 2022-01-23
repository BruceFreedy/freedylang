package me.brucefreedy.freedylang.lang.variable.number;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.control.conditional.Comparable;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;

public abstract class Number extends SimpleVar<java.lang.Number> implements Process<Object>, Comparable, Stacker<Object> {

    protected Process<?> process;

    public Number() {
        super(null);
    }

    public Number(java.lang.Number object) {
        super(object);
    }

    protected void setNumber(java.lang.Number number) {
        this.object = number;
    }

    public java.lang.Number getNumber() {
        if (object == null) return null;
        if (object.intValue() == object.doubleValue()) this.object = object.intValue();
        return object;
    }

    @Override
    public String toString() {
        return getNumber().toString();
    }

    @Override
    public int compare(Object o) {
        try {
            double d1 = object.doubleValue();
            double d2;
            if (o instanceof Number) {
                d2 = ((Number) o).object.doubleValue();
            } else if (o instanceof String) {
                d2 = Double.parseDouble(((String) o));
            } else d2 = 0;
            return Double.compare(d1, d2);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            double d1 = object.doubleValue();
            double d2;
            if (o instanceof Number) {
                d2 = ((Number) o).object.doubleValue();
            } else if (o instanceof String) {
                d2 = Double.parseDouble(((String) o));
            } else d2 = 0;
            return d1 == d2;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        process.run(processUnit);
    }

    @Override
    public Process<?> getProcess() {
        return process;
    }

    @Override
    public Object get() {
        return new SimpleNumber(object);
    }
}
