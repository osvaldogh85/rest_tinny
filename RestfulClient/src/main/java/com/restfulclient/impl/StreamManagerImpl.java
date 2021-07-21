package com.restfulclient.impl;

import com.restfulclient.call.RestClientConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import com.restfulclient.interfaces.IStreamManager;

public class StreamManagerImpl implements IStreamManager {

    private HttpURLConnection connection;
    private IRequest requestCall;

    private StreamManagerImpl() {
    }

    public static IStreamManager build(IRequest request) {
        var client = new StreamManagerImpl();
        client.addRequest(request);
        return client;
    }

    private void addRequest(IRequest request) {
        this.requestCall = request;
    }

    @Override
    public IResponse execute() {
        try {
            openConnection();
            return requestCall.call(this);
        } catch (Exception ex) {
            Logger.getLogger(StreamManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private boolean isOKHTTP() throws IOException {
        return (connection.getResponseCode() >= HttpURLConnection.HTTP_OK) && (connection.getResponseCode() <= HttpURLConnection.HTTP_PARTIAL);
    }

    @Override
    public OutputStream getRequestStream() throws Exception {
        return connection.getOutputStream();
    }

    @Override
    public BufferedReader getResponseStream() throws Exception {
        BufferedReader br;
        if (isOKHTTP()) {
            if (connection.getContentEncoding() != null && RestClientConstants.GZIP_ENCODING.equalsIgnoreCase(connection.getContentEncoding())) {
                br = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
            } else {
                if (connection.getContentEncoding() != null && RestClientConstants.DEFLATE_ENCODING.equalsIgnoreCase(connection.getContentEncoding())) {
                    br = new BufferedReader(new InputStreamReader( new InflaterInputStream(connection.getInputStream(), new Inflater(true))));
                } else {
                    br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                }
            }
        } else {
            br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
        }
        return br;
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private void openConnection() throws Exception {
        try {
            URL url = new URL(requestCall.getRequestPath().getPath());
            connection = (HttpURLConnection) url.openConnection();

            if (requestCall.getRequestPath().getMethod() == Method.PATCH) {
                allowMethods(requestCall.getRequestPath().getMethod().getName());
            }

            connection.setRequestMethod(requestCall.getRequestPath().getMethod().getName());
            connection.setReadTimeout(requestCall.getTimeOut());

            if (requestCall.getHeader() == null) {
                throw new Exception("Error while initializing remote connection ");
            }

            requestCall.getHeader().keySet().forEach(param -> {
                connection.setRequestProperty(param, requestCall.getHeader().get(param).toString());
            });

            connection.setDoOutput(true);
            connection.setDoInput(true);
        } catch (Exception e) {
            throw new Exception("Error while initializing remote connection " + e.getMessage());
        }
    }

    private void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
