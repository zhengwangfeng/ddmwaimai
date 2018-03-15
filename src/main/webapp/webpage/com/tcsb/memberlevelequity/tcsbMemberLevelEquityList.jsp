<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tcsbMemberLevelEquityList" checkbox="true" fitColumns="false" title="会员级别权益" actionUrl="tcsbMemberLevelEquityController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属店铺"  field="shopId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属店铺"  field="shopName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="会员级别id"  field="membershipLevelId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="会员级别"  field="membershipLevelName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否打折"  field="isDiscount"  dictionary="sf_yn"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="打折"  field="discount"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否赠送卡卷"  field="isGiveCard"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="卡卷ID"  field="cardId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名字"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名字"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tcsbMemberLevelEquityController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tcsbMemberLevelEquityController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tcsbMemberLevelEquityController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tcsbMemberLevelEquityController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tcsbMemberLevelEquityController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/tcsb/memberlevelequity/tcsbMemberLevelEquityList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tcsbMemberLevelEquityListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMemberLevelEquityListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMemberLevelEquityListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tcsbMemberLevelEquityListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tcsbMemberLevelEquityController.do?upload', "tcsbMemberLevelEquityList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tcsbMemberLevelEquityController.do?exportXls","tcsbMemberLevelEquityList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tcsbMemberLevelEquityController.do?exportXlsByT","tcsbMemberLevelEquityList");
}
 </script>