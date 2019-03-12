package com.kh.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class MeasuringInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(MeasuringInterceptor.class);

	// 클라이언트 요청후 컨트롤러(핸들러) 실행전 현재시간 체크
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		request.setAttribute("beginTime", System.currentTimeMillis());
		
		return true;
	}

	// 클라이언트 응답후 현재시간 체크
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		long beginTime = (long)request.getAttribute("beginTime");
		long endTime = System.currentTimeMillis();
		
		logger.info("IP:"+request.getRemoteHost()+"요청경로:"+request.getRequestURI()+":\t실행시간:"+((double)(endTime-beginTime)/1000)+"초");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
