package com.guilherme.usuario.business;

import com.guilherme.usuario.business.DTO.EnderecoDTO;
import com.guilherme.usuario.business.DTO.PhoneDTO;
import com.guilherme.usuario.business.DTO.UserDTO;
import com.guilherme.usuario.business.converter.UserConverter;
import com.guilherme.usuario.infra.entity.Endereco;
import com.guilherme.usuario.infra.entity.Telefone;
import com.guilherme.usuario.infra.entity.UsuarioConta;
import com.guilherme.usuario.infra.exception.ResourceNotFoundExcetion;
import com.guilherme.usuario.infra.exception.conflictException;
import com.guilherme.usuario.infra.security.JwtUtil;
import com.guilherme.usuario.repository.EnderecoRepository;
import com.guilherme.usuario.repository.TelefoneRepository;
import com.guilherme.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsuarioRepository usuarioRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


    public UserDTO salvaUsuario(UserDTO userDTO) {
        emailExistente(userDTO.getEmail());
        userDTO.setSenha(passwordEncoder.encode(userDTO.getSenha()));
        UsuarioConta usuarioConta = userConverter.paraUsuarioConta(userDTO);
        usuarioConta = usuarioRepository.save(usuarioConta);
        return userConverter.paraUserDTO(usuarioConta);
    }

    public void emailExistente(String email) {
        if (verificaEmailExistente(email)) {
            throw new conflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existByEmail(email);
    }

    public UserDTO buscaUsuarioporEmail(String email) {
        try {
            return userConverter.paraUserDTO(
             usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundExcetion("Email não encontrado " + email)
                    )
            );
        } catch (ResourceNotFoundExcetion e) {
            throw new ResourceNotFoundExcetion("Email nao encontrado "+ email);
        }
    }

    public void deletaUsuarioporEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }


    public UserDTO atualizadadosUsuario(String token, UserDTO dto) {

        String email= jwtUtil.extrairEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha()!= null ? passwordEncoder.encode(dto.getSenha()): null);
        UsuarioConta usuarioContaEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExcetion("Email não localizado"));
        UsuarioConta usuarioConta = userConverter.updateUsuario(dto, usuarioContaEntity);
       usuarioConta.setSenha(passwordEncoder.encode(usuarioConta.getPassword()));
       return userConverter.paraUserDTO(usuarioRepository.save(usuarioConta));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(()->
        new ResourceNotFoundExcetion("Id nao localizada" + idEndereco));

        Endereco endereco= userConverter.updateEndereco(enderecoDTO,entity);
        return userConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public PhoneDTO atualizaTelefone(Long idTelefone, PhoneDTO phoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(()->
                new ResourceNotFoundExcetion("Id nao localizada" + idTelefone));

        Telefone telefone= userConverter.updateTelefone(phoneDTO,entity);

        return userConverter.paraPhoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastroEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extrairEmailToken(token);
        UsuarioConta usuarioConta= usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundExcetion(" Email não localizado " + email));

        Endereco endereco= userConverter.paraEnderecoEntity(dto, usuarioConta.getId());
        Endereco enderecoEntity= enderecoRepository.save(endereco);

        return userConverter.paraEnderecoDTO(enderecoEntity);
    }

    public PhoneDTO cadastroTelefone(String token, PhoneDTO dto){
        String email = jwtUtil.extrairEmailToken(token);
        UsuarioConta usuarioConta= usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundExcetion(" Email não localizado " + email));

        Telefone telefone= userConverter.paraTelefoneEntity(dto, usuarioConta.getId());
        Telefone telefoneEntity= telefoneRepository.save(telefone);

        return userConverter.paraPhoneDTO(telefoneEntity);
    }
}