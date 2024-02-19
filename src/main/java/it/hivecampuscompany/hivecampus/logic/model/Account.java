package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String typeAccount;
    private String phoneNumber;

    public Account(AccountBean accountBean) {
        email = accountBean.getEmail();
        password = accountBean.getPassword();
        name = accountBean.getName();
        surname = accountBean.getSurname();
        typeAccount = accountBean.getTypeAccount();
        phoneNumber = accountBean.getPhoneNumber();
    }

    public Account(String storedEmail, String storedPassword, String storedName, String storeSurname, String storeTypeAccount, String storePhoneNumber) {
        email = storedEmail;
        password = storedPassword;
        name = storedName;
        surname = storeSurname;
        typeAccount = storeTypeAccount;
        phoneNumber = storePhoneNumber;
    }

    public Account() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
