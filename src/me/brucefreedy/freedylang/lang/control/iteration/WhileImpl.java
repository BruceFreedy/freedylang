package me.brucefreedy.freedylang.lang.control.iteration;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.AfterRun;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.bool.True;

@Processable(alias = "while")
public class WhileImpl extends ProcessImpl<WhileImpl> {

    private static final AfterRun empty = o -> {};

    AbstractFront body;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        if (process instanceof AbstractFront) {
            body = ((AbstractFront) process);
        }
    }

    private void run(ProcessUnit processUnit, Process<?> body) {
        processUnit.add(empty);
        body.run(processUnit);
        processUnit.popPeek();
    }

    private boolean condition(ProcessUnit processUnit) {
        run(processUnit, body);
        return body.get() instanceof True;
    }

    @Override
    public void run(ProcessUnit processUnit) {
        while ((body.getReturn() == null && processUnit.getReturner() == null) && condition(processUnit)) {
            run(processUnit, body.getProcess());
        }
    }

    @Override
    public WhileImpl get() {
        return this;
    }
}
