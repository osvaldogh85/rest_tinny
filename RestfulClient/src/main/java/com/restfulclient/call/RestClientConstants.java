/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restfulclient.call;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

public class RestClientConstants {

    public static final String CHARSET = "Charset";
    public static final String CHARSET_UTF8 = "utf-8";
    public static final String HOST = "Host";
    public static final String API_CONTENT_LANGUAGE = "Content-Language";
    public static final String API_CONTENT_LENGTH = "Content-Length";
    public static final String API_CONTENT_TYPE = "Content-Type";
    public static final String API_ACCEPT = "Accept";
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String API_USER_AGENT = "APIClientV1";
    public static final String API_USER_AGENT_TITLE = "User-Agent";
    public static final String API_ACCEPT_CONTENT = "*/*";
    public static final String API_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String GZIP_ENCODING = "gzip";
    public static final String DEFLATE_ENCODING = "deflate";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String API_CONNECTION = "Connection";
    public static final String API_KEEP_ALIVE = "keep-alive";
    public static final String MULTIPART_CONTENT_TYPE = "multipart/form-data; boundary=";
    public static final String OCTECT_STREAM = "application/octet-stream";
    public static final String X_FORM_ENCODED_URL = "application/x-www-form-urlencoded";
    public static final String JSON_CONTENT_TYPE_ODATA = "application/json;odata=verbose";
    public static final String CONNECTION = "Connection";
    public static final String CONNECTION_CLOSE = "close";
    public static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    public static final String API_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String API_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String BINARY = "binary";

    public static String getFormFileDataContent(String name, String fileName) {
        return "form-data; name=\"" + name + "\";filename=\"" + fileName + "\"";
    }

    public static String getFormDataContentn(String name) {
        return "form-data; name=\"" + name + "\"";
    }

    public static String getMultiPartContentType(String boundary) {
        return MULTIPART_CONTENT_TYPE + boundary;
    }

    public static String getTextPlainContent(String charset) {
        return "text/plain; charset=" + charset;
    }

    public static String getBoundaryForMultiPart() {
        return "****" + System.currentTimeMillis() + "****";
    }

    public static String urlencoded(LinkedHashMap<String, Object> data, Charset charset) throws Exception {
        StringBuilder encode = new StringBuilder();
        if (data.isEmpty()) {
            return null;
        }
        var paramCount = data.keySet().size() - 1;
        var count = 0;
        for (String key : data.keySet()) {
            if (charset != null) {
                if (count < paramCount) {
                    encode.append(URLEncoder.encode(key.trim(), charset)).append("=").append(URLEncoder.encode(data.get(key).toString().trim(), charset)).append("&");
                } else {
                    encode.append(URLEncoder.encode(key.trim(), charset)).append("=").append(URLEncoder.encode(data.get(key).toString().trim(), charset));
                }
            }else{
               if (count < paramCount) {
                    encode.append(key.trim()).append("=").append(data.get(key).toString().trim()).append("&");
                } else {
                    encode.append(key.trim()).append("=").append(data.get(key).toString().trim());
                } 
            }

            count++;
        }
        return encode.toString();
    }

}
