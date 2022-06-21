package br.gov.rj.detran;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.rj.detran.domain.Usuario;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(UsuarioResource.class)
public class UsuarioResourceTest {
  @Test
  public void getAll() {
    RestAssured.given()
        .get()
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void getById() {
    Usuario postUsuario = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(this.createUsuario())
        .post()
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract().as(Usuario.class);
    Usuario getUsuario = RestAssured.given()
        .get("/{id}", postUsuario.getIdentificador())
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract().as(Usuario.class);
    Assertions.assertThat(postUsuario).isEqualTo(getUsuario);
  }

  @Test
  public void getByIdNotFound() {
    RestAssured.given()
        .get("/{id}", 777)
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  public void post() {
    Usuario usuario = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(this.createUsuario())
        .post()
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract().as(Usuario.class);
    Assertions.assertThat(usuario.getIdentificador()).isNotNull();
  }

  @Test
  public void postFail() {
    Usuario usuario = this.createUsuario();
    usuario.setNomeCompleto(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(usuario)
        .post()
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void put() {
    Usuario usuario = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(this.createUsuario())
        .post()
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract().as(Usuario.class);
    
    Long identificador = usuario.getIdentificador();
    usuario.setNomeCompleto("PNCC");
    usuario.setIdentificador(null);
    
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(usuario)
        .put("/{id}", identificador)
        .then()
        .statusCode(HttpStatus.SC_NO_CONTENT);
  }

  @Test
  public void putFail() {
    Usuario usuario = RestAssured.given()
        .contentType(ContentType.JSON)
        .body(this.createUsuario())
        .post()
        .then()
        .statusCode(HttpStatus.SC_CREATED)
        .extract().as(Usuario.class);
    usuario.setNomeCompleto(null);
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(usuario)
        .put("/{id}", usuario.getIdentificador())
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void putNotFound() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(this.createUsuario())
        .put("/{id}", 777)
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

  private Usuario createUsuario() {
    Usuario usuario = new Usuario();
    usuario.setNomeCompleto(RandomStringUtils.randomAlphabetic(10));
    usuario.setEmail(RandomStringUtils.randomAlphabetic(5) + "@email.com");
    return usuario;
  }
}
