package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;

/**
 * empty body for process
 */
public abstract class EmptyImpl<T> extends Null implements Empty<T> {

    @Override
    public void parse(ParseUnit parseUnit) {
        //empty body
    }

    @Override
    public void run(ProcessUnit processUnit) {
        //empty body
    }

}
