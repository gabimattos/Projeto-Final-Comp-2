package model;

import java.io.Serializable;

/**
 * Representa um pais no mundo e sua localizacao geografica.
 * 
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public class Pais implements Comparable<Pais>, Serializable{
	private static final long serialVersionUID = 2000L;
	private String nome;
	private String codigo;
	private String slug;
	private float latitude;
	private float longitude;
	
	/**
	 * Cria uma nova instancia de pais com os dados fornecidos.
	 * 
	 * @param nome	nome do pais.
	 * @param codigo	codigo do pais. Ex: Brasil: BR, Uruguai: UR.
	 * @param slug	identificador do pais usado pela COVID-19 API.
	 * @param latitude	latitude geografica do centro do pais.
	 * @param longitude	longitude geografica do centro do pais.
	 */
	public Pais(String nome, String codigo, String slug, float latitude,
			float longitude) {
		this.nome = nome;
		this.codigo = codigo;
		this.slug = slug;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Funcao getter do nome do pais.
	 * 
	 * @return	Nome do pais.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Funcao setter do nome do pais.
	 * 
	 * @param nome	Novo nome do pais.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Funcao getter do codigo do pais. Ex: Brasil: BR, Uruguai: UR.
	 * 
	 * @return	Codigo do pais.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Funcao setter do codigo do pais. Ex: Brasil: BR, Uruguai: UR.
	 * 
	 * @param codigo	Novo codigo do pais.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Funcao getter do slug do pais. O slug e o identificador do pais, usado
	 * pela COVID-19 API.
	 * 
	 * @return	Slug do pais.
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * Funcao setter do slug do pais. O slug e o identificador do pais, usado
	 * pela COVID-19 API.
	 * 
	 * @param slug	Novo slug do pais.
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * Funcao getter da latitude do pais.
	 * 
	 * @return	Latitude do pais.
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Funcao setter da latitude do pais.
	 * 
	 * @param latitude	Noca latitude do pais.
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * Funcao getter da longitude do pais.
	 * 
	 * @return	Longitude do pais.
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Funcao setter da latitude do pais.
	 * 
	 * @param longitude	Nova longitude do pais.
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	@Override
	public int compareTo(Pais o) {
		return slug.compareTo(o.slug);
	}
	
}
