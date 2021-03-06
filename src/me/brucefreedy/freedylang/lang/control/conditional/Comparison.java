package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.ProcessUnit;
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
        Bool result;
        @Override
        public void run(ProcessUnit processUnit) {
            super.run(processUnit);
            Object o = process.get();
            if (o instanceof Bool) {
                result = Bool.get(!((Bool) o).getValue());
            }
        }

        @Override
        public Bool get() {
            if (result == null) return Bool.get(true);
            return result;
        }

        @Override
        public String toString() {
            return get().toString();
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

    @Processable(alias = "!=", regex = true)
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
