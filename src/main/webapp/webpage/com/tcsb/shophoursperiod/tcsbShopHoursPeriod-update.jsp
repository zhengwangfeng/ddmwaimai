<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>营业时间段</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbShopHoursPeriodController.do?doUpdate"  >
					<input id="id" name="id" type="hidden" value="${tcsbShopHoursPeriodPage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbShopHoursPeriodPage.shopId }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbShopHoursPeriodPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbShopHoursPeriodPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbShopHoursPeriodPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbShopHoursPeriodPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbShopHoursPeriodPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbShopHoursPeriodPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								营业时间段名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  value='${tcsbShopHoursPeriodPage.name}'datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">营业时间段名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/shophoursperiod/tcsbShopHoursPeriod.js"></script>		
