package be.vdab.web;

import java.util.List;

import com.sun.istack.NotNull;

import be.vdab.entities.Filiaal;

class AfschrijvenForm {
@NotNull
private List<Filiaal> filialen;

public List<Filiaal> getFilialen() {
	return filialen;
}

public void setFilialen(List<Filiaal> filialen) {
	this.filialen = filialen;
}


}
