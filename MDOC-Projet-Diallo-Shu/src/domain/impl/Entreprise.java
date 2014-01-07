package domain.impl;

import domain.IEntreprise;

public class Entreprise extends Contact implements IEntreprise {
	private int numSiret;

	public Entreprise(){}
	
	public Entreprise(int numSiret) {
		super();
		this.numSiret = numSiret;
	}

	public int getNumSiret() {
		return numSiret;
	}

	public void setNumSiret(int numSiret) {
		this.numSiret = numSiret;
	}
	
	
}
