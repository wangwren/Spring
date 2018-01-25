<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 通用的分页界面 -->
<SPAN id=pagelink>
	<DIV style="LINE-HEIGHT: 20px; HEIGHT: 20px; TEXT-ALIGN: right">
		<%-- 共[<B>${page.totalCount}</B>]条记录,共[<B>${page.totalPage}</B>]页 --%>

		当前第[${page.pageCode }]页
		<s:if test="#request.page.pageCode > 1">
			[<A href="javascript:to_page(${page.pageCode-1})">前一页</A>]
		</s:if>

		<s:iterator var="num" begin="#request.page.startPage"
			end="#request.page.totalPage">

			<B> 
				<a href="javascript:to_page(${num})">${num}</a>
			</B>

		</s:iterator>


		<s:if test="#request.page.pageCode < #request.page.totalPage">
			[<A href="javascript:to_page(${page.pageCode+1})">后一页</A>] 
		</s:if>
		
		到 <input type="text" size="3" id="page" name="pageCode" /> 页 <input
			type="button" value="Go" onclick="to_page()" />
	</DIV>
</SPAN>