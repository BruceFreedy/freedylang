package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;

/**
 * process that contains list of processes
 */
public abstract class ListProcess<T> extends Null implements Process<T> {

    protected final List<Process<?>> processes = new List<>();

    public List<Process<?>> getProcesses() {
        return processes;
    }

    @Override
    public String toString() {
        if (processes.isEmpty()) return super.toString();
        else if (processes.size() == 1) return processes.get(0).toString();
        else return processes.toString();
    }

}
