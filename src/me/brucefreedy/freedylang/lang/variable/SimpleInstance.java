package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.abst.Empty;
import me.brucefreedy.freedylang.lang.body.AbstractFront;

@Processable(alias = "new")
public class SimpleInstance extends AbstractVar<AbstractFront>
        implements Process<SimpleInstance>, Empty<SimpleInstance> {

    Process<?> process;

    public SimpleInstance() {
        super(null);
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        if (process instanceof VariableImpl) {
            VariableRegister register = processUnit.getVariableRegister();
            Object variable = register.getVariable(((VariableImpl) process).nodes);
            if (variable instanceof VariableImpl) {
                object = ((VariableImpl) variable).body;
                object.setScopeSupplier(this::getScope);
                object.run(processUnit);
            }
        }
    }

    @Override
    public SimpleInstance get() {
        return this;
    }

}
