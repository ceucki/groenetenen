package be.vdab.restservices;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import be.vdab.dao.CreateTestDAOBeans;
import be.vdab.datasource.CreateTestDataSourceBean;
import be.vdab.entities.Filiaal;
import be.vdab.services.CreateServiceBeans;
import be.vdab.services.FiliaalService;
import be.vdab.valueobjects.Adres;
import be.vdab.web.CreateControllerBeans;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CreateTestDataSourceBean.class, CreateTestDAOBeans.class, CreateServiceBeans.class,
		CreateControllerBeans.class, CreateRestControllerBeans.class })
@WebAppConfiguration
@Transactional
public class FiliaalRestControllerTest {
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private FiliaalService filiaalService;
	private Filiaal filiaal;
	private MockMvc mvc;

	@Before
	public void before() {
		filiaal = new Filiaal("naam", true, BigDecimal.TEN, new Date(),
				new Adres("straat", "huisNr", 1000, "gemeente"));
		filiaalService.create(filiaal);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	

}
