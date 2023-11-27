package net.anvian.mineControlLauncher.util.os;

public class JavaVersion {
    public static final int VERSION_OFFSET = 44;

    private static final String JAVA_CLASS_VERSION = "java.class.version";

    private static final String JAVA_VM_SPEC_VERSION = "java.vm.specification.version";

    private static final int FALLBACK_VERSION = 8;

    private static int version = -1;

    public static int get() {
        if (version < 0) {
            String property = System.getProperty(JAVA_CLASS_VERSION, "");
            if (!property.isEmpty())
                return version = (int)(Float.parseFloat(property) - VERSION_OFFSET);
            property = System.getProperty(JAVA_VM_SPEC_VERSION, "");
            if (property.contains("."))
                return version = (int)Float.parseFloat(property.substring(property.indexOf('.') + 1));
            if (!property.isEmpty())
                return version = Integer.parseInt(property);
            return FALLBACK_VERSION;
        }
        return version;
    }
}
