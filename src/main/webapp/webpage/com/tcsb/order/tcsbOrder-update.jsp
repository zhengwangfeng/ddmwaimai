<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tcsbOrderController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tcsbOrderPage.id }">
					<input id="shopId" name="shopId" type="hidden" value="${tcsbOrderPage.shopId }">
					<input id="openId" name="openId" type="hidden" value="${tcsbOrderPage.openId }">
					<input id="orderIstake" name="orderIstake" type="hidden" value="${tcsbOrderPage.orderIstake }">
					<input id="updateName" name="updateName" type="hidden" value="${tcsbOrderPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbOrderPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbOrderPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbOrderPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbOrderPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbOrderPage.createDate }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">订单编号:</label>
			</td>
			<td class="value">
		     	 <input id="orderNo" name="orderNo" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.orderNo}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单编号</label>
			</td>
			<%-- <td align="right">
				<label class="Validform_label">下单方式:</label>
			</td>
			<td class="value">
		     	 <input id="method" name="method" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.method}'>
		     	 <t:dictSelect field="method" type="select"
						typeGroupCode="method" defaultVal="${tcsbOrderPage.method}" hasLabel="false"   title="下单方式"></t:dictSelect>    
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">下单方式</label>
			</td> --%>
			<td align="right">
				<label class="Validform_label">下单时间:</label>
			</td>
			<td class="value">
					  <%-- <input id="createTime" name="createTime" type="text" style="width: 150px" 
		      						class="Wdate" onClick="WdatePicker()"  value='<fmt:formatDate value='${tcsbOrderPage.createTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'> --%>
						<input id="createTime" name="createTime" type="text" style="width: 150px" 
		      						  value='<fmt:formatDate value='${tcsbOrderPage.createTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">下单时间</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">订单状态:</label>
			</td>
			<td class="value">
		     	<%--  <input id="status" name="status" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.status}'> --%>
				 <t:dictSelect field="status" type="select"
						typeGroupCode="orderStatu" defaultVal="${tcsbOrderPage.status}" hasLabel="false"   title="订单状态"></t:dictSelect>  
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单状态</label>
			</td>
			<td align="right">
				<label class="Validform_label">支付状态:</label>
			</td>
			<td class="value">
		     	 <%-- <input id="payStatus" name="payStatus" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.payStatus}'> --%>
				<t:dictSelect field="payStatus" type="select"
						typeGroupCode="payStatus" defaultVal="${tcsbOrderPage.payStatus}" hasLabel="false"   title="支付状态"></t:dictSelect> 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">支付状态</label>
			</td>
			<%-- <td align="right">
				<label class="Validform_label">支付方式:</label>
			</td>
			<td class="value">
		     	 <input id="payMethod" name="payMethod" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.payMethod}'>
				<t:dictSelect field="payMethod" type="select"
						typeGroupCode="payMethod" defaultVal="${tcsbOrderPage.payMethod}" hasLabel="false"   title="支付方式"></t:dictSelect> 
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">支付方式</label>
			</td> --%>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">支付时间:</label>
			</td>
			<td class="value">
					 <%--  <input id="payTime" name="payTime" type="text" style="width: 150px" 
		      						class="Wdate" onClick="WdatePicker()"  value='<fmt:formatDate value='${tcsbOrderPage.payTime}' type="date" pattern="yyyy-MM-dd"/>'> --%>
					 <input id="payTime" name="payTime" type="text" style="width: 150px" 
		      						  value='<fmt:formatDate value='${tcsbOrderPage.payTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">支付时间</label>
			</td>
			<td align="right">
				<label class="Validform_label">特殊说明:</label>
			</td>
			<td class="value">
		     	 <input id="note" name="note" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.note}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">特殊说明</label>
			</td>
		</tr>
		<%-- <tr>
			<td align="right">
				<label class="Validform_label">优惠券:</label>
			</td>
			<td class="value">
		     	 <input id="couponId" name="couponId" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.couponId}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠券</label>
			</td>
			<td align="right">
				<label class="Validform_label">优惠活动:</label>
			</td>
			<td class="value">
		     	 <input id="discountActivityId" name="discountActivityId" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.discountActivityId}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠活动</label>
			</td>
		</tr> --%>
		<tr>
			<td align="right">
				<label class="Validform_label">配送费:</label>
			</td>
			<td class="value">
		     	 <input id="distributionPrice" name="distributionPrice" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.distributionPrice}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">配送费</label>
			</td>
			<td align="right">
				<label class="Validform_label">餐盒费:</label>
			</td>
			<td class="value">
		     	 <input id="boxFeePrice" name="boxFeePrice" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.boxFeePrice}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">餐盒费</label>
			</td>
		</tr> 
		<tr>
			<td align="right">
				<label class="Validform_label">优惠券:</label>
			</td>
			<td class="value">
		     	 <input id=userDisPrice name="userDisPrice" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.userDisPrice}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠券</label>
			</td>
			<td align="right">
				<label class="Validform_label">优惠活动:</label>
			</td>
			<td class="value">
		     	 <input id="shopDisPrice" name="shopDisPrice" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.shopDisPrice}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠活动</label>
			</td>
		</tr> 
		<tr>
			<td align="right">
				<label class="Validform_label">总消费:</label>
			</td>
			<td class="value">
		     	 <input id="totalMoney" name="totalMoney" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.totalPrice}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">总消费</label>
			</td>
			<td align="right">
				<label class="Validform_label">需支付:</label>
			</td>
			<td class="value">
		     	 <input id="toPayPrice" name="toPayPrice" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.toPayPrice}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">总消费</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">配送地址:</label>
			</td>
			<td class="value">
		     	 <input id="address" name="address" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.tcsbDistributionAddressEntity.address}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">配送地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系人昵称:</label>
			</td>
			<td class="value">
		     	 <input id="nickName" name="nickName" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.tcsbDistributionAddressEntity.nickName}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人昵称</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">联系人电话:</label>
			</td>
			<td class="value">
		     	 <input id="mobile" name="mobile" type="text" style="width: 150px" class="inputxt" value='${tcsbOrderPage.tcsbDistributionAddressEntity.mobile}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人电话</label>
			</td>
			<td align="right">
			</td>
			<td class="value">
			</td>
		</tr>
			</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tcsbOrderController.do?tcsbOrderItemList&id=${tcsbOrderPage.id}" icon="icon-search" title="订单项管理" id="tcsbOrderItem"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
		<tbody id="add_tcsbOrderItem_table_template">
			<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
			 	  <td align="left">
					  	<!-- <input name="tcsbOrderItemList[#index#].foodTypeId" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		> -->
					  		 <select id="foodtypeId" name="tcsbOrderItemList[#index#].foodTypeId" style="width: 120px;" onchange="foodtypechange(this)">
								 <c:forEach items="${tcsbFoodTypeEntity}" var="tcsbFoodTypeEntity">
									 <option value="${tcsbFoodTypeEntity.id }">${tcsbFoodTypeEntity.name }</option>
								 </c:forEach>
							 </select>
					  <label class="Validform_label" style="display: none;">菜品</label>
				  </td>
				  <td align="left">
					  	<!-- <input name="tcsbOrderItemList[#index#].foodId" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		> -->
					  		<select id="foodId" name="tcsbOrderItemList[#index#].foodId" datatype="*" onchange="changePrice(this)">
								<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
									<option value="${tcsbFoodEntitie.id }">${tcsbFoodEntitie.name }</option>
								</c:forEach>  
							</select>	
					  <label class="Validform_label" style="display: none;">食物</label>
				  </td>
				  <td align="left">
					  	<input name="tcsbOrderItemList[#index#].count" maxlength="10" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">数量</label>
				  </td>
				  <td align="left">
					  	<input name="tcsbOrderItemList[#index#].price" maxlength="22" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">单价</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/tcsb/order/tcsbOrder.js"></script>	
