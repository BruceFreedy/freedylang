package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.scope.Scope;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;

public interface Method {
    Object run(ProcessUnit processUnit, List<?> params);
}
