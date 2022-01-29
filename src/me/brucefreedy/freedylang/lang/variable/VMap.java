package me.brucefreedy.freedylang.lang.variable;

import me.brucefreedy.freedylang.lang.abst.Null;

import java.util.HashMap;

public class VMap extends SimpleVar<HashMap<String, Object>> {
    public VMap(HashMap<String, Object> object) {
        super(object);
        register("put", voidFunc(o -> {
            Object value = o.get(1);
            if (value instanceof Null) object.remove(o.get(0).toString());
            else object.put(o.get(0).toString(), value);
        }));
        register("get", func(o -> object.getOrDefault(o.first().toString(), new Null())));
    }
}
