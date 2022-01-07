package me.brucefreedy.freedylang.lang.body.parenthesis;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.body.AbstractFront;

@Processable(alias = "(", regex = true)
public class FrontParenthesis extends AbstractFront {
    @Override
    public boolean test(Process<?> process) {
        return process instanceof EndParenthesis;
    }
}
