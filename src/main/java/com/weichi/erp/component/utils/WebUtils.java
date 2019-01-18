package com.weichi.erp.component.utils;

import com.weichi.erp.component.myType.JsonResult;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wewon on 2018/5/23 11:15
 */
public class WebUtils {
    public enum RequestType {

        PAGE_REQUEST(0), // 页面
        AJAX_REQUEST(1), // AJAX
        UPLOAD_REQUEST(2);// 上传
        public int value;

        private RequestType(int value) {
            this.value = value;
        }

    }

    public static RequestType getRequestType(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            return RequestType.AJAX_REQUEST;
        } else if (ServletFileUpload.isMultipartContent(request)) {
            return RequestType.UPLOAD_REQUEST;
        } else {
            return RequestType.PAGE_REQUEST;
        }
    }

    /**
     * 是否Ajax请求
     *
     * @param request
     * @return
     */
    public static Boolean isAjaxRequest(HttpServletRequest request) {
        return RequestType.AJAX_REQUEST == getRequestType(request);
    }

    /**
     * 是否Upload请求
     *
     * @param request
     * @return
     */
    public static Boolean isUploadRequest(HttpServletRequest request) {
        return RequestType.UPLOAD_REQUEST == getRequestType(request);
    }

    /**
     * 是否Page请求
     *
     * @param request
     * @return
     */
    public static Boolean isPageRequest(HttpServletRequest request) {
        return RequestType.PAGE_REQUEST == getRequestType(request);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (StringUtils.isNotEmpty(ip)) {

            ip = getXforwardIp(ip);
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * @param ip
     * @return
     */
    private static String getXforwardIp(String ip) {
        String[] xfips = ip.split(",");
        if (ArrayUtils.isNotEmpty(xfips)) {

            for (String xfip : xfips) {
                if (StringUtils.isNotEmpty(xfip) && !"unknow".equalsIgnoreCase(xfip)) {
                    return xfip;
                }

            }
        }

        ip = "";
        return ip;
    }


    /*
     * 判断是否移动端，
     * 移动端 true
     * 非移动端：false
     *
     */

    public static boolean isMobile(HttpServletRequest request) {
        // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
        // 字符串在编译时会被转码一次,所以是 "\\b"
        // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
                + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
                + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
                + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

        //移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

        //获取ua，用来判断是否为移动端访问
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (null == userAgent) {
            userAgent = "";
        }

        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static void renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderJsonResult(HttpServletResponse response, JsonResult<?> jsonResult) {
        renderString(response, jsonResult.toJSONString(), "application/json");
    }

    /*
     * 爬取网页信息
     */
    public static String pickData(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
