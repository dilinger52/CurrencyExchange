package org.dilinger.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "currency")
public class Currency implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    String name;
    @Column
    Date date;
    @Column
    double averageExchange;

    @Column
    String source;

    public enum Source {
        MONOBANK,
        PRIVATEBANK,
        NBU
    }

    public Currency(String name, Date date, double averageExchange, String source) {
        this.id = 0;
        this.name = name;
        this.date = date;
        this.averageExchange = averageExchange;
        this.source = source;
    }

    public Currency() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAverageExchange() {
        return averageExchange;
    }

    public void setAverageExchange(double averageExchange) {
        this.averageExchange = averageExchange;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", source=" + source +
                ", averageExchange=" + averageExchange +
                '}';
    }
}
