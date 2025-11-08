package com.enrtreprise.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.enrtreprise.api.Configuration.JwtUtils;
import com.enrtreprise.api.service.CostumUserDetailsService;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CostumUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Récupère l’en-tête Authorization (Bearer <token>)
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            // Vérifie que l’en-tête commence bien par "Bearer "
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7); // Enlève "Bearer " pour ne garder que le token
                username = jwtUtils.getUsernameFromToken(token); // Extrait le nom d’utilisateur du token
            }

            // Vérifie qu’on a bien un username et qu’aucune authentification n’est déjà en cours
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Charge les informations de l’utilisateur depuis la base
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Vérifie que le token est valide (signature, expiration, etc.)
                if (jwtUtils.validateJwtToken(token)) {
                    // Crée un objet d’authentification pour Spring Security
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Ajoute des détails sur la requête (adresse IP, etc.)
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Stocke l’authentification dans le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continue le traitement normal de la requête
            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Si le token est expiré → 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expiré");
        } catch (io.jsonwebtoken.SignatureException e) {
            // Si la signature du token est invalide → 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Signature du token invalide");
        } catch (Exception e) {
            // Toute autre erreur liée au JWT → 401 Unauthorized
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token invalide ou absent");
        }
    }
}
