package primes.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import primes.model.HeyBean;
import primes.model.HeyBean;

import java.util.ArrayList;
import java.util.Map;

public class HistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID=4L;
    private Map<String,Object> session;
    private ArrayList<String> urls = new ArrayList<>();
    private String result=null;


    public String execute() throws Exception {
        //result = getHeyBean().getHist(username);
        // you could execute some business logic here, but for now
        // the result is "success" and struts.xml knows what to do
        String user = (String) session.get("username");
        String msg = getHeyBean().getHist(user);
        session.put("hist",msg);
        return "success";

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
