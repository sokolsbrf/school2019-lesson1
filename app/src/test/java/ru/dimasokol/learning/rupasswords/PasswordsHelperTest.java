package ru.dimasokol.learning.rupasswords;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class PasswordsHelperTest {

    private static final String[] RUS = {"й", "ц", "у", "к", "е", "н", "г", "ш", "щ"};
    private static final String[] ENG = {"q", "w", "e", "r", "t", "y", "u", "i", "o"};

    private static final String[] SOURCES = {
            "",
            "щшгнекуцй",
            "ЕууК"
    };

    private static final String[] RESULTS = {
            "",
            "oiuytrewq",
            "TeeR"
    };

    private static final String STRENGTH0 = "";
    private static final String[] STRENGTH1 = {
            "abc",
            "aaaaaaaaaaaaaaaaaaaaaa"
    };

    private static final String STRENGTH_MAX = "abcDef56956H@#7^##$7mm__gERфыаав";

    private PasswordsHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new PasswordsHelper(RUS, ENG);
    }

    @Test
    public void convert() {
        assertFalse("Error in test case", SOURCES.length != RESULTS.length);

        for (int i = 0; i < SOURCES.length; i++) {
            assertEquals(RESULTS[i], helper.convert(SOURCES[i]));
        }
    }

    @Test
    public void detectStrength() {
        assertEquals(0, helper.detectStrength(STRENGTH0));

        for (String s : STRENGTH1) {
            assertEquals(s,1, helper.detectStrength(s));
        }

        assertEquals(10, helper.detectStrength(STRENGTH_MAX));
    }

    @Test
    public void generatePassword() {
        String password = helper.generatePassword(8, true, true, true);
        assertNull(password, password);
    }
}