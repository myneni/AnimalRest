/**
 * 
 */
package com.cognizant.animalsearchapp.rest.animalservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.cognizant.animalsearchapp.rest.animaldao.AnimalDAOImpl;
import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.Animals;

/**
 * @author Anisha
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AnimalServiceImplTest {

	@Mock
	AnimalDAOImpl dao;

	@InjectMocks
	AnimalServiceImpl service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.animalservice.AnimalServiceImpl#getAnimalRegionByName(java.util.List)}.
	 * 
	 */
	@Test
	public final void testGetAnimalRegionByName() {
		Animals animals = new Animals();
		List<Animal> animalList = new ArrayList<Animal>();
		Animal animal = new Animal();
		animal.setName("tiger");
		Animal animal1 = new Animal();
		animal1.setName("lion");
		Animal animal2 = new Animal();
		animal2.setName("elephant");
		animalList.add(animal);
		animalList.add(animal1);
		animalList.add(animal2);
		animals.addAnimals(animalList);
		Mockito.when(dao.findRegionByName(anyList())).thenReturn(animals);

		Animals result = service.getAnimalRegionByName(animalList);
		assertNotNull(result);
		assertNotNull(result.getAnimals());
		assertEquals(animalList, result.getAnimals());
		Mockito.verify(dao).findRegionByName(anyList());
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.animalservice.AnimalServiceImpl#logAccessRequest(java.util.List)}.
	 */
	@Test
	public final void testLogAccessRequest() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.animalservice.AnimalServiceImpl#getAccessLog(java.util.List)}.
	 */
	@Test
	public final void testGetAccessLog() {
		// fail("Not yet implemented");
	}

}
