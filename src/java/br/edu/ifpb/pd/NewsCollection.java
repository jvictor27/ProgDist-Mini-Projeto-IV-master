/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pd;

import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news")
public class NewsCollection {
    
    private int count_news = 1;

    private NewsDB db;
    /**
     * Creates a new instance of NewsCollection
     */
    public NewsCollection() {
        db = new NewsDB("NewsPU");
    }
    
    @POST
    @Consumes({ "application/x-www-form-urlencoded" })
    @Produces({MediaType.APPLICATION_JSON})
    public Response postNews(@FormParam("id")String id,
                        @FormParam("author")String author,
                        @FormParam("title")String title,
                        @FormParam("content")String content){

        try{
            Integer int_id = Integer.parseInt(id);
            
            News n = new News(int_id, author, title, content);
            if(db.get(int_id) != null){
               return Response.status(Response.Status.CONFLICT).build();
            }
            if(db.save(n)){
               return Response.ok(n, MediaType.APPLICATION_JSON).build();
            }
            
        }catch(Exception e){
            print(e.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @PUT
    @Path("{id}/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateNews(@PathParam("id")String id,
                        @PathParam("title")String title){

        try{
            Integer int_id = Integer.parseInt(id);
            
            News n = db.get(int_id);
            if(n == null){
               return Response.status(Response.Status.NOT_FOUND).build();
            }
            
            n.setTitle(title);
            if(db.update(n)){
               return Response.ok(n, MediaType.APPLICATION_JSON).build();
            }
            
        }catch(Exception e){
            print(e.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
//    @Produces({MediaType.APPLICATION_JSON});
    @Produces({MediaType.TEXT_HTML})
    public Response getNews(@QueryParam("id") String id) {
        System.out.println("Qqqq");
        try{
            Integer id_int = Integer.parseInt(id);
            News n = db.get(id_int);
            if(n != null){
//                return Response.ok(n, MediaType.APPLICATION_JSON).build();
            return Response.status(200).entity(
                            "<h2>" + n.getTitle() + "</h2>"+
                            "<smal>" + n.getDate() + "</small>"+
                            "<span>" + n.getAuthor() + "</span>"+
                            "<p>" + n.getContent() + "</p>"
                    ).build();
            }
        }catch(Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response deleteNews(@PathParam("id") String id){
        try{
            Integer int_id = Integer.parseInt(id);
            News n = db.get(int_id);
            
            if(n == null){
               return Response.status(Response.Status.NOT_FOUND).build();
            }
            if(db.remove(n)){
               return Response.ok(n, MediaType.APPLICATION_XML).build();
            }
            
        }catch(Exception e){
            print(e.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    public String print(String m){
        System.out.println(m);
        return m;
    }
    
    
}
