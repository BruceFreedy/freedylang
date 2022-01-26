package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;

import java.util.Collection;

public class SimpleList extends VList<Object> {
    public SimpleList(List<Object> object) {
        super(object);
    }

    public SimpleList(Collection<Object> collection) {
        super(new List<>(collection));
        register("add", (Method) (processUnit, params) -> {
            for (Object o : params) {
                object.add(o);
                sync.accept(object);
            }
            return object;
        });
    }
}
