package com.pitufos.absolutemovies.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.pitufos.absolutemovies.entities.Usuario;

public class UsuarioDTO {

    private Long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private List<GeneroDTO> preferencias;
    private List<FavoritoDTO> favoritos;
    private List<InteracaoDTO> historico;

    public UsuarioDTO() {}

    public UsuarioDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.preferencias = usuario.getPreferencias()
            .stream().map(GeneroDTO::new).collect(Collectors.toList());
        this.favoritos = usuario.getFavoritos()
            .stream().map(FavoritoDTO::new).collect(Collectors.toList());
        this.historico = usuario.getHistorico()
            .stream().map(InteracaoDTO::new).collect(Collectors.toList());
    }
    
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(this.idUsuario);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        return usuario;
    }

    // Getters e Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<GeneroDTO> getPreferencias() { return preferencias; }
    public void setPreferencias(List<GeneroDTO> preferencias) { this.preferencias = preferencias; }
    public List<FavoritoDTO> getFavoritos() { return favoritos; }
    public void setFavoritos(List<FavoritoDTO> favoritos) { this.favoritos = favoritos; }
    public List<InteracaoDTO> getHistorico() { return historico; }
    public void setHistorico(List<InteracaoDTO> historico) { this.historico = historico; }

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
