////package lk.ijse.apigatway.Security;
////
////import jakarta.servlet.FilterChain;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import lk.ijse.apigatway.Security.JwtUtil;
////import lombok.RequiredArgsConstructor;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
////import org.springframework.web.filter.OncePerRequestFilter;
////import java.io.IOException;
////import java.util.Collections;
////
////@RequiredArgsConstructor
////public class JwtAuthFilter extends OncePerRequestFilter {
////    private final JwtUtil jwtUtil;
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
////        final String authHeader = request.getHeader("Authorization");
////
////        if (authHeader != null && authHeader.startsWith("Bearer ")) {
////            final String token = authHeader.substring(7);
////            String email = jwtUtil.validateAndGetUserEmail(token);
////
////            UsernamePasswordAuthenticationToken authToken =
////                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
////
////            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(authToken);
////        }
////
////        filterChain.doFilter(request, response);
////    }
////}
//
//package lk.ijse.apigatway.Security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter implements WebFilter { // 1. අනිවාර්යයෙන්ම WebFilter implement කරන්න
//
//    private final JwtUtil jwtUtil;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//
//        // 2. Authorization Header එක ලබා ගැනීම
//        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        String token = null;
//        String email = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            try {
//                email = jwtUtil.validateAndGetUserEmail(token); // ඔයාගේ JwtUtil එකේ තියෙන විදිහට extract කරගන්න
//            } catch (Exception e) {
//                // Token එක invalid නම් හෝ expire වෙලා නම් handle කරන්න
//            }
//        }
//
//        // 3. Username එකක් හමු වුනොත් සහ දැනට Security Context එකක් නැත්නම්
//        if (email != null) {
//            // Token එක validate කරන්න
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
//
//                // 4. Reactive ක්‍රමයට Security Context එකට ඇතුලත් කර ඊළඟ filter එකට යැවීම
//                return chain.filter(exchange)
//                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
//
//        }
//
//        // Token එකක් නැත්නම් හෝ invalid නම් සාමාන්‍ය පරිදි ඉදිරියට යන්න
//        return chain.filter(exchange);
//    }
//}

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
                // Handle invalid/expired token
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