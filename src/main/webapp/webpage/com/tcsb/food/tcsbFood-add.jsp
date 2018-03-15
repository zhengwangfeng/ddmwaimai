<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>美食</title>
  <t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tcsbFoodController.do?doAdd"  >
					<input id="id" name="id" type="hidden" value="${tcsbFoodPage.id }">
					<%-- <input id="foodTypeId" name="foodTypeId" type="hidden" value="${tcsbFoodPage.foodTypeId }"> --%>
					<input id="updateName" name="updateName" type="hidden" value="${tcsbFoodPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tcsbFoodPage.updateDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tcsbFoodPage.updateBy }">
					<input id="createName" name="createName" type="hidden" value="${tcsbFoodPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tcsbFoodPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tcsbFoodPage.createDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属菜品:
						</label>
					</td>
					<td class="value">
					     	 <!-- <input id="istakeout" name="istakeout" type="text" style="width: 150px" class="inputxt" > -->
			     	 <select id="foodTypeId" name="foodTypeId" datatype="*">
						<c:forEach items="${tcsbFoodTypeEntities}" var="tcsbFoodTypeEntitie">
							<option value="${tcsbFoodTypeEntitie.id }">${tcsbFoodTypeEntitie.name}</option>
						</c:forEach> 
					</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属菜品</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							菜品单位:
						</label>
					</td>
					<td class="value">
					     	<select id="foodUnitId" name="foodUnitId" datatype="*" >
					     	<c:forEach var="tcsbFoodUnitList" items="${tcsbFoodUnitList }">
					     		<option value="${tcsbFoodUnitList.id }">${tcsbFoodUnitList.name }</option>
					     	</c:forEach>
					    	</select>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							价格:
						</label>
					</td>
					<td class="value">
					     	 <input id="price" name="price" type="text" style="width: 150px" class="inputxt" datatype="/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/" errormsg="请输入两位小数">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">价格</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							顺序:
						</label>
					</td>
					<td class="value">
					     	 <input id="orders" name="orders" type="text" style="width: 150px" class="inputxt" datatype="*" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顺序</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否需要餐盒费:
						</label>
					</td>
					<td class="value">
						 <select id="needBoxFee" name="needBoxFee" datatype="*" >
							<option value="N" selected="selected">否</option>
							<option value="Y" >是</option>
					     </select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否参与优惠打折:
						</label>
					</td>
					<td class="value">
						 <select id="isDis" name="isDis" datatype="*" >
							<option value="N" >否</option>
							<option value="Y" selected="selected">是</option>
					     </select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
					     	 <!-- <input id="img" name="img" type="text" style="width: 150px" class="inputxt" > -->
					     	  <t:ckfinder name="img" uploadType="Images" width="100" height="60" buttonClass="ui-button" buttonValue="上传图片"></t:ckfinder>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片</label>
						</td>
						</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否是特色:
						</label>
					</td>
					<td class="value">
					     	 <!-- <input id="ischara" name="ischara" type="text" style="width: 150px" class="inputxt" > -->
				     	 <select id="ischara" name="ischara" datatype="*">
							<option value="0" selected="selected">否</option>
							<option value="1" >是</option>
						 </select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否是特色</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							菜状态:
						</label>
					</td>
					<td class="value">
					     	<!--  <input id="status" name="status" type="text" style="width: 150px" class="inputxt" > -->
					     	<select id="status" name="status" datatype="*">
								<option value="1" selected="selected">上架</option>
								<option value="0" >下架</option>
						    </select>		
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">菜状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/tcsb/food/tcsbFood.js"></script>		
