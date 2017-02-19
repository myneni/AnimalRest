package com.cognizant.animalsearchapp.rest.animaldao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
		fail("Not yet implemented");
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
