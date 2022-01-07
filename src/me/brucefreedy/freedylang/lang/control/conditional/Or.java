package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

@Processable(alias = {"||", "|"}, regex = true)
public class Or extends EmptyImpl<Or> {

    @Override
    public Or get() {
        return this;
    }

}
