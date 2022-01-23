package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;

/**
 * process that contains list of processes
 */
public class ListProcess extends VList<Process<?>> {


    public ListProcess() {
        this(new List<>());
    }

    public ListProcess(List<Process<?>> list) {
        super(list);
    }

    public List<Process<?>> getProcesses() {
        return object;
    }

}