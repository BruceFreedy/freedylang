package me.brucefreedy.freedylang.lang;

import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

/**
 * empty process, work for break run
 */
@Processable(alias = {
        ";", ",", "\n"
}, regex = true)
public class Breaker extends EmptyImpl<Breaker> {

    @Override
    public Breaker get() {
        return this;
    }

}
