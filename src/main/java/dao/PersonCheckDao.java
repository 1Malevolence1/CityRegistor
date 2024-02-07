package dao;

import domain.Passport;
import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao  {



    private static final String INFO_PERSON = "SELECT *\n" +
            "FROM cr_address_person AS ap\n" +
            "INNER JOIN cr_person p ON p.person_id = ap.person_id\n" +
            "INNER JOIN cr_address a ON a.address_code = ap.address_id\n" +
            "WHERE\n" +
            "CURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date or ap.end_date is null)" +
            "and UPPER(p.sur_name) = UPPER(?)\n" +
            "and upper(p.give_name) = upper(?)\n" +
            "and upper(p.patronymic) = upper(?)\n" +
            "and p.date_of_birth = ?\n" +
            "and a.street_code = ?\n" +
            "and upper(a.building) = upper(?)\n";


    private ConnectingBuilder connectingBuilder;

    public void setConnectingBuilder(ConnectingBuilder connectingBuilder) {
        this.connectingBuilder = connectingBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectingBuilder.getConnection();
    }

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException, SQLException {
        PersonResponse response = new PersonResponse();


        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(buildingSQL_Query(INFO_PERSON, request))){

            int count = 1;

            statement.setString(count++, request.getSurname());
            statement.setString(count++, request.getName());
            statement.setString(count++, request.getMiddleName());
            statement.setDate(count++, Date.valueOf(request.getDateOfBirth()));
            statement.setInt(count++, request.getStreetCode());
            statement.setString(count++, request.getBuilding());
            if(request.getExtension() != null) statement.setString(count++, request.getExtension());
            if(request.getApartment() != null)  statement.setString(count++, request.getApartment());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                response.setRegistered(true);
                response.setTemporal(resultSet.getBoolean("temporal"));
            }
        }

        return response;
    }
    private String buildingSQL_Query(String string, PersonRequest personRequest){
        StringBuilder stringBuilder = new StringBuilder(string);
        if(personRequest.getExtension() != null) stringBuilder.append(" and upper(a.extension) = upper(?)");
        else stringBuilder.append(" and extension is null");

        if(personRequest.getApartment() != null) stringBuilder.append(" and upper(a.apartment) = upper(?)");
        else stringBuilder.append(" and apartment is null");

        return stringBuilder.toString();
    }


}