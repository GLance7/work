package com.edu.entity;

import javax.persistence.*;

public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String collegename;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return collegename
     */
    public String getCollegename() {
        return collegename;
    }

    /**
     * @param collegename
     */
    public void setCollegename(String collegename) {
        this.collegename = collegename == null ? null : collegename.trim();
    }
}