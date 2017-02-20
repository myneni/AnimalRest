/**
 * 
 */
package com.cognizant.animalsearchapp.rest.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cognizant.animalsearchapp.rest.animalservice.AnimalService;
import com.cognizant.animalsearchapp.rest.model.Animal;
import com.cognizant.animalsearchapp.rest.model.AnimalAccessLog;
import com.cognizant.animalsearchapp.rest.model.Animals;
import com.cognizant.animalsearchapp.rest.validator.AnimalRequestValidator;

/**
 * @author Anisha
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AnimalRestControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private AnimalRestController restController;

	@Mock
	private AnimalService animalService;

	@Mock
	private AnimalRequestValidator validator;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(restController).build();
	}

	/**
	 * Test method for
	 * {@link com.cognizant.animalsearchapp.rest.resource.AnimalRestController#getAnimalRegion(java.lang.String)}.
	 * 
	 * @throws Exception
	 * @throws URISyntaxException
	 */
	@Test
	public final void testGetAnimalRegion() throws Exception {
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
		String input = "<animalrequest xmlns=\"com.cognizant.animalearchapp.rest\"><name>Elephant</name><name>tiger</name><name>DOg</name></animalrequest>";
		Mockito.when(validator.validateXMLSchema(Matchers.anyString(), Matchers.anyString())).thenReturn(true);
		Mockito.when(animalService.getAccessLog(Matchers.anyListOf(String.class))).thenReturn(null);
		Mockito.when(animalService.logAccessRequest(Matchers.anyListOf(String.class))).thenReturn(1);
		Mockito.when(animalService.getAnimalRegionByName(Matchers.anyListOf(String.class))).thenReturn(animals);

		MvcResult result = this.mockMvc.perform(get(new URI("/v1/animals/region")).param("requestXml", input))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();

		assertNotNull(content);
		assertTrue(content.length() > 0);
	}

	@Test
	public final void testGetAnimalRegionWithBadRequestException() throws Exception {

		List<AnimalAccessLog> logs = new ArrayList<AnimalAccessLog>();
		AnimalAccessLog accessLog = new AnimalAccessLog();
		accessLog.setAccessTime(new Date());
		accessLog.setName("lion,tiger,elephant");
		accessLog.setRequestId(1);
		logs.add(accessLog);

		String input = "<animalrequest xmlns=\"com.cognizant.animalearchapp.rest\"><name>Elephant</name><name>tiger</name><name>DOg</name></animalrequest>";
		Mockito.when(validator.validateXMLSchema(Matchers.anyString(), Matchers.anyString())).thenReturn(false);
		Mockito.when(animalService.getAccessLog(Matchers.anyListOf(String.class))).thenReturn(logs);
		Mockito.when(animalService.logAccessRequest(Matchers.anyListOf(String.class))).thenReturn(1);

		MvcResult result = this.mockMvc.perform(get(new URI("/v1/animals/region")).param("requestXml", input))
				.andExpect(status().isBadRequest()).andReturn();
		String content = result.getResponse().getContentAsString();

		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertTrue(content.contains("Bad Request"));
	}

	@Test
	public final void testGetAnimalRegionWithDuplicateException() throws Exception {

		List<AnimalAccessLog> logs = new ArrayList<AnimalAccessLog>();
		AnimalAccessLog accessLog = new AnimalAccessLog();
		accessLog.setAccessTime(new Date());
		accessLog.setName("lion,tiger,elephant");
		accessLog.setRequestId(1);
		logs.add(accessLog);

		String input = "<animalrequest xmlns=\"com.cognizant.animalearchapp.rest\"><name>Elephant</name><name>tiger</name><name>DOg</name></animalrequest>";
		Mockito.when(validator.validateXMLSchema(Matchers.anyString(), Matchers.anyString())).thenReturn(true);
		Mockito.when(animalService.getAccessLog(Matchers.anyListOf(String.class))).thenReturn(logs);
		Mockito.when(animalService.logAccessRequest(Matchers.anyListOf(String.class))).thenReturn(1);

		MvcResult result = this.mockMvc.perform(get(new URI("/v1/animals/region")).param("requestXml", input))
				.andExpect(status().isConflict()).andReturn();
		String content = result.getResponse().getContentAsString();

		assertNotNull(content);
		assertTrue(content.length() > 0);
		assertTrue(content.contains("Duplicate request"));
	}

}
