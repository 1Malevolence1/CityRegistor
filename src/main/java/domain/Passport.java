package domain;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Passport   {
    private String passportSeries;

    private String passportNumber;

    private LocalDate dateOfIssue;


    public Passport() {
    }

    public Passport(String passportSeries, String passportNumber, LocalDate dateOfIssue) {
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.dateOfIssue = dateOfIssue;
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

}
