package com.weichi.erp.component.myType;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

;

/**
 * Created by Wewon on 2018/9/14.
 */
public class ResponseStream {
    /**
     * 输出流
     *
     * @param stream
     * @param response
     * @throws IOException
     */
    public static void write(InputStream stream, HttpServletResponse response) throws IOException {
        if (stream == null) {
            throw new IOException("The stream is empty.");
        }
        // 输出流
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = stream.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 这里主要关闭。
            try {
                if (os != null) {
                    os.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输出流
     *
     * @param stream      输出流
     * @param fileName    文件名称
     * @param contentType 响应内容类型
     * @param response    HTTP响应
     */
    public static void out(InputStream stream, String fileName, ContentType contentType, HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        if (stream == null) {
            throw new IOException("The stream is empty.");
        }
        // 设置头信息
        setHeader(fileName, contentType, request, response);
        // 输出流
        write(stream, response);
    }

    /**
     * 输出流
     *
     * @param stream   输出流
     * @param fileName 文件名称
     * @param response HTTP响应
     */
    public static void out(InputStream stream, String fileName, HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        out(stream, fileName, null, request, response);
    }

    /**
     * 输出流，文件名UUID随机生成，无后缀。
     *
     * @param stream   输出流
     * @param response HTTP响应
     */
    public static void out(InputStream stream, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 随机生成文件名
        String fileName = UUID.randomUUID().toString().replace("-", "");
        out(stream, fileName, request, response);
    }

    /**
     * 设置头信息
     *
     * @param fileName    文件名
     * @param contentType 响应内容类型
     * @param response    HTTP响应
     */
    public static void setHeader(String fileName, ContentType contentType, HttpServletRequest
            request, HttpServletResponse response) throws IOException {
        response.setContentType(contentType == null ? ContentType.DEFAULT.getValue() : contentType.getValue());
        response.setHeader("Content-Disposition", "attachment;"
                + encodeDownloadFilename(fileName, request));
    }

    /**
     * 下载文件时，针对不同浏览器，进行附件名的编码
     * 客户端浏览器    获取方法：  request.getHeader("User-Agent");
     *
     * @param filename 下载文件名
     * @return 编码后的下载附件名
     * @throws IOException
     */
    private static String encodeDownloadFilename(String filename, HttpServletRequest request)
            throws IOException {
        String userAgent = request.getHeader("User-Agent");
        String new_filename = URLEncoder.encode(filename, "UTF8");
// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
        String rtn = "filename=\"" + filename + "\"";
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            // IE浏览器，只能采用URLEncoder编码
            if (userAgent.indexOf("msie") != -1 || userAgent.indexOf("trident") != -1) {
                rtn = "filename=\"" + new_filename + "\"";
            }
            // Opera浏览器只能采用filename*
            else if (userAgent.indexOf("opera") != -1) {
                rtn = "filename*=UTF-8''" + new_filename;
            }
            // Safari浏览器，只能采用ISO编码的中文输出
            else if (userAgent.indexOf("safari") != -1) {
                rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
            }
            // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
            else if (userAgent.indexOf("applewebkit") != -1) {
                new_filename = MimeUtility.encodeText(filename, "UTF8", "B");
                rtn = "filename=\"" + new_filename + "\"";
            }
            // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
            else if (userAgent.indexOf("mozilla") != -1) {
                rtn = "filename*=UTF-8''" + new_filename;
            }
        }
        // 文件名外的双引号处理firefox的空格截断问题
        return rtn;
    }

    /**
     * 响应内容类型
     */
    public enum ContentType {
        WORD("application/msword"),
        EXCEL("application/x-excel"),
        PDF("application/pdf;charset=UTF-8"),
        JPEG("image/jpeg"),
        GIF("image/gif"),
        PNG("image/png"),
        BMP("image/bmp"),
        ZIP("application/zip"),
        HTML("text/html;charset=UTF-8"),
        TEXT("text/plain;charset=UTF-8"),
        JSON("application/json;charset=UTF-8"),
        XML("text/xml;charset=UTF-8"),
        DEFAULT("application/force-download");

        private String value;

        ContentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
