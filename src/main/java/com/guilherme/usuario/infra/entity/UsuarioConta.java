package com.guilherme.usuario.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuario")
public class UsuarioConta implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name= "nome", length = 100)
    private String nome;
    @Column (name= "Email", length = 100)
    private String email;
    @Column (name= "Senha")
    private String senha;

    @OneToMany
    @JoinColumn(name= "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    @Override
    public String getPassword(){
        return "senha" ;
    }
    @Override
    public String getUsername(){
        return "email";
    }

}
