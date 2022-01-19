package me.brucefreedy.freedylang.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

@Getter
@AllArgsConstructor
public class Cover extends EmptyImpl<Object> {
    Object object;
    @Override
    public Object get() {
        return object;
    }
    @Override
    public String toString() {
        return object.toString();
    }
}
