package com.lxyg.app.customer.platform.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lxyg.app.customer.platform.model.Goods;
import com.lxyg.app.customer.platform.util.ConfigUtils;
import com.lxyg.app.customer.platform.util.QiniuImgUtil;
import com.qiniu.util.Auth;

public class PageController extends Controller {
//	private Column columnService = new Column();
////	private LogService logService = new LogService();
//
//	/**
//	 * 删除
//	 */
//	public void del() {
//		String colId = getPara("columnId");
//		String columnId[] = colId.split(",");
//		for (String s : columnId) {
//			int cId = Integer.parseInt(s);
//			Column c = Column.dao.findById(cId);
//			int parentId = c.getInt("parentid");
//			String sql = "";
//			List<Record> list = null;
//			switch(parentId) {
//			case 2 :
//				sql = "select * from hc_news where columnid=?";
//				Object[] paras = {columnId};
//				list = Db.find(sql, paras);
//				if(null != list) {
//					for (Record r : list) {
//						Db.delete("hc_news", "newsid", r);
//					}
//				}
//				Res.delBycolumnId(Integer.parseInt(colId));
//				break;
//			case 3 :
//				sql = "select * from hc_play where columnid=?";
//				Object[] paras1 = {columnId};
//				list = Db.find(sql, paras1);
//				if(null != list) {
//					for (Record r : list) {
//						Db.delete("hc_play", "playid", r);
//					}
//				}
//				Res.delBycolumnId(Integer.parseInt(colId));
//				break;
//			}
//			Column.dao.deleteById(cId);
//		}
//		renderJson("0");
//	}
//
	/**
	 * 跳转
	 */
	public void productType() {
		render("/column/colList.jsp");
	}
	public void addProductType(){
		render("/column/addCol.jsp");
	}
	
	public void productBrand() {
		render("/draw/drawList.jsp");
	}
	public void addProductBrand() {
		render("/draw/addDraw.jsp");
	}
	
	public void product() {
		render("/play/playList.jsp");
	}

	public void updateProduct(){
		int id=getParaToInt("productId");
		String index=getPara("index_search");
		if(index.equals("bz")){
			Goods g=new Goods().findById(id);
			setAttr("goods", g);
			setAttr("code", 10002);
		}
		if(index.equals("fbz")){
			Record r= Db.findFirst("select * from kk_product_fb fb where fb.id=?", id);
			setAttr("goods", r);
			setAttr("code", 10002);
		}
		setAttr("qiniuServer", ConfigUtils.getProperty("kaka.qiniu.server"));
		setAttr("token", QiniuImgUtil.loadUpToken());
		render("/play/editPlay.jsp");
	}
	
	public void addproduct() {
		String ak=ConfigUtils.getProperty("kaka.qiniu.ak");
		String sk=ConfigUtils.getProperty("kaka.qiniu.sk");
		Auth auth = Auth.create(ak, sk);
		String token=auth.uploadToken("lxyg");
		setAttr("token", token);
		setAttr("code", 10010);
		setAttr("qiniuServer", ConfigUtils.getProperty("kaka.qiniu.server"));
		render("/play/addPlay.jsp");
	}
	public void toOrderLis(){
		render("/shopdetail/allgoods.jsp");
	}
	
//
//	/**
//	 * 添加栏目
//	 */
//	public void addCol() {
//		UploadFile file = getFile("img","column");
//		Column column = getModel(Column.class);
//		if(null != file) {
//			String name = WebUtils.uuid().substring(0, 8);
//			String filename = DateTools.nowTime().concat(name) + file.getFileName().substring(file.getFileName().lastIndexOf("."));
//			file.getFile().renameTo(new File(ConfigUtils.getProperty("file.path") + "column/" + filename));
//			column.set("img", "column/" + filename);
//		}
//		column.save();
//		if (column.get("parentid").toString().equals("2")
//				|| column.get("parentid").toString().equals("3")) {// 添加新闻、玩乐的栏目时，同时添加栏目权限
//			Res r = new Res();
//			r.set("resname", column.get("cname"));
//			r.set("parentid", getPara("resParId"));
//			r.set("restype", 4);
//			r.set("flag", 0);
//			r.set("columnid", column.get("id"));
//			r.save();
//		}
////		User user = (User) getSession().getAttribute("user");
////		logService.add(user.getInt("uid"), IConstant.ADD, "增加栏目",
////				IPUtil.getIpAddr(getRequest()));
//		/*setAttr("parentId", column.get("parentid"));
//		setAttr("resParId", getPara("resParId"));*/
//		String url = "/column/list?parentId=" + column.get("parentid") + "&resParId=" + getPara("resParId");
//		redirect(url);
//	}
//
//	/**
//	 * 查看
//	 */
//	public void view() {
//		int id = getParaToInt("id");
//		Column c = Column.dao.findById(id);
//		setAttr("column", c);
////		User user = (User) getSession().getAttribute("user");
////		logService.add(user.getInt("uid"), IConstant.SHOW, "查看栏目详情",
////				IPUtil.getIpAddr(getRequest()));
//		render("");
//	}
//
//	/**
//	 * 
//	 */
//	public void preAdd() {
//		int id = getParaToInt("parentId");
//		setAttr("parentId", id);
//		if (id == 2)
//			setAttr("resParId", 76);
//		else if (id == 3)
//			setAttr("resParId", 79);
//		render("/column/addCol.jsp");
//	}
//
//	/**
//	 * 修改//修改排序用到，其他没用到
//	 */
//	public void update() {
//		int id = getParaToInt("id");
//		Column column = Column.dao.findById(id);
//		if (null != getPara("cname"))
//			column.set("cname", getPara("cname"));
//		if (null != getPara("remark"))
//			column.set("remark", getPara("remark"));
//		if (null != getPara("orderby"))
//			column.set("orderby", getPara("orderby"));
//		column.update();
////		User user = (User) getSession().getAttribute("user");
////		logService.add(user.getInt("uid"), IConstant.EDIT, "修改栏目详情",
////				IPUtil.getIpAddr(getRequest()));
//		renderJson("0");
//	}
//
//	/**
//	 * 转向修改页面
//	 */
//	public void preEdit() {
//		int id = getParaToInt("colId");
//		int parentId = getParaToInt("parentId");
//		if (parentId == 2)
//			setAttr("resParId", 76);
//		else if (parentId == 3)
//			setAttr("resParId", 79);
//		Column c = columnService.getCol(id);
//		setAttr("col", c);
//		setAttr("parentId", parentId);
//		render("/column/editCol.jsp");
//	}
//
//	/**
//	 * 修改
//	 */
//	public void edit() {
//		UploadFile file = getFile("img","column");
//		int id = getParaToInt("colId");
//		int parentId = getParaToInt("parentId");
//		Column c = columnService.getCol(id);
//		if(null != file) {
//			String name = WebUtils.uuid().substring(0, 8);
//			String filename = DateTools.nowTime().concat(name) + file.getFileName().substring(file.getFileName().lastIndexOf("."));
//			file.getFile().renameTo(new File(ConfigUtils.getProperty("file.path") + "column/" + filename));
//			c.set("img", "column/" + filename);
//		}
//		switch(parentId) {
//		case 2:
//		case 3:
//			String sql = "select * from hc_res where columnid=?";
//			Record r = Db.findFirst(sql, id);
//			r.set("resname", getPara("cname"));
//			r.set("orderno", getPara("orderby"));
//			Db.update("hc_res", "resid", r);
//			break;
//		}
//		c.set("remark", getPara("remark"));
//		c.set("cname", getPara("cname"));
//		c.set("orderby", getPara("orderby"));
//		c.update();
//		/*
//		 * Res r = Res.findByColid(id); r.set("resname", c.get("cname"));
//		 * r.set("orderno", getPara("orderby")); r.update();
//		 */
//		/*User user = (User) getSession().getAttribute("user");
//		logService.add(user.getInt("uid"), IConstant.EDIT, "修改栏目详情",
//				IPUtil.getIpAddr(getRequest()));*/
//		String url = "/column/list?parentId=" + parentId + "&resParId=" + getPara("resParId");
//		redirect(url);
//	}
//	
//	
//	/**
//	 * APP首页分类管理列表
//	 */
//	public void list4Classify() {
//		String sql = "SELECT id,cname,img,orderby FROM hc_column WHERE flag=1 AND parentid=4 ORDER BY orderby";
//		List<Record> classify = Db.find(sql);
//		setAttr("colList", classify);
//		render("/sys/classify.jsp");
//	}
//	
//	/**
//	 * APP首页分类管理进入修改页面
//	 */
//	public void preUpdate() {
//		int id = getParaToInt("colId");
//		Column c = columnService.getCol(id);
//		setAttr("col", c);
//		render("/sys/editCol.jsp");
//	}
//	
//	/**
//	 * APP首页分类管理修改分类
//	 */
//	public void updateCol() {
//		UploadFile file = getFile("img","column");
//		int id = getParaToInt("colId");
//		Column c = columnService.getCol(id);
//		if(null != file) {
//			String name = WebUtils.uuid().substring(0, 8);
//			String filename = DateTools.nowTime().concat(name) + file.getFileName().substring(file.getFileName().lastIndexOf("."));
//			file.getFile().renameTo(new File(ConfigUtils.getProperty("file.path") + "column/" + filename));
//			c.set("img", "column/" + filename);
//		}
//		c.set("cname", getPara("cname"));
//		c.set("orderby", getPara("orderby"));
//		c.update();
//		redirect("/column/list4Classify");
//	}
}