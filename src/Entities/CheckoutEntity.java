package Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "CHECKOUT", schema = "PUBLIC", catalog = "PUBLIC")
public class CheckoutEntity {
    private int id;
    private String username;
    private Date date;
    private Time time;
    private String address;
    private String deliveredby;
    private Double total;
    private String currency;
    private java.util.Date fullDate;

    public java.util.Date getFullDate() {
        Timestamp result =  Timestamp.valueOf(date.toString() + ' ' + time.toString());
        return result;
    }

    public void setFullDate(java.util.Date fullDate) {
        this.fullDate = fullDate;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 60)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "DATE", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "TIME", nullable = true)
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 8000)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "DELIVEREDBY", nullable = false, length = 1000)
    public String getDeliveredby() {
        return deliveredby;
    }

    public void setDeliveredby(String deliveredby) {
        this.deliveredby = deliveredby;
    }

    @Basic
    @Column(name = "TOTAL", nullable = true, precision = 0)
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Basic
    @Column(name = "CURRENCY", nullable = true, length = 5)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckoutEntity entity = (CheckoutEntity) o;

        if (id != entity.id) return false;
        if (username != null ? !username.equals(entity.username) : entity.username != null) return false;
        if (date != null ? !date.equals(entity.date) : entity.date != null) return false;
        if (time != null ? !time.equals(entity.time) : entity.time != null) return false;
        if (address != null ? !address.equals(entity.address) : entity.address != null) return false;
        if (deliveredby != null ? !deliveredby.equals(entity.deliveredby) : entity.deliveredby != null) return false;
        if (total != null ? !total.equals(entity.total) : entity.total != null) return false;
        if (currency != null ? !currency.equals(entity.currency) : entity.currency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (deliveredby != null ? deliveredby.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
