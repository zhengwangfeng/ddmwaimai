<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbFoodStandardRelationshipList" checkbox="true" fitColumns="false" title="食物规格关联表" actionUrl="tcsbFoodStandardRelationshipController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="食物规格ID"  field="foodStandardId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="食物ID"  field="foodId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbFoodStandardRelationshipController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbFoodStandardRelationshipController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbFoodStandardRelationshipController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbFoodStandardRelationshipController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbFoodStandardRelationshipController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/foodstandardrelationship/tcsbFoodStandardRelationshipList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFoodStandardRelationshipController.do?upload', "tcsbFoodStandardRelationshipList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFoodStandardRelationshipController.do?exportXls","tcsbFoodStandardRelationshipList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFoodStandardRelationshipController.do?exportXlsByT","tcsbFoodStandardRelationshipList");
}
 </script>