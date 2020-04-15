package com.guanweiming.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * @author 关卫明
 */
@Slf4j
public class HttpKit {

    /**
     * 获取Http请求对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpResponse对象
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getResponse();
    }

    private static Pattern portPattern = Pattern.compile("(\\d+)");

    public static Integer getPort() {
        return getRequest().getServerPort();
    }


    public static String getOpenId() {
        return String.valueOf(getRequest().getAttribute("openId"));
    }

    public static Long getUserId() {
        return NumberUtils.toLong(String.valueOf(getRequest().getAttribute("userId")));
    }


    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (isBlank(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (isBlank(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("获取ip异常：{}", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            // "***.***.***.***".length()
            // = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    private static boolean isBlank(String src) {
        return StringUtils.isBlank(src) || "unknown".equals(src);
    }


}
