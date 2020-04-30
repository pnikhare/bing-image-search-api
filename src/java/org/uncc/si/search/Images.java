/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uncc.si.search;

/**
 *
 * @author jsing
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.uncc.util.BingURL;
import org.uncc.util.DispatchRequest;
import org.uncc.util.Result;

@Path("images")
public class Images {

    @GET
    @Path("trending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        Result res = null;
        try {
            BingURL bingURL = new BingURL();
            bingURL.setMethod("GET");
            bingURL.setURL(bingURL.BaseUrl + "/trending");

            System.out.print("getting trending images ...");

            res = DispatchRequest.send(bingURL);
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).build();
        } catch (IOException ex) {
            System.out.println("Exception occured while fetching" + ex);
            if (res == null) {
                res = new Result();
                res.setResponseCode(HttpServletResponse.SC_BAD_REQUEST);
                res.setResponseMsg("Error :" + ex);
            }
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).build();
        }
    }

    @GET
    @Path("details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response details(@QueryParam("insightsToken") String insightsToken, @QueryParam("query") String query) {

        Result res = null;
        try {
            System.out.println("getting details of images ..");

            BingURL bingURL = new BingURL();
            bingURL.setMethod("GET");
            HashMap<String, String> parameter = null;
            parameter = bingURL.getParameter();
            System.out.println("got insightsToken" + insightsToken);
            if (query != null) {
                parameter.put("query", query);
                System.out.println("added q");
            }
            if (insightsToken != null) {
                parameter.put("insightsToken", insightsToken);
                System.out.println("added insightsToken");
            }

            bingURL.setURL(bingURL.BaseUrl + "/details");
            bingURL.setParameter(parameter);

            res = DispatchRequest.send(bingURL);
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).type(res.getMediaType()).build();
        } catch (Exception ex) {
            System.out.println("Exception occurred While sending request for details of images " + ex);
            if (res == null && res.getResponseMsg().equals(null)) {
                res = new Result();
                res.setResponseCode(HttpServletResponse.SC_BAD_REQUEST);
                res.setResponseMsg("Error :" + ex);
            }
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).type(res.getMediaType()).build();
        }
    }

//    @POST
//    @Path("details")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String detailspost(@QueryParam("insightsToken") String insightsToken, @QueryParam("query") String query,
//            @FormDataParam("form") InputStream form) {
//        
    @POST
    @Path("details")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response detailspost(@QueryParam("insightsToken") String insightsToken, @QueryParam("query") String query){
//                                @Context HttpHeaders httpheaders, @FormDataParam("imgurl") InputStream form) {

        Result res = new Result();
        try {
            System.out.println("getting details of images by post..");

            BingURL bingURL = new BingURL();
            bingURL.setMethod("POST");
            HashMap<String, String> headers = bingURL.getHeaders();
            HashMap<String, String> parameter = new HashMap<String, String>();
            // parameter =  bingURL.getParameter();
            System.out.println("got insightsToken" + insightsToken);
            if (insightsToken != null) {
                parameter.put("insightsToken", insightsToken);
                System.out.println("added insightsToken");
            }

            if (query != null) {
                parameter.put("query", query);
                System.out.println("added q");
            }

            bingURL.setURL(bingURL.BaseUrl + "/details");
            bingURL.setParameter(parameter);

//            String contentType = httpheaders.getHeaderString("Content-Type");
//            System.out.println(contentType);
//            if (contentType != null){
//               // headers.put("Content-Type", "multipart/form-data");
//                bingURL.setFormData(form);
//            }
            res = DispatchRequest.sendPost(bingURL);
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).type(res.getMediaType()).build();

        } catch (Exception ex) {
            System.out.println("Exception occurred While sending request for details of images " + ex);
            if (res == null && res.getResponseMsg().equals(null)) {
                res = new Result();
                res.setResponseCode(HttpServletResponse.SC_BAD_REQUEST);
                res.setResponseMsg("Error :" + ex);
            }

            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).type(res.getMediaType()).build();
        }
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("q") String query, @QueryParam("count") String count, @QueryParam("offset") String offset, @QueryParam("mkt") String mkt,
            @QueryParam("safeSearch") String safeSearch) {

        Result res = new Result();

        try {
            System.out.println("getting result of image search ..");

            BingURL bingURL = new BingURL();
            bingURL.setMethod("GET");

            HashMap<String, String> parameter = new HashMap<>();
            System.out.println("got query" + query);
            if (query != null) {
                parameter.put("q", query);
            }
//            else {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                result = "Error : query parameter is required";
//                resp.setResponseCode(400);
//                resp.setResponseMsg("BAD_REQUEST : { Error :query parameter is required } ");
//                System.out.println("Error : query parameter is required");
//                return resp;
//            }
            if (count != null) {
                parameter.put("count", count);
            }
            if (offset != null) {
                parameter.put("offset", offset);
            }
            if (mkt != null) {
                parameter.put("mkt", mkt);
            }
            if (safeSearch != null) {
                parameter.put("safeSearch", safeSearch);
            }

            bingURL.setURL(bingURL.BaseUrl + "/search");
            bingURL.setParameter(parameter);

            res = DispatchRequest.send(bingURL);
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).type(res.getMediaType()).build();

        } catch (Exception ex) {
            System.out.println("Exception occurred While sending request for details for image search " + ex);
            if (res == null && res.getResponseMsg().equals(null)) {
                res = new Result();
                res.setResponseCode(HttpServletResponse.SC_BAD_REQUEST);
                res.setResponseMsg("Error :" + ex);
            }
            return Response.status(res.getResponseCode()).entity(res.getResponseMsg()).type(res.getMediaType()).build();
        }

    }
}
