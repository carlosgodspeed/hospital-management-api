package hospital.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final SecurityExceptionHandler securityExceptionHandler;

    public SecurityConfig(
            JwtFilter jwtFilter,
            SecurityExceptionHandler securityExceptionHandler) {

        this.jwtFilter = jwtFilter;
        this.securityExceptionHandler = securityExceptionHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .exceptionHandling(exception ->
                exception.accessDeniedHandler(securityExceptionHandler)
            )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // Médicos: POST/DELETE só ADMIN; GET qualquer autenticado
                        .requestMatchers(HttpMethod.POST, "/api/medicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/medicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/medicos/**").hasAnyRole("ADMIN", "MEDICO")

                        // Pacientes: POST/DELETE só ADMIN; GET qualquer autenticado
                        .requestMatchers(HttpMethod.POST, "/api/pacientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("ADMIN", "PACIENTE")

                        // Compromissos: POST (agendar) ADMIN/PACIENTE; GET qualquer autenticado; PUT status só MEDICO/ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/compromissos").hasAnyRole("ADMIN", "PACIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/compromissos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/compromissos/**").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.DELETE, "/api/compromissos/**").hasAnyRole("ADMIN", "MEDICO")

                        // Usuários: só ADMIN
                        .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CRIPTOGRAFIA DE SENHA
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}