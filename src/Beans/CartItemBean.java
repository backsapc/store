package Beans;

import java.io.Serializable;

/**
 * Created by alexthor on 14.10.17.
 */
public class CartItemBean implements Serializable

{
    public CartItemBean(){
        this.product = "";
        this.numberOrdered = 0;
        this.price = 0;
    }

    public CartItemBean(String product, int numberOrdered, double price){
        this.product = product;
        this.numberOrdered = numberOrdered;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getNumberOrdered() {
        return numberOrdered;
    }

    public void setNumberOrdered(int numberOrdered) {
        this.numberOrdered = numberOrdered;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String product;
    private int numberOrdered;
    private double price;
}