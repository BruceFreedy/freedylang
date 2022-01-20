package me.brucefreedy.freedylang.lang.variable.number;

import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.control.conditional.Comparable;

public abstract class Number extends ProcessImpl<Object> implements Comparable {

    protected java.lang.Number number;

    protected void setNumber(java.lang.Number number) {
        this.number = number;
    }

    public java.lang.Number getNumber() {
        if (number == null) return null;
        if (number.intValue() == number.doubleValue()) this.number = number.intValue();
        return number;
    }

    @Override
    public String toString() {
        return getNumber().toString();
    }

    @Override
    public int compare(Object o) {
        try {
            double d1 = number.doubleValue();
            double d2;
            if (o instanceof Number) {
                d2 = ((Number) o).number.doubleValue();
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
            double d1 = number.doubleValue();
            double d2;
            if (o instanceof Number) {
                d2 = ((Number) o).number.doubleValue();
            } else if (o instanceof String) {
                d2 = Double.parseDouble(((String) o));
            } else d2 = 0;
            return d1 == d2;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Object get() {
        return new SimpleNumber(number);
    }
}
