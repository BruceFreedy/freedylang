package me.brucefreedy.freedylang.lang.variable.number;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.variable.Member;

public class SimpleNumber extends Number {
    public SimpleNumber(java.lang.Number number) {
        setNumber(number);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
        if (process instanceof Member) {
            parseUnit.popPeek();
            process = ((Member) process).getProcess();
            try {
                if (process instanceof SimpleNumber) {
                    SimpleNumber simpleNumber = (SimpleNumber) this.process;
                    object = object.doubleValue() + Double.parseDouble("." + simpleNumber.object.intValue());
                    process = simpleNumber.getProcess();
                }
            } catch(Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
