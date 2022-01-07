package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.Breaker;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;

/**
 * steal front of process over the empty process
 */
public abstract class StealerImpl<T> extends ProcessImpl<T> implements Stealer<T> {

    protected Process<?> a = new Breaker();
    protected boolean running;

    @Override
    public void setProcess(Process<?> process) {
        a = process;
    }

    public void addStealer(ParseUnit parseUnit) {
        parseUnit.add(this);
    }

}
