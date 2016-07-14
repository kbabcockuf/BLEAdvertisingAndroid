package com.babcock.bleadvertising;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by kevinbabcock on 7/12/16.
 *
 * Manufacturer Data must have the following format:
 *
 struct {
     char model_name[7];  // Device model name
     int16_t model_range; // Model range, signed big-endian.
     uint16_t fw_rev;     // Firmware rev, unsigned big-endian.
     uint8_t serial[6];   // Device serial number, raw bytes.
 };
 *
 */
public class AdMfgData {

    // Model Name
    private String mModelName;

    // Model Range
    private short mModelRange;

    // Firmware Revision
    private int mFwRev;

    // Serial Number - string format to handle leading zeroes.
    private String mSerialNumber;

    public AdMfgData(byte[] mfgData) throws UnsupportedEncodingException {
        if (mfgData == null || mfgData.length != 17) {
            throw new IllegalArgumentException("Manufacturer Data must be non-null and 17 bytes in length");
        }

        mModelName = new String(Arrays.copyOfRange(mfgData, 0, 7), "US-ASCII").trim();

        mModelRange = ByteBuffer.wrap(new byte[] { mfgData[7], mfgData[8]}).getShort();

        // Since this is only 2 bytes long, we can safely hold the maximum uint16 value in a signed Java integer.
        mFwRev = ByteBuffer.wrap(new byte[] { 0, 0, mfgData[9], mfgData[10]}).getInt();

        byte[] serialBytes = Arrays.copyOfRange(mfgData,11, 17);
        StringBuilder serialSb = new StringBuilder();
        for (int i = 0; i < serialBytes.length; i++)
        {
            serialSb.append(serialBytes[i] & 0xff);
        }

        mSerialNumber = serialSb.toString();
    }

    public String getModelName() {
        return mModelName;
    }

    public short getModelRange() {
        return mModelRange;
    }

    public int getFwRev() {
        return mFwRev;
    }

    public String getSerialNumber() {
        return mSerialNumber;
    }
}
