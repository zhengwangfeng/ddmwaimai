<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
.addbtnstyleclass{
			height: 21px;
            line-height: 21px;
            padding: 0 11px;
            background: #4ba8f7;
            border: 1px #26bbdb solid;
            border-radius: 3px;
            display: inline-block;
            text-decoration: none;
            font-size: 12px;
            outline: none;
            color:white;
}
.delbtnstyleclass{
	 		height: 21px;
            line-height: 21px;
            padding: 0 11px;
            background: #9D9D9D;
            border: 1px #9D9D9D solid;
            border-radius: 3px;
            display: inline-block;
            text-decoration: none;
            font-size: 12px;
            outline: none;
            color:white;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbOrderList" checkbox="true" fitColumns="false" title="订单" actionUrl="tcsbOrderController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
  <%--  <t:dgCol title="订单编号"  field="orderNo"    queryMode="group"  width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="下单方式"  field="method"    queryMode="group"  width="120"></t:dgCol> --%>
  <%--  <t:dgCol title="所属店铺ID"  field="shopId"    queryMode="group"  width="120"></t:dgCol> --%>
  <%--  <t:dgCol title="下单时间"  field="createTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="150"></t:dgCol> --%>
   <t:dgCol title="下单时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="订单状态"  field="status"  dictionary="orderStatu"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="支付状态"  field="payStatus" dictionary="payStatus"    queryMode="group"  width="120"></t:dgCol>
  <%--  <t:dgCol title="支付方式"  field="payMethod"    queryMode="group"  width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="openId"  field="openId"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="特殊说明"  field="note"    queryMode="group"  width="120"></t:dgCol>
  <t:dgCol title="是否已接单"  field="orderIstake"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
 <%--   <t:dgCol title="优惠券"  field="couponId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="优惠活动"  field="discountActivityId"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="是否接单"  field="orderIstake"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="餐盒费"  field="boxFeePrice"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="配送费"  field="distributionPrice"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="总消费"  field="totalPrice"    queryMode="group"  width="120"></t:dgCol>    
   <t:dgCol title="店铺优惠活动"  field="shopDisPrice"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="优惠券打折"  field="userDisPrice"    queryMode="group"  width="120"></t:dgCol> 
    <t:dgCol title="需支付"  field="toPayPrice"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="配送地址"  field="tcsbDistributionAddressEntity.address"    queryMode="group"  width="120"></t:dgCol> 
   <t:dgCol title="联系人姓名"  field="tcsbDistributionAddressEntity.nickName"    queryMode="group"  width="120"></t:dgCol> 
   <t:dgCol title="联系人电话"  field="tcsbDistributionAddressEntity.mobile"    queryMode="group"  width="120"></t:dgCol> 
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgFunOpt funname="receiveOrder(id)" title="接单"  exp="orderIstake#eq#N" urlclass="addbtnstyleclass"/>
	<t:dgFunOpt funname="receiveOrders(id)" title="已接单"  exp="orderIstake#eq#Y" urlclass="delbtnstyleclass"/>
	<t:dgToolBar  title="打印" icon="icon-print" url="tcsbOrderController.do?print3" funname="detail" ></t:dgToolBar>
   <%-- <t:dgDelOpt title="删除" url="tcsbOrderController.do?doDel&id={id}"  urlclass="ace_button" urlfont="fa-trash-o"/> --%>
  <%--  <t:dgToolBar title="录入" icon="icon-add" url="tcsbOrderController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbOrderController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbOrderController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/order/tcsbOrderList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbOrderListtb").find("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='payTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='payTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbOrderListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function receiveOrder(Id){
		var url = "tcsbOrderController.do?receiveOrder";
		$.post(
				url,
				{id:Id},
				function(data){
					$('#tcsbOrderList').datagrid('reload');	
				}
		);
	}

function receiveOrders(Id){
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbOrderController.do?upload', "tcsbOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbOrderController.do?exportXls","tcsbOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbOrderController.do?exportXlsByT","tcsbOrderList");
}
 </script>