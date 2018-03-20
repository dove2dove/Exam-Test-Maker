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
@Table(name = "Examparameters")
@NamedQueries({ @NamedQuery(name = "Examparameters.findAll", query = "SELECT e FROM Examparameters e") })
public class Examparameters implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "Id")
	private Integer id;
	@Column(name = "Examcode")
	private String examcode;
	@Column(name = "Examname")
	private String examname;
	@Lob
	@Column(name = "Examdescription")
	private String examdescription;
	
	public Examparameters() {
		this.id = 0;
	}
	public Examparameters(String examcode, String examname) {
		this.examcode = examcode;
		this.examname = examname;
	}

	public Examparameters(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExamcode() {
		return examcode;
	}

	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}

	public String getExamname() {
		return examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getExamdescription() {
		return examdescription;
	}

	public void setExamdescription(String examdescription) {
		this.examdescription = examdescription;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Examparameters)) {
			return false;
		}
		Examparameters other = (Examparameters) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "examdata.Examparameters[ id=" + id + " ]";
	}

}
