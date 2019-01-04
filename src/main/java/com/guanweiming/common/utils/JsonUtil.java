package com.guanweiming.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chezhu.xin
 */
public class JsonUtil {

  private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

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
   * @param src JavaBean对象
   * @param format 是否进行格式化
   * @return 根据条件返回Json字符串
   */
  public static String toJson(final Object src, boolean format) {
    return formatJson(toJson(src), format);
  }

  /**
   * 将json字符串转换成JavaBean
   *
   * @param json json字符串
   * @param classOfT 想要转化成的结果类型
   * @param <T> 结果类型
   * @return 转换之后的JavaBean对象
   */
  public static <T> T fromJson(String json, Class<T> classOfT) {
    return new Gson().fromJson(json, classOfT);
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
