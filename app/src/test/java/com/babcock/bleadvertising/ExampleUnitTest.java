package com.babcock.bleadvertising;

import org.junit.Assert;
import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void mfgDataParse() throws Exception {

        // Test some reasonable data.

        byte[] adData = new byte[]
                {'u','b','e','r','m','o','d', 6, 9, -1, 0, 1, 2, 3, 4, 5, 6 };

        AdMfgData adMfgData = new AdMfgData(adData);

        Assert.assertEquals("ubermod", adMfgData.getModelName());

        Assert.assertEquals(1545, adMfgData.getModelRange());

        Assert.assertEquals(65280, adMfgData.getFwRev());

        Assert.assertEquals("123456", adMfgData.getSerialNumber());

        // Test zeroes

        adData = new byte[]
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

        adMfgData = new AdMfgData(adData);

        Assert.assertEquals("", adMfgData.getModelName());

        Assert.assertEquals(0, adMfgData.getModelRange());

        Assert.assertEquals(0, adMfgData.getFwRev());

        Assert.assertEquals("000000", adMfgData.getSerialNumber());

        // Test limits

        adData = new byte[]
                {-1, -1, -1, -1, -1, -1, -1, -128, 0, -1, -1, -1, -1, -1, -1, -1, -1 };

        adMfgData = new AdMfgData(adData);

        // Don't understand why Java won't produce the ASCII value for 0xFF (ÿ)...
        Assert.assertEquals("�������", adMfgData.getModelName());

        Assert.assertEquals(-32768, adMfgData.getModelRange());

        Assert.assertEquals(65535, adMfgData.getFwRev());

        Assert.assertEquals("255255255255255255", adMfgData.getSerialNumber());
    }
}