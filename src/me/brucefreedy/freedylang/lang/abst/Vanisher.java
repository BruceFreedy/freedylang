package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.Process;

public interface Vanisher<T> extends Process<T> {
    boolean vanish();
}
