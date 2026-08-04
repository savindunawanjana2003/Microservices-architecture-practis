package lk.ijse.apigatway.Controller;

import lk.ijse.apigatway.Dto.AuthRequest;
import lk.ijse.apigatway.Dto.AuthResponse;
import lk.ijse.apigatway.Dto.UserRegistetion;
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

    ///----------------
    /// Api gatway must includ only one end poind (login)
    /// -----------------

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest request) {
        System.out.println("===========================================================");

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
                    System.out.println("WebClient Call failed  : " + error.getMessage());
                    error.printStackTrace();

                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }



    @PostMapping("/registetion")
    public Mono<ResponseEntity<?>> registetion(@RequestBody UserRegistetion requestObject) {

        System.out.println("//////////////////////////////////======================");
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/v1/user/save")
                .bodyValue(requestObject)
                .retrieve()
                .toBodilessEntity()
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(ResponseEntity.ok(requestObject.getRole() + " saved succsess fully"));
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                    }
                })
                .onErrorResume(error -> {
                    System.out.println("❌ WebClient Call eka fail una : " + error.getMessage());
                    error.printStackTrace();

                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                });
    }

}