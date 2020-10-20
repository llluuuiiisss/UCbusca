package primes.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import primes.model.HeyBean;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.util.Map;

public class RegisterAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID=4L;
    private Map<String,Object> session;
    private String username=null;
    private String password=null;
    private String email=null;

    public String execute() throws Exception {
        System.out.println("começou");
        if(this.username!=null && !username.equals("")){
            session.put("username", username);
            session.put("password", password);
            session.put("email", email);
        }
        System.out.println("1");
        String msg = getHeyBean().getRegisto(this.username,this.password,this.email);
        // you could execute some business logic here, but for now
        // the result is "success" and struts.xml knows what to do

        if(msg.equals("2")){
            return "success";
        }
        else {
            return "insuccess";
        }
    }
    public void setUsername(String username) {
        this.username = username; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setPassword(String password) {
        this.password = password; // what about this input?
    }

    public HeyBean getHeyBean()  {
        if(!session.containsKey("heyBean"))
            this.setHeyBean(new HeyBean());

        System.out.println("passou");
        return (HeyBean) session.get("heyBean");
    }

    public void setHeyBean(HeyBean heyBean) {
        this.session.put("heyBean", heyBean);
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
