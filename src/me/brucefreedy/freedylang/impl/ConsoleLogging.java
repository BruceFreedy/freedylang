package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.VoidFunction;

@Processable(alias = {"log"})
public class ConsoleLogging extends VoidFunction {

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
        System.out.println(process.toString());
    }

}
