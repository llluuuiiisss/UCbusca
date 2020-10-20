package primes.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import primes.model.HeyBean;

import java.util.ArrayList;
import java.util.Map;

public class MakeAdminAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID=4L;
    private Map<String,Object> session;
    private String user=null;
    private String result=null;
    private ArrayList<String> urls = new ArrayList<>();


    public String execute() throws Exception {
        if(this.user!=null && !user.equals("")){
            session.put("user", user);

        }
        result = getHeyBean().setAdmin(this.user);
        // you could execute some business logic here, but for now
        // the result is "success" and struts.xml knows what to do

        //em msg esta os resultados da pesquisa caso nao econtrou nada na mensagem vai estar "Nao encontrou nada"
        System.out.println(this.result);
        session.put("result",result);
        if(result.equals("acepted")){
            return "success";
        }else{
            return "denied";
        }
    }

    public void setUser(String user) {
        this.user = user; // will you sanitize this input? maybe use a prepared statement?
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
