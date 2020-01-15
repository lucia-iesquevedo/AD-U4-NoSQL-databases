/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Purchase { 
    private ObjectId iditem;
    private String nombreitem;
    private LocalDateTime date;

    public Purchase(ObjectId iditem, String nombreitem, LocalDateTime date) {
        this.iditem = iditem;
        this.nombreitem = nombreitem;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Purchase{" + "iditem=" + iditem + ", nombreitem=" + nombreitem + ", date=" + date + '}';
    }
    

    public Purchase() {
    }
    
    
    
    
    public ObjectId getIditem() {
        return iditem;
    }

    public void setIditem(ObjectId iditem) {
        this.iditem = iditem;
    }

    public String getNombreitem() {
        return nombreitem;
    }

    public void setNombreitem(String nombreitem) {
        this.nombreitem = nombreitem;
    }


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

   
}
