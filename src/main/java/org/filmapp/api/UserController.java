package org.filmapp.api;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.filmapp.dto.UserDto;
import org.filmapp.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static java.lang.System.Logger.Level.INFO;

public class UserController extends HttpServlet {
    public UserService userService;
    private final Gson gson;

    public UserController(UserService userService) {
        this.gson = new Gson()
        ;        if (userService == null) {
            System.getLogger("timing").log(INFO, "UserService is null");
        }
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int id = Integer.parseInt(idParam);

        Optional<UserDto> user = userService.findById(id);

        if (!user.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String userJson = gson.toJson(user.get());

        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.print(userJson);
        writer.flush();
    }
}
