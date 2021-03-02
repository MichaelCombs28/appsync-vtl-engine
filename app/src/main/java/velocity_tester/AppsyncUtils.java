package velocity_tester;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppsyncUtils {
    private static ObjectMapper mapper = new ObjectMapper();
    private List<AppsyncException> errors;
    private TimeUtils time;
    private ListUtils list;
    private MapUtils map;
    private DynamoDBUtils dynamodb;
    private MathUtils math;
    private StringUtils string;

    private Map<String, Object> utilMocks;

    public AppsyncUtils(Map<String, Object> utilMocks) {
        this.errors = new ArrayList<>();
        this.time = new TimeUtils(this);
        this.list = new ListUtils();
        this.map = new MapUtils();
        this.dynamodb = new DynamoDBUtils();
        this.math = new MathUtils(this);
        this.string = new StringUtils();
        this.utilMocks = utilMocks != null ? utilMocks : Collections.emptyMap();
    }

    public AppsyncUtils() {
        this.errors = new ArrayList<>();
        this.time = new TimeUtils(this);
        this.list = new ListUtils();
        this.utilMocks = Collections.emptyMap();
    }

    public void qr(Object o) {
    }

    public void quiet(Object o) {
    }

    public String escapeJavaScript(String str) {
        return str.replace("\\", "\\\\").replace("\t", "\\t").replace("\b", "\\b").replace("\n", "\\n")
                .replace("\r", "\\r").replace("\f", "\\f").replace("\'", "\\'").replace("\"", "\\\"");
    }

    public Object parseJson(String json) throws Exception {
        return mapper.readValue(json, Object.class);
    }

    public String autoId() {
        return (String) utilMocks.getOrDefault("autoId", "uuid");
    }

    public String urlEncode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
    }

    public String urlDecode(String encoded) throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
    }

    public String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] base64Decode(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    public String toJson(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public void unauthorized() throws Exception {
        throw new Exception();
    }

    public void error(String message) throws Exception {
        error(message, null, null, null);
    }

    public void error(String message, String errorType) throws Exception {
        error(message, errorType, null, null);
    }

    public void error(String message, String errorType, Object data) throws Exception {
        error(message, errorType, data, null);
    }

    public void error(String message, String errorType, Object data, Object errorInfo) throws Exception {
        throw new AppsyncException(message, errorType, data, errorInfo);
    }

    public void appendError(String message) {
        appendError(message, null, null, null);
    }

    public void appendError(String message, String errorType) {
        appendError(message, errorType, null, null);
    }

    public void appendError(String message, String errorType, Object data) {
        appendError(message, errorType, data, null);
    }

    public void appendError(String message, String errorType, Object data, Object errorInfo) {
        this.errors.add(new AppsyncException(message, errorType, data, errorInfo));
    }

    public void validate(boolean isValid, String message) throws Exception {
        validate(isValid, message, null, null, null);
    }

    public void validate(boolean isValid, String message, String errorType) throws Exception {
        validate(isValid, message, errorType, null, null);
    }

    public void validate(boolean isValid, String message, String errorType, Object data) throws Exception {
        validate(isValid, message, errorType, data, null);
    }

    public void validate(boolean isValid, String message, String errorType, Object data, Object errorInfo)
            throws Exception {
        if (!isValid)
            error(message, errorType, data, errorInfo);
    }

    public boolean isNull(Object o) {
        return o == null;
    }

    public boolean isNullOrEmpty(String s) {
        return s == null || s.equals("");
    }

    public boolean isNullOrBlank(String s) {
        return s == null || s.matches("^\\s*$");
    }

    public Object defaultIfNull(Object o, Object defaultO) {
        if (o == null) {
            return defaultO;
        }
        return o;
    }

    public String defaultIfNullOrEmpty(String s, String defaultS) {
        if (isNullOrEmpty(s)) {
            return defaultS;
        }
        return s;
    }

    public Boolean matches(String s, String regex) {
        return s.matches(regex);
    }

    public List<AppsyncException> getErrors() {
        return this.errors;
    }

    public TimeUtils getTime() {
        return this.time;
    }

    public ListUtils getList() {
        return this.list;
    }

    public MapUtils getMap() {
        return this.map;
    }

    public DynamoDBUtils getDynamodb() {
        return this.dynamodb;
    }

    public MathUtils getMath() {
        return this.math;
    }

    public StringUtils getString() {
        return this.string;
    }

    public class TimeUtils {
        private AppsyncUtils utils;

        public TimeUtils(AppsyncUtils utils) {
            this.utils = utils;
        }

        public String nowISO8601() {
            return (String) utils.utilMocks.getOrDefault("time.nowISO8601", "2018-02-06T19:01:35.749Z");
        }

        public Long nowEpochSeconds() {
            return (Long) utils.utilMocks.getOrDefault("time.nowEpochSeconds", 1517943695L);
        }

        public Long nowEpochMilliSeconds() {
            return (Long) utils.utilMocks.getOrDefault("time.nowEpochSeconds", 1517943695750L);
        }
    }

    public class ListUtils {
        public List<Object> sortList(List<Object> src, boolean descending, String key) throws RuntimeException {
            if (src.size() > 1000) {
                throw new RuntimeException("Does not support more than 1k items");
            }
            src.sort((a, b) -> {
                int i;
                if (a instanceof Map && b instanceof Map) {
                    Map<String, Comparable> map1 = (Map<String, Comparable>) a;
                    Map<String, Comparable> map2 = (Map<String, Comparable>) b;
                    Comparable v1 = map1.get(key);
                    Comparable v2 = map2.get(key);
                    if (v1 == null && v2 == null) {
                        return 0;
                    } else if (v1 == null) {
                        i = -1;
                    } else if (v2 == null) {
                        i = 1;
                    } else {
                        i = map1.get(key).compareTo(map2.get(key));
                    }
                } else if (a instanceof Map) {
                    i = 1;
                } else if (b instanceof Map) {
                    i = -1;
                } else {
                    if (a == null && b == null) {
                        i = 0;
                    } else if (a == null) {
                        i = -1;
                    } else if (b == null) {
                        i = 1;
                    } else {
                        i = ((Comparable) a).compareTo((Comparable) b);
                    }
                }
                return flip(i, descending);
            });
            return src;
        }

        private int flip(int i, boolean descending) {
            if (descending) {
                return i;
            }
            return i * -1;
        }
    }

    public class MapUtils {
        public Map<String, Object> copyAndRetainAllKeys(Map<String, Object> map, List<String> keys) {
            Map<String, Object> copy = new LinkedHashMap<>();
            map.forEach((k, v) -> {
                if (keys.contains(k)) {
                    copy.put(k, v);
                }
            });
            return copy;
        }

        public Map<String, Object> copyAndRemoveAllKeys(Map<String, Object> map, List<String> keys) {
            Map<String, Object> copy = new LinkedHashMap<>();
            map.forEach((k, v) -> {
                if (!keys.contains(k)) {
                    copy.put(k, v);
                }
            });
            return copy;
        }
    }

    public class DynamoDBUtils {
        public Map<String, Object> toDynamoDB(Object o) {
            if (o == null) {
                return Collections.singletonMap("NULL", true);
            } else if (o instanceof String) {
                return Collections.singletonMap("S", o);
            } else if (o instanceof Integer) {
                return Collections.singletonMap("N", o);
            } else if (o instanceof Float) {
                return Collections.singletonMap("N", o);
            } else if (o instanceof List) {
                List<Object> objs = ((List<Object>) o).stream().map(obj -> this.toDynamoDB(obj))
                        .collect(Collectors.toList());
                return Collections.singletonMap("L", objs);
            } else if (o instanceof Boolean) {
                return Collections.singletonMap("BOOL", o);
            } else if (o instanceof Byte) {
                return Collections.singletonMap("B", o);
            }
            Map<String, Object> obj = (Map<String, Object>) o;
            Map<String, Object> result = new LinkedHashMap<>();
            obj.forEach((k, v) -> {
                result.put(k, this.toDynamoDB(v));
            });
            return Collections.singletonMap("M", result);
        }

        public String toDynamoDBJson(Object o) throws JsonProcessingException {
            return mapper.writeValueAsString(toDynamoDB(o));
        }

        public Map<String, String> toString(String s) {
            return Collections.singletonMap("S", s);
        }

        public String toStringJson(String s) throws JsonProcessingException {
            return mapper.writeValueAsString(toString(s));
        }

        public Map<String, Object> toStringSet(List<String> values) {
            return Collections.singletonMap("SS", values);
        }

        public String toStringSetJson(List<String> values) throws JsonProcessingException {
            return mapper.writeValueAsString(toStringSet(values));
        }

        public Map<String, Object> toNumberSet(List<Number> values) {
            return Collections.singletonMap("NN", values);
        }

        public String toNumberSetJson(List<Number> values) throws JsonProcessingException {
            return mapper.writeValueAsString(toNumberSet(values));
        }

        public Map<String, Object> toBinary(String s) {
            return Collections.singletonMap("B", s);
        }

        public String toBinaryJson(String s) throws JsonProcessingException {
            return mapper.writeValueAsString(toBinary(s));
        }

        public Map<String, Object> toBinarySet(List<String> s) {
            return Collections.singletonMap("BS", s);
        }

        public String toBinarySetJson(List<String> s) throws JsonProcessingException {
            return mapper.writeValueAsString(toBinarySet(s));
        }

        public Map<String, Object> toBoolean(Boolean b) {
            return Collections.singletonMap("BOOL", b);
        }

        public String toBooleanJson(Boolean b) throws JsonProcessingException {
            return mapper.writeValueAsString(toBoolean(b));
        }

        public Map<String, Object> toNull() {
            return Collections.singletonMap("NULL", true);
        }

        public String toNullJson() throws JsonProcessingException {
            return mapper.writeValueAsString(toNull());
        }

        public Map<String, Object> toList(List<Object> objs) {
            return Collections.singletonMap("L", objs.stream().map(o -> toDynamoDB(o)).collect(Collectors.toList()));
        }

        public String toListJson(List<Object> objs) throws JsonProcessingException {
            return mapper.writeValueAsString(toList(objs));
        }

        public Map<String, Object> toMap(Map<String, Object> value) {
            return Collections.singletonMap("M", toMapValues(value));
        }

        public String toMapJson(Map<String, Object> value) throws JsonProcessingException {
            return mapper.writeValueAsString(toMap(value));
        }

        public Map<String, Object> toMapValues(Map<String, Object> value) {
            Map<String, Object> copy = new LinkedHashMap<>();
            value.forEach((k, v) -> {
                copy.put(k, toDynamoDB(v));
            });
            return copy;
        }

        public String toMapValuesJson(Map<String, Object> value) throws JsonProcessingException {
            return mapper.writeValueAsString(toMapValues(value));
        }

        public Map<String, Object> toS3Object(String key, String bucket, String region) throws JsonProcessingException {
            Map<String, String> s3 = new LinkedHashMap<>();
            s3.put("key", key);
            s3.put("bucket", bucket);
            s3.put("region", region);
            return Collections.singletonMap("S", mapper.writeValueAsString(s3));
        }

        public String toS3ObjectJson(String key, String bucket, String region) throws JsonProcessingException {
            return mapper.writeValueAsString(toS3Object(key, bucket, region));
        }
    }

    public class MathUtils {
        private AppsyncUtils utils;

        MathUtils(AppsyncUtils utils) {
            this.utils = utils;
        }

        public Long roundNum(Double d) {
            return Math.round(d);
        }

        public Double minVal(Double v1, Double v2) {
            return Math.min(v1, v2);
        }

        public Double maxVal(Double v1, Double v2) {
            return Math.max(v1, v2);
        }

        public Double randomDouble() {
            return (Double) this.utils.utilMocks.getOrDefault("math.randomDouble", Math.random());
        }

        public Integer randomWithinRange(Integer lowerBound, Integer upperBound) {
            return (int) ((Math.random() * (upperBound - lowerBound)) + lowerBound);
        }
    }

    public class StringUtils {
        public String toUpper(String s) {
            return s.toUpperCase();
        }

        public String toLower(String s) {
            return s.toLowerCase();
        }

        public String toReplace(String s, String substring, String replacement) {
            return s.replaceAll(substring, replacement);
        }
    }
}
