package me.brucefreedy.freedylang.lang.body.brace;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.body.AbstractFront;

@Processable(alias = "{", regex = true)
public class FrontBrace extends AbstractFront {
    @Override
    public boolean test(Process<?> process) {
        return process instanceof EndBrace;
    }
}
