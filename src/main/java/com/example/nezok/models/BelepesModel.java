package com.example.nezok.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Time;

@Entity
@Table(name="belepes")
public class BelepesModel {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Column(name="nezoid")
    private Integer nezoid;
    @Getter
    @Column(name="meccsid")
    private Integer meccsid;
    @Getter
    @Column(name="idopont")
    private String idopont;
    @ManyToOne
    @JoinColumn(name = "nezoid", insertable = false, updatable = false)
    private NezoModel nezoModel;

    @ManyToOne
    @JoinColumn(name = "meccsid", insertable = false, updatable = false)
    private MeccsModel meccsModel;

    public void setIdopont(String idopont) {
        this.idopont = idopont;
    }

    public void setNezoid(Integer nezoid) {
        this.nezoid = nezoid;
    }

    public void setMeccsid(Integer meccsid) {
        this.meccsid = meccsid;
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
