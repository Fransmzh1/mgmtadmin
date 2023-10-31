package sbadmin;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminServerProperties adminServer;

    public SecurityConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        SavedRequestAwareAuthenticationSuccessHandler successHandler = 
        		new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/applications");

        http
		.authorizeHttpRequests(authHttpRequests -> authHttpRequests
				.requestMatchers(HttpMethod.POST, "/test/**").permitAll()	
	            .requestMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
	            .requestMatchers(this.adminServer.getContextPath() + "/login/**").permitAll()
	            .anyRequest().authenticated())
            
        .formLogin(formLogin -> formLogin
        		.loginPage(this.adminServer.path("/login"))
        		.successHandler(successHandler) )
        
		.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher(
						this.adminServer.getContextPath() + "/logout"))
				)

		.cors(cors -> cors.disable())

		.csrf(csrf -> csrf
//				.disable()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringRequestMatchers(
						new AntPathRequestMatcher(this.adminServer.getContextPath() + 
							"/logout", HttpMethod.POST.toString()), 
						new AntPathRequestMatcher(this.adminServer.getContextPath() + 
							"/instances", HttpMethod.POST.toString()), 
						new AntPathRequestMatcher(this.adminServer.getContextPath() + 
							"/instances/*", HttpMethod.DELETE.toString()),
						new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**")
					)
				)
		
		.httpBasic(Customizer.withDefaults());
        
        http.rememberMe(r -> r
        	.key(UUID.randomUUID().toString())
    		.tokenValiditySeconds(1209600));

		return http.build();
    }

}
