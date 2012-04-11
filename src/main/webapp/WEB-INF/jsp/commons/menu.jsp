<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<ul id="sample-menu-4" class="sf-menu sf-navbar">
	<li><a class="sf-with-ul" href="#">Article<span class="sf-sub-indicator"> &#187;</span></a>
		<ul>
			<li><a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');">Search</a></li>
		</ul></li>
	<li class="current"><a class="sf-with-ul" href="#">menu item<span class="sf-sub-indicator"> &#187;</span></a>
		<ul>
			<li class="current"><a class="sf-with-ul" href="#">path to current<span class="sf-sub-indicator">
						&#187;</span></a>
				<ul>
					<li><a href="#">menu item</a></li>
					<li><a href="#aba">menu item</a></li>
					<li><a href="#abb">menu item</a></li>
					<li class="current"><a href="#abc">current page</a></li>
					<li><a href="#abd">menu item</a></li>
				</ul></li>
		</ul></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" href="#">&nbsp;</a></li>
	<li><a class="sf-with-ul" id="login" href="javascript:switchMenu('login');">Login</a></li>
</ul>