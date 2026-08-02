package lk.ijse.apigatway.Controller;

import lk.ijse.apigatway.Dto.AuthRequest;
import lk.ijse.apigatway.Dto.AuthResponse;
import lk.ijse.apigatway.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final WebClient.Builder webClientBuilder;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest request) {


        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/v1/user/validate")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("User Service eken credentials tocken eka genarete karanawa  valide  use  una nisa");

                        String token = jwtUtil.generateToken(request.getEmail());
                        return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                    }
                })
                .onErrorResume(error -> {
                    System.out.println("❌ WebClient Call eka fail una : " + error.getMessage());
                    error.printStackTrace();

                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }
}