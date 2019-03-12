package com.kh.myapp.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDenyHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.access.AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		
		Authentication auth = (Authentication)request.getUserPrincipal();
		
		log.error("접근제한구역에 진입자발생!"+auth.getName());
		log.error("redirect!");
		String msg= auth.getName();
		//response.sendRedirect("/accessDeny");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().append("<script>")
							.append("alert('접근제한구역입니다"+msg+"');")
							.append("location.href='/'")
							.append("</script>");
	}
}
