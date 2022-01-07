package me.brucefreedy.freedylang.lang;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.brucefreedy.common.List;
import me.brucefreedy.freedylang.lang.abst.AfterRun;
import me.brucefreedy.freedylang.lang.abst.Returner;
import me.brucefreedy.freedylang.lang.variable.VariableRegister;

@RequiredArgsConstructor
public class ProcessUnit extends List<AfterRun> {

    @Getter
    private final VariableRegister variableRegister;

    @Getter
    @Setter
    private Returner returner = null;

}
