package net.anvian.mineControlLauncher.util.checher;

import java.io.IOException;
import java.nio.file.Path;

public interface Checker {
    void check(Path dirPath) throws IOException;
}
