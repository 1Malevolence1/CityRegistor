

package web;

import dao.PersonCheckDao;
import dao.PoolConnectBuilder;
import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(
        name = "CheckPersonServlet",
        urlPatterns = {"/checkPerson"}
)
public class CheckPersonServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);
    private PersonCheckDao personCheckDao;

    public CheckPersonServlet() {
    }

    public void init() throws ServletException {
        logger.info("SERVLET is created");
        personCheckDao = new PersonCheckDao();
        personCheckDao.setConnectBuilder(new PoolConnectBuilder());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PersonRequest pr = new PersonRequest();
        pr.setSurname(req.getParameter("surname"));
        pr.setName(req.getParameter("givenname"));
        pr.setMiddleName(req.getParameter("middleName"));
        pr.setDateOfBirth(LocalDate.parse(req.getParameter("dateOfBirth"), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        pr.setStreetCode(Integer.parseInt(req.getParameter("streetCode")));
        pr.setBuilding(req.getParameter("building"));
        pr.setExtension(req.getParameter("extension"));
        pr.setApartment(req.getParameter("apartment"));

        try {
            PersonResponse personResponse = personCheckDao.checkPerson(pr);
            if (personResponse.getRegistered()) {
                resp.getWriter().write("Registered");
            } else {
                resp.getWriter().write("not registered");
            }

        } catch (PersonCheckException var6) {
            throw new RuntimeException(var6);
        } catch (SQLException var7) {
            throw new RuntimeException(var7);
        }
    }
}
