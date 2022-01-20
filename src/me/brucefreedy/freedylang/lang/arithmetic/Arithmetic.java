package me.brucefreedy.freedylang.lang.arithmetic;

import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.variable.number.Number;

public interface Arithmetic extends Process<Object> {
    java.lang.Number operate(java.lang.Number a, java.lang.Number b);
    Seq getSeq();


    abstract class LowSeqArithmetic extends ArithmeticImpl {
        @Override
        public Seq getSeq() {
            return Seq.LOW;
        }
    }

    abstract class HighSeqArithmetic extends ArithmeticImpl {
        @Override
        public Seq getSeq() {
            return Seq.HIGH;
        }
    }

    @Processable(alias = "+", regex = true)
    class Addition extends LowSeqArithmetic {
        @Override
        public java.lang.Number operate(java.lang.Number a, java.lang.Number b) {
            return a.doubleValue() + b.doubleValue();
        }
    }

    @Processable(alias = "*", regex = true)
    class Multiply extends HighSeqArithmetic {
        @Override
        public java.lang.Number operate(java.lang.Number a, java.lang.Number b) {
            return a.doubleValue() * b.doubleValue();
        }
    }

    @Processable(alias = "**", regex = true)
    class Squared extends HighSeqArithmetic {
        @Override
        public java.lang.Number operate(java.lang.Number a, java.lang.Number b) {
            return Math.pow(a.doubleValue(), b.doubleValue());
        }
        @Override
        public Seq getSeq() {
            return Seq.HIGHEST;
        }
    }

    @Processable(alias = "/", regex = true)
    class Divide extends HighSeqArithmetic {
        @Override
        public java.lang.Number operate(java.lang.Number a, java.lang.Number b) {
            return a.doubleValue() / b.doubleValue();
        }
    }

    @Processable(alias = "%", regex = true)
    class Remainder extends HighSeqArithmetic {
        @Override
        public java.lang.Number operate(java.lang.Number a, java.lang.Number b) {
            return a.doubleValue() % b.doubleValue();
        }
    }

    @Processable(alias = "-", regex = true)
    class Subtract extends LowSeqArithmetic {
        @Override
        public java.lang.Number operate(java.lang.Number a, java.lang.Number b) {
            return a.doubleValue() - b.doubleValue();
        }

        @Override
        protected void setNumber(java.lang.Number number) {
            if (disabled) super.setNumber(number.doubleValue() * -1);
            else super.setNumber(number);
        }
    }

}
