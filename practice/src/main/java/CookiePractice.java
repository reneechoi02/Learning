import javax.servlet.http.Cookie;
public class CookiePractice {
    public void setCookie(){
        Cookie cookie = new Cookie("name","renee");
        cookie.setDomain("reneechoi.cn");
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setSecure(true);
    }
}
