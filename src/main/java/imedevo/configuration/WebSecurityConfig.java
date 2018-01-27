package imedevo.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security configuration.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/users/login", "users/registration", "/users/getall", "/users/{id}",
            "/doctors/getall", "/doctors/{id}", "/clinics/getall", "/clinics/{id}",
            "/forgot/reset", "/forgot/newpassword")
        .permitAll()
        .antMatchers("/users/{id}", "/users/updateuser")
        .hasAuthority("USER")
        .antMatchers("/doctors/updatedoctor")
        .hasAuthority("DOCTOR")
        .antMatchers("/users/createdoctor", "/doctors/updatedoctor",
            "doctors/deletedoctor/{id}", "clinics/createclinic", "clinics/updateclinic",
            "clinics/deleteclinic/{id}")
        .hasAuthority("CLINIC_ADMIN")
        .antMatchers("/users/createuser", "/users/updateuser", "/users/deleteuser/{id}",
            "/users/createdoctor", "/doctors/updatedoctor", "doctors/deletedoctor/{id}",
            "clinics/createclinic", "clinics/updateclinic", "clinics/deleteclinic/{id}")
        .hasAuthority("SUPER_ADMIN")
        .antMatchers("/**")
        .hasAuthority("SYSTEM")
        .and()
        .logout().logoutUrl("/services/users/logout")
        .clearAuthentication(true).logoutSuccessUrl("/services/users/login")
        .and()
        .httpBasic()
        .and()
        .csrf().disable();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select u.email as username, u.password, true as enabled from users u "
                + "where u.email=?")
        .authoritiesByUsernameQuery(
            "select u.email as username, r.role from users u "
                + "join user_roles ur "
                + "join roles r "
                + "on r.id=ur.role_id and u.id=ur.user_id "
                + "where u.email=?");
  }
}
