package adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

public class Utils {

    public static List<String> getInputsFromFiles(Class<?> clazz) throws IOException {
        return getInputsFromFiles(getLastPackage(clazz), clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 5));
    }

    private static String getLastPackage(Class<?> clazz) {
        String fullName = clazz.getCanonicalName();
        String justPackage = fullName.substring(0, fullName.lastIndexOf('.'));
        return justPackage.substring(justPackage.lastIndexOf('.') + 1, justPackage.length());
    }

    public static List<String> getInputsFromFiles(String folder, Class<?> clazz) throws IOException {
        return getInputsFromFiles(folder, clazz.getSimpleName().substring(0, clazz.getSimpleName().length() - 5));
    }

    public static List<String> getInputsFromFiles(String folder, String name) throws IOException {
        return Files
                .list(Paths.get("src/test/resources/" + folder))
                .filter(p -> p.getFileName().toString().startsWith(name))
                .sorted()
                .map(Utils::getFileAsString)
                .collect(toList());
    }

    public static String getFileAsString(Path path) {
        try {
            byte[] encoded = Files.readAllBytes(path);
            return new String(encoded, UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
