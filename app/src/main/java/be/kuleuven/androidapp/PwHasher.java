package be.kuleuven.androidapp;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

// A Java program that uses the MD5 to do the hashing
public class PwHasher
{
    public static String getMd5(String input)
    {
        try
        {
// invoking the static getInstance() method of the MessageDigest class (message digest is hash functie)
// Notice it has MD5 in its parameter.
            MessageDigest msgDst = MessageDigest.getInstance("MD5");
// the digest() method is invoked to compute the message digest
// from an input digest() and it returns an array of byte
            byte[] msgArr = msgDst.digest(input.getBytes());
// getting signum representation from byte array msgArr (sign functie)
            BigInteger bi = new BigInteger(1, msgArr);
// Converting into hex value
            StringBuilder hshtxt = new StringBuilder(bi.toString(16));
            while (hshtxt.length() < 32)
            {
                hshtxt.insert(0, "0");
            }
            return hshtxt.toString();
        }
// for handling the exception
        catch (NoSuchAlgorithmException abc)
        {
            throw new RuntimeException(abc);
        }
    }
}