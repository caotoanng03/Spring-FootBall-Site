package com.example.nezok.models;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name="belepes")
public class BelepesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nezoid")
    private Integer nezoid;
    @Column(name="meccsid")
    private Integer meccsid;
    @Column(name="idopont")
    private Time idopont;
    @ManyToOne
    @JoinColumn(name = "nezoid", insertable = false, updatable = false)
    private NezoModel nezoModel;

    @ManyToOne
    @JoinColumn(name = "meccsid", insertable = false, updatable = false)
    private MeccsModel meccsModel;

    public Integer getNezoid() {
        return nezoid;
    }

    public void setNezoid(Integer nezoid) {
        this.nezoid = nezoid;
    }

    public Integer getMeccsid() {
        return meccsid;
    }

    public void setMeccsid(Integer meccsid) {
        this.meccsid = meccsid;
    }

    public Integer getId() {
        return id;
    }

    public NezoModel getNezo() {
        return nezoModel;
    }

    public void setNezo(NezoModel nezoModel) {
        this.nezoModel = nezoModel;
    }

    public MeccsModel getMeccs() {
        return meccsModel;
    }

    public void setMeccs(MeccsModel meccsModel) {
        this.meccsModel = meccsModel;
    }
}
