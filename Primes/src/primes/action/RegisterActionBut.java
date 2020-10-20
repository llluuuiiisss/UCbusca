package primes.action;

import primes.model.HeyBean;

import java.util.Map;

public class RegisterActionBut {
    public String execute() throws Exception {
        // you could execute some business logic here, but for now
        // the result is "success" and struts.xml knows what to do
        return "success";
    }
}
