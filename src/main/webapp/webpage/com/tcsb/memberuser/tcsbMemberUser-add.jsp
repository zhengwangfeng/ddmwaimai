<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会员信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbMemberUserController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbMemberUserPage.id }">
					<input id="membershipLevelId" name="membershipLevelId" type="hidden" value="${tcsbMemberUserPage.membershipLevelId }">
					<input id="balance" name="balance" type="hidden" value="${tcsbMemberUserPage.balance }">
					<input id="openid" name="openid" type="hidden" value="${tcsbMemberUserPage.openid }">
					<input id="createTime" name="createTime" type="hidden" value="${tcsbMemberUserPage.createTime }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbMemberUserPage.shopId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户昵称:
						</label>
					</td>
					<td class="value">
					     	 <input id="nickname" name="nickname" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户昵称</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户性别:
						</label>
					</td>
					<td class="value">
					     	 <input id="sex" name="sex" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户性别</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							省份:
						</label>
					</td>
					<td class="value">
					     	 <input id="province" name="province" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">省份</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市:
						</label>
					</td>
					<td class="value">
					     	 <input id="city" name="city" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">城市</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							国家:
						</label>
					</td>
					<td class="value">
					     	 <input id="country" name="country" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">国家</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户头像:
						</label>
					</td>
					<td class="value">
					     	 <input id="headimgurl" name="headimgurl" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户头像</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户特权信息:
						</label>
					</td>
					<td class="value">
					     	 <input id="privilege" name="privilege" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户特权信息</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							mobile:
						</label>
					</td>
					<td class="value">
					     	 <input id="mobile" name="mobile" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">mobile</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
					     	 <input id="note" name="note" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/memberuser/tcsbMemberUser.js"></script>		
