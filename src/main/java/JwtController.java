import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    //Generate Token
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken( @RequestBody JwtRequest jwtRequest){
        String token= jwtUtil.generateToken(jwtRequest.getUsername(),jwtRequest.getRole());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    //validate Token
    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody JwtValidationRequest validationRequest){
        Boolean isValid= jwtUtil.isTokenValid(validationRequest.getToken());
        return ResponseEntity.ok(isValid);
    }

    //Parse Token
    @PostMapping("/parse-token")
    public ResponseEntity<?> parseToken(@RequestBody JwtValidationRequest validationRequest){
        return ResponseEntity.ok(jwtUtil.extractClaims(validationRequest.getToken()));
    }
}
