package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Cover;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

/**
 * process that contains list of processes
 */
public abstract class ListProcess extends AbstractVar<List<Process<?>>> {

    public ListProcess() {
        super(new List<>());
        register("add", (Method) (processUnit, params) -> {
            for (Object o : params) {
                if (o instanceof Process<?>) object.add(((Process<?>) o));
                else object.add(new Cover(o));
            }
            return object;
        });
        register("remove", (Method) (processUnit, params) -> object.removeAll(params));
        register("size", (Method) (unit, params) -> new SimpleNumber(object.size()));
        register("get", (Method) (unit, params) -> {
            Object first = params.first();
            if (first instanceof Number) {
                int index = ((Number) first).getNumber().intValue();
                if (object.size() <= index) return new Null();
                return object.get(index);
            } else return new Null();
        });
        register("each", (MethodRunAfter) (unit, params) -> {
            for (Process<?> process : object) {
                process.run(unit);
                unit.getVariableRegister().setVariable("e", process.get());
                for (Object o : params) {
                    if (o instanceof Process<?>) ((Process<?>) o).run(unit);
                }
            }
            return new SimpleNumber(object.size());
        });
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