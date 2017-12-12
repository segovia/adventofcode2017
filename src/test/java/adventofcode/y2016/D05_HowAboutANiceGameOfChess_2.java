package adventofcode.y2016;

import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D05_HowAboutANiceGameOfChess_2 {

    @Test
    @Ignore("Ignored since this test takes 16s to run")
    public void test() throws Exception {
        assertThat(run("abc"), is("05ace8e3"));
        assertThat(run("ugkcyxxp"), is("f2c730e5"));
    }

    private String run(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");

        Character[] out = new Character[8];
        for (int i = 0; true; i++) {
            String md5 = doMD5Hash(md, input + i);
            if (md5.startsWith("00000")) {
                char posChar = md5.charAt(5);
                if (posChar < '0' || posChar > '7') continue;
                int pos = posChar - '0';
                if (out[pos] != null) continue;
                out[pos] = Character.toLowerCase(md5.charAt(6));
                System.out.println("Current code: " + toString(out));
                if (isComplete(out)) break;
            }
        }
        return toString(out);
    }

    private String doMD5Hash(MessageDigest md, String cur) throws Exception {
        byte[] bytesOfMessage = cur.getBytes("UTF-8");
        return DatatypeConverter.printHexBinary(md.digest(bytesOfMessage));
    }

    private boolean isComplete(Character[] out) {
        for (Character c : out) if (c == null) return false;
        return true;
    }

    private String toString(Character[] out) {
        StringBuilder sb = new StringBuilder();
        for (Character c : out) sb.append(c == null ? '_' : c);
        return sb.toString();
    }
}
