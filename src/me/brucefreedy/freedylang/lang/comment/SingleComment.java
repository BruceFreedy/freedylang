package me.brucefreedy.freedylang.lang.comment;

import me.brucefreedy.freedylang.lang.Processable;

@Processable(alias = {"//", "#"}, regex = true)
public class SingleComment extends AbstractComment {
    @Override
    public String getEndSeq() {
        return "\n";
    }
}
