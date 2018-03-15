<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>会员统计已消费</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbAssociatorStatisticsController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbAssociatorStatisticsPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								会员结算时间:
							</label>
						</td>
						<td class="value">
									  <input id="createTime" name="createTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tcsbAssociatorStatisticsPage.createTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">会员结算时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								会员ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="userId" name="userId" type="text" style="width: 150px" class="inputxt"  value='${tcsbAssociatorStatisticsPage.userId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">会员ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								所属店铺:
							</label>
						</td>
						<td class="value">
						     	 <input id="shopId" name="shopId" type="text" style="width: 150px" class="inputxt"  value='${tcsbAssociatorStatisticsPage.shopId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属店铺</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/associatorstatistics/tcsbAssociatorStatistics.js"></script>		
