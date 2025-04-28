package com.guilherme.usuario.business.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTO>enderecos;
    private List<PhoneDTO>telefones;
}
