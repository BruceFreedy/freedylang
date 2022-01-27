package me.brucefreedy.freedylang.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.abst.Stealer;
import me.brucefreedy.freedylang.lang.abst.Taker;
import me.brucefreedy.freedylang.registry.ProcessRegister;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * just kinda unit that help to parse
 */
@RequiredArgsConstructor
public class ParseUnit extends List<Stealer<?>> {

    @Getter
    final List<List<Process<?>>> declaration = new List<>();

    @Getter
    @Setter
    Process<?> next;

    @NonNull
    @Getter
    ProcessRegister processRegister;

    @NonNull
    @Getter
    @Setter
    private String source;

    @Override
    public Stealer<?> popPeek() {
        Stealer<?> stealer = super.popPeek();
        if (stealer instanceof Taker) return popPeek();
        else return stealer;
    }

    @Override
    public boolean popPeek(Consumer<Stealer<?>> consumer) {
        return super.popPeek(consumer);
    }

    public boolean steal(Consumer<Process<?>> setter, Supplier<Process<?>> getter) {
        AtomicBoolean taken = new AtomicBoolean();
        AtomicBoolean value = new AtomicBoolean();
        boolean result = popPeek(stealer -> {
            stealer.setProcess(getter.get());
            if (stealer instanceof Taker) {
                taken.set(true);
                value.set(steal(setter, getter));
            } else setter.accept(stealer);
        });
        if (value.get()) return taken.get();
        return result;
    }

}
