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
        process = Process.parsing(parseUnit);
        parseUnit.steal(p -> process = p, () -> process);
        if (process instanceof AbstractFront) {
            body = ((AbstractFront) process);
            process = new Breaker();
            runBody = body.getProcess();
            if (runBody instanceof Breaker) {
                runBody = Process.parsing(parseUnit);
                parseUnit.steal(p -> runBody = p, () -> runBody);
            }
            if (runBody instanceof Stacker) {
                process = ((Stacker<?>) runBody).getProcess();
            } else {
                process = Process.parsing(parseUnit);
                parseUnit.steal(p -> process = p, () -> process);
            }
            if (process instanceof Else) {
                (anElse = (Else) process).anIf = this;
                process = new Breaker();
            }
        }
    }

    @Override
    public void run(ProcessUnit processUnit) {
        body.run(processUnit);
        run(body.getProcesses().stream().map(Supplier::get).allMatch(o -> o instanceof True), processUnit);
        if (anElse != null) anElse.run(processUnit);
        process.run(processUnit);
    }

    public void run(boolean result, ProcessUnit processUnit) {
        this.result = Bool.get(result);
        if (result && runBody != null) runBody.run(processUnit);
    }

    @Override
    public Bool get() {
        if (result == null) return new False();
        return result;
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
