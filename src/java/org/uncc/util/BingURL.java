/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uncc.util;

import java.io.InputStream;
import java.util.HashMap;

/**
 *
 * @author jsing
 */
public class BingURL {
    
    private String subscriptionKey = "814af20b12b446b59523ea43362868a0";
    //private String subscriptionKey2 = "facb7d83529041feb66857596abf30d8";
    public final String BaseUrl;
    private String URL;
    private String Method;
    private InputStream formData = null;
    private HashMap<String, String> headers;
    private HashMap<String, String> parameter;

    public BingURL(){
        this.BaseUrl = "https://api.cognitive.microsoft.com/bing/v7.0/images";
        headers = new HashMap<>();
        headers.put("Accept","application/json");
        headers.put("Ocp-Apim-Subscription-Key",subscriptionKey); 
        parameter = new HashMap<>();
        
    }
    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String Method) {
        this.Method = Method;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }

    public InputStream getFormData() {
        return formData;
    }

    public void setFormData(InputStream formData) {
        this.formData = formData;
    }

    
}
