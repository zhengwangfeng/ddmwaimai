<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺配送范围属性</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbDistributionScopeAttrController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbDistributionScopeAttrPage.id }">
					<input id="type" name="type" type="hidden" value="${tcsbDistributionScopeAttrPage.type }">
					<input id="userId" name="userId" type="hidden" value="${tcsbDistributionScopeAttrPage.userId }">
					<input id="chargeType" name="chargeType" type="hidden" value="${tcsbDistributionScopeAttrPage.chargeType }">
					<input id="consumptionNotFull" name="consumptionNotFull" type="hidden" value="${tcsbDistributionScopeAttrPage.consumptionNotFull }">
					<input id="note" name="note" type="hidden" value="${tcsbDistributionScopeAttrPage.note }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbDistributionScopeAttrPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbDistributionScopeAttrPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbDistributionScopeAttrPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbDistributionScopeAttrPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbDistributionScopeAttrPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbDistributionScopeAttrPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								起送金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="beginSendMoney" name="beginSendMoney" type="text" style="width: 150px" class="inputxt"  value='${tcsbDistributionScopeAttrPage.beginSendMoney}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起送金额</label>
						</td>
					</tr>	
					<tr>
						<td align="right">
							<label class="Validform_label">
								配送费:
							</label>
						</td>
						<td class="value">
						     	 <input id="distributionFee" name="distributionFee" type="text" style="width: 150px" class="inputxt"  value='${tcsbDistributionScopeAttrPage.distributionFee}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配送费</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								配送范围:
							</label>
						</td>
						<td class="value">
						     	 <%-- <input id="distributionScopeId" name="distributionScopeId" type="text" style="width: 150px" class="inputxt"  value='${tcsbDistributionScopeAttrPage.distributionScopeId}'> --%>
								 <select id="distributionScopeId" name="distributionScopeId" datatype="*">
										<c:forEach items="${tcsbDistributionScopeEntities}" var="tcsbDistributionScopeEntitie">
											<option value="${tcsbDistributionScopeEntitie.id }" <c:if test="${tcsbDistributionScopeEntitie.id==tcsbDistributionScopeAttrPage.distributionScopeId}">selected="selected"</c:if>>${tcsbDistributionScopeEntitie.mileage}</option>
										</c:forEach> 
									</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配送范围</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/distributionscopeattr/tcsbDistributionScopeAttr.js"></script>		
