<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>tcsb_food_function</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbFoodFunctionController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tcsbFoodFunctionPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								operation:
							</label>
						</td>
						<td class="value">
						     	 <input id="operation" name="operation" type="text" style="width: 150px" class="inputxt"  value='${tcsbFoodFunctionPage.operation}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">operation</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								functionid:
							</label>
						</td>
						<td class="value">
						     	 <input id="functionid" name="functionid" type="text" style="width: 150px" class="inputxt"  value='${tcsbFoodFunctionPage.functionid}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">functionid</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								foodid:
							</label>
						</td>
						<td class="value">
						     	 <input id="foodid" name="foodid" type="text" style="width: 150px" class="inputxt"  value='${tcsbFoodFunctionPage.foodid}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">foodid</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								datarule:
							</label>
						</td>
						<td class="value">
						     	 <input id="datarule" name="datarule" type="text" style="width: 150px" class="inputxt"  value='${tcsbFoodFunctionPage.datarule}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">datarule</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/tcsbfoodfunction/tcsbFoodFunction.js"></script>		
