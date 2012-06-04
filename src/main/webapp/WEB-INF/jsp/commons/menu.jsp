<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<ul id="nav" style="width: 100%">
	<li>
		<a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');" style="text-transform: uppercase">Search</a>
	</li>
	<s:if test="user.l2 || user.l3">
	<li><a id="m2" href="javascript:switchMenu('m2', 'edit!preCreate.action');" style="text-transform: uppercase">Create New Article</a></li>
	<li><a id="m3" href="javascript:switchMenu('m3', 'edit!list.action');" style="text-transform: uppercase">Unpublished Articles</a></li>
	<li><a id="m5" href="javascript:switchMenu('m5', 'searchArticle!publishedIndex.action');" style="text-transform: uppercase">L3 Latest Published Articles</a></li>
	</s:if>
	<s:if test="user.l3Admin">
		<li><a id="m4" href="javascript:switchMenu('m4', 'language!index.action');" style="text-transform: uppercase">Language</a></li>
	</s:if>
	
	
</ul>