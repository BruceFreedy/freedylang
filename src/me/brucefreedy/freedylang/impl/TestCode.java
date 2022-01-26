package me.brucefreedy.freedylang.impl;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.freedylang.lang.variable.text.SimpleText;

@Processable(alias = "testcode")
public class TestCode extends ProcessImpl<Object> {

    String result = "not tested";

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        super.run(processUnit);
//        System.out.println(processUnit.getVariableRegister());

        result = ((process instanceof VariableImpl ?
                        process.get().getClass().getSimpleName()
                        : process.getClass().getSimpleName())
                        + "=" + process.get());
    }

    @Override
    public SimpleText get() {
        return new SimpleText(result);
    }
}
