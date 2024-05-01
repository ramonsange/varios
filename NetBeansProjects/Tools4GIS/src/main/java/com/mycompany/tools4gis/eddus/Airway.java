package com.mycompany.tools4gis.eddus;

import java.util.List;

public class Airway {
    private long id;
    private String name;
    private List<Fixpoint> fixpoints;

    public Airway(long id, String name, List<Fixpoint> fixpoints) {
        this.id = id;
        this.name = name;
        this.fixpoints = fixpoints;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fixpoint> getFixPoints() {
        return fixpoints;
    }

    public void setFixPoints(List<Fixpoint> fixpoints) {
        this.fixpoints = fixpoints;
    }
}