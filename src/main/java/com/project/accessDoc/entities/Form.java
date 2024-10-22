package com.project.accessDoc.entities;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Schema
@Entity
@Table(name = "form")
public class Form implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date; // Fecha actual

	@Column(name = "name")
	private String name; // Nombre del archivo PDF

	@ManyToOne
	@JoinColumn(name = "signature_id") // Clave foránea a la tabla Signature
	private Signature signature;

	@Column(name = "status")
	private String status; // Indica si está cerrado o no

	// Constructor vacío
	public Form() {
	}

	// Constructor completo
	public Form(Date date, String name, Signature signature, String status) {
		this.date = date;
		this.name = name;
		this.signature = signature;
		this.status = status;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
