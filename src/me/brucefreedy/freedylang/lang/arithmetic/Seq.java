package me.brucefreedy.freedylang.lang.arithmetic;

public enum Seq {
    LOW, HIGH, HIGHEST;

    public boolean compare(Seq seq) {
        return this.ordinal() >= seq.ordinal();
    }
}
