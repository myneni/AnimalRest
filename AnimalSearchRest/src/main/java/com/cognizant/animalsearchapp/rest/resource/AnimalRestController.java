package com.cognizant.animalsearchapp.rest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.animalsearchapp.rest.animalservice.AnimalService;
import com.cognizant.animalsearchapp.rest.exception.AnimalNotFoundException;
import com.cognizant.animalsearchapp.rest.exception.DuplicateReqestException;
import com.cognizant.animalsearchapp.rest.exception.InvalidRequestException;
import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;
import com.cognizant.animalsearchapp.rest.model.ErrorResponse;

@RestController
@RequestMapping(value = "/animals")
public class AnimalRestController {

	private final Logger logger = LoggerFactory.getLogger(AnimalRestController.class);

	@Autowired
	private AnimalService animalService;

	@RequestMapping(value = "/region", method = RequestMethod.POST)
	public Animals getAnimalRegion(@RequestBody Animals aList)
			throws DuplicateReqestException, InvalidRequestException, AnimalNotFoundException {

		if (aList == null || CollectionUtils.isEmpty(aList.getAnimals()))
			throw new InvalidRequestException();

		/*
		 * check if duplicate Request Same can be done in better way by fetching
		 * animal regions from DB whose access time is more than 12 hours
		 */

		List<String> names = new ArrayList<String>();
		for (Animal animal : aList.getAnimals()) {
			names.add(animal.getName());
		}

		List<AnimalAccessLog> requestLogs = animalService.getAccessLog(names);
		if (CollectionUtils.isEmpty(requestLogs)) {
			logger.debug("No Request in last 1 day");
		} else {
			throw new DuplicateReqestException();
		}
		// Log a request
		animalService.logAccessRequest(names);

		// Find the Animals
		Animals animals = animalService.getAnimalRegionByName(aList.getAnimals());

		if (animals == null || CollectionUtils.isEmpty(animals.getAnimals()))
			throw new AnimalNotFoundException();

		return animals;
	}

	@ExceptionHandler(DuplicateReqestException.class)
	public ResponseEntity<ErrorResponse> duplicateRequestExceptionHandler(HttpServletRequest req, Exception e) {
		ErrorResponse error = new ErrorResponse("DUPLICATE_REQUEST", "Duplicate request in last 24 hours");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorResponse> badRequestExceptionHandler(HttpServletRequest req, Exception e) {
		ErrorResponse error = new ErrorResponse("DUPLICATE_REQUEST", "Bad Request. Missing Input");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AnimalNotFoundException.class)
	public ResponseEntity<ErrorResponse> animalNotFoundExceptionHandler(HttpServletRequest req, Exception e) {
		ErrorResponse error = new ErrorResponse("DUPLICATE_REQUEST", "Animals not found in Repository");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

}
