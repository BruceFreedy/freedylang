import me.brucefreedy.freedylang.lang.Process;
import me.brucefreedy.freedylang.lang.ProcessUnit;
import me.brucefreedy.freedylang.registry.ProcessRegister;
import me.brucefreedy.freedylang.registry.ProcessUtils;

import java.nio.file.Paths;

public class Test {

    public static void main(String[] args) {
        ProcessRegister processRegister = new ProcessRegister();
        processRegister.register();
        Process<?> process = ProcessUtils.parsing(processRegister, Paths.get(
                "C:\\Users\\Bruce\\IdeaProjects\\freedylang\\freedy.java"
//                "C:\\Users\\admin\\IdeaProjects\\freedylang\\freedy.java"
        ));
        System.out.println("----------------------");
        process.run(new ProcessUnit(processRegister.getVariableRegister()));
        System.out.println("----------------------");
    }

}
