package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;
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
        parseUnit.add(this);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (running) return;
        running = true;
        if (a != null) {
            a.run(processUnit);
            if (a instanceof ScopeSupplier) {
                processUnit.getVariableRegister().add(((ScopeSupplier) a).getScope());
                process.run(processUnit);
                result = process.get();
                processUnit.getVariableRegister().popPeek();
            }
        }
        running = false;
    }

    @Override
    public Object get() {
        return result;
    }

    @Override
    public void setProcess(Process<?> process) {
        super.setProcess(process);
    }
}
