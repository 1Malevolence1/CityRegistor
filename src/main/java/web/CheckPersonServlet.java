package web;




import dao.PersonCheckDao;
import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


@WebServlet(name ="CheckPersonServlet", urlPatterns ="/checkPerson")
public class CheckPersonServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CheckPersonServlet.class);
    private PersonCheckDao personCheckDao;
    @Override
    public void init() throws ServletException {
        logger.info("SERVLET is created");
        personCheckDao = new PersonCheckDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String surname = req.getParameter("surname");
        PersonRequest pr = new PersonRequest();
        pr.setSurname(surname);
        pr.setName("Виктор");
        pr.setMiddleName("Сергеевич");
        pr.setDateOfBirth(LocalDate.of(1995,3,18));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setExtension("2");
        pr.setApartment("121");

        try {
             personCheckDao = new PersonCheckDao();
            PersonResponse personResponse = personCheckDao.checkPerson(pr);
            if(personResponse.getRegistered()){
                resp.getWriter().write("Registered");
            }

            else {
                resp.getWriter().write("not registered");
            }
        } catch (PersonCheckException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
