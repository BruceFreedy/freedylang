package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.*;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.bool.False;
import me.brucefreedy.freedylang.lang.variable.bool.True;

import java.util.function.Supplier;

@Processable(alias = "if")
public class IfImpl extends ProcessImpl<Bool> {

    Bool result;
    AbstractFront body;  //condition body
    Process<?> runBody;  //'true' body
    Else anElse;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        if (process instanceof AbstractFront) {
            body = ((AbstractFront) process);
            runBody = body.getProcess();
            if (runBody instanceof Breaker) {
                runBody = Process.parsing(parseUnit);
            }
            if (runBody instanceof Stacker) {
                Process<?> stacker = ((Stacker<?>) runBody).getProcess();
                if (stacker instanceof Else) {
                    anElse = ((Else) stacker);
                }
            }
            super.parse(parseUnit);
            if (process instanceof Else) {
                anElse = ((Else) process);
            }
        }
    }

    @Override
    public void run(ProcessUnit processUnit) {
        body.getProcesses().forEach(p -> p.run(processUnit));
        run(body.getProcesses().stream().map(Supplier::get).allMatch(o -> o instanceof True), processUnit);
        super.run(processUnit);
    }

    public void run(boolean result, ProcessUnit processUnit) {
        if (anElse != null) anElse.setAvailable(!result);
        if (result && runBody != null) runBody.run(processUnit);
        else if (anElse != null) anElse.run(processUnit);
    }

    @Override
    public Bool get() {
        if (result == null) return new False();
        return result;
    }
}
