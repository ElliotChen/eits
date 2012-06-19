<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<ul id="nav" style="width: 100%">
	<li>
		<a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');" style="text-transform: uppercase">Search</a>
	</li>
	<s:if test="user.l2 || user.l3">
	<li><a id="m2" href="javascript:switchMenu('m2', 'edit!preCreate.action');" style="text-transform: uppercase">Create Article</a></li>
	<li><a id="m3" href="javascript:switchMenu('m3', 'edit!list.action');" style="text-transform: uppercase">Unpublished Articles</a></li>
	<li><a id="m5" href="javascript:switchMenu('m5', 'searchArticle!publishedIndex.action');" style="text-transform: uppercase">L3 Latest Articles</a></li>
	<li><a id="m8" href="javascript:switchMenu('m8', 'advSearchArticle!index.action');" style="text-transform: uppercase">Agent Search</a></li>
	</s:if>
	<s:if test="user.l3Admin">
	<li>
		<a id="m6" href="#" style="text-transform: uppercase">System</a>
		<ul>
			<li><a id="m4" href="javascript:switchMenu('m4', 'language!index.action');" style="text-transform: uppercase">Language</a></li>
			<li><li><a id="m7" href="javascript:switchMenu('m7', 'news!index.action');" style="text-transform: uppercase">ZyTech News</a></li></li>
		</ul>
	</li>
	
	<%-- 
	<li>
		<a id="m6" href="#" style="text-transform: uppercase">Export</a>
		<ul>
			<li><a href="${ctx}/export!exportNews.action" target="_blanke">Export News</a></li>
			<li><a href="${ctx}/export!exportKB.action" target="_blanke">Export KB</a></li>
		</ul>
	</li>
	--%>
	
	</s:if>
</ul>