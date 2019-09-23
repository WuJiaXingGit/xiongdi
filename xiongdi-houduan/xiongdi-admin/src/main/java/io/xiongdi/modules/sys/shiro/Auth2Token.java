package io.xiongdi.modules.sys.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class Auth2Token implements AuthenticationToken {

    private String token;

    public Auth2Token(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
