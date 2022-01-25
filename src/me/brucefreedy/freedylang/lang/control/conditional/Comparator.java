package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.abst.StealerImpl;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;

public abstract class Comparator extends StealerImpl<Bool> implements Comparison, Condition {

    Bool result = Bool.get(false);
    Condition condition;

    public Bool getResult() {
        return result;
    }

    @Override
    public boolean getBool() {
        return result.getValue();
    }

    public void setResult(Bool result) {
        this.result = result;
    }

    @Override
    public Bool get() {
        return getResult();
    }

    @Override
    public String toString() {
        return getResult().toString();
    }


    @Override
    public Process<?> getProcess() {
        if (condition != null) return condition.getProcess();
        return super.getProcess();
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        if (process instanceof Stacker) {
            Process<?> peek = ((Stacker<?>) process).getPeek();
            if (peek instanceof Or) {
                Process<?> comparator = Process.parsing(parseUnit);
                parseUnit.steal(p -> {
                    if (p instanceof Condition) condition = ((Condition) p);
                }, () -> comparator);
            }
        }
        addStealer(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (running) return;
        running = true;
        a.run(processUnit);
        process.run(processUnit);
        running = false;
        setResult(Bool.get(compare(a.get(), process.get())));
        if (!getResult().getValue() && condition != null) {
            condition.run(processUnit);
            setResult(condition.get());
        }
    }

}
