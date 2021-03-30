package com.lambda.foodtruck.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;

@Entity
@Table(name = "images")
public class Images extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imgid;

    private ImageIcon image;

    @ManyToOne()
    @JoinColumn(name = "menuid")
    @JsonIgnoreProperties(value = "itemphotos",allowSetters = true)
    private Menu menu;

    public Images()
    {
    }


    public long getImgid()
    {
        return imgid;
    }

    public void setImgid(long imgid)
    {
        this.imgid = imgid;
    }

    public ImageIcon getImage()
    {
        return image;
    }

    public void setImage(Image image,String filename)
    {
        if(filename!=null)
        {
            this.image = new ImageIcon(filename);
        }
        else if(image!=null)
        {
           this.image = new ImageIcon(image) ;
        }
    }
}
