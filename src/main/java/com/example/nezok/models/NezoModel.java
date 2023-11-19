package com.example.nezok.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="nezo")
public class NezoModel {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String nev;

    @Getter
    private Integer ferfi;

    @Getter
    private Integer berletes;

    @Getter
    @OneToMany
    @JoinColumn(name="nezoid")
    private List<BelepesModel> belepesModelList;

    public void setBelepesModelList(List<BelepesModel> belepesModelList) {
        this.belepesModelList = belepesModelList;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setFerfi(Integer ferfi) {
        this.ferfi = ferfi;
    }


    public void setBerletes(Integer berletes) {
        this.berletes = berletes;
    }
}
