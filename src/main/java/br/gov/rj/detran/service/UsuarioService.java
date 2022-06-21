package br.gov.rj.detran.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.gov.rj.detran.domain.Usuario;
import br.gov.rj.detran.entity.UsuarioEntity;
import br.gov.rj.detran.exception.UsuarioNotFoundException;
import br.gov.rj.detran.mapper.UsuarioMapper;
import br.gov.rj.detran.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;

  public List<Usuario> findAll() {
    return this.usuarioMapper.toDomainList(this.usuarioRepository.findAll().list());
  }

  public Optional<Usuario> findById(@NonNull Long id) {
    return this.usuarioRepository.findByIdOptional(id).map(this.usuarioMapper::toDomain);
  }

  @Transactional
  public Usuario save(@Valid Usuario usuario) {
    log.debug("Saving Usuario: {}", usuario);

    UsuarioEntity entity = this.usuarioMapper.toEntity(usuario);
    this.usuarioRepository.persist(entity);

    return this.usuarioMapper.toDomain(entity);
  }

  @Transactional
  public Usuario update(@NotNull Long id, @Valid Usuario usuario) throws UsuarioNotFoundException {
    log.debug("Updating Usuario: {}", id, usuario);

    UsuarioEntity entity = this.usuarioRepository
        .findByIdOptional(id)
        .orElseThrow(() -> new UsuarioNotFoundException("Nenhum usuário localizado para o identificador [%s]", id));

    this.usuarioMapper.updateEntityFromDomain(usuario, entity);
    this.usuarioRepository.persist(entity);

    return this.usuarioMapper.toDomain(entity);
  }
}
