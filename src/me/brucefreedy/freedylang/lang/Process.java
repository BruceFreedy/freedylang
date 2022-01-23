package me.brucefreedy.freedylang.lang;

import me.brucefreedy.freedylang.lang.abst.Vanisher;
import me.brucefreedy.freedylang.lang.regex.Regex;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;

import java.util.function.Supplier;

public interface Process<R> extends Supplier<R> {

    default void debug() {
        System.out.println(getClass().getSimpleName());
    }

    void parse(ParseUnit parseUnit);

    void run(ProcessUnit processUnit);

    /**
     * parse and return parse-finished process instance
     */
    static Process<?> parsing(ParseUnit parseUnit) {
        String source = parseUnit.getSource();
        if (source.isEmpty()) return new Breaker();
        Regex.RexResult rex = Regex.rex(source);
        if (rex.index == -1) {
            rex.index = source.length();
            rex.size = 0;
        }
        String processName = source.substring(0, rex.index);
        Process<?> process;
        Supplier<Process<?>> processSupplier = parseUnit.getProcessRegister().getRegistry(processName);
        java.lang.Number number = null;
        try {
            number = Double.parseDouble(processName);
        } catch(NumberFormatException ignored) {}
        if (number != null) {
            process = new SimpleNumber(number);
            parseUnit.setSource(source.substring(rex.index));
        } else if (processSupplier == null && !processName.isEmpty()) {
          process = new VariableImpl(processName);
            parseUnit.setSource(source.substring(rex.index));
        } else if (processSupplier == null) {
            String rexString = source.substring(rex.index, rex.index + rex.size);
            Supplier<Process<?>> rexProcessSupplier = parseUnit.getProcessRegister().getRegistry(rexString);
            parseUnit.setSource(source.substring(rex.index + rex.size));
            if (rexProcessSupplier == null) {
                process = null;
            } else
                process = rexProcessSupplier.get();
        } else {
            parseUnit.setSource(source.substring(rex.index));
            process = processSupplier.get();
        }
        if (process == null) return parsing(parseUnit);
//        process.debug();
        process.parse(parseUnit);
        if (process instanceof Vanisher && ((Vanisher<?>) process).vanish()) return parsing(parseUnit);
        if (process instanceof Skipper) return parsing(parseUnit);
        return process;
    }
}
