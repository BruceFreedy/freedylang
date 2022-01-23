package me.brucefreedy.freedylang.lang.variable.text;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.abst.ListProcess;
import me.brucefreedy.freedylang.lang.abst.Method;
import me.brucefreedy.freedylang.lang.abst.Null;
import me.brucefreedy.freedylang.lang.abst.Text;
import me.brucefreedy.freedylang.lang.variable.number.AbstractNumber;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AbstractText extends Text {
    public AbstractText(String object) {
        super(object);
        register("length", (Method) (unit, params) -> new AbstractNumber(object.length()));
        register("split", (Method) (unit, params) -> {
            try {
                return new ListProcess(new List<>(Arrays.stream(
                        object.split(params.first().toString())).map(AbstractText::new).collect(Collectors.toList())));
            } catch (Exception ignored) {
                return new Null();
            }
        });

    }
}
