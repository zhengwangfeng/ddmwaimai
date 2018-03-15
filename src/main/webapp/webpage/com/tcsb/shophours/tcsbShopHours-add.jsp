<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>营业时间</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbShopHoursController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${tcsbShopHoursPage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbShopHoursPage.shopId }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbShopHoursPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbShopHoursPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbShopHoursPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbShopHoursPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbShopHoursPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbShopHoursPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							营业时间段:
						</label>
					</td>
					<td class="value">
						<select id="shopHoursPeriodId" name="shopHoursPeriodId" datatype="*">
							<option value="">---请选择---</option>
							<c:forEach items="${tcsbShopHoursPeriodEntitys}" var="tcsbShopHoursPeriodEntity">
								<option value="${tcsbShopHoursPeriodEntity.id}">${tcsbShopHoursPeriodEntity.name}</option>
							</c:forEach>
						</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">营业时间段</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							营业时间:
						</label>
					</td>
					<td class="value">
					     	 <input id="businessHours" name="businessHours" type="text" style="width: 150px" class="inputxt">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">营业时间</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
 <script>
	 function dateInit1(e) {
		 $("#endTime").val("2017-01-01 "+e+":00");
     }

     function dateInit2(e) {
         $("#beginTime").val("2017-01-01 "+e+":00");
     }


 </script>
  <script src = "webpage/com/tcsb/shophours/tcsbShopHours.js"></script>		
