package net.anvian.mineControlLauncher.util.os;

import java.util.Locale;

public class OsChecker {
    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    public enum ArchType {
        amd64, aarch64, x86_64 ,Other
    }

    protected static OSType detectedOS;
    protected static ArchType detectedArch;

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }

    public static ArchType getArchitectureType() {
        if (detectedArch == null) {
            String arch = System.getProperty("os.arch");
            switch (arch) {
                case "amd64" -> detectedArch = ArchType.amd64;
                case "aarch64" -> detectedArch = ArchType.aarch64;
                case "x86_64" -> detectedArch = ArchType.x86_64;
                default -> detectedArch = ArchType.Other;
            }
        }
        return detectedArch;
    }
}