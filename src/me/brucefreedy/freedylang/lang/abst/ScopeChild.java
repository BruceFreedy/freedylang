package me.brucefreedy.freedylang.lang.abst;

import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.lang.scope.Scope;

public interface ScopeChild {
    void setParent(ProcessUnit processUnit, Scope scope);
}
