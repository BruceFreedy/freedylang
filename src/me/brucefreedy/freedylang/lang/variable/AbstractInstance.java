package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Empty;
import me.brucefreedy.freedylang.lang.body.AbstractFront;

@Processable(alias = "new")
public class AbstractInstance extends AbstractVar<AbstractFront>
        implements Process<AbstractInstance>, Empty<AbstractInstance> {

    Process<?> process;

    public AbstractInstance() {
        super(null);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        VariableRegister register = processUnit.getVariableRegister();
        process.run(processUnit);
        if (process instanceof VariableImpl) {
            Object variable = process.get();
            if (variable instanceof VariableImpl) {
                object = ((VariableImpl) variable).body;
                object.setScopeSupplier(this::getScope);
                object.run(processUnit);
            }
        }
    }

    @Override
    public AbstractInstance get() {
        return this;
    }

}
