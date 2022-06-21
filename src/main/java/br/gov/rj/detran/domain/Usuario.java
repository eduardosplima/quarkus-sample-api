package br.gov.rj.detran.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import lombok.Data;

@Data
public class Usuario {
  @Null
  private Long identificador;

  @NotEmpty
  private String nomeCompleto;

  @Email
  private String email;
}
