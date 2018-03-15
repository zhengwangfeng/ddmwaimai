<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbDistributionScopeAttrList" checkbox="true" fitColumns="false" title="店铺配送范围属性" actionUrl="tcsbDistributionScopeAttrController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起送金额"  field="beginSendMoney"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="type"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="配送员"  field="userId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="计费类型"  field="chargeType"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="消费未满"  field="consumptionNotFull"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="配送费"  field="distributionFee"    queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="配送范围"  field="distributionScopeId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="配送范围"  field="mileage"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="group"  width="180"></t:dgCol>
   <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbDistributionScopeAttrController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbDistributionScopeAttrController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbDistributionScopeAttrController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbDistributionScopeAttrController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbDistributionScopeAttrController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/distributionscopeattr/tcsbDistributionScopeAttrList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbDistributionScopeAttrListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDistributionScopeAttrListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDistributionScopeAttrListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbDistributionScopeAttrListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbDistributionScopeAttrController.do?upload', "tcsbDistributionScopeAttrList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbDistributionScopeAttrController.do?exportXls","tcsbDistributionScopeAttrList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbDistributionScopeAttrController.do?exportXlsByT","tcsbDistributionScopeAttrList");
}
 </script>