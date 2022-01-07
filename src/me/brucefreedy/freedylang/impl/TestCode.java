package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;

@Processable(alias = "test")
public class TestCode extends ProcessImpl<TestCode> {

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
        System.out.println(processUnit.getVariableRegister());
//        System.out.println(process.getClass().getSimpleName() + "=" + process);
    }

    @Override
    public TestCode get() {
        return this;
    }
}
