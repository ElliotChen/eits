<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<ul id="sample-menu-4" class="sf-menu sf-navbar">
	<li><a class="sf-with-ul" href="#">Article<span class="sf-sub-indicator"> &#187;</span></a>
		<ul>
			<li><a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');">Search</a></li>
			<s:if test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
				<li><a id="m2" href="javascript:switchMenu('m2', 'edit!empty.action');">Create New Article</a></li>
				<li><a id="m3" href="javascript:switchMenu('m3', 'edit!list.action');">Unpublished Articles</a></li>
			</s:if>
		</ul></li>
	<s:if test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
		<li><a class="sf-with-ul" href="#">System<span class="sf-sub-indicator"> &#187;</span></a>
			<ul>
				<li><a id="m4" href="javascript:switchMenu('m4', 'language!index.action');">Language</a></li>
			</ul></li>
	</s:if>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
</ul>