package me.brucefreedy.freedylang.lang.abst;

/**
 * just like placeHolder aka. replaceToken
 */
public abstract class Text extends ProcessImpl<String> {

    protected String stringValue = "";

    @Override
    public String get() {
        return stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
