package primes.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import primes.model.HeyBean;
import primes.model.Url;

import java.util.ArrayList;
import java.util.Map;

public class SearchOnAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID=4L;
    private Map<String,Object> session;
    private String pesquisa=null;
    private String result=null;
    private ArrayList<Url> urls = new ArrayList<Url>();


    public String execute() throws Exception {
        System.out.println("começou");
        if(this.pesquisa!=null && !pesquisa.equals("")){
            session.put("pesquisa", pesquisa);

        }
        System.out.println("1");
        String user = (String) session.get("username");
        result = getHeyBean().getSearchOn(this.pesquisa,user);
        if(result.equals("Nao encontrou nada")){
            return "denied";
        }
        System.out.println(this.result);
        session.put("result",result);
        String[] aux = null;
        aux = result.split(":::");
        for(int i=0;i<aux.length;i++){
            System.out.println(aux[i]);
            String[] aux1 = aux[i].split(",");
            Url u = new Url(aux1[1],aux1[0],aux1[2],null);
            urls.add(u);
        }
        session.put("urls",urls);
        return "success";
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa; // will you sanitize this input? maybe use a prepared statement?
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
