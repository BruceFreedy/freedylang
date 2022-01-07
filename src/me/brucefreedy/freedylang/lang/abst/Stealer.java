package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.Process;

/**
 * set process that before empty
 */
public interface Stealer<T> extends Process<T> {
    void setProcess(Process<?> process);
}
