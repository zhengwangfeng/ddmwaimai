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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tcsbOrderController.do?doAdd" >
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
			<%-- <td align="right">
				<label class="Validform_label">下单方式:</label>
			</td>
			<td class="value">
		     	 <!-- <input id="method" name="method" type="text" style="width: 150px" class="inputxt"> -->
		     	 <t:dictSelect field="method" type="select"
						typeGroupCode="method"  hasLabel="false"  title="下单方式"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">下单方式</label>
			</td> --%>
			<td align="right">
				<label class="Validform_label">订单状态:</label>
			</td>
			<td class="value">
		     	 <!-- <input id="status" name="status" type="text" style="width: 150px" class="inputxt"> -->
		     	  <t:dictSelect field="status" type="select"
						typeGroupCode="orderStatu"  hasLabel="false"  title="订单状态"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单状态</label>
			</td>
			<%-- <td align="right">
				<label class="Validform_label">支付方式:</label>
			</td>
			<td class="value">
		     	 <!-- <input id="payMethod" name="payMethod" type="text" style="width: 150px" class="inputxt"> -->
		     	  <t:dictSelect field="payMethod" type="select"
						typeGroupCode="payMethod"  hasLabel="false"  title="订单状态"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">支付方式</label>
			</td> --%>
			<td align="right">
				<label class="Validform_label">特殊说明:</label>
			</td>
			<td class="value">
		     	 <input id="note" name="note" type="text" style="width: 150px" class="inputxt">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">特殊说明</label>
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
					  	<!-- <input name="tcsbOrderItemList[#index#].foodTypeId" maxlength="32" type="text" class="inputxt"  style="width:120px;" > -->
					  		<select id="foodtypeId" name="tcsbOrderItemList[#index#].foodTypeId" style="width: 120px;" onchange="foodtypechange(this)">
								<c:forEach items="${tcsbFoodTypeEntity}" var="tcsbFoodTypeEntity">
									<option value="${tcsbFoodTypeEntity.id }">${tcsbFoodTypeEntity.name }</option>
								</c:forEach>  
							</select>
					  <label class="Validform_label" style="display: none;">菜品</label>
				  </td>
				  <td align="left">
					  	<!-- <input name="tcsbOrderItemList[#index#].foodId" maxlength="32" type="text" class="inputxt"  style="width:120px;" > -->
					  	<select id="foodId" name="tcsbOrderItemList[#index#].foodId" datatype="*" onchange="changePrice(this)">
								<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
									<option value="${tcsbFoodEntitie.id }">${tcsbFoodEntitie.name }</option>
								</c:forEach>  
							</select>	
					  <label class="Validform_label" style="display: none;">食物</label>
				  </td>
				  <td align="left">
					  	<input name="tcsbOrderItemList[#index#].count" maxlength="10" 
					  		type="text" class="inputxt"  style="width:120px;" datatype="n">
					  <label class="Validform_label" style="display: none;">数量</label>
				  </td>
				  <td align="left">
					  	<input name="tcsbOrderItemList[#index#].price" maxlength="22" 
					  		type="text" class="inputxt"  style="width:120px;" >
					  <label class="Validform_label" style="display: none;">单价</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/tcsb/order/tcsbOrder.js"></script>
 <script>
 //初始化价格
 $(function(){
	  var foodId = $("#foodId").val();
	  var trObj = $("#foodId").parent().parent();
	  var url = "tcsbFoodController.do?getFood";
	  $.ajax({
         url:url,
         type:"POST",
         data:{
       	  foodId:foodId 
         },
         dataType:"JSON",
         async: false,
         success:function(data){
             if(data.success){
           	   trObj.find('td:eq(5) input').val( data.obj.price);//5-6
           	   trObj.find('td:eq(4) input').val(1);//3-4
             }
         }
     });
 }); 
 function foodtypechange(obj){
	   var foodTypeId = $(obj).val();
	   var trObj = $(obj).parent().parent();
	   var url = "tcsbOrderController.do?getfoodByfoodType";
	   $.ajax({
	         url:url,
	         type:"POST",
	         data:{
	        	 typeid:foodTypeId
	         },
	         dataType:"JSON",
	         async: false,
	         success:function(data){
	        	 trObj.find('td:eq(3) select').html("");//3-3
	        	 for(var i=0;i<data.length;i++){
	        		 //3-3
	        		  trObj.find('td:eq(3) select').append("<option value="+data[i].id+">"+data[i].name+"</option>"); 
	        		 //$("#foodId").append("<option value="+data[i].id+">"+data[i].name+"</option>");
	        	 }
	        	 var obj = trObj.find('td:eq(3) select');
	        	 changePrice(obj);
	         }
	     });
 } 
 function changePrice(obj){
	  var foodId = $(obj).val();
	  var trObj = $(obj).parent().parent();
	  var oldPrice =parseFloat( trObj.find('td:eq(6) input').val());//5-6
	  var url = "tcsbFoodController.do?getFood";
	  $.ajax({
        url:url,
        type:"POST",
        data:{
      	  foodId:foodId 
        },
        dataType:"JSON",
        async: false,
        success:function(data){
            if(data.success){
            trObj.find('td:eq(5) input').val( data.obj.price);//5-6
			//trObj.find('td:eq(5) input').val(data.obj.unitName);//4-5
             
           /*   if(data.obj.isFloat == 1){
           	  //3-4
           	  trObj.find('td:eq(4) input').attr("datatype","/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/");
             }else{
           	  //3-4
           	  trObj.find('td:eq(4) input').attr("datatype","n");
             } */
             var price = parseFloat(data.obj.price);
    	 	 //var totalPrice =parseFloat($("#totalPrice").val())+price-oldPrice;
         	  //$("#totalPrice").val(toDecimal(totalPrice));
         	  //$("#offlinePrice").val(toDecimal(totalPrice));
            }
        }
    });
} 
 </script>
	