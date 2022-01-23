package me.brucefreedy.freedylang.lang.variable.number;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.variable.Member;

public class AbstractNumber extends Number {
    public AbstractNumber(java.lang.Number number) {
        setNumber(number);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
        if (process instanceof Member) {
            process = Process.parsing(parseUnit);
            try {
                if (process instanceof AbstractNumber) {
                    AbstractNumber simpleNumber = (AbstractNumber) this.process;
                    object = object.doubleValue() + Double.parseDouble("." + simpleNumber.object.intValue());
                    process = simpleNumber.getProcess();
                }
            } catch(Exception ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
