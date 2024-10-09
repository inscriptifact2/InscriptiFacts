package isf.sc.utils;

import isf.common.ISFBase64;
import java.io.Serializable;

public class ImageData
        implements Serializable {

    private String b64str;
    private byte data[];

    public ImageData(String s) {
        b64str = s;
    }

    public ImageData(byte abyte0[]) {
        data = abyte0;
        b64str = ISFBase64.encodeBytes(abyte0);
    }

    public String getEncodedString() {
        return b64str;
    }

    public byte[] getDecodedByteArray() {
        return data;
    }

    public String getXML() {
        String s = "<IMAGE_DATA DATA='" + b64str + "' />";
        return s;
    }
}
