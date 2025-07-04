package com.cts.library.filter;

import java.io.IOException;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.library.authentication.CurrentUser;
import com.cts.library.exception.UnauthorizedAccessException;
import com.cts.library.model.MemberToken;
import com.cts.library.repository.MemberTokenRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{
	
	private MemberTokenRepo memberTokenRepo;
    private CurrentUser currentUser;
    
    public AuthenticationFilter(MemberTokenRepo memberTokenRepo, CurrentUser currentUser) {
    	this.memberTokenRepo = memberTokenRepo;
    	this.currentUser = currentUser;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
 
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
 
        System.out.println("Token received: " + token);
        
 
        if (token != null && !token.isBlank()) {
        	MemberToken memberToken =  memberTokenRepo.findByMemberToken(token)
                .orElseThrow(() -> new UnauthorizedAccessException("Invalid or expired token"));
 
            currentUser.setCurrentUser(memberToken.getMember());
 
        }
 
        filterChain.doFilter(request, response);
    }
    

}
