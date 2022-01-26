package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.variable.AbstractVar;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;
import me.brucefreedy.freedylang.lang.variable.number.SimpleNumber;
import me.brucefreedy.freedylang.lang.variable.number.Number;
import me.brucefreedy.freedylang.lang.variable.text.SimpleText;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * just like placeHolder aka. replaceToken
 */
public abstract class Text extends AbstractVar<String> implements Process<Text>, Stacker<Text> {

    Process<?> process;

    public Text(String string) {
        super(string);
        register("length", (Method) (unit, params) -> new SimpleNumber(object.length()));
        register("split", func(params -> new SimpleList(Arrays.stream(object.split(params.toString()))
                .map(SimpleText::new).collect(Collectors.toList()))));
        register("sub", (Method) (unit, params) -> {
            try {
                int start = ((Number) params.get(0)).getNumber().intValue();
                int end = ((Number) params.get(1)).getNumber().intValue();
                return new SimpleText(object.substring(start, end));
            } catch (Exception ignored) {}
            return new Null();
        });
        register("indexOf", func(params -> new SimpleNumber(object.indexOf(params.get(0).toString()))));
        register("charAt", func(params -> new SimpleText(object.charAt(((Number) params.get(0)).getNumber().intValue()) + "")));
        register("lowerCase", func(params -> new SimpleText(object.toLowerCase())));
        register("upperCase", func(params -> new SimpleText(object.toUpperCase())));
        register("startWith", func(params -> Bool.get(object.startsWith(params.get(0).toString()))));
        register("endWith", func(params -> Bool.get(object.endsWith(params.get(0).toString()))));
        register("replace", func(params -> new SimpleText(object.replaceAll(params.get(0).toString(), params.get(1).toString()))));
        register("equalsIgnoreCase", func(params -> Bool.get(object.equalsIgnoreCase(params.toString()))));
        register("equals", func(params -> Bool.get(object.equals(params.toString()))));
    }

    public Text() {
        this("");
    }

    @Override
    public void parse(ParseUnit parseUnit) {
        process = Process.parsing(parseUnit);
    }

    @Override
    public void run(ProcessUnit processUnit) {
        process.run(processUnit);
    }

    @Override
    public Process<?> getProcess() {
        return process;
    }

    @Override
    public Text get() {
        return this;
    }

    @Override
    public String toString() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;
        return toString().equals(o.toString());
    }

}
