package br.org.tutty.collaborative_whiteboard.cw.utils;

import br.org.tutty.collaborative_whiteboard.cw.exceptions.EncryptedException;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by drferreira on 16/12/14.
 */
public class EncryptorResources {

    public static String encrypt(String value) throws EncryptedException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            byte[] digest = messageDigest.digest(value.getBytes());

            return (new BASE64Encoder()).encode(digest);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            throw new EncryptedException(exception);
        }

    }
}
