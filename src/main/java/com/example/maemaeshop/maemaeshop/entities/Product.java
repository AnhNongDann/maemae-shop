package com.example.maemaeshop.maemaeshop.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String title;
    private String describe;
    private int price;
    private int amount;
    private String pictures;


    public Product() {
    }

    public Product(String productName, String title, String describe, int price, int amount, String pictures) {
        this.productName = productName;
        this.title = title;
        this.describe = describe;
        this.price= price;
        this.amount= amount;
        this.pictures= pictures;
    }
}
