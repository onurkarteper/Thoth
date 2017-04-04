package com.devfie.thoth.data;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Onur Karteper on 4/2/2017.
 */

public class Md5Helper {

    public static String md5(String string) {
        // string = "network=" + getString(R.string.wifi_prod) + "&username=" + string + "su yuzde yuz nemlimidir?";
        Log.d("md5String", string);
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }
        Log.d("md5Result", hex.toString());
        return hex.toString();
    }
}
