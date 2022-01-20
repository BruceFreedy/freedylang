package me.brucefreedy.freedylang.lang.control.iteration;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.ProcessImpl;
import me.brucefreedy.freedylang.lang.body.AbstractFront;
import me.brucefreedy.freedylang.lang.variable.bool.True;

import java.util.function.Supplier;

@Processable(alias = "for")
public class ForImpl extends ProcessImpl<ForImpl> {

    @Override
    public void run(ProcessUnit processUnit) {
        if (process instanceof AbstractFront) {
            AbstractFront body = (AbstractFront) this.process;
            List<Process<?>> processes = body.getProcesses();
            if (processes.size() != 3) return;
            processes.get(0).run(processUnit);
            Process<?> cond = processes.get(1);
            Process<?> incre = processes.get(2);
            Supplier<Boolean> runnable = () -> {
                cond.run(processUnit);
                return cond.get() instanceof True;
            };
            while (runnable.get()) {
                body.getProcess().run(processUnit);
                incre.run(processUnit);
            }
        }
    }

    @Override
    public ForImpl get() {
        return this;
    }
}

