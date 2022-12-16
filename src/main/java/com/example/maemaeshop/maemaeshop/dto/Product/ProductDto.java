package com.example.maemaeshop.maemaeshop.dto.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("describe")
    private String describe;
    @JsonProperty("price")
    private int price;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("pictures")
    private String pictures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return price == that.price && amount == that.amount && Objects.equals(productName, that.productName) && Objects.equals(title, that.title) && Objects.equals(describe, that.describe) && Objects.equals(pictures, that.pictures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, title, describe, price, amount, pictures);
    }
}
