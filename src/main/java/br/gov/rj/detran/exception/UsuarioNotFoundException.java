package br.gov.rj.detran.exception;

public class UsuarioNotFoundException extends Exception {
  public UsuarioNotFoundException(String message) {
    super(message);
  }

  public UsuarioNotFoundException(String format, Object... objects) {
    super(String.format(format, objects));
  }
}
