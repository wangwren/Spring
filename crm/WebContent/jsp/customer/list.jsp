<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>客户列表</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
	rel=stylesheet>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<SCRIPT language=javascript>

	//提交分页查询的表单
	function to_page(page){
		if(page){
			//将page值赋值给一个id为page的
			$("#page").val(page);
		}
		//之后提交表单
		document.customerForm.submit();
		
	}
	
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
</SCRIPT>

<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
	<FORM id="customerForm" name="customerForm"
		action="${pageContext.request.contextPath }/customer_findByPage.action"
		method=post>
		
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg"
						border=0></TD>
					<TD width="100%" background="${pageContext.request.contextPath }/images/new_020.jpg"
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
								<TD class=manageHead>当前位置：客户管理 &gt; 客户列表</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>
						<TABLE borderColor=#cccccc cellSpacing=0 cellPadding=0
							width="100%" align=center border=0>
							<TBODY>
								<TR>
									<TD height=25>
										<TABLE cellSpacing=0 cellPadding=2 border=0>
											<TBODY>
												<TR>
													<TD>客户名称：</TD>
													<TD>
														<INPUT class=textbox id=sChannel2 style="WIDTH: 80px" maxLength=50 name="cust_name" value="${model.cust_name}">
													</TD>
													
													<td>客户级别</td>
													<td>
														<!-- 在customer中封装的是dict对象，所以name需要这样写，
														由于customer实现了模型驱动，所以不用加customer，客户来源同理 -->
														<select name="level.dict_id" id="levelId">
															<option value="">----请选择----</option>
														</select>
													</td>
													
													<td>客户来源</td>
													<td>
														<select name="source.dict_id" id="sourceId">
															<option value="">----请选择----</option>
														</select>
													</td>
													
													<TD>
														<INPUT class=button id=sButton2 type=submit value="筛选 " name=sButton2>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							    
								<TR>
									<TD>
										<TABLE id=grid
											style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc"
											cellSpacing=1 cellPadding=2 rules=all border=0>
											<TBODY>
												<TR
													style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
													<TD>客户名称</TD>
													<TD>客户级别</TD>
													<TD>客户来源</TD>
													<TD>联系人</TD>
													<TD>电话</TD>
													<TD>手机</TD>
													<TD>操作</TD>
												</TR>
												<s:iterator var="customer" value="#request.page.beanList">
													<TR
														style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
														<TD><s:property value="#customer.cust_name"/></TD>
														
														<!-- 想要显示出来源和级别需要通过外键，而且需要在web.xml中设置延迟加载，否则会报no session的错 -->
														<TD>${customer.level.dict_item_name }</TD>
														<TD>${customer.source.dict_item_name }</TD>
														
														<TD>${customer.cust_linkman }</TD>
														<TD>${customer.cust_phone }</TD>
														<TD>${customer.cust_mobile }</TD>
														<TD>
														<a href="${pageContext.request.contextPath }/customer_initUpdate.action?cust_id=${customer.cust_id}">修改</a>
														&nbsp;&nbsp;
														<!-- cust_id为模型驱动中的属性，不必在action中再添加该属性，customer中有 -->
														<a href="${pageContext.request.contextPath }/customer_delete.action?cust_id=${customer.cust_id}" onclick="return window.confirm('确定要删除吗?')">删除</a>
														</TD>
													</TR>
												</s:iterator>

											</TBODY>
										</TABLE>
									</TD>
								</TR>
								
								<TR>
									<TD><SPAN id=pagelink>
											<DIV
												style="LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right">
												<%-- 共[<B>${page.totalCount}</B>]条记录,共[<B>${page.totalPage}</B>]页 --%>
												
												当前第[${page.pageCode }]页
												<s:if test="#request.page.pageCode > 1">
													[<A href="javascript:to_page(${page.pageCode-1})">前一页</A>]
												</s:if>
												
												<s:iterator var="num" begin="#request.page.startPage" end="#request.page.totalPage" >
													
													<B>
														<a href="javascript:to_page(${num})">${num}</a>
													</B>
												
												</s:iterator>
												
												
												<s:if test="#request.page.pageCode < #request.page.totalPage">
												[<A href="javascript:to_page(${page.pageCode+1})">后一页</A>] 
												</s:if>
												到
												<input type="text" size="3" id="page" name="pageCode" />
												页
												
												<input type="button" value="Go" onclick="to_page()"/>
											</DIV>
									</SPAN></TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg"><IMG
						src="${pageContext.request.contextPath }/images/new_023.jpg" border=0></TD>
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
