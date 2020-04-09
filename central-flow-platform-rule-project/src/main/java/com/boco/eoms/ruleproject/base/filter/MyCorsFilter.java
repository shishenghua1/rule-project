package com.boco.eoms.ruleproject.base.filter;

import com.boco.eoms.ruleproject.base.util.StaticMethod;
import com.boco.eoms.ruleproject.fujian.simulation.controller.SimulationStatisticsRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*",filterName = "CorsFilter")
public class MyCorsFilter extends HttpServlet  implements Filter {

	private Logger logger = LoggerFactory.getLogger(MyCorsFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization,x-requested-with,appat,apppd");
		response.setHeader("Access-Control-Max-Age", "3600");
		String userId = StaticMethod.nullObject2String(request.getParameter("userId"));
		//判断userId是否为空，为空则从参数里获取，不为空则设置到session里
		if(!"".equals(userId)){
			request.getSession().setAttribute("userId",userId);
		}else{
			userId = StaticMethod.nullObject2String(request.getSession().getAttribute("userId"));
			if("".equals(userId)){
				logger.error("该次会话不存在userId");
			}
		}
		logger.info("该次会话userId为"+userId);
		if (request.getMethod()!="OPTIONS") {
		  chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

