import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class SessionPractice {
    public void setSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("loginTime",new Date());
    }
}
