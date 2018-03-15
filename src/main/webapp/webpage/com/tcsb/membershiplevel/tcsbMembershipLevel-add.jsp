<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会员级别</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbMembershipLevelController.do?doAdd"  >
					<input id="id" name="id" type="hidden" value="${tcsbMembershipLevelPage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbMembershipLevelPage.shopId }">
					<%--<input id="memberTitleId" name="memberTitleId" type="hidden" value="${tcsbMembershipLevelPage.memberTitleId }">--%>
					<input id="updateName" name="updateName" type="hidden" value="${tcsbMembershipLevelPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbMembershipLevelPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbMembershipLevelPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbMembershipLevelPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbMembershipLevelPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbMembershipLevelPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							会员级别名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">会员级别名称</label>
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						会员级别条件:
					</label>
				</td>
				<td class="value">
					<!-- <input id="fullcutTemplateId" name="fullcutTemplateId" type="text" style="width: 150px" class="inputxt" > -->
					<select id="memberLevelConditionsId" name="memberLevelConditionsId" datatype="*">
						<option value="">---请选择---</option>
						<c:forEach items="${tcsbMemberLevelConditionsEntitys}" var="tcsbMemberLevelConditionsEntity">
							<option value="${tcsbMemberLevelConditionsEntity.id}">${tcsbMemberLevelConditionsEntity.charge}</option>
						</c:forEach>
					</select>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">会员级别条件</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						会员头衔:
					</label>
				</td>
				<td class="value">
					<!-- <input id="fullcutTemplateId" name="fullcutTemplateId" type="text" style="width: 150px" class="inputxt" > -->
					<select id="memberTitleId" name="memberTitleId" datatype="*">
						<option value="">---请选择---</option>
						<c:forEach items="${tcsbMemberTitleEntitys}" var="tcsbMemberTitle">
							<option value="${tcsbMemberTitle.id}">${tcsbMemberTitle.name}</option>
						</c:forEach>
					</select>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">会员级别条件</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/membershiplevel/tcsbMembershipLevel.js"></script>		
