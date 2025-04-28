package com.guilherme.usuario.business.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {
    private Long Id;
    private String numero;
    private String ddd;
}
