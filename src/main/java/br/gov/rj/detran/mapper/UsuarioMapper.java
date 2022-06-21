package br.gov.rj.detran.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.gov.rj.detran.domain.Usuario;
import br.gov.rj.detran.entity.UsuarioEntity;

@Mapper(componentModel = "cdi")
public interface UsuarioMapper {
  List<Usuario> toDomainList(List<UsuarioEntity> entities);

  @Mapping(source = "id", target = "identificador")
  @Mapping(source = "nome", target = "nomeCompleto")
  Usuario toDomain(UsuarioEntity entity);

  @InheritInverseConfiguration(name = "toDomain")
  UsuarioEntity toEntity(Usuario domain);

  void updateEntityFromDomain(Usuario domain, @MappingTarget UsuarioEntity entity);
}
