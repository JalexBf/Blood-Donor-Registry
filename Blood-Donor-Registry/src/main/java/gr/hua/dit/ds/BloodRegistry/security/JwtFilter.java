package gr.hua.dit.ds.BloodRegistry.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Request;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter  extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {





        if (request.getRequestURI().startsWith("/bloodregistry/users/signIn") || request.getMethod().equals(HttpMethod.OPTIONS.toString())  ) {

            filterChain.doFilter(request, response);
            return;
        }

        String fulltoken= request.getHeader("Authorization");
        String token=fulltoken.substring(7);





        if(token==null){
            throw new RuntimeException("token not present");
        }


        final String jwt=token;
        final String username;


        username = jwtService.extractUserName(jwt);
        authorizeUser(request, jwt, username);
        filterChain.doFilter(request, response);
    }

    private void authorizeUser(@NonNull HttpServletRequest request, String refreshJwt, String username) {
        if (StringUtils.isNotEmpty(username)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService()
                    .loadUserByUsername(username);
            if (jwtService.isTokenValid(refreshJwt, userDetails)) {
                if (userDetails.isAccountNonExpired()) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }else{
                    throw new RuntimeException("The user is expired, locked, or disabled.");
                }
            }
        }
    }


}
