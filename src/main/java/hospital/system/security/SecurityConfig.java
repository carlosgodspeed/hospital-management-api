package hospital.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                // ROTAS LIVRES
                .requestMatchers("/api/auth/**").permitAll()

                // ADMIN
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // MÉDICO
                .requestMatchers("/api/medicos/**").hasAnyRole("ADMIN", "MEDICO")

                // PACIENTE
                .requestMatchers("/api/pacientes/**").hasAnyRole("ADMIN", "PACIENTE")

                // COMPROMISSOS (qualquer autenticado)
                .requestMatchers("/api/compromissos/**").authenticated()

                // QUALQUER OUTRA
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