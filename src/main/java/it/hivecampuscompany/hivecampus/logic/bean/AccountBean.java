package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.logic.exception.PasswordMismatchException;
import it.hivecampuscompany.hivecampus.logic.model.Account;

public class AccountBean extends CredentialsBean{
    private String name;
    private String surname;
    private String typeAccount;
    private String phoneNumber;

    public AccountBean(){
        //Default constructor
    }
    public AccountBean(Account account){
        this.email = account.getEmail();
        this.name = account.getName();
        this.surname = account.getSurname();
        this.typeAccount = account.getTypeAccount();
        this.phoneNumber = account.getPhoneNumber();
    }

    public AccountBean(String storedEmail, String storedName, String storedSurname, String storedPhoneNumber) {
        this.email = storedEmail;
        this.name = storedName;
        this.surname = storedSurname;
        this.phoneNumber = storedPhoneNumber;
    }

    public void setPassword(String password, String confirmPassword) throws PasswordMismatchException, EmptyFieldsException {
        if (password.isEmpty() || confirmPassword.isEmpty()) throw new EmptyFieldsException("Password and/or Confirm Password is empty.");
        if (password.equals(confirmPassword)){
            setPassword(password);
            return;
        }
        throw new PasswordMismatchException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws EmptyFieldsException{
        if (name.isEmpty()) throw new EmptyFieldsException("Name is empty.");
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws EmptyFieldsException {
        if (surname.isEmpty()) throw new EmptyFieldsException("Surname is empty.");
        this.surname = surname;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws EmptyFieldsException {
        if (phoneNumber.isEmpty()) throw new EmptyFieldsException("Phone Number is empty.");
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", typeAccount='" + typeAccount + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
