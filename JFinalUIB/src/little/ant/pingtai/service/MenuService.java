package little.ant.pingtai.service;

import java.util.List;

import little.ant.pingtai.model.Menu;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

public class MenuService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(MenuService.class);
	

	/**
	 * 获取子节点数据
	 * @param parentIds
	 * @return
	 */
	public String childNodeData(String systemsIds, String parentIds){
		String sql = null;
		List<Menu> list = null;
		if(null != parentIds){
			sql = " select ids, names, isparent, images from pt_menu where parentMenuIds = ? order by orderIds asc ";
			list = Menu.dao.find(sql, parentIds);
			
		}else{
			sql = " select ids, names, isparent, images from pt_menu where parentMenuIds is null and systemsIds=? order by orderIds asc ";
			list = Menu.dao.find(sql, systemsIds);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		int size = list.size() - 1;
		for (Menu menu : list) {
			sb.append(" { ");
			sb.append(" id : '").append(menu.getStr("ids")).append("', ");
			sb.append(" name : '").append(menu.getStr("names")).append("', ");
			sb.append(" isParent : true, ");
			sb.append(" font : {'font-weight':'bold'}, ");
			sb.append(" icon : '").append("/jsFile/zTree/css/zTreeStyle/img/diy/").append(menu.getStr("images")).append("' ");
			sb.append(" }");
			if(list.indexOf(menu) < size){
				sb.append(", ");
			}
		}
		
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * 保存
	 * @param pIds
	 * @param names
	 * @param orderIds
	 * @return
	 */
	@Before(Tx.class)
	public String save(String pIds, String names, int orderIds) {
		Menu pDept = Menu.dao.findById(pIds);
		pDept.set("isparent", "true").update();
		
		String images = "";
		if(orderIds < 2 || orderIds > 9){
			orderIds = 2;
			images = "2.png";
		}else{
			images = orderIds + ".png";
		}

		Menu menu = new Menu();
		menu.set("isparent", "false");
		menu.set("parentmenuids", pIds);
		menu.set("orderids", orderIds);
		menu.set("names", names);
		menu.set("images", images);
		menu.save();
		
		return menu.getStr("ids");
	}
	
	/**
	 * 更新
	 * @param ids
	 * @param pIds
	 * @param names
	 * @param principalIds
	 */
	public void update(String ids, String pIds, String names) {
		Menu menu = Menu.dao.findById(ids);
		if(null != names && !names.isEmpty()){
			//更新模块名称
			menu.set("names", names).update();
			
		}else if(null != pIds && !pIds.isEmpty()){
			//更新上级模块
			menu.set("parentmenuids", pIds).update();
		}
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public boolean delete(String ids) {
		Record record = Db.findFirst("select count(*) as counts from pt_menu where parentmenuids=?", ids);
		Long counts = record.getNumber("counts").longValue();
	    if(counts > 1){
	    	return false;
	    }
	    Menu.dao.deleteById(ids);
	    return true;
	}
	
	/**
	 * 设置菜单功能
	 * @param roleIds
	 * @param moduleIds
	 * @param operatorIds
	 */
	public void setOperator(String menuIds, String operatorIds){
		Menu menu = Menu.dao.findById(menuIds);
		menu.set("operatorids", operatorIds).update();
	}
	
}
