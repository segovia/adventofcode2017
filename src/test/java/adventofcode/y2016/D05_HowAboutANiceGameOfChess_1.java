package adventofcode.y2016;

import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class D05_HowAboutANiceGameOfChess_1 {

    @Test
    @Ignore("Ignored since this test takes 8s to run")
    public void test() throws Exception {
        assertThat(run("abc"), is("18f47a30"));
        assertThat(run("ugkcyxxp"), is("d4cd2ee1"));
    }

    private String run(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");

        StringBuilder out = new StringBuilder();
        for (int i = 0; out.length() < 8; i++) {
            String md5 = doMD5Hash(md, input + i);
            if (md5.startsWith("00000")) {
                out.append(Character.toLowerCase(md5.charAt(5)));
                System.out.println("Current code: " + out.toString());
            }
        }
        return out.toString();
    }

    private String doMD5Hash(MessageDigest md, String cur) throws Exception {
        byte[] bytesOfMessage = cur.getBytes("UTF-8");
        return DatatypeConverter.printHexBinary(md.digest(bytesOfMessage));
    }
}
