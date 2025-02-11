package modelo;
// Generated 11 feb 2025, 17:57:30 by Hibernate Tools 6.5.1.Final

/**
 * Musica generated by hbm2java
 */
public class Musica implements java.io.Serializable {

	private Integer idMusica;
	private Compositor compositor;
	private Estilo estilo;
	private String descMusica;

	public Musica() {
	}

	public Musica(Compositor compositor, Estilo estilo, String descMusica) {
		this.compositor = compositor;
		this.estilo = estilo;
		this.descMusica = descMusica;
	}

	public Integer getIdMusica() {
		return this.idMusica;
	}

	public void setIdMusica(Integer idMusica) {
		this.idMusica = idMusica;
	}

	public Compositor getCompositor() {
		return this.compositor;
	}

	public void setCompositor(Compositor compositor) {
		this.compositor = compositor;
	}

	public Estilo getEstilo() {
		return this.estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	public String getDescMusica() {
		return this.descMusica;
	}

	public void setDescMusica(String descMusica) {
		this.descMusica = descMusica;
	}

}
