package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;

/**
 * just like placeHolder aka. replaceToken
 */
public abstract class Text extends AbstractVar<String> implements Process<String>, Stacker<String> {

    Process<?> process;

    public Text() {
        super("");
    }

    public Text(String object) {
        super(object);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        process.run(processUnit);
    }

    @Override
    public Process<?> getProcess() {
        return process;
    }

    @Override
    public String get() {
        return object;
    }

    @Override
    public String toString() {
        return object;
    }

}
