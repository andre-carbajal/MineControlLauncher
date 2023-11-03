package net.anvian.util;

import jakarta.validation.constraints.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils {
    @NotNull
    public static String getStackTrace(@NotNull Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
