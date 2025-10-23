package com.pitufos.absolutemovies.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "ia")
public class IA implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ex.: "gpt-4o", "gpt-3.5-turbo" — dá para guardar qual modelo usar.
     */
    @Column(nullable = false)
    private String modelo;

    /**
     * Prompt default / template que será usado como base nas chamadas.
     * Pode conter placeholders (ex: {{perfil_usuario}}) que o service substituirá.
     */
    @Lob
    @Column(name = "prompt_template", columnDefinition = "TEXT")
    private String prompt;

    /**
     * Descrição opcional ou notas administrativas sobre este "config" de IA.
     */
    @Column(length = 1000)
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public IA() {}

    public IA(String modelo, String prompt) {
        this.modelo = modelo;
        this.prompt = prompt;
    }

    // ===== Getters / Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ===== equals / hashCode / toString =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IA)) return false;
        IA ia = (IA) o;
        return Objects.equals(id, ia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IA{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", ativo=" + ativo +
                ", createdAt=" + createdAt +
                '}';
    }
}
