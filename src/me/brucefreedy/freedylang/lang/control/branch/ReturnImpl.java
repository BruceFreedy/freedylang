package me.brucefreedy.freedylang.lang.control.branch;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.abst.Returner;

@Processable(alias = "return")
public class ReturnImpl extends ProcessImpl<Object> implements Returner {

    Process<?> result;
    Object resultObj = new Null();

    @Override
    public void parse(ParseUnit parseUnit) {
        super.parse(parseUnit);
        parseUnit.steal(p -> process = p, () -> process);
        result = process;
    }

    @Override
    public void run(ProcessUnit processUnit) {
        result.run(processUnit);
        resultObj = result.get();
        processUnit.setReturner(this);
    }

    @Override
    public Object get() {
        return resultObj;
    }

    @Override
    public Object getReturn() {
        return resultObj;
    }
}
