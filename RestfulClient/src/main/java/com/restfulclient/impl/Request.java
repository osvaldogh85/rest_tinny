package com.restfulclient.impl;

import com.restfulclient.call.RestClientConstants;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.restfulclient.interfaces.IHeader;
import com.restfulclient.interfaces.IRequest;
import com.restfulclient.interfaces.IRequestPath;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.file.Files;
import com.restfulclient.interfaces.IClient;
import com.restfulclient.interfaces.IRequestBody;

public class Request implements IRequest {

    private CPHeaderImpl headerImpl=null;
    private IRequestPath path=null;
    private IRequestBody body=null;
    private Map<String, Object> formMultipartParams = null;

    private Request(IRequestPath requestPath) {
        this.path = requestPath;
        this.headerImpl = new CPHeaderImpl();
    }

    public static IRequest build(IRequestPath requestPath) {
        return new Request(requestPath);
    }

    public void addBodyHeaderFields(PrintWriter writer, String LINE_FEED, String param, String value) {
        writer.append(param + ": " + value);
        writer.append(LINE_FEED);
    }

    @Override
    public Map<String, Object> getHeader() {
        return headerImpl.getHeader();
    }

    @Override
    public void addHeader(String name, String value) {
        if (headerImpl != null) {
            headerImpl.addHeader(name, value);
        }
    }

    @Override
    public void addFormMultipartParams(String name, Object value) {
        if (formMultipartParams == null) {
            formMultipartParams = new HashMap<String, Object>();
            formMultipartParams.put(name, value);
        }else
            formMultipartParams.put(name, value);
    }

    @Override
    public IRequestPath getRequestPath() {
        return path;
    }
     @Override
    public void setRequestBody(IRequestBody body) {
        this.body=body;
    }
  
    @Override
    public boolean useBodyRequest() {
       boolean useBodyRequest = false;
       if(body != null){
           if(body.getMessage() != null){              
               useBodyRequest= true;           
           }
       }
       return useBodyRequest;
    }

    @Override
    public void call(IClient client) throws ApiException {
        if (useBodyRequest()) {
            try {
                writeBodyRequest(client);
            } catch (Exception ex) {
                throw new ApiException(0, ex.getMessage(), null, "");
            }
        }
    }

    public String guessContentTypeFromFile(File file) {
        String contentType = URLConnection.guessContentTypeFromName(file.getName());
        if (contentType == null) {
            return RestClientConstants.OCTECT_STREAM;
        } else {
            return contentType;
        }
    }

    private boolean isBinary() {
        return headerImpl.headerMap.get(RestClientConstants.API_CONTENT_TYPE).equals(RestClientConstants.MULTIPART_CONTENT_TYPE)
                || headerImpl.headerMap.get(RestClientConstants.API_CONTENT_TYPE).equals(RestClientConstants.OCTECT_STREAM);
    }

    private void writeBodyRequest(IClient client) throws ApiException, IOException, Exception {
        if (isBinary() == false) {
            OutputStream output = client.getRequestStream();
            DataOutputStream wr = new DataOutputStream(output);
            try  {              
                wr.write(body.getMessage());
                wr.flush();
            } catch (IOException ex) {
                throw new ApiException(ex);
            }finally
            {
                wr.close();
                output.close();
            }
        } else {
            switch(this.getHeader().get(RestClientConstants.API_CONTENT_TYPE).toString()){
                case RestClientConstants.MULTIPART_CONTENT_TYPE:                    
                     String LINE_FEED = "\r\n";
                     String boundary = RestClientConstants.getBoundaryForMultiPart();
                     client.addRequestProperty(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.getMultiPartContentType(boundary));
                     OutputStream output = client.getRequestStream();  
                     PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, RestClientConstants.CHARSET_UTF8), true);
                     try  {
                        for (Map.Entry<String, Object> param : formMultipartParams.entrySet()) {
                           addFilePart(output, writer, boundary, LINE_FEED, ((File) param.getValue()));
                      }
                     } catch (IOException ex) {
                       throw ex;
                     } finally {
                        formMultipartParams.clear();
                        writer.close();
                        output.close();
                     }
                    break;

                    
                case RestClientConstants.OCTECT_STREAM:
                    OutputStream outputOctet = client.getRequestStream();
                    try  {
                    for (Map.Entry<String, Object> param : formMultipartParams.entrySet()) {
                       Files.copy(((File) param.getValue()).toPath(), outputOctet);
                    }
                    } catch (IOException ex) {
                       throw ex;
                    } finally {
                       outputOctet.close();
                       formMultipartParams.clear();
                    }
                break;

                default:                         
                 
            }           
        }
    }

    private void addFilePart(OutputStream output, PrintWriter writer, String boundary, final String LINE_FEED, File file)
            throws IOException {
        String fileName = file.getName();
        writer.append(boundary).append(LINE_FEED);
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_DISPOSITION, RestClientConstants.getFormFileDataContent("file", fileName));
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_TYPE, URLConnection.guessContentTypeFromName(fileName));
        writer.append(LINE_FEED);
        writer.flush();
        Files.copy(file.toPath(), output);
        writer.append(LINE_FEED);
        writer.append(boundary).append(LINE_FEED);
        writer.flush();
    }

    public void addFormField(PrintWriter writer, String boundary, String charset, final String LINE_FEED, String name, String value) {
        writer.append(boundary).append(LINE_FEED);
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_DISPOSITION, RestClientConstants.getFormDataContentn(name.substring(name.lastIndexOf(".") + 1, name.length())));
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_TYPE, RestClientConstants.getFormDataContentn("UTF-8"));
        writer.append(LINE_FEED);
        writer.append(value);
        writer.flush();
        writer.append(LINE_FEED);
        writer.append(LINE_FEED).flush();
        writer.append(boundary).append(LINE_FEED);
        writer.flush();
    }

    private class CPHeaderImpl implements IHeader {

        private final Map<String, Object> headerMap;

        public CPHeaderImpl() {
            headerMap = new HashMap<>();
        }

        @Override
        public Map<String, Object> getHeader() {
            return headerMap;
        }

        @Override
        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }
    }
}
