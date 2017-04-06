package asw.dashboard.cucumber;

import static org.junit.Assert.assertTrue;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;

public class DashboardLoginStep {

	//private WebDriver driver = DriverGenerator.generarDriverFirefoxPortable();
	//private WebDriver driver = DriverGenerator.generarDriverFirefox();
	//private WebDriver driver = DriverGenerator.generarDriverHTML();
	
	@Cuando("^el administrador se encuentra en la pagina de login$")
	public void el_administrador_se_encuentra_en_la_pagina_de_login() {
	}

	@Entonces("^inserta su mail \"(.+)\" y su password \"(.+)\"$")
	public void inserta_su_mail_y_su_password(String name, String password) {		

	}

	@Entonces("^se logea de manera correcta$")
	public void se_logea_de_manera_correcta() {	

		
	}
}