package me.brucefreedy.freedylang.registry;

import me.brucefreedy.freedylang.lang.ParseUnit;
import me.brucefreedy.freedylang.lang.Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProcessUtils {

    private ProcessUtils() {}

    public static Process<?> parsing(ProcessRegister processRegister, String fileName, String source) {
        return Process.parsing(new ParseUnit(processRegister, "{class " + fileName + " {" + source + "}}"));
    }

    public static Process<?> parsing(ProcessRegister processRegister, Path path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsing(processRegister, withoutExtension(path.toFile().getName()), stringBuilder.toString());
    }

    public static String withoutExtension(String name) {
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1) return name;
        return name.substring(0, dotIndex);
    }

}
