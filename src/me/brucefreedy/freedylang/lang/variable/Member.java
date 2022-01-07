package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

@Processable(alias = ".", regex = true)
public class Member extends EmptyImpl<Member> {
    @Override
    public Member get() {
        return this;
    }
}
