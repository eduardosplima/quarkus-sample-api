package br.gov.rj.detran;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.gov.rj.detran.domain.Usuario;
import br.gov.rj.detran.exception.UsuarioNotFoundException;
import br.gov.rj.detran.service.UsuarioService;
import lombok.AllArgsConstructor;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UsuarioResource {
  private final UsuarioService usuarioService;

  @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Usuario.class)))
  @GET
  public Response get() {
    return Response.ok(this.usuarioService.findAll()).build();
  }

  @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Usuario.class)))
  @APIResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  @GET
  @Path("/{id}")
  public Response getById(
      @Parameter(name = "id", required = true) @PathParam("id") Long id) {
    return this.usuarioService.findById(id)
        .map(usuario -> Response.ok(usuario).build())
        .orElse(Response.status(Response.Status.NOT_FOUND).build());
  }

  @APIResponse(responseCode = "201", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT, implementation = Usuario.class)))
  @APIResponse(responseCode = "400", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  @POST
  public Response post(@NotNull @Valid Usuario usuario, @Context UriInfo uriInfo) {
    Usuario novoUsuario = this.usuarioService.save(usuario);
    URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(novoUsuario.getIdentificador())).build();
    return Response.created(uri).entity(novoUsuario).build();
  }

  @APIResponse(responseCode = "204")
  @APIResponse(responseCode = "400", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  @APIResponse(responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  @PUT
  @Path("/{id}")
  public Response put(
      @Parameter(name = "id", required = true) @PathParam("id") Long id,
      @NotNull @Valid Usuario usuario) {
    try {
      this.usuarioService.update(id, usuario);
    } catch (UsuarioNotFoundException e) {
      throw new NotFoundException(e.getMessage());
    }
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}
