package me.brucefreedy.freedylang.lang.control.conditional;

import me.brucefreedy.freedylang.lang.abst.BoolGetter;
import me.brucefreedy.freedylang.lang.abst.Stacker;
import me.brucefreedy.freedylang.lang.variable.bool.Bool;

public interface Condition extends BoolGetter<Bool>, Stacker<Bool> { }
