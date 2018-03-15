<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbFoodFunctionList" checkbox="true" fitColumns="false" title="tcsb_food_function" actionUrl="tcsbFoodFunctionController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="operation"  field="operation"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="functionid"  field="functionid"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="foodid"  field="foodid"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="datarule"  field="datarule"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbFoodFunctionController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbFoodFunctionController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbFoodFunctionController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbFoodFunctionController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbFoodFunctionController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/tcsbfoodfunction/tcsbFoodFunctionList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbFoodFunctionController.do?upload', "tcsbFoodFunctionList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbFoodFunctionController.do?exportXls","tcsbFoodFunctionList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbFoodFunctionController.do?exportXlsByT","tcsbFoodFunctionList");
}
 </script>