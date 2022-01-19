package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.ProcessUnit;

public interface Method {
    Object run(ProcessUnit processUnit, List<?> params);
}
