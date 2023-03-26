package org.filmapp.api;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.filmapp.dto.ActorDto;
import org.filmapp.service.ActorService;

import javax.inject.Inject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static java.lang.System.Logger.Level.INFO;

public class ActorController extends HttpServlet {
    public ActorService actorService;
    private final Gson gson;

    public ActorController(ActorService actorService) {
        this.gson = new Gson()
;        if (actorService == null) {
            System.getLogger("timing").log(INFO, "ActorService is null");
        }
        this.actorService = actorService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the ID parameter from the request
        String idParam = request.getParameter("id");

        // Check if the ID parameter is provided
        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Convert the ID parameter to an integer
        int id = Integer.parseInt(idParam);

        // Get the actor from the repository
        Optional<ActorDto> actorOptional = actorService.findById(id);

        // Check if the actor was found
        if (!actorOptional.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String actorJson = gson.toJson(actorOptional.get());

        // Set the response content type to JSON
        response.setContentType("application/json");

        // Write the JSON string to the response output stream
        PrintWriter writer = response.getWriter();
        writer.print(actorJson);
        writer.flush();
    }
}
