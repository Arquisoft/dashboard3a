package asw.model;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import asw.model.Association.Asignar;
import asw.model.Association.AsignarCategoria;
import asw.model.Association.AsignarSugerencia;

@Entity
@Table(name = "TSugerencias")
public class Sugerencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String contenido;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Usuario usuario;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Categoria categoria;
	private int votosPositivos;
	private int votosNegativos;
	
	@Transient
	private double valoracion;
	@Transient
	private int votosTotales;
	@Transient
	private int numComentarios;
	
	@OneToMany(mappedBy="sugerencia", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Comentario> comentarios = new HashSet<>();
	
	Sugerencia(){}

	public Sugerencia(String contenido, Categoria categoria, Usuario usuario) {
		super();
		this.contenido = contenido;
		this.fecha = new Date();
		this.votosPositivos = 0;
		this.votosNegativos = 0;
		AsignarSugerencia.link(usuario, this);
		AsignarCategoria.link(categoria, this);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public Long getId() {
		return id;
	}
	
	protected void _setUsuario(Usuario usuario){
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	protected void _setCategoria(Categoria categoria){
		this.categoria = categoria;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public int getVotosPositivos() {
		return votosPositivos;
	}
	
	public int getVotosNegativos() {
		return votosNegativos;
	}
	
	protected Set<Comentario> _getComentarios() {
		return comentarios;
	}
	
	public Set<Comentario> getComentarios() {
		return new HashSet<>(comentarios);
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	protected void _addComentario(Comentario comentario){
		comentarios.add(comentario);
	}
	
	public void addVotoPositivo(){
		this.votosPositivos++;
	}
	
	public void addVotoNegativo() {
		this.votosNegativos++;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void setVotosPositivos(int votosPositivos) {
		this.votosPositivos = votosPositivos;
	}
	
	public void setVotosNegativos(int votosNegativos) {
		this.votosNegativos = votosNegativos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((contenido == null) ? 0 : contenido.hashCode());
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
		Sugerencia other = (Sugerencia) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (contenido == null) {
			if (other.contenido != null)
				return false;
		} else if (!contenido.equals(other.contenido))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sugerencia [id=" + id + ", contenido=" + contenido + ", usuario=" + usuario + ", categoria=" + categoria
				+ "]";
	}
	
	public double getValoracion() {
		int votos = votosPositivos + votosNegativos;
		if(votos == 0)
			valoracion = 0;
		else
			valoracion = votosPositivos/votos;
		return valoracion;
	}
	
	public int getVotosTotales() {
		votosTotales = votosNegativos + votosPositivos;
		return votosTotales;
	}
	
	public int getNumComentarios() {
		numComentarios = comentarios.size();
		return numComentarios;
	}
}