package me.brucefreedy.freedylang.lang.quotation;

import me.brucefreedy.freedylang.lang.Processable;

@Processable(alias = "'", regex = true)
public class SingleQuotation extends AbstractQuotation {
    @Override
    protected String getQuotation() {
        return "'";
    }
}
