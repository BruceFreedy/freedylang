package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;

import java.util.function.Supplier;

/**
 * process that contains list of processes
 */
public abstract class ListProcess extends AbstractVar<List<Process<?>>> {

    public ListProcess() {
        super(new List<>());
        register("add", (Method) (processUnit, params) -> object.addAll(params));
        register("remove", (Method) (processUnit, params) -> object.removeAll(params));
    }

    public List<Process<?>> getProcesses() {
        return object;
    }

    @Override
    public String toString() {
        if (object.isEmpty()) return super.toString();
        else if (object.size() == 1) return object.get(0).toString();
        else return object.toString();
    }

}
