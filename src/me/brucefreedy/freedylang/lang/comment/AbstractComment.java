package me.brucefreedy.freedylang.lang.comment;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Skipper;

public abstract class AbstractComment extends Skipper {

    public abstract String getEndSeq();

    @Override
    public void parse(ParseUnit parseUnit) {
        String source = parseUnit.getSource();
        int endIndex = source.indexOf(getEndSeq());
        if (endIndex == -1) endIndex = source.length();
        else endIndex += getEndSeq().length();
        parseUnit.setSource(source.substring(endIndex));
    }

}
