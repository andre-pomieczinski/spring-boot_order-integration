package order.integration.api.controller;

import jakarta.validation.Valid;
import order.integration.api.dtos.AuthUserDto;
import order.integration.api.infra.security.TokenJWTDto;
import order.integration.api.infra.security.TokenService;
import order.integration.api.model.AuthUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authUser(@RequestBody @Valid AuthUserDto authUserDto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(authUserDto.login(), authUserDto.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.genereteToken((AuthUserModel) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDto(tokenJWT));
    }

}
