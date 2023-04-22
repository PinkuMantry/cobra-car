package in.nareshit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/images/**").permitAll()
		.antMatchers("/rental/home","/rental/newRegister","/rental/details","/rental/logout","/rental/booking_datails","/rental/admin","/rental/login","/rental/newUser","/rental/saveUser").permitAll()
		.antMatchers("/home").permitAll()
		
		
		.antMatchers("/rental/Admin_home").hasAuthority("admin")
		.antMatchers("/rental/customer_home").hasAuthority("customer")
		.antMatchers("/rental/driver_home").hasAuthority("driver")
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/rental/login")
		.loginProcessingUrl("/login")
		.failureUrl("/rental/login")
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/rental/logout")
		
		.and()
		.exceptionHandling()
		.accessDeniedPage("/rental/denied")
		
		;
	}
	
}
