package util;

import java.util.*;

/**
 * FitFlow Academic AOP Project
 * Lightweight, zero-dependency JSON builder and parser written in pure standard Java.
 *
 * OOP Concept: Utility Class pattern, Recursion, and Collection traversal.
 * Avoids heavy external Maven/JAR dependencies (Jackson/Gson) so the project
 * compiles cleanly anywhere using standard 'javac'.
 */
public final class JsonUtil {

    private JsonUtil() {
        // Prevent instantiation
    }

    /**
     * Converts a Java Object (Map, List, Primitive, String, Enum, etc.) into a valid JSON String.
     */
    @SuppressWarnings("unchecked")
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return "\"" + escape((String) obj) + "\"";
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof Enum) {
            return "\"" + ((Enum<?>) obj).name() + "\"";
        }
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!first) sb.append(",");
                sb.append("\"").append(escape(String.valueOf(entry.getKey()))).append("\":");
                sb.append(toJson(entry.getValue()));
                first = false;
            }
            sb.append("}");
            return sb.toString();
        }
        if (obj instanceof Collection) {
            Collection<?> col = (Collection<?>) obj;
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object item : col) {
                if (!first) sb.append(",");
                sb.append(toJson(item));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        }
        if (obj.getClass().isArray()) {
            Object[] arr = (Object[]) obj;
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object item : arr) {
                if (!first) sb.append(",");
                sb.append(toJson(item));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        }

        // Fallback for custom objects: fallback to toString escaped
        return "\"" + escape(obj.toString()) + "\"";
    }

    private static String escape(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (ch < ' ') {
                        sb.append(String.format("\\u%04x", (int) ch));
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }

    /**
     * Parses a simple JSON object string (key-value pairs) into a Map<String, Object>.
     * Supports nested primitives, numbers, strings, and booleans.
     */
    public static Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (json == null) return map;
        String s = json.trim();
        if (s.startsWith("{") && s.endsWith("}")) {
            s = s.substring(1, s.length() - 1).trim();
        }
        if (s.isEmpty()) return map;

        int len = s.length();
        int i = 0;
        while (i < len) {
            // Find key
            while (i < len && (Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ',')) i++;
            if (i >= len) break;

            if (s.charAt(i) != '"') break;
            i++; // skip opening quote
            int keyStart = i;
            while (i < len && s.charAt(i) != '"') i++;
            String key = s.substring(keyStart, i);
            i++; // skip closing quote

            // Skip whitespace & colon
            while (i < len && (Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ':')) i++;

            // Parse value
            if (i >= len) break;
            char ch = s.charAt(i);

            if (ch == '"') {
                // String value
                i++;
                int valStart = i;
                StringBuilder valSb = new StringBuilder();
                while (i < len) {
                    char c = s.charAt(i);
                    if (c == '\\' && i + 1 < len) {
                        valSb.append(s.charAt(i + 1));
                        i += 2;
                    } else if (c == '"') {
                        i++;
                        break;
                    } else {
                        valSb.append(c);
                        i++;
                    }
                }
                map.put(key, valSb.toString());
            } else if (ch == '{') {
                // Nested object
                int braceCount = 1;
                int startObj = i;
                i++;
                while (i < len && braceCount > 0) {
                    if (s.charAt(i) == '{') braceCount++;
                    else if (s.charAt(i) == '}') braceCount--;
                    i++;
                }
                String subJson = s.substring(startObj, i);
                map.put(key, parseJsonObject(subJson));
            } else if (ch == '[') {
                // Array
                int bracketCount = 1;
                int startArr = i;
                i++;
                while (i < len && bracketCount > 0) {
                    if (s.charAt(i) == '[') bracketCount++;
                    else if (s.charAt(i) == ']') bracketCount--;
                    i++;
                }
                String subArr = s.substring(startArr, i);
                map.put(key, parseJsonArray(subArr));
            } else {
                // Literal (number, boolean, null)
                int valStart = i;
                while (i < len && s.charAt(i) != ',' && s.charAt(i) != '}') i++;
                String rawVal = s.substring(valStart, i).trim();
                map.put(key, parseLiteral(rawVal));
            }

            while (i < len && (Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ',')) i++;
        }
        return map;
    }

    private static List<Object> parseJsonArray(String json) {
        List<Object> list = new ArrayList<>();
        if (json == null) return list;
        String s = json.trim();
        if (s.startsWith("[") && s.endsWith("]")) {
            s = s.substring(1, s.length() - 1).trim();
        }
        if (s.isEmpty()) return list;

        int len = s.length();
        int i = 0;
        while (i < len) {
            while (i < len && (Character.isWhitespace(s.charAt(i)) || s.charAt(i) == ',')) i++;
            if (i >= len) break;

            char ch = s.charAt(i);
            if (ch == '"') {
                i++;
                StringBuilder valSb = new StringBuilder();
                while (i < len) {
                    char c = s.charAt(i);
                    if (c == '\\' && i + 1 < len) {
                        valSb.append(s.charAt(i + 1));
                        i += 2;
                    } else if (c == '"') {
                        i++;
                        break;
                    } else {
                        valSb.append(c);
                        i++;
                    }
                }
                list.add(valSb.toString());
            } else {
                int start = i;
                while (i < len && s.charAt(i) != ',' && s.charAt(i) != ']') i++;
                String raw = s.substring(start, i).trim();
                list.add(parseLiteral(raw));
            }
        }
        return list;
    }

    private static Object parseLiteral(String raw) {
        if ("null".equalsIgnoreCase(raw)) return null;
        if ("true".equalsIgnoreCase(raw)) return Boolean.TRUE;
        if ("false".equalsIgnoreCase(raw)) return Boolean.FALSE;
        try {
            if (raw.contains(".")) {
                return Double.parseDouble(raw);
            } else {
                return Long.parseLong(raw);
            }
        } catch (NumberFormatException e) {
            return raw;
        }
    }
}
