package little.ant.pingtai.controller;

import little.ant.pingtai.model.Syslog;
import little.ant.pingtai.service.SysLogService;

import org.apache.log4j.Logger;

public class SysLogController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SysLogController.class);
	
	private SysLogService syslogService = new SysLogService();
	
	public void index() {
		defaultOrder("startdate", "desc");
		syslogService.list(splitPage);
		render("/pingtai/sysLog/list.html");
	}
	
	public void view() {
		setAttr("sysLog", syslogService.view(getPara()));
		render("/pingtai/sysLog/view.html");
	}
	
	public void delete() {
		Syslog.dao.deleteById(getPara());
		redirect("/jf/syslog");
	}

}


