<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbFoodStockList" checkbox="true" fitColumns="false" title="食物库存设置" actionUrl="tcsbFoodStockController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="库存类型"  field="stockType" dictionary ="stockType"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="食物名称"  field="foodId"  hidden ="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="食物名称"  field="foodName"     queryMode="group"  width="120"></t:dgCol>
  <%--  <t:dgCol title="所属店铺"  field="shopId"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="库存量"  field="stock"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="食物类型"  field="foodTypeId" replace="${foodTypeReplace}"    queryMode="single" query="true"  width="120"></t:dgCol>
    <t:dgCol title="食物类型"  field="foodTypeName"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbFoodStockController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="tcsbFoodStockController.do?goAdd" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbFoodStockController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbFoodStockController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbFoodStockController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/foodstock/tcsbFoodStockList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbFoodStockListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodStockListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodStockListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbFoodStockListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFoodStockController.do?upload', "tcsbFoodStockList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFoodStockController.do?exportXls","tcsbFoodStockList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFoodStockController.do?exportXlsByT","tcsbFoodStockList");
}
 </script>