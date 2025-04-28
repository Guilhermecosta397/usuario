package com.guilherme.usuario.business.converter;

import com.guilherme.usuario.business.DTO.EnderecoDTO;
import com.guilherme.usuario.business.DTO.PhoneDTO;
import com.guilherme.usuario.business.DTO.UserDTO;
import com.guilherme.usuario.infra.entity.Endereco;
import com.guilherme.usuario.infra.entity.Telefone;
import com.guilherme.usuario.infra.entity.UsuarioConta;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UsuarioConta paraUsuarioConta(UserDTO userDTO) {
        return UsuarioConta.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .enderecos(paraListaEnderecos(userDTO.getEnderecos()))
                .telefones(paraListaTelefones(userDTO.getTelefones()))
                .build();
    }

    // Converte UsuarioConta em UserDTO (ENTIDADE → SAÍDA)
    public UserDTO paraUserDTO(UsuarioConta usuarioConta) {
        return UserDTO.builder()
                .nome(usuarioConta.getNome())
                .email(usuarioConta.getEmail())
                .senha(usuarioConta.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioConta.getEnderecos()))
                .telefones(paraListaPhoneDTO(usuarioConta.getTelefones()))
                .build();
    }

    // --------- Endereços ---------

    public List<Endereco> paraListaEnderecos(List<EnderecoDTO> enderecoDTO) {
        if (enderecoDTO == null) return new ArrayList<>();
        return enderecoDTO.stream()
                .map(this::paraEndereco)
                .collect(Collectors.toList());
    }

    public Endereco paraEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .cep(dto.getCep())
                .cidade(dto.getCidade())
                .numero(dto.getNumero())
                .estado(dto.getEstado())
                .complemento(dto.getComplemento())
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        if (enderecos == null) return new ArrayList<>();
        return enderecos.stream()
                .map(this::paraEnderecoDTO)
                .collect(Collectors.toList());
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .numero(endereco.getNumero())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento())
                .build();
    }

    // --------- Telefones ---------

    public List<Telefone> paraListaTelefones(List<PhoneDTO> phoneDTO) {
        if (phoneDTO == null) return new ArrayList<>();
        return phoneDTO.stream()
                .map(this::paraTelefone)
                .collect(Collectors.toList());
    }

    public Telefone paraTelefone(PhoneDTO dto) {
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .build();
    }

    public List<PhoneDTO> paraListaPhoneDTO(List<Telefone> telefones) {
        if (telefones == null) return new ArrayList<>();
        return telefones.stream()
                .map(this::paraPhoneDTO)
                .collect(Collectors.toList());
    }

    public PhoneDTO paraPhoneDTO(Telefone telefone) {
        return PhoneDTO.builder()
                .Id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }


    public UsuarioConta updateUsuario(UserDTO usuario, UsuarioConta entity){
        return UsuarioConta.builder()
                .nome(usuario.getNome() != null ? usuario.getNome(): entity.getNome())
                .id(entity.getId())
                .senha(usuario.getSenha() != null ? usuario.getSenha(): entity.getSenha())
                .email(usuario.getEmail() != null ? usuario.getEmail(): entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null? dto.getRua() : entity.getRua())
                .numero(dto.getNumero()!= null? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade()!= null? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep()!= null? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento()!=null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado()!= null? dto.getEstado() : entity.getEstado())
                .build();
    }

    public Telefone updateTelefone(PhoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd()!= null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero()!= null ? dto.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity (EnderecoDTO dto, Long idUsuario){
        return Endereco.builder()
                .rua(dto.getRua())
                .estado(dto.getEstado())
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .complemento(dto.getComplemento())
                .numero(dto.getNumero())
                .usuario_id(dto.getId())
                .build();
    }

    public Telefone paraTelefoneEntity (PhoneDTO dto, Long idUsuario){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .id(dto.getId())
                .build();
    }
}
