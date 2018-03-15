<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺配送范围</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbDistributionScopeController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbDistributionScopePage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbDistributionScopePage.shopId }">
					<input id="pinyin" name="pinyin" type="hidden" value="${tcsbDistributionScopePage.pinyin }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbDistributionScopePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbDistributionScopePage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbDistributionScopePage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbDistributionScopePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbDistributionScopePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbDistributionScopePage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							配送公里数(小于等于):
						</label>
					</td>
					<td class="value">
					     	 <input id="mileage" name="mileage" type="text" style="width: 150px" class="inputxt" datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/" errormsg="请输入两位小数">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配送公里数(小于等于)</label>
						</td>
				</tr>		
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否开启:
						</label>
					</td>
					<td class="value">
					     	 <t:dictSelect field="status" typeGroupCode="sf_yn" hasLabel="false" defaultVal="Y" datatype="*"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否开启</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/distributionscope/tcsbDistributionScope.js"></script>		
