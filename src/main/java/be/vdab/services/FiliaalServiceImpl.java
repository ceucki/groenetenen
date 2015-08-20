package be.vdab.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.valueobjects.PostcodeReeks;

@Service
@ReadOnlyTransactionalService
class FiliaalServiceImpl implements FiliaalService {

	private final FiliaalDAO filiaalDAO;

	@Autowired
	FiliaalServiceImpl(FiliaalDAO filiaalDAO) {
		this.filiaalDAO = filiaalDAO;
	}

	@ModifyingTransactionalServiceMethod
	@Override
	public void create(Filiaal filiaal) {
		filiaal.setId(filiaalDAO.save(filiaal).getId());
	}

	@Override
	public Filiaal read(long id) {
		return filiaalDAO.findOne(id);
	}

	@ModifyingTransactionalServiceMethod
	@Override
	public void update(Filiaal filiaal) {
		filiaalDAO.save(filiaal);

	}

	@ModifyingTransactionalServiceMethod
	@Override
	public void delete(long id) {
		Filiaal filiaal = filiaalDAO.findOne(id);
		if (filiaal != null) {
			if (!filiaal.getWerknemers().isEmpty()) {
				throw new FiliaalHeeftNogWerknemersException();
			}
			filiaalDAO.delete(id);
		}
	}

	@Override
	public List<Filiaal> findAll() {
		return filiaalDAO.findAll(new Sort("naam"));
	}

	@Override
	public long findAantalFilialen() {
		return filiaalDAO.count();
	}

	@Override
	public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks) {
		return filiaalDAO.findByAdresPostcodeBetweenOrderByNaam(reeks.getVanpostcode(), reeks.getTotpostcode());
	}

	@Override
	public List<Filiaal> findNietAfgeschreven() {
		return filiaalDAO.findByWaardeGebouwNot(BigDecimal.ZERO);
	}

	@Override
	@ModifyingTransactionalServiceMethod
	public void afschrijven(Iterable<Filiaal> filialen) {
		for (Filiaal filiaal : filialen){
			filiaal.afschrijven(); // je wijzigt een entity binnen een transactie.
		}
	
	//
	}
	

}
