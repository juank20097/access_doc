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

	@Column(name = "signature_archive")
    private byte[] signatureArchive;  // Archivo PK12 de la firma

    // Constructor vacío
    public Signature() {}

    // Constructor completo
    public Signature(byte[] signatureArchive) {
        this.signatureArchive = signatureArchive;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getSignatureArchive() {
        return signatureArchive;
    }

    public void setSignatureArchive(byte[] signatureArchive) {
        this.signatureArchive = signatureArchive;
    }
}
