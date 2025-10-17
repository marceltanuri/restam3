package io.github.marceltanuri.frameworks.restam3;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Utility class for printing a banner.
 *
 * @author Marcel Tanuri
 */
public class Banner {

    private Banner() {
    }

    /**
     * Prints the banner.
     */
    public static void print() {
        String appName = "RESTam3";
        String version = getManifestValue("Implementation-Version", "dev");
        String inspiration = getManifestValue("Inspiration", null);

        String fullText = String.format("%s v%s", appName, version);
        String secondLine = inspiration != null ? "“" + inspiration + "”" : null;

        int terminalWidth = detectTerminalWidthOrDefault(80);
        printBoxed(fullText, secondLine, terminalWidth);
    }

    /**
     * Prints a boxed text.
     *
     * @param line1 the first line of text
     * @param line2 the second line of text
     * @param width the width of the box
     */
    private static void printBoxed(String line1, String line2, int width) {
        int paddingHorizontal = 4;
        int contentWidth = Math.max(line1.length(), line2 != null ? line2.length() : 0) + paddingHorizontal * 2;
        int boxWidth = Math.min(Math.max(contentWidth + 2, 10), width - 2);
        int leftMargin = Math.max((width - boxWidth) / 2, 0);

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BOLD = "\u001B[1m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_YELLOW = "\u001B[33m";

        String horizontal = repeat("═", boxWidth - 2);
        String top = repeat(" ", leftMargin) + "╔" + horizontal + "╗";
        String bottom = repeat(" ", leftMargin) + "╚" + horizontal + "╝";

        System.out.println();
        System.out.println(top);
        printCentered(line1, boxWidth, leftMargin, ANSI_BOLD + ANSI_CYAN, ANSI_RESET);
        if (line2 != null) {
            printCentered(line2, boxWidth, leftMargin, ANSI_YELLOW, ANSI_RESET);
        }
        System.out.println(bottom);
        System.out.println();
    }

    /**
     * Prints a centered text.
     *
     * @param text       the text to print
     * @param boxWidth   the width of the box
     * @param leftMargin the left margin
     * @param prefix     the prefix to add to the text
     * @param suffix     the suffix to add to the text
     */
    private static void printCentered(String text, int boxWidth, int leftMargin, String prefix, String suffix) {
        int inner = boxWidth - 2;
        int start = (inner - text.length()) / 2;
        System.out.println(
                repeat(" ", leftMargin) + "║" +
                        repeat(" ", Math.max(start, 0)) +
                        prefix + text + suffix +
                        repeat(" ", inner - start - text.length()) +
                        "║"
        );
    }

    /**
     * Gets a value from the manifest file.
     *
     * @param key          the key of the value
     * @param defaultValue the default value to return if the key is not found
     * @return the value from the manifest file or the default value
     */
    private static String getManifestValue(String key, String defaultValue) {
        try {
            URL resource = Banner.class.getClassLoader().getResource("META-INF/MANIFEST.MF");
            if (resource != null) {
                try (var stream = resource.openStream()) {
                    Manifest manifest = new Manifest(stream);
                    Attributes attrs = manifest.getMainAttributes();
                    String value = attrs.getValue(key);
                    if (value != null && !value.isBlank()) {
                        return value.replace("\"", "").trim();
                    }
                }
            }
        } catch (IOException ignored) {}
        return defaultValue;
    }

    /**
     * Detects the terminal width or returns a default value.
     *
     * @param fallback the fallback value
     * @return the terminal width or the fallback value
     */
    private static int detectTerminalWidthOrDefault(int fallback) {
        try {
            String cols = System.getenv("COLUMNS");
            if (cols != null) return Integer.parseInt(cols);
        } catch (Exception ignored) {}
        return fallback;
    }

    /**
     * Repeats a string a specified number of times.
     *
     * @param s     the string to repeat
     * @param times the number of times to repeat the string
     * @return the repeated string
     */
    private static String repeat(String s, int times) {
        if (times <= 0) return "";
        return s.repeat(times);
    }
}
