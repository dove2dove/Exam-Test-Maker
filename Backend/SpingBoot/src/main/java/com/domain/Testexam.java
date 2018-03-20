/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dove
 */
@Entity
@Table(name = "Testexam")
@NamedQueries({ @NamedQuery(name = "Testexam.findAll", query = "SELECT t FROM Testexam t") })
public class Testexam implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Column(name = "Username")
	private String username;
	@Column(name = "Examcode")
	private String examcode;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "Passscore")
	private BigDecimal passscore;
	@Column(name = "Totalscore")
	private BigDecimal totalscore;
	@Column(name = "Totalquestions")
	private Integer totalquestions;
	@Column(name = "Correctanswer")
	private Integer correctanswer;
	@Column(name = "durationmillsec")
	private Integer durationmillsec;
	@Column(name = "Startdatetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdatetime;
	@Column(name = "Examtime")
	private String examtime;
	@Column(name = "Status")
	private String status;

	public Testexam() {
		this.id = 0;
	}
	public Testexam(Integer id, String examcode, String username) {
		this.username = username;
		this.examcode = examcode;
		this.id = id;
	}

	public Testexam(Integer id) {
		this.id = id;
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

	public String getExamcode() {
		return examcode;
	}

	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}

	public BigDecimal getPassscore() {
		return passscore;
	}

	public void setPassscore(BigDecimal passscore) {
		this.passscore = passscore;
	}

	public BigDecimal getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(BigDecimal totalscore) {
		this.totalscore = totalscore;
	}

	public Integer getTotalquestions() {
		return totalquestions;
	}

	public void setTotalquestions(Integer totalquestions) {
		this.totalquestions = totalquestions;
	}

	public Integer getCorrectanswer() {
		return correctanswer;
	}

	public void setCorrectanswer(Integer correctanswer) {
		this.correctanswer = correctanswer;
	}

	public Integer getDurationmillsec() {
		return durationmillsec;
	}

	public void setDurationmillsec(Integer durationmillsec) {
		this.durationmillsec = durationmillsec;
	}

	public Date getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	public String getExamtime() {
		return examtime;
	}

	public void setExamtime(String examtime) {
		this.examtime = examtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Testexam)) {
			return false;
		}
		Testexam other = (Testexam) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "examdata.Testexam[ id=" + id + " ]";
	}

}
