<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺收藏</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbShopCollectController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbShopCollectPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								shopId:
							</label>
						</td>
						<td class="value">
						     	 <input id="shopId" name="shopId" type="text" style="width: 150px" class="inputxt"  value='${tcsbShopCollectPage.shopId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">shopId</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								userId:
							</label>
						</td>
						<td class="value">
						     	 <input id="userId" name="userId" type="text" style="width: 150px" class="inputxt"  value='${tcsbShopCollectPage.userId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">userId</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								createTime:
							</label>
						</td>
						<td class="value">
									  <input id="createTime" name="createTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tcsbShopCollectPage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">createTime</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/shopcollect/tcsbShopCollect.js"></script>		
