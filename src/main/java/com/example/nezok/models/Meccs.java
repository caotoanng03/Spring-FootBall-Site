package com.example.nezok.models;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
public class Meccs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date datum;

    private String kezdes;

    private Integer belepo;

    private String tipus;

    @OneToMany
    @JoinColumn(name="meccsid")
    private List<Belepes> belepesList;

    public void setBelepesList(List<Belepes> belepesList) {
        this.belepesList = belepesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setKezdes(String kezdes) {
        this.kezdes = kezdes;
    }

    public void setBelepo(Integer belepo) {
        this.belepo = belepo;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}

