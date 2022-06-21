package br.gov.rj.detran.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity(name = "Usuario")
@Table(name = "USUARIO")
@SequenceGenerator(name = "SQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
@Data
public class UsuarioEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USUARIO")
  @Column(name = "ID")
  private Long id;

  @Column(name = "NOME")
  @NotEmpty
  private String nome;

  @Column(name = "EMAIL")
  @Email
  private String email;
}
