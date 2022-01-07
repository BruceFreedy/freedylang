package me.brucefreedy.freedylang.lang.quotation;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.abst.Text;

/**
 * mark up string that wrapped quotation
 */
public abstract class AbstractQuotation extends Text {

    protected abstract String getQuotation();

    @Override
    public void parse(ParseUnit parseUnit) {
        String source = parseUnit.getSource();
        String quotation = getQuotation();
        int index = source.indexOf(quotation);
        if (index == -1) stringValue = "";
        else {
            stringValue = source.substring(0, index);
            parseUnit.setSource(source.substring(Math.min(source.length(), index + quotation.length())));
        }
        super.parse(parseUnit);
    }

}
