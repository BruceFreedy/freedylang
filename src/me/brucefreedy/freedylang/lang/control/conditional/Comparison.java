package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;

import java.util.Objects;

public interface Comparison {
    boolean compare(Object a, Object b);

    abstract class SimpleComparator extends Comparator {
        public abstract boolean compare(int value);
        @Override
        public final boolean compare(Object a, Object b) {
            if (a instanceof Comparable) {
                return compare(((Comparable) a).compare(b));
            } else return false;
        }
    }

    abstract class BoolComparator extends ProcessImpl<Bool> {
        final Bool bool;

        public BoolComparator(Bool bool) {
            this.bool = bool;
        }

        @Override
        public Bool get() {
            return bool;
        }

        @Override
        public String toString() {
            return bool.toString();
        }
    }

    @Processable(alias = "!", regex = true)
    class ReverseBool extends ProcessImpl<Bool> {
        @Override
        public Bool get() {
            Object o = process.get();
            if (o instanceof Bool) return Bool.get(!((Bool) o).getValue());
            else return Bool.get(false);
        }
    }

    @Processable(alias = "true")
    class True extends BoolComparator {
        public True() {
            super(Bool.get(true));
        }
    }

    @Processable(alias = "false")
    class False extends BoolComparator {
        public False() {
            super(Bool.get(false));
        }
    }

    @Processable(alias = "==", regex = true)
    class Equals extends Comparator {
        @Override
        public boolean compare(Object a, Object b) {
            return Objects.equals(a, b);
        }
    }

    @Processable(alias = {"=!", "!="}, regex = true)
    class NotEquals extends Comparator {
        @Override
        public boolean compare(Object a, Object b) {
            return !Objects.equals(a, b);
        }
    }

    @Processable(alias = {"<"}, regex = true)
    class Bigger extends SimpleComparator {
        @Override
        public boolean compare(int value) {
            return value < 0;
        }
    }

    @Processable(alias = {"<="}, regex = true)
    class EqBigger extends SimpleComparator {
        @Override
        public boolean compare(int value) {
            return value <= 0;
        }
    }

    @Processable(alias = {">"}, regex = true)
    class Smaller extends SimpleComparator {
        @Override
        public boolean compare(int value) {
            return value > 0;
        }
    }

    @Processable(alias = {">="}, regex = true)
    class EqSmaller extends SimpleComparator {
        @Override
        public boolean compare(int value) {
            return value >= 0;
        }
    }

}
