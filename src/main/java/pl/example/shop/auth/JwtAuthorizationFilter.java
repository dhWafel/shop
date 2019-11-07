package pl.example.shop.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer")) {
            SecurityContextHolder.getContext().setAuthentication(null);     //mówi że użytkownik nie jest autoryzowany
            chain.doFilter(request, response);                              //funkcja nadrzędna trzeba dać
            return;
        }
        Claims claims = Jwts.parser()
                .setSigningKey("secretkey")
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        String authorities = claims.get("authorities", String.class);

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        if (authorities != null && !authorities.isEmpty()) {
            simpleGrantedAuthorities = Arrays
                    .stream(authorities.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        String email = claims.getSubject();

        if(email != null){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, null, simpleGrantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else{
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setStatus(401);
        }
        chain.doFilter(request, response);
    }


}
