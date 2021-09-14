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
import com.restfulclient.interfaces.IRequestParameters;

public class Request implements IRequest {

    private CPHeaderImpl headerImpl=null;
    private IRequestPath path=null;
    private IRequestBody body=null;
    private Map<String, Object> formMultipartParams = null;
    private Method method;
    
    private Request(Method method,IRequestPath requestPath) {
        this.path = requestPath;
        this.headerImpl = new CPHeaderImpl();
        this.method=method;
        if(method == Method.GET || method == Method.HEAD)
            this.addBody(RequestBody.build(BodyType.NONE));
    }

    public static IRequest build(Method method,IRequestPath requestPath) throws Exception {
        if (method == null) {
            throw new Exception("Error while creating path there is no method to request");
        }
        return new Request(method,requestPath);
    }

     @Override
    public Method getMethod() {
        return method;
    } 
    
    @Override
    public Map<String, Object> getHeader() {
        return headerImpl.getHeader();
    }

    @Override
    public void addHeader(String name, Object value) {
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
    public String buildXFormURLEncodedParameters() throws ApiException {
        return path.buildXFormURLEncodedParameters();
    }
    
    @Override
    public String getFullPath(){
      return path.getPath();
    }
    
    @Override
    public void buildPathForRequest() throws ApiException{
         path.buildPath();
    }
    
    @Override
    public void addRequestParameterInstance(IRequestParameters parameters) {
        this.path.addRequestParameter(parameters);
    }
    
    @Override
    public void addNewRequestParameter(String name, Object value) {       
        this.path.addNewRequestParameter(name,value);
     
    }
    
    @Override
    public void addNewPathParameter(String name, Object value){
       this.path.addNewPathParameter(name,value);
    }
    
    @Override
    public boolean existsParameterType(){
       return this.path.getParameterType() != null;
    }
    
    @Override
    public ParameterType getCurrentParameterType(){
       return existsParameterType() ? this.path.getParameterType() : null;
    }
    
    @Override
    public boolean compareParameterType(ParameterType type){
       return this.path.getParameterType() == type;
    }
    
    @Override
     public void execute(IClient client) throws ApiException {
        if (useBodyRequest()) {
            try {
                postRequest(client);
            } catch (Exception ex) {
                throw new ApiException(0, ex.getMessage(), null, "");
            }
        }
    }

    @Override
    public final void addBody(IRequestBody body) {
       this.body=body;
    }
    
    @Override
    public boolean useBodyRequest() {
       return body != null ? BodyType.NONE != body.getBodyType() : false;             
    }

    private String guessContentTypeFromFile(File file) {
        String contentType = URLConnection.guessContentTypeFromName(file.getName());
        if (contentType == null) {
            return RestClientConstants.OCTECT_STREAM;
        } else {
            return contentType;
        }
    }

    private void writeRawData(IClient client)throws ApiException, IOException, Exception{
            OutputStream output = client.getRequestStream();
            DataOutputStream wr = new DataOutputStream(output);
            try  {              
                wr.write(body.getBody());
                wr.flush();
            } catch (IOException ex) {
                throw new ApiException(ex);
            }finally
            {
                wr.close();
                output.close();
            }
    }
    private void postRequest(IClient client) throws ApiException, IOException, Exception {       
            final String LINE_FEED = "\r\n";
            switch(body.getBodyType()){
                case RAW:
                    writeRawData(client);
                    break;
                case URL_XFORM_ENCODED:  
                    writeRawData(client); 
                    break;
                case MULTIPART_FORM:                   
                     final String boundary = RestClientConstants.getBoundaryForMultiPart();
                     client.addRequestProperty(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.getMultiPartContentType(boundary));
                     OutputStream output = client.getRequestStream();  
                     PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, RestClientConstants.CHARSET_UTF8), true);
                     try  {
                        for (Map.Entry<String, Object> param : formMultipartParams.entrySet()) {
                            if(param.getValue() instanceof File){
                               addFilePart(output, writer, boundary, LINE_FEED, ((File) param.getValue()));
                            }else{
                               addFormField( writer, boundary, RestClientConstants.CHARSET_UTF8, LINE_FEED, param.getKey() ,param.getValue().toString());   
                            }                          
                         }
                     } catch (IOException ex) {
                       throw ex;
                     } finally {
                        finishForm(writer, boundary, LINE_FEED);
                        formMultipartParams.clear();                       
                        output.close();
                     }
                    break;

                 case FORM_DATA:                    
                     String boundaryForm = RestClientConstants.getBoundaryForMultiPart();
                     client.addRequestProperty(RestClientConstants.API_CONTENT_TYPE, RestClientConstants.getMultiPartContentType(boundaryForm));
                     OutputStream outputForm = client.getRequestStream();  
                     PrintWriter writerForm = new PrintWriter(new OutputStreamWriter(outputForm, RestClientConstants.CHARSET_UTF8), true);
                     try  {
                     for (Map.Entry<String, Object> param : formMultipartParams.entrySet()) {
                         addFormField( writerForm, boundaryForm, RestClientConstants.CHARSET_UTF8,LINE_FEED, param.getKey() ,param.getValue().toString());
                     }
                     } catch (Exception ex) {
                       throw ex;
                     } finally {
                        finishForm(writerForm, boundaryForm, LINE_FEED);
                        formMultipartParams.clear();                       
                        outputForm.close();
                     }
                    break;
                case BINARY:
                    OutputStream outputOctet = client.getRequestStream();
                    try  {
                         File file = (File) body.getBodyContent();                     
                         Files.copy(file.toPath(), outputOctet);                   
                    } catch (IOException ex) {
                       throw ex;
                    } finally {
                       outputOctet.close();                      
                    }
                break;

                default:                         
                 
            }         
    }
    
    private void finishForm(PrintWriter writer, String boundary, final String LINE_FEED)
    {
         writer.flush();
         writer.append(boundary).append(LINE_FEED);
         writer.close(); 
    }
    

    private void addFilePart(OutputStream output, PrintWriter writer, String boundary, final String LINE_FEED, File file)
            throws IOException {
        String fileName = file.getName();
        writer.append(boundary).append(LINE_FEED);
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_DISPOSITION, RestClientConstants.getFormFileDataContent("file", fileName));
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_TYPE, guessContentTypeFromFile(file));
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_TRANSFER_ENCODING, RestClientConstants.BINARY);
        writer.append(LINE_FEED);
        writer.flush();
        Files.copy(file.toPath(), output);
        writer.append(LINE_FEED);      
        writer.flush();
    }

    public void addFormField(PrintWriter writer, String boundary, String charset, final String LINE_FEED, String name, String value) {
        writer.append(boundary).append(LINE_FEED);
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_DISPOSITION, RestClientConstants.getTextPlainContent(name));
        addBodyHeaderFields(writer, LINE_FEED, RestClientConstants.API_CONTENT_TYPE, RestClientConstants.getTextPlainContent("UTF-8"));
        writer.append(LINE_FEED);
        writer.append(value);        
        writer.append(LINE_FEED);
        writer.flush();       
    }    
    
    private void addBodyHeaderFields(PrintWriter writer, String LINE_FEED, String param, String value) {
        writer.append(param + ": " + value);
        writer.append(LINE_FEED);
    }

    @Override
    public void clean() {
      headerImpl.clean();
      path.clean();
      path=null;
      if(body!=null)
        body.clean();
      body=null;
      formMultipartParams = null;
      method=null;
      headerImpl=null;
    }

    @Override
    public IRequestBody getBody() {
        return body;
    }

 
    private class CPHeaderImpl implements IHeader {

        private Map<String, Object> headerMap;

        public CPHeaderImpl() {
            headerMap = new HashMap<>();
        }

        @Override
        public Map<String, Object> getHeader() {
            return headerMap;
        }

        @Override
        public void addHeader(String name, Object value) {
            headerMap.put(name, value);
        }

        @Override
        public void clean() {
           headerMap.clear();
           headerMap=null;
        }
    }
}
