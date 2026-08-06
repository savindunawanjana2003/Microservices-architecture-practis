

package lk.ijse.apigatway.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                email = jwtUtil.validateAndGetUserEmail(token);
            } catch (Exception e) {
            }
        }

        if (email != null) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
        }

        return chain.filter(exchange);
    }
}

//  this is a very  interesting part  this file throught we have created a new  filter for the spring sequryty filter chain after the create  new filter
//  end of the code we have added  the over custermais  new  above filter to the spring sequryty filter chain

// in this prosess check the reqest  heder  like bello things=>

//                     is includ ->   Bearer key word
// actualy this is a nomal word if you wont  to cheng this wthout problam  we can chenge this  but  now all the time   the reqest  must includ  new updated word in the heder
//insted of Bearer ok shall we move next