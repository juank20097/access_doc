package com.project.accessDoc.entities;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Schema
@Entity
@Table(name = "signature")
public class Signature implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "signature_archive", length = 15000)
    private String signatureArchive;
    
	@Column(name = "signature_password")
    private String signaturePassword;  

    // Constructor vacío
    public Signature() {}

	/**
	 * @return the signatureArchive
	 */
	public String getSignatureArchive() {
		return signatureArchive;
	}

	/**
	 * @param signatureArchive the signatureArchive to set
	 */
	public void setSignatureArchive(String signatureArchive) {
		this.signatureArchive = signatureArchive;
	}

	/**
	 * @return the signaturePassword
	 */
	public String getSignaturePassword() {
		return signaturePassword;
	}

	/**
	 * @param signaturePassword the signaturePassword to set
	 */
	public void setSignaturePassword(String signaturePassword) {
		this.signaturePassword = signaturePassword;
	}

    

}
