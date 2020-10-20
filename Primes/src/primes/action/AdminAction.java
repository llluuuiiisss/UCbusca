package primes.action;

import primes.model.HeyBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.ArrayList;
import java.util.Map;

public class AdminAction extends ActionSupport implements SessionAware{
    private static final long serialVersionUID=4L;
    private Map<String,Object> session;
    private ArrayList<String> urls = new ArrayList<>();


    public String execute() throws Exception {
        //result = getHeyBean().getHist(username);
        // you could execute some business logic here, but for now
        // the result is "success" and struts.xml knows what to do
        String user = (String) session.get("username");
        String msg = getHeyBean().getAdmin(user);
        if(msg.equals("acepted")){
            return "success";
        }else{
            return "denied";
        }


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
