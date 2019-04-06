package pers.fairy.miusa.common;

import org.springframework.stereotype.Component;
import pers.fairy.miusa.entity.User;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 20:04
 */
@Component
public class HostHolder {

    private final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public User getUser() {
        return threadLocal.get();
    }

    public void setUser(User user) {
        threadLocal.set(user);
    }

    public void clear(){
        threadLocal.remove();
    }
}
