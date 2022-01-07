package me.brucefreedy.freedylang.lang;

import me.brucefreedy.freedylang.lang.abst.EmptyImpl;

/**
 * skipper class skip self process while parsing, must not be run
 */
@Processable(alias = {
        " ",
        "\t",
}, regex = true)
public class Skipper extends EmptyImpl<Skipper> {
    @Override
    public Skipper get() {
        return this;
    }
}
