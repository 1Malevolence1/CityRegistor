package dao;


import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

class PersonCheckDaoTest {
    @Test
    public void checkPerson() throws PersonCheckException, SQLException {
        PersonRequest pr = new PersonRequest();
        pr.setSurname("Петров");
        pr.setName("Виктор");
        pr.setMiddleName("Сергеевич");
        pr.setDateOfBirth(LocalDate.of(1995,3,18));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setExtension("2");
        pr.setApartment("121");
        PersonCheckDao personCheckDao = new PersonCheckDao();
        personCheckDao.setConnectBuilder(new DirectConnectingBuilder());
        PersonResponse personResponse = personCheckDao.checkPerson(pr);
        Assertions.assertTrue(personResponse.getRegistered());
        Assertions.assertFalse(personResponse.getTemporal());
    }
}