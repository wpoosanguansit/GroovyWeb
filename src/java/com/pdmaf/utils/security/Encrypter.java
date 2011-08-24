package com.pdmaf.utils.security;

import com.pdmaf.utils.generators.Base64;
import com.pdmaf.utils.exceptions.ApplicationException;

import javax.crypto.*;
import java.security.Key;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 3, 2009
 * Time: 4:24:54 PM
 *
 * http://forums.sun.com/thread.jspa?threadID=5298375
 * By default Linux/Solaris are using NativePRNG algorithm
 * under Java5 which is incomparable with SecureRandom generation
 * used in this code.
 * Windows Java5 as well as Windows and Linux/Solaris under Java 1.4
 * are using SHA1PRNG SecureRandom algorithm.
 *
 * To fix this issue and force Java5 under Linux/Solaris uses SHA1PRNG
 * instead of NativePRNG do the following:
 *
 * edit the file java.security located at
 * <your java installation directory>/jre/lib/security/java.security
 *
 * find the line:
 * securerandom.source=file:/dev/urandom
 * commet it out and insert the new line
 * securerandom.source=file:/dev/random
 *
 * 
 */
public class Encrypter {
    private static SecureRandom sr;
    private static KeyGenerator generator;
    private static Cipher cipher;

    public static final String encrypt(String input) {
        initEncrypter();
        final Key key = generator.generateKey();
        // encrypt

        byte[] encryptionBytes = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE,key);
            encryptionBytes = cipher.doFinal(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new ApplicationException("Encrypter: error in encrypting stirng. ", unsupportedEncodingException);
        } catch (InvalidKeyException invalidKeyException) {
            throw new ApplicationException("Encrypter: error in encrypting stirng. ", invalidKeyException);
        } catch (BadPaddingException badPaddingException) {
            throw new ApplicationException("Encrypter: error in encrypting stirng. ", badPaddingException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
             throw new ApplicationException("Encrypter: error in encrypting stirng. ", illegalBlockSizeException);
        }

        return Base64.encodeBytes(encryptionBytes);
    }

    public static final String decrypt(String encryptedKey) {
        initEncrypter();
        final Key key = generator.generateKey();
        // convert encrypted bytes into string
        // decrypt

        String result = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            final byte[] decodedByte = cipher.doFinal(Base64.decode(encryptedKey.getBytes("UTF-8")));
            result = new String(decodedByte, "UTF-8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new ApplicationException("Encrypter: error in decrypting stirng. ", unsupportedEncodingException);            
        } catch (InvalidKeyException invalidKeyException) {
            throw new ApplicationException("Encrypter: error in decrypting stirng. ", invalidKeyException);
        } catch (BadPaddingException badPaddingException) {
            throw new ApplicationException("Encrypter: error in decrypting stirng. ", badPaddingException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
            throw new ApplicationException("Encrypter: error in decrypting stirng. ", illegalBlockSizeException);                     
        }

        return result;
    }

    private static void initEncrypter() {
        try {
            sr = new SecureRandom("intellegent".getBytes("UTF-8"));
            generator = KeyGenerator.getInstance("DESEDE");
            cipher = Cipher.getInstance("DESEDE/ECB/PKCS5Padding");
            generator.init(168,sr);

        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new ApplicationException("Encrypter: error in initializing enrypter. ", unsupportedEncodingException);                                 
        } catch (NoSuchPaddingException noSuchPaddingException) {
            throw new ApplicationException("Encrypter: error in initializing enrypter. ", noSuchPaddingException);
        } catch (java.security.NoSuchAlgorithmException e) {

        }

    }
}
