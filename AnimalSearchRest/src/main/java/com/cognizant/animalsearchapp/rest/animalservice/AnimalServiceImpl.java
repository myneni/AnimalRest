/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animalservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cognizant.animalsearchapp.rest.animaldao.AnimalDao;
import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Anisha
 *
 */
@Service
public class AnimalServiceImpl implements AnimalService {
	private final Logger logger = LoggerFactory.getLogger(AnimalServiceImpl.class);

	private static final long TWELVE_HOURS = 12 * 60 * 60 * 1000;

	@Autowired
	private AnimalDao animalDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cognizant.animalsearchapp.rest.animalservice.AnimalService#
	 * getAnimalRegionByName(java.util.List)
	 */
	@Override
	public Animals getAnimalRegionByName(List<Animal> animalList) {
		logger.debug("Animal List: {}", animalList);
		List<String> animalNames = new ArrayList<String>();
		Animals animals = null;
		for (Animal animal : animalList) {
			animalNames.add(animal.getName());
		}

		if (!CollectionUtils.isEmpty(animalNames)) {
			animals = animalDao.findRegionByName(animalNames);
		}
		logger.debug("Animal Region Resoponse: {}", animals);
		return animals;
	}

	public int logAccessRequest(List<String> names) {
		logger.debug("Animal logAccessRequest: {}", names);
		return animalDao.logAccessRequest(names);
	}

	@Override
	public List<AnimalAccessLog> getAccessLog(List<String> names) {

		logger.debug("Animal updateAccessTime: {}", names);
		return animalDao.getAccessLog(names);
	}

}
