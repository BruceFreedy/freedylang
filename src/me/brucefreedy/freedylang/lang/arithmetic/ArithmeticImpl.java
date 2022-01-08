package me.brucefreedy.freedylang.lang.arithmetic;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.abst.Stealer;
import me.brucefreedy.freedylang.lang.variable.number.Number;

public abstract class ArithmeticImpl extends Number implements Stealer<Number>, Arithmetic {

    Process<?> a;
    Process<?> b;
    public ArithmeticImpl next;
    boolean running;
    boolean disabled;

    @Override
    public void setProcess(Process<?> process) {
        a = process;
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        b = process;
        if (b instanceof ArithmeticImpl) {
            ArithmeticImpl b = (ArithmeticImpl) this.b;
            b.disabled = true;
            next = b.next;
            parseUnit.popPeek();
        } else if (process instanceof Stacker) {
            Process<?> stacker = ((Stacker<?>) this.process).getProcess();
            if (stacker instanceof ArithmeticImpl) {
                next = ((ArithmeticImpl) stacker);
                next.a = b;
                parseUnit.popPeek();
            }
        }

        parseUnit.add(this);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (running) return;
        running = true;
        if (disabled) {
            b.run(processUnit);
            Object o = b.get();
            if (o instanceof Number) {
                setNumber(((Number) o).getNumber());
            }
        } else if (next != null) {
            if (getSeq().compare(next.getSeq())) {
                work(processUnit, a, b);
                next.a = this;
                next.run(processUnit);
                next.a = b;
                number = next.number;
            } else {
                next.run(processUnit);
                work(processUnit, a, next);
            }
        } else {
            work(processUnit, a, b);
            running = false;
            super.run(processUnit);
        }
        running = false;
    }

    private void work(ProcessUnit processUnit, Process<?> a, Process<?> b) {
        a.run(processUnit);
        b.run(processUnit);
        Object aO = a.get();
        Object bO = b.get();
        if (bO instanceof Number) {
            Number nB = (Number) bO;
            java.lang.Number numB = nB.getNumber();
            if (numB != null) {
                if (aO instanceof Number) {
                    Number nA = (Number) aO;
                    java.lang.Number numA = nA.getNumber();
                    if (numA != null) {
                        setNumber(operate(numA, numB));
                    } else {
                        setNumber(numB);
                    }
                } else {
                    setNumber(numB);
                }
            }
        }
    }

    @Override
    public Number get() {
        return this;
    }

    @Override
    public String toString() {
        if (number == null) return a.toString() + (next == null ? b.toString() : next.toString());
        else return super.toString();
    }

}
