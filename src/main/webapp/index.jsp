<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EITS</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish-navbar.css" />" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.css" />" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.datepick.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/displaytag.css" />" />
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.validate.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.datepick.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/hoverIntent.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/superfish.js" />"></script>

<script>
	$().ready(function() {
		$("#menu ul.sf-menu").superfish({
			pathClass : 'current'
		});
	});
	
	function switchMenu(menuId, action) {
		$('#menu li.current').removeClass('current');
		$('#menu li').removeClass('sf-breadcrumb');
		$('#'+menuId).parents("li").addClass('current');
		$("#menu ul.sf-menu").superfish({
			pathClass : 'current'
		});
		
		$.ajax({
			url: action,
			success: function(data) {
				$('#main').html(data);
			}
		});
		
		
	}
</script>
</head>
<body>
	<form id="menuForm" >
	</form>
	<div id="header" style="height:90px; width: 90%; position: relative;"></div>
	<div id="menu" style="height:100px; width: 90%; position: relative;">
		<ul id="sample-menu-4" class="sf-menu sf-navbar">
			<li><a class="sf-with-ul" href="#">Article<span class="sf-sub-indicator"> &#187;</span></a>
				<ul>
					<li><a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');">Search</a></li>
				</ul>
			</li>
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
	</div>
	
	<div id="main" style="height:300px; width: 90%; position: relative;">
	</div>
	<div id="footer"></div>
</body>
</html>
