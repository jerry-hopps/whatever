package net.nemo.whatever.security;

import net.nemo.whatever.util.ShiroSecurityHelper;
import org.springframework.social.UserIdSource;

/**
 * Created by tonyshi on 2016/12/27.
 */
public class ShiroUserIdSource implements UserIdSource{

    @Override
    public String getUserId() {
        return ShiroSecurityHelper.getCurrentUsername();
    }
}
