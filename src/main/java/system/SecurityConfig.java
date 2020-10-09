package system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import system.services.AccountsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final
    AccountsService accountsService;

    @Autowired
    public SecurityConfig(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/get", "/delete", "/update","/","/getOrders","/updateOrder","/getOrderById/{id}", "/order").hasAnyRole("USER", "ADMIN")
                .antMatchers("/createA", "/get/{id}", "/getAll", "/delete/{id}", "/updateA").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .cors();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.accountsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
