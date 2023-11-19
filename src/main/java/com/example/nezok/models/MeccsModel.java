package com.example.nezok.models;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="meccs")
public class MeccsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String datum;

    @Getter
    private String kezdes;

    @Getter
    private Integer belepo;

    @Getter
    private String tipus;

    @Getter
    @OneToMany
    @JoinColumn(name="meccsid")
    private List<BelepesModel> belepesModelList;

    public void setBelepesList(List<BelepesModel> belepesModelList) {
        this.belepesModelList = belepesModelList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDatum(String datum) {
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

