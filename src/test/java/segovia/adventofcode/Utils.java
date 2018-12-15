package segovia.adventofcode;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

public class Utils {

    public static List<String> getInputsFromFiles(Class<?> clazz) throws IOException {
        return getInputsFromFiles(getPackageFolderPath(clazz), clazz.getSimpleName().substring(0, 3));
    }

    private static String getPackageFolderPath(Class<?> clazz) {
        String fullName = clazz.getCanonicalName();
        String justPackage = fullName.substring(0, fullName.lastIndexOf('.'));
        return justPackage.replaceAll("\\.", "/");
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


    public static int[] toIntArray(String[] nums) {
        return Arrays.stream(nums).mapToInt(Integer::parseInt).toArray();
    }

    public static long[] toLongArray(String[] nums) {
        return Arrays.stream(nums).mapToLong(Long::parseLong).toArray();
    }

    public static void print(char[][] matrix) {
        for (char[] line : matrix) {
            for (char c : line) System.out.print(c);
            System.out.println();
        }
    }

    public static void print(int[][] matrix, int width) {
        for (int[] line : matrix) {
            for (int i : line) {
                System.out.print(String.format("%" + width + "d", i));
            }
            System.out.println();
        }
    }

    public static void print(long[][] matrix, int width) {
        for (long[] line : matrix) {
            for (long i : line) {
                System.out.print(String.format("%" + width + "d", i));
            }
            System.out.println();
        }
    }

    public static void print(boolean[][] matrix) {
        for (boolean[] line : matrix) {
            for (boolean b : line) System.out.print(b ? '#' : '.');
            System.out.println();
        }
    }


    private static MessageDigest MD5 = initMD5();

    private static MessageDigest initMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doMD5(String input) throws Exception {
        byte[] bytesOfMessage = input.getBytes("UTF-8");
        return DatatypeConverter.printHexBinary(MD5.digest(bytesOfMessage)).toLowerCase();
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void swap(int[] arr, int a, int b) {
        int aux = arr[a];
        arr[a] = arr[b];
        arr[b] = aux;
    }
}
