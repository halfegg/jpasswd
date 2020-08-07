package com.halfegg.jpasswd;

public class Crypto {

    private Crypto() {}

    public static byte[] decrypt(byte[] data) {
        byte[] crypt = new byte[data.length];
        for (int i = 0; i < data.length; i++)
            crypt[i] = (byte) (i % 2 == 0 ? data[i] + 1 : data[i] - 1);
        return crypt;
    }

    public static byte[] encrypt(byte[] data) {
        byte[] crypt = new byte[data.length];
        for (int i = 0; i < data.length; i++)
            crypt[i] = (byte) (i % 2 == 0 ? data[i] - 1 : data[i] + 1);
        return crypt;
    }

    private static byte[] enhance(byte[] data) {
        // THIS METHOD SHOULD BE IMPLEMENTED
        // ENHANCE ENCRYPTION HERE
        return data;
    }
}
