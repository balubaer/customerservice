package de.niklas.custemerservice.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class Auftraege implements Comparable<Auftraege> {
	@Id
	@Column(unique = true)
	private String auftragid;
	private String kundeid;
	private String created;
	private String lastchange;

	private String artikelnummer;

	public String getAuftragid() {
		return auftragid;
	}

	public String getKundeid() {
		return kundeid;
	}

	public String getCreated() {
		return created;
	}

	public String getLastchange() {
		return lastchange;
	}

	public void setAuftragid(String auftragid) {
		this.auftragid = auftragid;
	}

	public void setKundeid(String kundeid) {
		this.kundeid = kundeid;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public void setLastchange(String lastchange) {
		this.lastchange = lastchange;
	}

	public String getArtikelnummer() {
		return artikelnummer;
	}

	public void setArtikelnummer(String artikelnummer) {
		this.artikelnummer = artikelnummer;
	}

	private String trimTimeStamp(String timeStamp) {
		String result = timeStamp;
		int iend = lastchange.indexOf("+");
		if (iend != -1) {
			result = timeStamp.substring(0, iend);
		}
		return result;
	}

	public LocalDateTime getLastchangeTimeStamp() {

		String timeStamp = trimTimeStamp(this.lastchange);
		LocalDateTime dateTime = LocalDateTime.parse(timeStamp);
		return dateTime;
	}

	@Override
	public int compareTo(Auftraege o) {
		int result = 0;
		String oTimeStamp = trimTimeStamp(o.getLastchange());
		String timeStamp = trimTimeStamp(this.lastchange);
		LocalDateTime oDateTime = LocalDateTime.parse(oTimeStamp);
		LocalDateTime dateTime = LocalDateTime.parse(timeStamp);
		if (oDateTime.isAfter(dateTime)) {
			result = -1;
		}
		if (oDateTime.isBefore(dateTime)) {
			result = 1;
		}
		return result;
	}
}
