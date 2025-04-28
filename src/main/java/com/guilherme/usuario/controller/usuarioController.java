package com.guilherme.usuario.controller;

import com.guilherme.usuario.business.DTO.EnderecoDTO;
import com.guilherme.usuario.business.DTO.PhoneDTO;
import com.guilherme.usuario.business.DTO.UserDTO;
import com.guilherme.usuario.business.UserService;
import com.guilherme.usuario.infra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class usuarioController {


    @Autowired
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody UserDTO UserDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(UserDTO.getEmail(),
                        UserDTO.getSenha())
        );

        return "Bearer" + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UserDTO> buscaUsuarioporEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.buscaUsuarioporEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioporEmail(@PathVariable String email) {
        userService.deletaUsuarioporEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping

    public ResponseEntity<UserDTO> atualizardadosUsuario(@RequestBody UserDTO dto,

                                                         @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(userService.atualizadadosUsuario(token, dto));

    }

    @PutMapping("/Endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco( @RequestBody EnderecoDTO dto,
                                                        @RequestParam ("id") Long id){
        ///return ResponseEntity.ok(userService.atualizaEndereco(Id, dto));
        return ResponseEntity.ok(userService.atualizaEndereco(id,dto));
    }

    @PutMapping("/Telefone")
    public ResponseEntity<PhoneDTO> atualizatelefone( @RequestBody PhoneDTO dto,
                                                     @RequestParam ("id") Long id){
        return ResponseEntity.ok(userService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")

    public ResponseEntity<EnderecoDTO> cadastroEndereco( @RequestBody EnderecoDTO dto,
                                                         @RequestParam ("Authorization") String token){

        return ResponseEntity.ok(userService.cadastroEndereco(token,dto));
    }

    @PostMapping("/telefone")
    public ResponseEntity<PhoneDTO> cadastroTelefone( @RequestBody PhoneDTO dto,
                                                         @RequestParam ("Authorization") String token){

        return ResponseEntity.ok(userService.cadastroTelefone(token,dto));
    }
}
