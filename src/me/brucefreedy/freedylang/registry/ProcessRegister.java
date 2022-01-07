package me.brucefreedy.freedylang.registry;

import lombok.Getter;
import me.brucefreedy.common.RegistryImpl;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.Processable;
import me.brucefreedy.freedylang.lang.regex.Regex;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;

import java.util.Arrays;
import java.util.function.Supplier;

public class ProcessRegister extends RegistryImpl<String, Supplier<Process<?>>> {

    @Getter
    private final VariableRegister variableRegister = new VariableRegister();

    public void register() {
        Arrays.stream(ProcessDef.values()).map(ProcessDef::getProcessSupplier).forEach(this::register);
    }

    public void register(Supplier<Process<?>> process) {
        try {
            Processable annotation = process.get().getClass().getAnnotation(Processable.class);
            if (annotation == null) return;
            for (String alias : annotation.alias()) {
                if (alias == null || alias.isEmpty()) continue;
                if (annotation.regex()) Regex.addRegex(alias);
                register(alias.toLowerCase(), process);
            }
        } catch (Exception ignored) {}
    }

}
