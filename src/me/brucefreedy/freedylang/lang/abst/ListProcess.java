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
        registerMethod("add", method(o -> object.addAll(o), (Supplier<List<Process<?>>>) List::new, o -> o, o -> o));
        registerMethod("remove", (processUnit, params) -> object.removeAll(params));
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
