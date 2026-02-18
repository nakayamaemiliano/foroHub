package com.example.foroHub.controller;

import com.example.foroHub.DTO.authDTO.LoginRequest;
import com.example.foroHub.DTO.authDTO.RegisterRequest;
import com.example.foroHub.DTO.authDTO.TokenResponse;
import com.example.foroHub.exception.BusinessException;
import com.example.foroHub.model.Rol;
import com.example.foroHub.model.Usuario;
import com.example.foroHub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.jwt.expiration-seconds:3600}")
    long expirationSeconds;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Ya existe un usuario con email: " + dto.email());
        }

        Usuario u = Usuario.builder()
                .nombre(dto.nombre())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .rol(Rol.USUARIO)
                .build();

        usuarioRepository.save(u);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationSeconds);

        var roles = auth.getAuthorities().stream().map(a -> a.getAuthority()).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("foro-hub")
                .issuedAt(now)
                .expiresAt(exp)
                .subject(auth.getName()) // email
                .claim("roles", roles)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new TokenResponse(token, exp));
    }
}
