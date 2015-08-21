package be.vdab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class CreateSecurityFilter extends WebSecurityConfigurerAdapter {

	private static final String MANAGER = "manager";
	private static final String HELPDESKMEDEWERKER = "helpdeskmedewerker";
	private static final String MAGAZIJNIER = "magazijnier";

	@Override
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("joe").password("theboss").authorities(MANAGER).and().withUser("averell")
				.password("hungry").authorities(HELPDESKMEDEWERKER, MAGAZIJNIER);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**").antMatchers("/styles/**").antMatchers("/scripts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().authorizeRequests()
				.antMatchers("/filialen/toevoegen", "/filialen/*/wijzigen", "/filialen/*/verwijderen")
				.hasAuthority(MANAGER).antMatchers(HttpMethod.POST, "/filialen").hasAnyAuthority(MANAGER)
				.antMatchers("/werknemers").hasAnyAuthority(MAGAZIJNIER, HELPDESKMEDEWERKER);
	}
}
