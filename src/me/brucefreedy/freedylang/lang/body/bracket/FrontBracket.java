package me.brucefreedy.freedylang.lang.body.bracket;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.body.AbstractFront;

@Processable(alias = "[", regex = true)
public class FrontBracket extends AbstractFront {
    @Override
    public boolean test(Process<?> process) {
        return process instanceof EndBracket;
    }
}
