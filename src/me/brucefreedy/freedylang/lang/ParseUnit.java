package me.brucefreedy.freedylang.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.abst.Stealer;
import me.brucefreedy.freedylang.lang.variable.VariableImpl;
import me.brucefreedy.freedylang.registry.ProcessRegister;

import java.util.function.Consumer;

/**
 * just kinda unit that help to parse
 */
@AllArgsConstructor
public class ParseUnit extends List<Stealer<?>> {
    
    @Getter
    final List<List<Process<?>>> declaration = new List<>();

    @Getter
    ProcessRegister processRegister;

    @Getter
    @Setter
    private String source;

    @Override
    public Stealer<?> popPeek() {
        return super.popPeek();
    }

    @Override
    public boolean popPeek(Consumer<Stealer<?>> consumer) {
        return super.popPeek(consumer);
    }
}
