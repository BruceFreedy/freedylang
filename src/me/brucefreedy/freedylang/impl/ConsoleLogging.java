package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.VoidFunction;

@Processable(alias = {"log"})
public class ConsoleLogging extends VoidFunction {

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        parseUnit.popPeek(stealer -> {
            stealer.setProcess(process);
            process = stealer;
        });
    }

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
        System.out.println(process.toString());
    }

}
