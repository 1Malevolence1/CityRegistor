package dao;

import domain.PersonRequest;
import domain.PersonResponse;
import exception.PersonCheckException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonCheckDao {
    private static final String INFO_PERSON = "SELECT *\nFROM cr_address_person AS ap\nINNER JOIN cr_person p ON p.person_id = ap.person_id\nINNER JOIN cr_address a ON a.address_code = ap.address_id\nWHERE\nCURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date or ap.end_date is null)and UPPER(p.sur_name) = UPPER(?)\nand upper(p.give_name) = upper(?)\nand upper(p.patronymic) = upper(?)\nand p.date_of_birth = ?\nand a.street_code = ?\nand upper(a.building) = upper(?)\n";
    private ConnectBuilder connectBuilder;

    public PersonCheckDao() {
    }

    public void setConnectBuilder(ConnectBuilder connectBuilder) {
        this.connectBuilder = connectBuilder;
    }

    public Connection getConnectBuilder() throws SQLException {
        return this.connectBuilder.getConnection();
    }

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException, SQLException {
        PersonResponse response = new PersonResponse();
        Connection con = this.getConnectBuilder();

        try {
            PreparedStatement statement = con.prepareStatement(this.buildingSQL_Query("SELECT *\nFROM cr_address_person AS ap\nINNER JOIN cr_person p ON p.person_id = ap.person_id\nINNER JOIN cr_address a ON a.address_code = ap.address_id\nWHERE\nCURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_date or ap.end_date is null)and UPPER(p.sur_name) = UPPER(?)\nand upper(p.give_name) = upper(?)\nand upper(p.patronymic) = upper(?)\nand p.date_of_birth = ?\nand a.street_code = ?\nand upper(a.building) = upper(?)\n", request));

            try {
                int count = 1;
                statement.setString(count++, request.getSurname());
                statement.setString(count++, request.getName());
                statement.setString(count++, request.getMiddleName());
                statement.setDate(count++, Date.valueOf(request.getDateOfBirth()));
                statement.setInt(count++, request.getStreetCode());
                statement.setString(count++, request.getBuilding());
                if (request.getExtension() != null) {
                    statement.setString(count++, request.getExtension());
                }

                if (request.getApartment() != null) {
                    statement.setString(count++, request.getApartment());
                }

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    response.setRegistered(true);
                    response.setTemporal(resultSet.getBoolean("temporal"));
                }
            } catch (Throwable var9) {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                    }
                }

                throw var9;
            }

            if (statement != null) {
                statement.close();
            }
        } catch (Throwable var10) {
            if (con != null) {
                try {
                    con.close();
                } catch (Throwable var7) {
                    var10.addSuppressed(var7);
                }
            }

            throw var10;
        }

        if (con != null) {
            con.close();
        }

        return response;
    }

    private String buildingSQL_Query(String string, PersonRequest personRequest) {
        StringBuilder stringBuilder = new StringBuilder(string);
        if (personRequest.getExtension() != null) {
            stringBuilder.append(" and upper(a.extension) = upper(?)");
        } else {
            stringBuilder.append(" and extension is null");
        }

        if (personRequest.getApartment() != null) {
            stringBuilder.append(" and upper(a.apartment) = upper(?)");
        } else {
            stringBuilder.append(" and apartment is null");
        }

        return stringBuilder.toString();
    }
}
