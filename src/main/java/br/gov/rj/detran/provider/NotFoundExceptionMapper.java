package br.gov.rj.detran.provider;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.gov.rj.detran.domain.ErrorResponse;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
  @Override
  public Response toResponse(NotFoundException e) {
    ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(errorMessage);

    return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
  }
}
