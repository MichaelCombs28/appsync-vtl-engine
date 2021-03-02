package velocity_tester;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

class AppsyncUtilsTest {
    AppsyncUtils util;

    @BeforeEach
    public void initUtil() {
        util = new AppsyncUtils();
    }

    @Test
    void testAutoId() {
        assertEquals("uuid", util.autoId());
    }

    @Test
    void testUrlEncode() throws UnsupportedEncodingException {
        String encoded = util.urlEncode("campaign#asdf");
        assertEquals("campaign%23asdf", encoded);
    }

    @Test
    void testUrlDecode() throws UnsupportedEncodingException {
        String decoded = util.urlDecode("campaign%23asdf");
        assertEquals("campaign#asdf", decoded);
    }

    @Test
    void testBase64Encode() {
        String toBeEncoded = "gid://shopify/ProductVariant/12345";
        String encoded = util.base64Encode(toBeEncoded.getBytes());
        assertEquals("Z2lkOi8vc2hvcGlmeS9Qcm9kdWN0VmFyaWFudC8xMjM0NQ==", encoded);
    }

    @Test
    void testBase64Decode() {
        String toBeDecoded = "Z2lkOi8vc2hvcGlmeS9Qcm9kdWN0VmFyaWFudC8xMjM0NQ==";
        byte[] encoded = util.base64Decode(toBeDecoded);
        assertEquals("gid://shopify/ProductVariant/12345", new String(encoded));
    }

    @Test
    void testToJson() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        String expectation = "{\"hello\":\"world\"}";
        assertEquals(expectation, util.toJson(map));
    }

    @Test
    void testNowEpochSeconds() {
        assertEquals(1517943695L, util.getTime().nowEpochSeconds());
    }

    @Test
    void testIsNullOrBlank() {
        assertTrue(util.isNullOrBlank("              "));
        assertTrue(util.isNullOrBlank(""));
        assertTrue(util.isNullOrBlank(null));
        assertFalse(util.isNullOrBlank("                      a"));
    }

    @Test
    void testSortList() {
        List<Object> lst = Arrays.asList(5, 4, 3, 2, 1, null);
        // Any random string is specified in the
        util.getList().sortList(lst, true, "any random string");
        assertArrayEquals(lst.toArray(), new Integer[] { null, 1, 2, 3, 4, 5 });
    }

}
