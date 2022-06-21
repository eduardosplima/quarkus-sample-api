package br.gov.rj.detran.repository;

import javax.enterprise.context.ApplicationScoped;

import br.gov.rj.detran.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepositoryBase<UsuarioEntity, Long> {
  
}
