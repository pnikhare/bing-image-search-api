/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uncc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author jsing
 */
public class DispatchRequest {

    DispatchRequest() {
    }

    public static Result send(BingURL bingURL) throws IOException {
        String result = "";
        HttpURLConnection conn = null;
        Result res = new Result();

        try {

            StringBuilder urlBuilder = new StringBuilder(bingURL.getURL());
            StringBuilder paramBuilder = new StringBuilder();

            for (Map.Entry<String, String> entry : bingURL.getParameter().entrySet()) {
                paramBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            if (!bingURL.getParameter().isEmpty()) {
                urlBuilder.append("?");
                bingURL.setURL(urlBuilder.toString() + paramBuilder.substring(0, paramBuilder.length() - 1));
            }
            System.out.println("Sending request to : " + bingURL.getURL());
            URL url = new URL(bingURL.getURL());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(bingURL.getMethod());

            for (Map.Entry<String, String> entry : bingURL.getHeaders().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            BufferedReader br = null;
            String output;
            if (conn.getResponseCode() != 200 && conn.getResponseCode() != 204) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                System.out.println("Output from Server WHEN FAILED.... \n");
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                System.out.println("Output from Server .... \n");
            }

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result = result + output;
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            res.setResponseCode(conn.getResponseCode());
            System.out.println("conn.getContentType():"+conn.getContentType());
            res.setMediaType(conn.getContentType());
            res.setResponseMsg(result);
            if (conn != null) {
                conn.disconnect();
            }
            System.out.println("result got :" + result);
            return res;
        }
    }

    public static Result sendPost(BingURL bingURL) throws IOException {

        String result = "";
        HttpURLConnection conn = null;
        Result res = new Result();

        try {

            StringBuilder urlBuilder = new StringBuilder(bingURL.getURL());
            StringBuilder paramBuilder = new StringBuilder();

            for (Map.Entry<String, String> entry : bingURL.getParameter().entrySet()) {
                paramBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            if (!bingURL.getParameter().isEmpty()) {
                urlBuilder.append("?");
                bingURL.setURL(urlBuilder.toString() + paramBuilder.substring(0, paramBuilder.length() - 1));
            }
            System.out.println("Sending request to : " + bingURL.getURL());
            URL url = new URL(bingURL.getURL());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(bingURL.getMethod());

            for (Map.Entry<String, String> entry : bingURL.getHeaders().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                int read = 0;
                byte[] bytes = new byte[1024];
                System.out.println("bingURL.getFormData()"+bingURL.getFormData());
                
                if (bingURL.getFormData() != null) {
                    
                    InputStream is = bingURL.getFormData();
                    while ((read = is.read(bytes)) != -1) {
                        System.out.println("write form data to output stream");
                        os.write(bytes, 0, read);
                    }
                }
            }

            BufferedReader br = null;
            String output;
            if (conn.getResponseCode() != 200 && conn.getResponseCode() != 204) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
                System.out.println("Output from Server WHEN FAILED.... \n");
            } else {
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                System.out.println("Output from Server .... \n");
            }

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result = result + output;
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            res.setResponseCode(conn.getResponseCode());
            res.setMediaType(conn.getContentType());
            res.setResponseMsg(result);
            if (conn != null) {
                conn.disconnect();
            }
            System.out.println("result got :" + result);
            return res;
        }
    }
}
