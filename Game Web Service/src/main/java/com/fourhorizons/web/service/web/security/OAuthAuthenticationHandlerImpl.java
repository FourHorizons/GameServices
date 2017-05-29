package com.fourhorizons.web.service.web.security;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth.provider.ConsumerAuthentication;
import org.springframework.security.oauth.provider.ConsumerCredentials;
import org.springframework.security.oauth.provider.OAuthAuthenticationHandler;
import org.springframework.security.oauth.provider.token.OAuthAccessProviderToken;
import org.springframework.stereotype.Component;

@Component
public class OAuthAuthenticationHandlerImpl implements OAuthAuthenticationHandler {    
    final static Logger log = LoggerFactory.getLogger(OAuthAuthenticationHandlerImpl.class);

    static SimpleGrantedAuthority userGA = new SimpleGrantedAuthority("ROLE_USER");
    static SimpleGrantedAuthority adminGA = new SimpleGrantedAuthority("ROLE_ADMIN");

    @Override
    public Authentication createAuthentication(HttpServletRequest request, ConsumerAuthentication authentication, OAuthAccessProviderToken authToken) {
    	if(authentication.isSignatureValidated()){
    		Collection<GrantedAuthority> authorities = new HashSet<>(authentication.getAuthorities());
    		authorities.add(userGA);
    		String username = getUsernameFromAuthentication(authentication);
    		Principal principal = new NamedOAuthPrincipal(username, authorities,
    				authentication.getConsumerCredentials().getConsumerKey(),
    				authentication.getConsumerCredentials().getSignature(),
    				authentication.getConsumerCredentials().getSignatureMethod(),
    				authentication.getConsumerCredentials().getSignatureBaseString(),
    				authentication.getConsumerCredentials().getToken()
    				);
    		Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
    		return auth;
    	}else{
    		return null;
    	}
    }

    private String getUsernameFromAuthentication(ConsumerAuthentication auth){
    	return auth.getConsumerCredentials().getConsumerKey();
    }
    
    public static class NamedOAuthPrincipal extends ConsumerCredentials implements Principal {
		private static final long serialVersionUID = 1L;
		public String name;
        public Collection<GrantedAuthority> authorities;

        public NamedOAuthPrincipal(String name, Collection<GrantedAuthority> authorities, String consumerKey, String signature, String signatureMethod, String signatureBaseString, String token) {
            super(consumerKey, signature, signatureMethod, signatureBaseString, token);
            this.name = name;
            this.authorities = authorities;
        }

        @Override
        public String getName() {
            return name;
        }

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }
    }
}