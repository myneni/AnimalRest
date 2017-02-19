package com.cognizant.animalsearchapp.rest.animaldao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.Animals;

@RunWith(MockitoJUnitRunner.class)
public class AnimalDAOImplTest {

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Mock
	DataSource dataSource;

	@InjectMocks
	AnimalDAOImpl daoImpl;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testAnimalDAOImpl() {
		assertNotNull(daoImpl);
		assertNotNull(namedParameterJdbcTemplate);

	}

	@Test
	public final void testFindRegionByName() {
		Animals animals = new Animals();
		List<Animal> animalList = new ArrayList<Animal>();
		Animal animal = new Animal(1, "Tiger", "Africa", new Date());

		Animal animal1 = new Animal(2, "Lion", "India", new Date());

		Animal animal2 = new Animal(3, "Monkey", "North America", new Date());

		animalList.add(animal);
		animalList.add(animal1);
		animalList.add(animal2);
		animals.addAnimals(animalList);

		List<String> names = Arrays.asList("Tiger", "Lion", "Monkey");

		Mockito.when(namedParameterJdbcTemplate.query(Matchers.anyString(), Matchers.anyMap(),
				Matchers.any(ResultSetExtractor.class))).thenReturn(animalList);
		Animals result = daoImpl.findRegionByName(names);

		assertNotNull(result);
		assertNotNull(result.getAnimals());
		assertEquals(result.getAnimals().size(), 3);

		Mockito.verify(namedParameterJdbcTemplate).query(Matchers.anyString(), Matchers.anyMap(),
				Matchers.any(ResultSetExtractor.class));
	}

	@Test
	public final void testLogAccessRequest() {
		fail("Not yet implemented");
	}

	@Test
	public final void testGetAccessLog() {
		fail("Not yet implemented");
	}

}
