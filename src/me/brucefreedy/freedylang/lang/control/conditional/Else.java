package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.Breaker;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.abst.Stacker;

@Processable(alias = "else")
public class Else extends ProcessImpl<Else> {

    boolean available;
    IfImpl anIf;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        if (process instanceof Breaker) super.parse(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (anIf != null) available = !anIf.result.getValue();
        if (available) {
            this.available = false;
            super.run(processUnit);
        } else if (process instanceof Stacker) {
            ((Stacker<?>) process).getProcess().run(processUnit);
        }
    }

    @Override
    public Else get() {
        return this;
    }

}
