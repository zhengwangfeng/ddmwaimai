<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>食物库存设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbFoodStockController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbFoodStockPage.id }">
					<input id="foodId" name="foodId" type="hidden" value="${tcsbFoodStockPage.foodId }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbFoodStockPage.shopId }">
					<input id="foodTypeId" name="foodTypeId" type="hidden" value="${tcsbFoodStockPage.foodTypeId }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbFoodStockPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbFoodStockPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbFoodStockPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbFoodStockPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbFoodStockPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbFoodStockPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							库存类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="stockType" name="stockType" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库存类型</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							库存量:
						</label>
					</td>
					<td class="value">
					     	 <input id="stock" name="stock" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库存量</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/foodstock/tcsbFoodStock.js"></script>		
