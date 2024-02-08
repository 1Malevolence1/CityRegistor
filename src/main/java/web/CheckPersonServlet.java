package web;

import dao.PersonCheckDao;
import dao.PoolConnectBuilder;
import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
        this.personCheckDao = new PersonCheckDao();
        this.personCheckDao.setConnectBuilder(new PoolConnectBuilder());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String surname = req.getParameter("surname");
        PersonRequest pr = new PersonRequest();
        pr.setSurname(surname);
        pr.setName("Виктор");
        pr.setMiddleName("Сергеевич");
        pr.setDateOfBirth(LocalDate.of(1995, 3, 18));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setExtension("2");
        pr.setApartment("121");

        try {
            PersonResponse personResponse = this.personCheckDao.checkPerson(pr);
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