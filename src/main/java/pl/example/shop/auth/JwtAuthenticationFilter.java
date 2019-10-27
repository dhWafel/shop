package pl.example.shop.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("email"), request.getParameter("password"), Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Map<String, Object> clains = new HashMap<>();
        String authorities = authResult.getAuthorities()
                .stream()
                .map(m -> ((GrantedAuthority) m).getAuthority())
                .collect(Collectors.joining(","));
        clains.put("authorities", authorities);

        String token = Jwts.builder()
                .setClaims(clains)
                .setSubject(((UserDetails) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 30000000))
                .signWith(SignatureAlgorithm.HS512, "secretkey")
                .compact();

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
}
