/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pd;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

public class NewsDB {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public NewsDB(String pu) {
        init(pu);
    }
    
    private void init(String pu){
        try{
            emf = Persistence.createEntityManagerFactory(pu);
            em = emf.createEntityManager();
        }catch(Exception e){
            
        }
    }
    
    private void begin(){
        em.getTransaction().begin();
    }
    
    private void commit(){
        em.getTransaction().commit();
    }
    
    public List<News> getAll(){
        Query q = em.createNamedQuery("news.findAll");
        return q.getResultList();
    }
    
    public boolean save(News news){
        try{
            begin();
            em.persist(news);
            em.flush();
            commit();
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
           em.close();
        }
        
        return false;
    }
    
    public boolean update(News news){
        try{
            begin();
            em.persist(news);
            em.flush();
            commit();
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
           em.close();
        }
        
        return false;
    }
    
    public boolean remove(News news){
        try{
            begin();
            em.remove(news);
            commit();
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
           em.close();
        }
        
        return false;
    }
    
    public News get(Integer id){
       
       return em.find(News.class, id);
       //System.out.println(n);
//       return n;
    }
    
}
