package asw.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TUsuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String usuario; // Clave primaria
	private String contraseña;

	@OneToOne
	@JoinColumn(name = "CIUDADANO_ID")
	private Ciudadano ciudadano;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Sugerencia> sugerencias = new HashSet<>();
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Comentario> comentarios = new HashSet<>();

	@Transient
	@JsonIgnore
	private Sugerencia sugerenciaMejorValorada;
	@Transient
	@JsonIgnore
	private int mediaVotosPositivosTodasSugerencias;
	
	Usuario() {
	};

	public Usuario(String usuario) {
		this.usuario = usuario;
	}

	public Usuario(String usuario, String contraseña, Ciudadano ciudadano) {
		this(usuario);
		this.contraseña = contraseña;
		this.ciudadano = ciudadano;
	}

	public Long getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	protected void _setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public Set<Sugerencia> getSugerencias() {
		return new HashSet<>(sugerencias);
	}

	protected Set<Sugerencia> _getSugerencias() {
		return sugerencias;
	}

	public void setSugerencias(Set<Sugerencia> sugerencias) {
		this.sugerencias = sugerencias;
	}

	public Set<Comentario> getComentarios() {
		return new HashSet<>(comentarios);
	}

	protected Set<Comentario> _getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	protected void _addSugerencia(Sugerencia sugerencia) {
		sugerencias.add(sugerencia);
	}

	protected void _removeSugerencia(Sugerencia sugerencia) {
		sugerencias.remove(sugerencia);
	}

	protected void _addComentario(Comentario comentario) {
		comentarios.add(comentario);
	}

	protected void _removeComentario(Comentario comentario) {
		comentarios.remove(comentario);
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", contraseña=" + contraseña + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public double getMediaVotosPositivosTodasSugerencias() {
		int votos = 0;
		int votosPositivos = 0;

		for (Sugerencia s : sugerencias) {
			votos += s.getVotosPositivos() + s.getVotosNegativos();
			votosPositivos += s.getVotosPositivos();
		}
		if(votos == 0)
			return 0;
		else {
			mediaVotosPositivosTodasSugerencias = votosPositivos / votos;
			return mediaVotosPositivosTodasSugerencias;
		}
	}

	public Sugerencia getSugerenciaMejorValorada() {

		for (Sugerencia s : sugerencias) {
			if (sugerenciaMejorValorada != null) {
				if (s.getValoracion() > sugerenciaMejorValorada.getValoracion()) {
					sugerenciaMejorValorada = s;
				}
			} else {
				sugerenciaMejorValorada = s;
			}
		}

		return sugerenciaMejorValorada;
	}
}