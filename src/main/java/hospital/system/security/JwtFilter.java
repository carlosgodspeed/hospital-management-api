package hospital.system.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import hospital.system.model.Usuario;
import hospital.system.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("========== JWT FILTER ==========");
        System.out.println("URI: " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");

        System.out.println("Authorization Header: " + authHeader);

        String path = request.getRequestURI();

        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            System.out.println("Token recebido: " + token);

            username = jwtUtil.extractUsername(token);

            System.out.println("Username extraído: " + username);
        }

        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            var authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            null,
                            authorities);

            System.out.println("=================================");
            System.out.println("Usuário autenticado: " + usuario.getUsername());
            System.out.println("Role: " + usuario.getRole());
            System.out.println("Authorities: " + authorities);
            System.out.println("=================================");

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}