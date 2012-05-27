<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<ul id="nav" style="width: 100%">
	<li>
		<a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');">Search</a>
	</li>
	<s:if test="!user.guest">
	<li><a id="m2" href="javascript:switchMenu('m2', 'edit!preCreate.action');">Create New Article</a></li>
				<li><a id="m3" href="javascript:switchMenu('m3', 'edit!list.action');">Unpublished Articles</a></li>
				<li><a id="m4" href="javascript:switchMenu('m4', 'language!index.action');">Language</a></li>
	</s:if>
	
	
</ul>