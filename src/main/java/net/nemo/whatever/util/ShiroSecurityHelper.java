package net.nemo.whatever.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * Created by tonyshi on 2016/12/14.
 */
public class ShiroSecurityHelper {
    public static String getCurrentUsername() {
        Subject subject = getSubject();
        PrincipalCollection collection = subject.getPrincipals();
        if (null != collection && !collection.isEmpty()) {
            return (String) collection.iterator().next();
        }
        return null;
    }

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
