package pl.michalklecha.sns.treeBuilder.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static MD5 ourInstance = new MD5();

    public static MD5 getInstance() {
        return ourInstance;
    }

    private MessageDigest md;

    private MD5() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getHash(String str) {
        md.reset();
        md.update(str.getBytes());
        return getHashText(md.digest());
    }

    private String getHashText(byte[] bytes) {
        BigInteger bigInt = new BigInteger(1, bytes);
        StringBuilder hashText = new StringBuilder(bigInt.toString(16));
        while (hashText.length() < 32) {
            hashText.insert(0, "0");
        }
        return hashText.toString();
    }
}
