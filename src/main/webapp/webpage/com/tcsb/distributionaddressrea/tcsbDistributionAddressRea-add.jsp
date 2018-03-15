<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户配送地址关联</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbDistributionAddressReaController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbDistributionAddressReaPage.id }">
					<input id="createTime" name="createTime" type="hidden" value="${tcsbDistributionAddressReaPage.createTime }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户唯一标识:
						</label>
					</td>
					<td class="value">
					     	 <input id="openId" name="openId" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户唯一标识</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							配送地址ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="distributionAddressId" name="distributionAddressId" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配送地址ID</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/distributionaddressrea/tcsbDistributionAddressRea.js"></script>		
