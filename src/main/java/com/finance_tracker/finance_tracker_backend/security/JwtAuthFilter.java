/*
 Esta clase sera el filtro que intercepta cada peticion HTTP, saca el token
 del header Autorization, lo valida con JwtService y si es valido le dice a Spring
 que el usuario esta autenticado
 */
package com.finance_tracker.finance_tracker_backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request
                                   ,HttpServletResponse response
                                   ,FilterChain filterChain)
                                  throws ServletException, IOException {
        String autherHeader = request.getHeader("Authorization"); //extraemos las credenciales del usuario


        // lo que hace esta condicion verifica si el token esta verficado con el rango correcto
        if (autherHeader == null || !autherHeader.startsWith("Bearer ")){
            //primero pregunta si la peticion trajo el header "Authorization, esto es por si alguien entra si iniciar sesion"
            //despues pregunta si el header no empieza por Bearer, ya que alguien pocdria mandar un Authorization sin Bearer
            // y saltarse la verificacion
            filterChain.doFilter(request,response);//despues le pasamos request y response a la siguiente capa de seguridad
            return;
        }

        String token = autherHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (jwtService.isTokenValid(token, email)){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);//aqui cargamos los datos del usuario desde la DB

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null , userDetails.getAuthorities()
            );//aqui creamos un objeto de autenticacion que Spring Security entiende, esto almacena datos del usuario, permisos

            SecurityContextHolder.getContext().setAuthentication(authToken);// y aqui guardamos esos datos
        }
        filterChain.doFilter(request,response);
    }
}
