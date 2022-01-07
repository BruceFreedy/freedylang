package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.Process;

public interface BoolGetter<T> extends Process<T> {
    boolean getBool();
}
