package primes.model;

import java.util.ArrayList;

public class UserBean{
    private String name;
    private String password;
    private String email;

    public String getName() {
        return this.name;
    }
    public String getPass() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPass(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
