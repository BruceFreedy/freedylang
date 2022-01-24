package me.brucefreedy.freedylang.lang.body;

import me.brucefreedy.freedylang.lang.Breaker;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.abst.*;
import me.brucefreedy.freedylang.lang.scope.Scope;

import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractFront extends ListProcess
        implements Empty<Object>, AfterRun, Stacker<Object>, Predicate<Process<?>>, Returner {

    protected Process<?> footProcess = new Breaker();
    protected Supplier<Scope> scopeSupplier = Scope::new;
    protected Runnable beforeRun = () -> {};
    protected Object result;

    @Override
    public Object getReturn() {
        return result;
    }

    public Supplier<Scope> getScopeSupplier() {
        return scopeSupplier;
    }

    public void setScopeSupplier(Supplier<Scope> scopeSupplier) {
        this.scopeSupplier = scopeSupplier;
    }

    public void setBeforeRun(Runnable beforeRun) {
        this.beforeRun = beforeRun;
    }

    @Override
    public Process<?> getProcess() {
        return footProcess;
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        while (!parseUnit.getSource().isEmpty()) {
            Process<?> process = Process.parsing(parseUnit);
            Process<?> peek = null;
            boolean popped = parseUnit.popPeek(stealer -> {
                getProcesses().add(stealer);
                stealer.setProcess(process);
            });
            if (process instanceof Stacker) {
                peek = ((Stacker<?>) process).getPeek();
                if (!popped && peek instanceof Empty) getProcesses().add(process);
            }
            if (test(process) || test(peek)) {
                if (!parseUnit.getSource().isEmpty()) {
                    footProcess = Process.parsing(parseUnit);
                }
                break;
            }
        }

    }

    @Override
    public void run(ProcessUnit processUnit) {
        Scope scope = getScopeSupplier().get();
        boolean isMethodScope = scope.getType() == Scope.ScopeType.METHOD;
        processUnit.getVariableRegister().add(scope);
        beforeRun.run();
        for (Process<?> process : getProcesses()) {
            process.run(processUnit);
            if (processUnit.getReturner() != null) {
                result = processUnit.getReturner().getReturn();
                processUnit.setReturner(null);
                break;
            }
        }
        processUnit.getVariableRegister().remove(scope);
//        processUnit.popPeek(afterRun -> afterRun.afterRun(processUnit));
//        processUnit.add(this);
    }

    @Override
    public void afterRun(ProcessUnit processUnit) {
        if (footProcess != null) footProcess.run(processUnit);
    }

    @Override
    public Object get() {
        if (result != null) return result;
        if (!getProcesses().isEmpty()) return getProcesses().first().get();
        return new Null();
    }

    @Override
    public String toString() {
        if (result != null) return result.toString();
        return super.toString();
    }
}
