package com.ucmed.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by ucmed on 2017/3/16.
 */
public class SHA256Util {
    private static final Logger LOG = LoggerFactory.getLogger(SHA256Util.class);

    /**
     * @param strSrc
     * @param encName
     * @return
     */
    public static String hash(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");

            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch(NoSuchAlgorithmException e) {
            LOG.error("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    /**
     * Bytes to Hex
     * @param bts
     * @return
     */
    private static String bytes2Hex(byte[] bts) {
        StringBuffer buf = new StringBuffer();
        String tmp = null;
        for(int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if(tmp.length() == 1) {
                buf.append("0");
            }
            buf.append(tmp);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        String securityKey = UUID.randomUUID().toString();
        System.out.println("SecurityKey:" + securityKey);
        String pwHash = SHA256Util.hash("ucmed");
        String securePW = SHA256Util.hash(pwHash
                + securityKey);
        System.out.println(securePW);
    }

}
