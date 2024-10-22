package com.project.accessDoc.entities;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema
@Entity
@Table(name = "permission")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ip_origin")
	private String ipOrigin;

	@Column(name = "description_origin")
	private String descriptionOrigin;

	@Column(name = "area_origin")
	private String areaOrigin;

	@Column(name = "ip_destination")
	private String ipDestination;

	@Column(name = "description_destination")
	private String descriptionDestination;

	@Column(name = "area_destination")
	private String areaDestination;

	@Column(name = "protocol")
	private String protocol;

	@Column(name = "ports")
	private String ports;

	@Column(name = "duration")
	private String duration;

	@ManyToOne
	@JoinColumn(name = "form_id") // Clave foránea a la tabla Form
	private Form form;

	// Constructor vacío
	public Permission() {
	}

	// Constructor completo
	public Permission(String ipOrigin, String descriptionOrigin, String areaOrigin, String ipDestination,
			String descriptionDestination, String areaDestination, String protocol, String ports, String duration,
			Form form) {
		this.ipOrigin = ipOrigin;
		this.descriptionOrigin = descriptionOrigin;
		this.areaOrigin = areaOrigin;
		this.ipDestination = ipDestination;
		this.descriptionDestination = descriptionDestination;
		this.areaDestination = areaDestination;
		this.protocol = protocol;
		this.ports = ports;
		this.duration = duration;
		this.form = form;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpOrigin() {
		return ipOrigin;
	}

	public void setIpOrigin(String ipOrigin) {
		this.ipOrigin = ipOrigin;
	}

	public String getDescriptionOrigin() {
		return descriptionOrigin;
	}

	public void setDescriptionOrigin(String descriptionOrigin) {
		this.descriptionOrigin = descriptionOrigin;
	}

	public String getAreaOrigin() {
		return areaOrigin;
	}

	public void setAreaOrigin(String areaOrigin) {
		this.areaOrigin = areaOrigin;
	}

	public String getIpDestination() {
		return ipDestination;
	}

	public void setIpDestination(String ipDestination) {
		this.ipDestination = ipDestination;
	}

	public String getDescriptionDestination() {
		return descriptionDestination;
	}

	public void setDescriptionDestination(String descriptionDestination) {
		this.descriptionDestination = descriptionDestination;
	}

	public String getAreaDestination() {
		return areaDestination;
	}

	public void setAreaDestination(String areaDestination) {
		this.areaDestination = areaDestination;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}
}
