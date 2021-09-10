package com.restfulclient.impl;

import com.restfulclient.call.RestClientConstants;
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
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.List;
import java.util.Map;
import com.restfulclient.interfaces.IClient;

public class Client implements IClient {

    private HttpURLConnection connection;
    private final IRequest request;
    private IResponse response;
    
    private Client(IRequest request) {
        this.request = request;
       
    }

    public static IClient build(IRequest request) {
        var client = new Client(request);
        return client;
    }

    @Override
    public boolean isOKHTTP() throws IOException {
        return (connection.getResponseCode() >= HttpURLConnection.HTTP_OK) && (connection.getResponseCode() <= HttpURLConnection.HTTP_PARTIAL);
    }

    @Override
    public OutputStream getRequestStream() throws IOException, ApiException {
        try {
            return connection.getOutputStream();
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    @Override
    public InputStreamReader getResponseStream() throws IOException, ApiException {
        InputStreamReader br = null;
        try {
            if (connection.getContentEncoding() != null && RestClientConstants.GZIP_ENCODING.equalsIgnoreCase(connection.getContentEncoding())) {
                br = (new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
            } else {
                if (connection.getContentEncoding() != null && RestClientConstants.DEFLATE_ENCODING.equalsIgnoreCase(connection.getContentEncoding())) {
                    br = (new InputStreamReader(new InflaterInputStream(connection.getInputStream(), new Inflater(true))));
                } else {
                    br = (new InputStreamReader((connection.getInputStream())));
                }
            }
        } catch (IOException e) {
            throw new ApiException(e);
        } catch (Exception e) {
            throw new ApiException(e);
        }
        return br;
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (connection.getErrorStream() != null) {
                    connection.getErrorStream().close();
                }
                if (connection.getInputStream() != null) {
                    connection.getInputStream().close();
                }
            } catch (IOException ex) {

            }
            connection.disconnect();
            connection = null;
        }
    }

    @Override
    public IResponse execute() throws IOException, ApiException {
        try {
            response = executeCall();
        } catch (ApiException ex) {
            try {
                close();
                throw ex;
            } catch (IOException ex1) {
                throw new ApiException(ex1);
            }
        } catch (IOException ex) {
            throw new ApiException(ex);
        }
        return response;
    }

    private IResponse executeCall() throws ApiException, IOException {
        try {
            if (request.getHeader() == null) {
                throw new ApiException("Error while initializing remote connection No header defined ");
            }
            URL url = new URL(request.getRequestPath().getPath());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(45000);
            request.getHeader().keySet().forEach(param -> {
                connection.setRequestProperty(param, request.getHeader().get(param).toString());
            });
            if(request.getMethod() == Method.PATCH){
               allowMethods(Method.PATCH.getName());
            }
            connection.setDefaultUseCaches(false);
            connection.setUseCaches(false);
            connection.setRequestMethod(request.getMethod().getName());
            if (request.useBodyRequest()) {
                connection.setDoOutput(true);
                connection.setDoInput(true);
            } else {
                connection.setDoOutput(false);
                connection.setDoInput(true);
            }
            request.execute(this);
            response = Response.build();
            response.process(this);
            return response;
        } catch (ApiException ex) {
            try {
                close();
                throw ex;
            } catch (IOException ex1) {
                throw new ApiException(ex1);
            }
        } catch (IOException ex) {
            throw new ApiException(ex);
        } finally {
            close();
        }
    }

    @Override
    public void addRequestProperty(String param, String value) {
        connection.setRequestProperty(param, value);
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

    @Override
    public int getResponseCode() throws IOException, ApiException {
        try {
            return connection.getResponseCode();
        } catch (IOException ex) {
            throw new ApiException(ex);
        }
    }

    @Override
    public InputStreamReader getErrorStream() throws IOException, ApiException {
        InputStreamReader br = null;
        try {
            br = (new InputStreamReader((connection.getErrorStream())));
        } catch (Exception e) {           
           throw new ApiException(e);            
        }
        return br;
    }

    @Override
    public String getResponseMessage() throws IOException, ApiException {
        return connection.getResponseMessage();
    }

    @Override
    public Map<String, List<String>> getResponseHeader() throws IOException, ApiException {
        return connection.getHeaderFields();
    }

    @Override
    public void clean() {
       this.request.clean();     
    }

}
