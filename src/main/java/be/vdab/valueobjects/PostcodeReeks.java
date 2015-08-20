package be.vdab.valueobjects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import be.vdab.constraints.PostcodeReeksVanKleinerDanOfGelijkAanTot;

@PostcodeReeksVanKleinerDanOfGelijkAanTot
public class PostcodeReeks {

	private final static int MIN_POSTCODE = 1000;
	private final static int MAX_POSTCODE = 9999;
	@NotNull @Range(min = MIN_POSTCODE,max = MAX_POSTCODE) 
	private Integer vanpostcode;
	@NotNull @Range(min = MIN_POSTCODE,max = MAX_POSTCODE) 
	private Integer totpostcode;
	
	
	

	public Integer getVanpostcode() {
		return vanpostcode;
	}

	public Integer getTotpostcode() {
		return totpostcode;
	}
	

	public boolean bevat(int postcode) {
		// bevat de reeks een bepaalde postcode ? (wordt gebuikt in de DAO
		// layer)
		return postcode >= vanpostcode && postcode <= totpostcode;
	}

	

}
