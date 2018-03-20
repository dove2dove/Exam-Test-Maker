/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dove
 */
@Entity
@Table(name = "Questions")
@NamedQueries({
    @NamedQuery(name = "Questions.findAll", query = "SELECT q FROM Questions q")})
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Title")
    private String title;
    @Lob
    @Column(name = "Problemdescription")
    private String problemdescription;
    @Basic(optional = false)
    @Column(name = "Morethanoneanswer")
    private boolean morethanoneanswer;
    @Column(name = "Displayorder")
    private Integer displayorder;

    public Questions() {
    }

    public Questions(Integer id) {
        this.id = id;
    }

    public Questions(Integer id, boolean morethanoneanswer) {
        this.id = id;
        this.morethanoneanswer = morethanoneanswer;
    }
    public Questions(Integer id, String username, String title, String problemdescription, boolean morethanoneanswer, Integer displayorder) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.problemdescription = problemdescription;
        this.morethanoneanswer = morethanoneanswer;
        this.displayorder = displayorder;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblemdescription() {
        return problemdescription;
    }

    public void setProblemdescription(String problemdescription) {
        this.problemdescription = problemdescription;
    }

    public boolean getMorethanoneanswer() {
        return morethanoneanswer;
    }

    public void setMorethanoneanswer(boolean morethanoneanswer) {
        this.morethanoneanswer = morethanoneanswer;
    }

    public Integer getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(Integer displayorder) {
        this.displayorder = displayorder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questions)) {
            return false;
        }
        Questions other = (Questions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examdata.Questions[ id=" + id + " ]";
    }
    
}
