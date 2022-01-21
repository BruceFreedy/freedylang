package me.brucefreedy.freedylang.lang.variable;

import lombok.Getter;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.Text;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.scope.ScopeSupplier;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.freedylang.lang.variable.text.SimpleText;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AbstractVar<T> extends Null implements ScopeSupplier {
    protected final Scope scope = new Scope();
    @Getter
    protected T object;

    public AbstractVar(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        if (object == null) return super.toString();
        return object.toString();
    }
    @Override
    public Scope getScope() {
        return scope;
    }

    public Scope register(String name, Object var) {
        Scope scope = getScope();
        scope.register(name, var);
        return scope;
    }

    protected Method stringValue(Consumer<String> setter, Supplier<String> getter) {
        return method(o -> o instanceof Text, String.class, setter, getter::get);
    }

    protected Method doubleValue(Consumer<Double> setter, Supplier<Double> getter) {
        return number(setter, getter, java.lang.Number::doubleValue);
    }

    protected Method floatValue(Consumer<Float> setter, Supplier<Float> getter) {
        return number(setter, getter, java.lang.Number::floatValue);
    }

    protected Method longValue(Consumer<Long> setter, Supplier<Long> getter) {
        return number(setter, getter, java.lang.Number::longValue);
    }

    protected Method shortValue(Consumer<Short> setter, Supplier<Short> getter) {
        return number(setter, getter, java.lang.Number::shortValue);
    }

    protected Method intValue(Consumer<Integer> setter, Supplier<Integer> getter) {
        return number(setter, getter, java.lang.Number::intValue);
    }

    protected <TYPE extends java.lang.Number> Method number(Consumer<TYPE> setter, Supplier<TYPE> getter, Function<java.lang.Number, TYPE> box) {
        return number(i -> setter.accept(box.apply(i)), getter::get);
    }

    protected Method number(Consumer<java.lang.Number> setter, Supplier<java.lang.Number> getter) {
        return method(o -> o instanceof Number, java.lang.Number.class, setter, () -> new SimpleNumber(getter.get()));
    }

    protected <TYPE, RETURN> Method method(Consumer<RETURN> setter, Supplier<RETURN> getter,
                                           Function<TYPE, RETURN> function, Function<List<?>, TYPE> func2) {
        return (processUnit, list) -> {
            TYPE apply = func2.apply(list);
            return method(apply == null ? null : function.apply(apply), setter, getter);
        };
    }

    private <TYPE> TYPE method(TYPE t, Consumer<TYPE> setter, Supplier<TYPE> getter) {
        if (t != null) setter.accept(t);
        return getter.get();
    }

    protected java.lang.Number number(List<?> t) {
        return t.first() instanceof Number ? ((Number) t.first()).getNumber() : null;
    }

    protected <TYPE> Method method(CastCheck<AbstractVar<TYPE>> check, Class<TYPE> typeClass,
                                   Consumer<TYPE> setter, Supplier<Object> getter) {
        return (unit, params) -> {
            AbstractVar<TYPE> var = check.cast(params);
            if (var != null) setter.accept(var.getObject());
            return getter.get();
        };
    }

}
