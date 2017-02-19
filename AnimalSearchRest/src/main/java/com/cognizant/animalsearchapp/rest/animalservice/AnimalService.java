/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animalservice;

import java.util.List;

import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Anisha
 *
 */
public interface AnimalService {
	public Animals getAnimalRegionByName(List<Animal> animalList);

	public List<AnimalAccessLog> getAccessLog(List<String> names);

	public int logAccessRequest(List<String> names);
}
