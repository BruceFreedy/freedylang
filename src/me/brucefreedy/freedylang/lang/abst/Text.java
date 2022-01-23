package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import me.brucefreedy.freedylang.lang.variable.SimpleVar;

/**
 * just like placeHolder aka. replaceToken
 */
public abstract class Text extends AbstractVar<String> implements Process<Text>, Stacker<Text> {

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
    public Text get() {
        return this;
    }

    @Override
    public String toString() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;
        return toString().equals(o);
    }

}
