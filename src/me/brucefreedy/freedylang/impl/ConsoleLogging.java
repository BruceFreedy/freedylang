package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.VoidFunction;

@Processable(alias = {"log"})
public class ConsoleLogging extends VoidFunction {

    Process<?> target;

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        parseUnit.steal(p -> process = p, () -> process);
        target = process;
        super.parse(parseUnit);
        parseUnit.steal(p -> process = p, () -> process);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        target.run(processUnit);
        System.out.println(target.toString());
        super.run(processUnit);
    }

}
