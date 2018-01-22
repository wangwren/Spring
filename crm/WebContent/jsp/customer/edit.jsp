<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>客户修改</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>


<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
<script type="text/javascript">
//使用JQ实现客户级别和客户来源的异步查询
//页面加载完成后发送ajax请求
$(function(){
	//发送ajax请求,获得客户级别
	var url = "${pageContext.request.contextPath}/dict_findByCode.action";
	var param = {"dict.dict_type_code" : "006"};
	//定义返回的是json数据
	$.post(url,param,function(data){
		//后台返回的list集合转成json数据是数组，里边存放的是对象，遍历
		//i代表遍历的下标值，n代表遍历的当前对象
		$(data).each(function(i,n){
			//JQ的DOM操作
			
			//对于客户级别和来源的数据回显
			//值栈中的dict_id和json数据中对象的dict_id相同就是先前想要查询的条件,值栈中的值也可以用EL表达式取出
			var vsId = "${model.level.dict_id}";
			if(vsId == n.dict_id){
				$("#levelId").append("<option value='" + n.dict_id + "' selected>" + n.dict_item_name + "</option>");
			}else{
				$("#levelId").append("<option value='" + n.dict_id + "'>" + n.dict_item_name + "</option>");
			}
		});
	},"json");
	
	//客户来源的异步查询
	var url = "${pageContext.request.contextPath}/dict_findByCode.action";
	var param = {"dict.dict_type_code" : "002"};
	//定义返回的是json数据
	$.post(url,param,function(data){
		//后台返回的list集合转成json数据是数组，里边存放的是对象，遍历
		//i代表遍历的下标值，n代表遍历的当前对象
		$(data).each(function(i,n){
			//JQ的DOM操作
			var vsId = "${model.source.dict_id}";
			if(vsId == n.dict_id){
				$("#sourceId").append("<option value='" + n.dict_id + "' selected>" + n.dict_item_name + "</option>");
			}else{
				$("#sourceId").append("<option value='" + n.dict_id + "'>" + n.dict_item_name + "</option>");
			}
		});
	},"json");
	
})

</script>
</HEAD>
<BODY>
	<!-- 修改客户，可以重新选择上传文件，所以需要加enctype="multipart/form-data" -->
	<FORM id=form1 name=form1 action="${pageContext.request.contextPath }/customer_update.action" method="post" enctype="multipart/form-data">
	
		<!-- 隐藏客户id -->
		<input type="hidden" name="cust_id" value="${model.cust_id }"/>
		<!-- 隐藏原有的文件上传路径，如果重新上传了新文件，就更新数据库中路径，如果没有就还使用该文件路径 -->
		<input type="hidden" name="filePath" value="${model.filePath }" />

		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg"
						border=0></TD>
					<TD width="100%" background=${pageContext.request.contextPath }/images/new_020.jpg
						height=20></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_021.jpg"
						border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15 background=${pageContext.request.contextPath }/images/new_022.jpg><IMG
						src="${pageContext.request.contextPath }/images/new_022.jpg" border=0></TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD class=manageHead>当前位置：客户管理 &gt; 修改客户</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<TABLE cellSpacing=0 cellPadding=5  border=0>
							<TR>
								<td>客户名称：</td>
								<td>
								<INPUT class=textbox id=sChannel2
											style="WIDTH: 180px" maxLength=50 name="cust_name" value="${model.cust_name }">
								</td>
								<td>客户级别 ：</td>
								<td>
								<!-- 在customer中封装的是dict对象，所以name需要这样写，
								由于customer实现了模型驱动，所以不用加customer，客户来源同理 -->
									<select name="level.dict_id" id="levelId">
									</select>	
								</td>
							</TR>
							
							<TR>
								<td>信息来源：</td>
								<td>
									<select name="source.dict_id" id="sourceId">
									</select>
								</td>
								<td>联系人：</td>
								<td>
								<INPUT class=textbox id=sChannel2
														style="WIDTH: 180px" maxLength=50 name="cust_linkman" value="${model.cust_linkman }">
								</td>
							</TR>
							<TR>
								
								
								<td>固定电话 ：</td>
								<td>
								<INPUT class=textbox id=sChannel2
														style="WIDTH: 180px" maxLength=50 name="cust_phone" value="${model.cust_phone }">
								</td>
								<td>移动电话 ：</td>
								<td>
								<INPUT class=textbox id=sChannel2
														style="WIDTH: 180px" maxLength=50 name="cust_mobile" value="${model.cust_mobile }">
								</td>
							</TR>
							
							
							<TR>
								
								<td>上传资质 ：</td>
								<td>
									<input type="file" name="upload"/>
								</td>
							</TR>
							<tr>
								<td rowspan=2>
								<INPUT class=button id=sButton2 type=submit
														value=" 保存 " name=sButton2>
								</td>
							</tr>
						</TABLE>
						
						
					</TD>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg">
					<IMG src="${pageContext.request.contextPath }/images/new_023.jpg" border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_024.jpg"
						border=0></TD>
					<TD align=middle width="100%"
						background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg"
						border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
	</FORM>
</BODY>
</HTML>
