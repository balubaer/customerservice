package de.niklas.custemerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Kunde {

	@Id
	@Column(unique = true)
	private Long kundenid;

	private String vorname;
	private String nachname;
	private String email;
	private String strasse;
	private String strassenzusatz;
	private String ort;
	private String land;
	private String plz;
	private String firmenname;

	public Long getKundenid() {
		return kundenid;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public String getEmail() {
		return email;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getStrassenzusatz() {
		return strassenzusatz;
	}

	public String getOrt() {
		return ort;
	}

	public String getLand() {
		return land;
	}

	public String getPlz() {
		return plz;
	}

	public String getFirmenname() {
		return firmenname;
	}

	public void setKundenid(Long kundenid) {
		this.kundenid = kundenid;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public void setStrassenzusatz(String strassenzusatz) {
		this.strassenzusatz = strassenzusatz;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public void setFirmenname(String firmenname) {
		this.firmenname = firmenname;
	}

}
