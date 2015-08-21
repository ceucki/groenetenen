package be.vdab.restservices;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.exceptions.FiliaalNietGevondenException;
import be.vdab.services.FiliaalService;

@RestController
@RequestMapping("/filialen")
@ExposesResourceFor(Filiaal.class)
class FiliaalRestController {
	private final FiliaalService filiaalService;

	@Autowired
	FiliaalRestController(FiliaalService filiaalService, EntityLinks entityLinks) {
		this.filiaalService = filiaalService;
		this.entityLinks = entityLinks;
	}

	@RequestMapping(path = "{filiaal}", method = RequestMethod.GET)
	Filiaal read(@PathVariable Filiaal filiaal) {
		if (filiaal == null) {
			throw new FiliaalNietGevondenException();
		}
		return filiaal;
	}

	@ExceptionHandler(FiliaalNietGevondenException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	void filiaalNietGevonden() {
	}

	@RequestMapping(path = "{filiaal}", method = RequestMethod.DELETE)
	void delete(@PathVariable Filiaal filiaal) {
		if (filiaal == null) {
			throw new FiliaalNietGevondenException();
		}
		filiaalService.delete(filiaal.getId());
	}

	@ExceptionHandler(FiliaalHeeftNogWerknemersException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String filiaalHeeftNogWerknemers() {
		return "filiaal heeft nog werknemers";
	}

	@RequestMapping(method = RequestMethod.POST)
	void create(@RequestBody @Valid Filiaal filiaal) {
		filiaalService.create(filiaal);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String filiaalMetVerkeerdeProperties(MethodArgumentNotValidException ex) {
		StringBuilder fouten = new StringBuilder();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fouten.append(error.getField()).append('\n');
		}
		fouten.deleteCharAt(fouten.length() - 1);
		return fouten.toString();
	}

	private final EntityLinks entityLinks;

}
