package com.guanweiming.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chezhu.xin
 */
@Slf4j
public class JsonUtil {

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 返回的数据如果值为空或者空字符串，就不进行序列化 日期按照yyyy-MM-dd返回 时间按照yyyy-MM-dd HH:mm:ss返回 Long类型的数据序列化成String
     */
    public static ObjectMapper getObjectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.ALWAYS);
        builder.serializerByType(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        builder.serializerByType(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        builder.serializerByType(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        builder.deserializerByType(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        builder.deserializerByType(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        builder.deserializerByType(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        builder.serializerByType(Long.class, ToStringSerializer.instance);
        builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        return builder.build();
    }

    /**
     * 将JavaBean转换成json字符串
     *
     * @param src JavaBean对象
     * @return 转换的结果
     */
    public static String toJson(final Object src) {
        return GSON.toJson(src);
    }

    /**
     * 将JavaBean转换成json字符串 并格式化
     *
     * @param src    JavaBean对象
     * @param format 是否进行格式化
     * @return 根据条件返回Json字符串
     */
    public static String toJson(final Object src, boolean format) {
        return formatJson(toJson(src), format);
    }

    /**
     * 将json字符串转换成JavaBean
     *
     * @param json     json字符串
     * @param classOfT 想要转化成的结果类型
     * @param <T>      结果类型
     * @return 转换之后的JavaBean对象
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }

    /**
     * 转换json串到 集合
     *
     * @param collectionClass 集合类 比如 HashMap.class List.class
     * @param elementClasses  实体类 比如 Table.class
     */
    public static <T> T fromJson(String json, Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType javaType = createCollectionType(collectionClass, objectMapper, elementClasses);
        return fromJson(json, javaType, objectMapper);
    }


    /**
     * 構造泛型的Collection Type如: ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     */
    private static JavaType createCollectionType(Class<?> collectionClass, ObjectMapper objectMapper,
                                                 Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
     *
     * @see #createCollectionType(Class, ObjectMapper, Class...)
     */
    private static <T> T fromJson(String jsonString, JavaType javaType, ObjectMapper objectMapper) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) objectMapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    public static JSONObject toJsonObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static JSONArray toJsonArray(String json) {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static double getDouble(JSONObject jsonObject, String name) {
        try {
            return jsonObject.getDouble(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static double getDouble(String json, String name) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getDouble(name);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static JSONArray getJsonArray(String detail) throws JSONException {
        if (StringUtil.isBlank(detail)) {
            return new JSONArray("['']");
        }
        try {
            return new JSONArray(detail);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray("['']");
        }
    }

    private static String formatJson(String src, boolean format) {
        if (StringUtil.isBlank(src)) {
            return "";
        }
        if (!format) {
            return src;
        }
        src = src.replace("\\\"", "\"");
        StringBuilder sb = new StringBuilder("\n");
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < src.length(); i++) {
            last = current;
            current = src.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("\t");
        }
    }
}
