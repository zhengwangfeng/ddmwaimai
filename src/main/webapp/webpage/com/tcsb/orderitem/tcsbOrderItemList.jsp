<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTcsbOrderItemBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTcsbOrderItemBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTcsbOrderItemBtn').bind('click', function(){   
 		 var tr =  $("#add_tcsbOrderItem_table_template tr").clone();
	 	 $("#add_tcsbOrderItem_table").append(tr);
	 	 resetTrNum('add_tcsbOrderItem_table');
	 	 return false;
    });  
	$('#delTcsbOrderItemBtn').bind('click', function(){   
      	$("#add_tcsbOrderItem_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tcsbOrderItem_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tcsbOrderItem_table").createhftable({
	    	height:'300px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTcsbOrderItemBtn" href="#">添加</a> <a id="delTcsbOrderItemBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="tcsbOrderItem_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">序号</td>
		<td align="center" bgcolor="#EEEEEE" style="width: 25px;">操作</td>
		          <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						菜品
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						食物
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						数量
				  </td>
				  <td align="left" bgcolor="#EEEEEE" style="width: 126px;">
						单价
				  </td>
	</tr>
	<tbody id="add_tcsbOrderItem_table">
	<c:if test="${fn:length(tcsbOrderItemList)  > 0 }">
		<c:forEach items="${tcsbOrderItemList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tcsbOrderItemList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
				   <td align="left">
				  	<%-- <input name="tcsbOrderItemList[${stuts.index }].foodTypeId" maxlength="32" 
				  		type="text" class="inputxt"  style="width:120px;"  value="${poVal.foodTypeId }"> --%>
				  		<select id="foodTypeId" name="tcsbOrderItemList[${stuts.index }].foodTypeId" datatype="*" >
							<c:forEach items="${tcsbFoodTypeEntity}" var="tcsbFoodTypeEntity">
								<option value="${tcsbFoodTypeEntity.id }" <c:if test="${poVal.foodTypeId==tcsbFoodTypeEntity.id}">selected="selected"</c:if>>${tcsbFoodTypeEntity.name }</option>
							</c:forEach>
						</select>
				  <label class="Validform_label" style="display: none;">菜品</label>
				   </td>
				   <td align="left">
					  <%-- 	<input name="tcsbOrderItemList[${stuts.index }].foodId" maxlength="32" 
					  		type="text" class="inputxt"  style="width:120px;"  value="${poVal.foodId }"> --%>
					  		<select id="foodId" name="tcsbOrderItemList[${stuts.index }].foodId" datatype="*" >
								<c:forEach items="${tcsbFoodEntities}" var="tcsbFoodEntitie">
									<option value="${tcsbFoodEntitie.id }" <c:if test="${poVal.foodId==tcsbFoodEntitie.id}">selected="selected"</c:if>>${tcsbFoodEntitie.name }</option>
								</c:forEach>  
							</select>
					  <label class="Validform_label" style="display: none;">食物</label>
				   </td>
				   <td align="left">
					  	<input name="tcsbOrderItemList[${stuts.index }].count" maxlength="10" 
					  		type="text" class="inputxt"  style="width:120px;"  value="${poVal.count }" datatype="n">
					  <label class="Validform_label" style="display: none;">数量</label>
				   </td>
				   <td align="left">
					  	<input name="tcsbOrderItemList[${stuts.index }].price" maxlength="22" 
					  		type="text" class="inputxt"  style="width:120px;"  value="${poVal.price }">
					  <label class="Validform_label" style="display: none;">单价</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
