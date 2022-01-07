package me.brucefreedy.freedylang.lang.comment;

import me.brucefreedy.freedylang.lang.Processable;

@Processable(alias = "/*", regex = true)
public class MultiComment extends AbstractComment {
    @Override
    public String getEndSeq() {
        return "*/";
    }
}