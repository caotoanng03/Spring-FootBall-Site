package com.example.nezok.models;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name="belepes")
public class Belepes {

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
    private Nezo nezo;

    @ManyToOne
    @JoinColumn(name = "meccsid", insertable = false, updatable = false)
    private Meccs meccs;

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

    public Nezo getNezo() {
        return nezo;
    }

    public void setNezo(Nezo nezo) {
        this.nezo = nezo;
    }

    public Meccs getMeccs() {
        return meccs;
    }

    public void setMeccs(Meccs meccs) {
        this.meccs = meccs;
    }
}
