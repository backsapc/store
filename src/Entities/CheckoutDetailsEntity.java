package Entities;

import javax.persistence.*;

@Entity
@Table(name = "CHECKOUTDETAILS", schema = "PUBLIC", catalog = "PUBLIC")
public class CheckoutDetailsEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "ORDERID", nullable = true)
    private Integer orderId;
    @Basic
    @Column(name = "PRODUCTNAME", nullable = true, length = 250)
    private String productName;
    @Basic
    @Column(name = "AMOUNT", nullable = false)
    private int amount;
    @Basic
    @Column(name = "PRICE", nullable = false, precision = 0)
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckoutDetailsEntity that = (CheckoutDetailsEntity) o;

        if (id != that.id) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        return result;
    }


}
