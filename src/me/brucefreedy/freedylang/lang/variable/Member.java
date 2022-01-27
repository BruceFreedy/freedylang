package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.StealerImpl;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;

@Processable(alias = ".", regex = true)
public class Member extends StealerImpl<Object> {

    Object result;
    boolean running;

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
//        parseUnit.steal(p -> process = p, () -> process);
        parseUnit.add(this);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (running) return;
        running = true;
        a.run(processUnit);
        if (a instanceof AbstractVar) {
            processUnit.getVariableRegister().add(((ScopeSupplier) a).getScope());
            process.run(processUnit);
            result = process.get();
            processUnit.getVariableRegister().popPeek();
        }
        running = false;
    }

    @Override
    public Object get() {
        if (result == null) return new Null();
        return result;
    }

    @Override
    public void setProcess(Process<?> process) {
        super.setProcess(process);
    }

    @Override
    public String toString() {
        return get().toString();
    }

}
