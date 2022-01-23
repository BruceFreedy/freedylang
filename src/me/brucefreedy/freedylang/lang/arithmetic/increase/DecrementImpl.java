package me.brucefreedy.freedylang.lang.arithmetic.increase;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

@Processable(alias = "--", regex = true)
public class DecrementImpl extends AbstractIncrease {
    @Override
    public Number increase(Number number) {
        return new SimpleNumber(number.getNumber().doubleValue() - 1);
    }
}
