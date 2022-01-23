package me.brucefreedy.freedylang.lang.control.iteration;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.bool.True;

@Processable(alias = "while")
public class WhileImpl extends ProcessImpl<WhileImpl> {

//    private static final AfterRun empty = o -> {};

    AbstractFront body;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        if (process instanceof AbstractFront) {
            body = ((AbstractFront) process);
        }
    }

    private boolean condition(ProcessUnit processUnit) {
        body.run(processUnit);
        return body.get() instanceof True;
    }

    @Override
    public void run(ProcessUnit processUnit) {
        while ((processUnit.getReturner() == null) && condition(processUnit))
            body.getProcess().run(processUnit);
    }

    @Override
    public WhileImpl get() {
        return this;
    }
}
