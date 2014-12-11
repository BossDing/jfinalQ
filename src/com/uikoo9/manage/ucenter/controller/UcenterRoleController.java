package com.uikoo9.manage.ucenter.controller;

import java.util.ArrayList;
import java.util.List;

import com.uikoo9.manage.ucenter.model.UcenterRoleModel;
import com.uikoo9.util.QStringUtil;
import com.uikoo9.util.crud.QJson;
import com.uikoo9.util.file.QCacheUtil;
import com.uikoo9.util.jfinal.QController;
import com.uikoo9.util.jfinal.QControllerUrl;
import com.uikoo9.util.ucenter.model.UcenterUserModel;

/**
 * UcenterRoleController
 * @author qiaowenbin
 */
@QControllerUrl("/ucenter/role")
public class UcenterRoleController extends QController{
	
	/**
	 * 跳转到首页 
	 */
	public void index(){
		setAttr("qpage", list(UcenterRoleModel.class));
		render("/WEB-INF/view/manage/ucenter/ucenter-role-index.ftl");
	}
	
	/**
	 * 跳转到保存修改页 
	 */
	public void savep(){
		setAttr("row", getRow(UcenterRoleModel.class));
		render("/WEB-INF/view/manage/ucenter/ucenter-role-input.ftl");
	}
	
	/**
	 * 保存或修改
	 */
	public void save(){
		String validate = validate();
		if(validate == null){
			renderJson(save(UcenterRoleModel.class));
		}else{
			renderJson(new QJson(validate, QJson.TYPE_BS_DANG));
		}
	}
	
	/**
	 * 删除一条或多条
	 */
	public void del(){
		renderJson(del(UcenterRoleModel.class));
	}
	
	/**
	 * 跳转到设置用户页面
	 */
	public void setUser(){
		Integer id = getParaToInt(0);
		UcenterRoleModel role = UcenterRoleModel.dao.findById(id);
		setAttr("roleid", id);
		
		List<UcenterUserModel> inusers = new ArrayList<UcenterUserModel>();
		String inuserids = role.getStr("ucenter_role_user_ids");
		if(QStringUtil.notEmpty(inuserids)){
			for(String userid : inuserids.split(",")){
				inusers.add(UcenterUserModel.dao.findById(userid));
			}
		}
		setAttr("inusers", inusers);

		String outusersql = "select * from t_ucenter_user " + (QStringUtil.isEmpty(inuserids) ? "" : "where id not in ("+inuserids+") ") + "order by user_name";
		setAttr("outusers", UcenterUserModel.dao.find(outusersql));
		
		render("/WEB-INF/view/manage/ucenter/ucenter-role-set-user.ftl");
	}
	
	/**
	 * 添加用户
	 */
	public void addUser(){
		QJson json = new QJson();
		json.setSuccess(true);
		
		try {
			UcenterRoleModel role = UcenterRoleModel.dao.findById(getParaToInt("roleid"));
			String newUserIds = (QStringUtil.isEmpty((String)role.get("ucenter_role_user_ids")) ? "" : role.get("ucenter_role_user_ids") + ",") + getPara("userids");
			role.set("ucenter_role_user_ids", newUserIds).update();
			json.setType(QJson.TYPE_BS_SUCC);
		} catch (Exception e) {
			e.printStackTrace();
			json.setType(QJson.TYPE_BS_DANG);
			json.setMsg("添加用户失败 !");
		}
		
		renderJson(json);
	}
	
	/**
	 * 移除用户
	 */
	public void removeUser(){
		QJson json = new QJson();
		json.setSuccess(true);
		
		try {
			StringBuilder sb = new StringBuilder();
			
			UcenterRoleModel role = UcenterRoleModel.dao.findById(getParaToInt("roleid"));
			List<String> oldUserIds = QStringUtil.splitToList(role.getStr("ucenter_role_user_ids"), ",");
			List<String> newUserIds = QStringUtil.splitToList(getPara("userids"), ",");
			oldUserIds.removeAll(newUserIds);
			
			for(String id : oldUserIds){
				sb.append("," + id);
			}
			role.set("ucenter_role_user_ids", sb.toString().replaceFirst(",", "")).update();
			json.setType(QJson.TYPE_BS_SUCC);
		} catch (Exception e) {
			e.printStackTrace();
			json.setType(QJson.TYPE_BS_DANG);
			json.setMsg("添加用户失败 !");
		}
		
		renderJson(json);
	}
	
	/**
	 * 跳转到设置Url页面
	 */
	@SuppressWarnings("unchecked")
	public void setUrl(){
		setAttr("roleid", getParaToInt(0));

		UcenterRoleModel role = UcenterRoleModel.dao.findById(getParaToInt(0));
		List<String> inurls = QStringUtil.splitToList(role.getStr("ucenter_role_urls"), ",");
		List<String> outurls = new ArrayList<String>();
		for(String url : (List<String>)QCacheUtil.getFromEHCache("urls")){
			outurls.add(url);
		}
		outurls.removeAll(inurls);
		
		setAttr("inurls", inurls);
		setAttr("outurls", outurls);
		
		render("/WEB-INF/view/manage/ucenter/ucenter-role-set-url.ftl");
	}
	
	/**
	 * 添加Url
	 */
	public void addUrl(){
		QJson json = new QJson();
		json.setSuccess(true);
		
		try {
			UcenterRoleModel role = UcenterRoleModel.dao.findById(getParaToInt("roleid"));
			String newUrls = (QStringUtil.isEmpty((String)role.get("ucenter_role_urls")) ? "" : role.get("ucenter_role_urls") + ",") + getPara("urls");
			role.set("ucenter_role_urls", newUrls).update();
			json.setType(QJson.TYPE_BS_SUCC);
		} catch (Exception e) {
			e.printStackTrace();
			json.setType(QJson.TYPE_BS_DANG);
			json.setMsg("添加Url失败 !");
		}
		
		renderJson(json);
	}
	
	/**
	 * 移除Url
	 */
	public void removeUrl(){
		QJson json = new QJson();
		json.setSuccess(true);
		
		try {
			StringBuilder sb = new StringBuilder();
			
			UcenterRoleModel role = UcenterRoleModel.dao.findById(getParaToInt("roleid"));
			List<String> oldUrls = QStringUtil.splitToList(role.getStr("ucenter_role_urls"), ",");
			List<String> newUrls = QStringUtil.splitToList(getPara("urls"), ",");
			oldUrls.removeAll(newUrls);
			
			for(String url : oldUrls){
				sb.append("," + url);
			}
			role.set("ucenter_role_urls", sb.toString().replaceFirst(",", "")).update();
			json.setType(QJson.TYPE_BS_SUCC);
		} catch (Exception e) {
			e.printStackTrace();
			json.setType(QJson.TYPE_BS_DANG);
			json.setMsg("添加Url失败 !");
		}
		
		renderJson(json);
	}
	
}
