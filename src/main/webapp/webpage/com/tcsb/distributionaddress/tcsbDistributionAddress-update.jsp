<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户配送地址</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbDistributionAddressController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbDistributionAddressPage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbDistributionAddressPage.shopId }">
					<input id="createTime" name="createTime" type="hidden" value="${tcsbDistributionAddressPage.createTime }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								配送地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="address" name="address" type="text" style="width: 150px" class="inputxt"  value='${tcsbDistributionAddressPage.address}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配送地址</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经度:
							</label>
						</td>
						<td class="value">
						     	 <input id="longitude" name="longitude" type="text" style="width: 150px" class="inputxt"  value='${tcsbDistributionAddressPage.longitude}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经度</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								纬度:
							</label>
						</td>
						<td class="value">
						     	 <input id="latitude" name="latitude" type="text" style="width: 150px" class="inputxt"  value='${tcsbDistributionAddressPage.latitude}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">纬度</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/distributionaddress/tcsbDistributionAddress.js"></script>		
