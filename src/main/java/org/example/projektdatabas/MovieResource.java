package org.example.projektdatabas;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.projektdatabas.db.MovieRepository;
import org.example.projektdatabas.model.Film;

import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    private MovieRepository repository;

    @POST //Skapar en ny film genom att denna funktion tar emot Film objektet som sedan skickas vidare till MovieRepository.
    // Lagras i databasen.
    public Response createMovie(Film film) { //Retunerar 201.
        Film created = repository.create(film);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET //Hämtar alla filmer
    public Response getAllMovies() {//Här används MovieRepository för att hämta alla filmer från databasen.
        return Response.ok().entity(repository.findAll()).build(); //Retunerar 200 OK, med en lista med alla filmer.
    }

    @GET //Hämtar filmer med ID
    @Path("/{id}")
    public Response getMovie(@PathParam("id") Long id) {
        Film film = repository.find(id);
        if (film == null) { //Om filmen ej hittas retuneras 404 not found.
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(film).build(); //Hittas filmen retunerar 200ok
    }

    @PUT //Uppdatera film med id samt ny data
    @Path("/{id}")
    public Response updateMovie(@PathParam("id") Long id, Film film) {
        Film existing = repository.find(id);//kollar att filmen existerar
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        film.setId(id);
        Film updated = repository.update(film);
        return Response.ok(updated).build(); //Retunerar 200 OK med uppdateringarna.
    }

    @DELETE //Ta bort en film med specifikt id
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        Film existing = repository.find(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repository.delete(id);
        return Response.noContent().build(); // 204 No content= borrtagning lyckad
    }
}

