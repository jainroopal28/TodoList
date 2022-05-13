package com.project.todo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "task")
public class Task {
	@Id
	@Column(name="task_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="desc")
	private String desc;

	@Column(name = "crt_dt")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="UTC")
	@Temporal(TemporalType.TIMESTAMP)
	private Date currentDate;

	@Column(name = "last_upd_dt")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone="UTC")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdDt;

	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	@Column(name="is_completed")
	private boolean isCompleted;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getLastUpdDt() {
		return lastUpdDt;
	}
	public void setLastUpdDt(Date lastUpdDt) {
		this.lastUpdDt = lastUpdDt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public boolean getIsCompleted() { 
		return isCompleted; 
	}
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted; 
	}
}
