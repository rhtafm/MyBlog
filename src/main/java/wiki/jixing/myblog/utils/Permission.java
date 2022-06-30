package wiki.jixing.myblog.utils;

import org.springframework.stereotype.Component;
import wiki.jixing.myblog.entity.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Component
public class Permission {
    @Resource
    private HttpSession session;

    public Boolean allow() {
        Object obj = session.getAttribute("user");
        if (obj == null) {
            return false;
        }
        User user = (User) obj;
        Integer userType = user.getType();
        return userType == 1;
    }
}
