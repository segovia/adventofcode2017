import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

class Utils {

    static List<String> getInputsFromFiles(Class<?> clazz) throws IOException {
        return getInputsFromFiles(clazz.getSimpleName());
    }

    static List<String> getInputsFromFiles(String name) throws IOException {
        return Files
                .list(Paths.get("resources"))
                .filter(p -> p.getFileName().toString().startsWith(name))
                .sorted()
                .map(Utils::getFileAsString)
                .collect(toList());
    }

    private static String getFileAsString(Path path) {
        try {
            byte[] encoded = Files.readAllBytes(path);
            return new String(encoded, UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
