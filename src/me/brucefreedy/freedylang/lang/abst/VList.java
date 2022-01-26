package me.brucefreedy.freedylang.lang.abst;

import lombok.Setter;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
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
                if (object.size() <= index || index < 0) return new Null();
                return object.get(index);
            } else return new Null();
        });
        register("each", (MethodRunAfter) (unit, params) -> {
            for (T process : new List<>(object)) {
                if (process instanceof AbstractVar) {
                    Scope scope = ((AbstractVar<?>) process).getScope();
                    unit.getVariableRegister().add(scope);
                    params.stream().filter(o -> o instanceof Process).forEach(o -> ((Process<?>) o).run(unit));
                    unit.getVariableRegister().popPeek();
                }
            }
            return new SimpleNumber(object.size());
        });
        register("contains", (Method) (unit, params) -> Bool.get(object.containsAll(params)));
    }

    @Override
    public String toString() {
        if (object.isEmpty()) return super.toString();
        else if (object.size() == 1) return object.get(0).toString();
        else return object.toString();
    }

}
