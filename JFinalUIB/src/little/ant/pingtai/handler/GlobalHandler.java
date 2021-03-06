package little.ant.pingtai.handler;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import little.ant.pingtai.beetl.MyBeetlRender;
import little.ant.pingtai.model.Syslog;
import little.ant.pingtai.thread.ThreadSysLog;
import little.ant.pingtai.tools.ToolContext;
import little.ant.pingtai.tools.ToolDateTime;
import little.ant.pingtai.tools.ToolWeb;

import org.apache.log4j.Logger;

import com.jfinal.handler.Handler;

/**
 * 全局Handler，设置一些通用功能
 * @author 董华健
 * 描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {
	
	private static Logger log = Logger.getLogger(GlobalHandler.class);
	
	public static String reqSysLogKey = "reqSysLog";
	
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		log.info("初始化访问系统功能日志");
		Syslog reqSysLog = getSysLog(request);
		long starttime = ToolDateTime.getDateByTime();
		reqSysLog.set("startdate", ToolDateTime.getSqlTimestamp(starttime));//开始时间
		request.setAttribute(reqSysLogKey, reqSysLog);
		
		log.info("设置 web 路径");
		String cxt = ToolContext.getContextAllPath(request);
		request.setAttribute("cxt", cxt);
		
		log.debug("beetl cookie处理");
		Map<String, Cookie> cookieMap = ToolWeb.readCookieMap(request);
		request.setAttribute("cookieMap", cookieMap);

		log.debug("beetl 请求参数处理");
		request.setAttribute("paramMap", ToolWeb.getParamMap(request));

		log.info("设置Header");
		request.setAttribute("decorator", "none");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		
		nextHandler.handle(target, request, response, isHandled);
		
		log.info("请求处理完毕，计算耗时");
		
		// 结束时间
		long endtime = ToolDateTime.getDateByTime();
		reqSysLog.set("enddate", ToolDateTime.getSqlTimestamp(endtime));
		
		// 总耗时
		Long haoshi = endtime - starttime;
		reqSysLog.set("haoshi", haoshi);
		
		// 视图耗时
		long renderTime = 0;
		if(null != request.getAttribute(MyBeetlRender.renderTimeKey)){
			renderTime = (long) request.getAttribute(MyBeetlRender.renderTimeKey);
		}
		reqSysLog.set("viewhaoshi", renderTime);
		
		// action耗时
		reqSysLog.set("actionhaoshi", haoshi - renderTime);
		
		log.info("日志添加到入库队列");
		ThreadSysLog.add(reqSysLog);
	}
	
	/**
	 * 创建日志对象,并初始化一些属性值
	 * @param request
	 * @return
	 */
	public Syslog getSysLog(HttpServletRequest request){
		String requestPath = ToolWeb.getRequestURIWithParam(request); 
		String ip = ToolWeb.getIpAddr(request);
		String referer = request.getHeader("Referer"); 
		String userAgent = request.getHeader("User-Agent");
		String cookie = request.getHeader("Cookie");
		String method = request.getMethod();
		String xRequestedWith = request.getHeader("X-Requested-With");
		String host = request.getHeader("Host");
		String acceptLanguage = request.getHeader("Accept-Language");
		String acceptEncoding = request.getHeader("Accept-Encoding");
		String accept = request.getHeader("Accept");
		String connection = request.getHeader("Connection");

		Syslog reqSysLog = new Syslog();
		
		reqSysLog.set("ips", ip);
		reqSysLog.set("requestpath", requestPath);
		reqSysLog.set("referer", referer);
		reqSysLog.set("useragent", userAgent);
		reqSysLog.set("cookie", cookie);
		reqSysLog.set("method", method);
		reqSysLog.set("xrequestedwith", xRequestedWith);
		reqSysLog.set("host", host);
		reqSysLog.set("acceptlanguage", acceptLanguage);
		reqSysLog.set("acceptencoding", acceptEncoding);
		reqSysLog.set("accept", accept);
		reqSysLog.set("connection", connection);

		return reqSysLog;
	}
	
}
