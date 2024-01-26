package dao;

import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao {


    private static final  String INFO_PERSON = "SELECT person_id, sur_name, give_name, " +
            "patronymic, date_of_birth," +
            " ,certificate_number, certificate_date " +
            "FROM cr_person";

    private PersonResponse checkPerson(PersonRequest personRequest) throws PersonCheckException, SQLException {
        PersonResponse personResponse = new PersonResponse();

        try(Connection con = ConnectingBuilder.getConnection(); PreparedStatement statement = con.prepareStatement(INFO_PERSON)){
            ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()){
               personResponse.setRegistered(true);
               personResponse.setTemporal(resultSet.getBoolean("temporal"));
           }
        }

        return personResponse;
    }

}
