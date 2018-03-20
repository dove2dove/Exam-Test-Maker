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
@Table(name = "Multiplechoiceanswers")
@NamedQueries({
    @NamedQuery(name = "Multiplechoiceanswers.findAll", query = "SELECT m FROM Multiplechoiceanswers m")})
public class Multiplechoiceanswers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @Column(name = "Title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "Answerchoice")
    private String answerchoice;
    @Basic(optional = false)
    @Column(name = "Displayorder")
    private String displayorder;
    @Basic(optional = false)
    @Column(name = "Correctanswer")
    private boolean correctanswer;
    @Basic(optional = false)
    @Column(name = "Candidateanswer")
    private boolean candidateanswer;

	public Multiplechoiceanswers() {
		this.id = 0;
	}
    public Multiplechoiceanswers(Integer id) {
        this.id = id;
    }

    public Multiplechoiceanswers(Integer id, String username, String title, String answerchoice, String displayorder, boolean correctanswer, boolean candidateanswer) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.answerchoice = answerchoice;
        this.displayorder = displayorder;
        this.correctanswer = correctanswer;
        this.candidateanswer = candidateanswer;
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

    public String getAnswerchoice() {
        return answerchoice;
    }

    public void setAnswerchoice(String answerchoice) {
        this.answerchoice = answerchoice;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public boolean getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(boolean correctanswer) {
        this.correctanswer = correctanswer;
    }

    public boolean getCandidateanswer() {
        return candidateanswer;
    }

    public void setCandidateanswer(boolean candidateanswer) {
        this.candidateanswer = candidateanswer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Multiplechoiceanswers)) {
            return false;
        }
        Multiplechoiceanswers other = (Multiplechoiceanswers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examdata.Multiplechoiceanswers[ id=" + id + " ]";
    }
    
}
