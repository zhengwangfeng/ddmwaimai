<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>餐盒费设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbBoxFeeController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbBoxFeePage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbBoxFeePage.shopId }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbBoxFeePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbBoxFeePage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbBoxFeePage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbBoxFeePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbBoxFeePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbBoxFeePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								每份价格:
							</label>
						</td>
						<td class="value">
						     	 <input id="price" name="price" type="text" style="width: 150px" class="inputxt"  value='${tcsbBoxFeePage.price}' datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/" errormsg="请输入两位小数">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">每份价格</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								是否开启:
							</label>
						</td>
						<td class="value">
						     	 <%-- <input id="status" name="status" type="text" style="width: 150px" class="inputxt"  value='${tcsbBoxFeePage.status}' > --%>
							 <t:dictSelect field="status" typeGroupCode="sf_yn" hasLabel="false" defaultVal="${tcsbBoxFeePage.status}" datatype="*"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否开启</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/boxfee/tcsbBoxFee.js"></script>		
