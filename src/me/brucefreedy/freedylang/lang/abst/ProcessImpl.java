package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.Breaker;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;

/**
 * stack process principle, must be parents of whole process
 */
public abstract class ProcessImpl<T> extends Null implements Process<T>, Stacker<T> {

    protected Process<?> process = new Breaker();

    @Override
    public Process<?> getProcess() {
        return process;
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        process.run(processUnit);
    }


}
