/**
 * 
 */
package com.cognizant.animalsearchapp.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Anisha
 *
 */
@JacksonXmlRootElement(localName = "animals", namespace = "com.cognizant.animalearchapp.rest")
public class Animals implements Serializable {
	@JacksonXmlProperty(localName = "animal")
	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Animal> animals = new ArrayList<Animal>();

	public void addAnimal(Animal a) {
		if (this.animals == null)
			animals = new ArrayList<Animal>();

		this.animals.add(a);
	}

	public void addAnimals(List<Animal> animals) {
		if (this.animals == null)
			animals = new ArrayList<Animal>();

		this.animals.addAll(animals);
	}
}
