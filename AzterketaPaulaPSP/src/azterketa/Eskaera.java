package azterketa;

import java.io.Serializable;

public class Eskaera implements Serializable{

	private int ID;
	private String pasahitza;
	private String irudia;

	public Eskaera(int iD, String pasahitza, String irudia) {
		ID = iD;
		this.pasahitza = pasahitza;
		this.irudia = irudia;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}

	public String getIrudia() {
		return irudia;
	}

	public void setIrudia(String irudia) {
		this.irudia = irudia;
	}

}
