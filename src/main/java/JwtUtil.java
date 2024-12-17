import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY="your-secret-key";
    //generate Token
    public String generateToken(String username, String role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role",role); //Add custom claims
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+36000))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }
    //Validate token
    public boolean isTokenValid(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch(JwtException| IllegalArgumentException e){
            return false;
        }
    }
    //Extract Claims
    public Claims extractClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
