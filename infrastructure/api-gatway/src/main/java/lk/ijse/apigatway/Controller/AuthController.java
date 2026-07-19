package lk.ijse.apigatway.Controller;

import lk.ijse.apigatway.Dto.AuthRequest;
import lk.ijse.apigatway.Dto.AuthResponse;
import lk.ijse.apigatway.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final WebClient.Builder webClientBuilder; // Injecting WebCl

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/api/v1/user/validate") // User Service එකේ URL එක (නැත්නම් Service Name එක)
                .bodyValue(request) // Request body එක විදිහට email/username සහ password යවනවා
                .retrieve()
                .toBodilessEntity() // User valid නම් 200 OK එකක් එනවා කියලා හිතමු
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        // User Service එකෙන් credentials හරියි කිව්වොත් token එක generate කරනවා
                        System.out.println("User Service එකෙන් credentials හරියි කිව්වොත් token එක generate කරනවා");
                        String token = jwtUtil.generateToken(request.getEmail()); // මෙතනට request.getUsername() හෝ email දාන්න
                        return ResponseEntity.ok(new AuthResponse(token));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).<AuthResponse>build();
                    }
                })
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()).block();

//        if ("user@gmail.com".equals(request.getEmail()) && "2003".equals(request.getPassword())) {
//            String token = jwtUtil.generateToken(request.getUsername());
//            return ResponseEntity.ok(new AuthResponse(token));
//        } else {
//            return ResponseEntity.status(401).build();
//        }
    }
}