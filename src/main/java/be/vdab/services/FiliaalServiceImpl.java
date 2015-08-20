package be.vdab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.valueobjects.PostcodeReeks;

@Service
@ReadOnlyTransactionalService
public class FiliaalServiceImpl implements FiliaalService {

	private final FiliaalDAO filiaalDAO;

	@Autowired
	FiliaalServiceImpl(FiliaalDAO filiaalDAO) {
		this.filiaalDAO = filiaalDAO;
	}

	@ModifyingTransactionalServiceMethod
	@Override
	public void create(Filiaal filiaal) {
		filiaalDAO.create(filiaal);

	}

	@Override
	public Filiaal read(long id) {
		return filiaalDAO.read(id);
	}

	@ModifyingTransactionalServiceMethod
	@Override
	public void update(Filiaal filiaal) {
		filiaalDAO.update(filiaal);

	}

	@ModifyingTransactionalServiceMethod
	@Override
	public void delete(long id) {
		Filiaal filiaal = filiaalDAO.read(id);
		if (filiaal != null) {
			if (!filiaal.getWerknemers().isEmpty()) {
				throw new FiliaalHeeftNogWerknemersException();
			}
			filiaalDAO.delete(id);
		}
	}

	@Override
	public List<Filiaal> findAll() {
		return filiaalDAO.findAll();
	}

	@Override
	public long findAantalFilialen() {
		return filiaalDAO.findAantalFilialen();
	}

	@Override
	public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks) {
		return filiaalDAO.findByPostcodeReeks(reeks);
	}

}
