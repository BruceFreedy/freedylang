package me.brucefreedy.freedylang.lang.abst;

import lombok.Setter;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

import java.util.function.Consumer;

public abstract class VList<T> extends SimpleVar<List<T>> {

    @Setter
    protected Consumer<List<T>> sync = p -> {};

    public VList(List<T> object) {
        super(object);
        register("add", (Method) (processUnit, params) -> {
            for (Object o : params) {
                if (o instanceof Process<?>) object.add((T) o);
                else object.add((T) o);
                sync.accept(object);
            }
            return object;
        });
        register("remove", (Method) (processUnit, params) -> {
            object.removeAll(params);
            sync.accept(object);
            return new Null();
        });
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
            for (T process : object) {
                if (process instanceof Process<?>) {
                    Process<?> p = (Process<?>) process;
                    p.run(unit);
                    Object o = p.get();
                    unit.getVariableRegister().setVariable("e", o);
                } else {
                    unit.getVariableRegister().setVariable("e", process);
                }
                for (Object o : params) {
                    if (o instanceof Process<?>) ((Process<?>) o).run(unit);
                }
            }
            return new SimpleNumber(object.size());
        });
        register("contains", (Method) (unit, params) -> Bool.get(object.contains(params.first())));
    }

    @Override
    public String toString() {
        if (object.isEmpty()) return super.toString();
        else if (object.size() == 1) return object.get(0).toString();
        else return object.toString();
    }

}
