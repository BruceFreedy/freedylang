package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;

import java.util.Collection;

public class SimpleList extends VList<Object> {
    public SimpleList(List<Object> object) {
        super(object);
    }

    public SimpleList(Collection<Object> collection) {
        super(new List<>(collection));
    }
}
