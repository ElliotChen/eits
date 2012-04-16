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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css" />" />
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.validate.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.datepick.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/hoverIntent.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/superfish.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/select-chain.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/ckeditor/ckeditor.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/ckeditor/adapters/jquery.js" />"></script>
<script>
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	$('.cancel').live('click', function() {return confirm('Cancel?');});
	$('.save').live('click', function() {return confirm('Save?');});
	$().ready(function() {
		$("#menu ul.sf-menu").superfish({
			pathClass : 'current'
		});
		
		$('#articleForm').ajaxForm({
            target: '#main',
            success : $.unblockUI
        });
		
		$('#editArticleForm').ajaxForm({
            target: '#main',
            success : $.unblockUI
        });
		
		$('a.login-window').click(function() {
			
            //Getting the variable's value from a link 
	var loginBox = $(this).attr('href');

	//Fade in the Popup
	$(loginBox).fadeIn(300);
	
	//Set the center alignment padding + border see css style
	var popMargTop = ($(loginBox).height() + 24) / 2; 
	var popMargLeft = ($(loginBox).width() + 24) / 2; 
	
	$(loginBox).css({ 
		'margin-top' : -popMargTop,
		'margin-left' : -popMargLeft
	});
	
	// Add the mask to body
	$('body').append('<div id="mask"></div>');
	$('#mask').fadeIn(300);
	
	return false;
});

// When clicking on the button close or the mask layer the popup closed
$('a.close, #mask').live('click', function() { 
  $('#mask , .login-popup').fadeOut(300 , function() {
	$('#mask').remove();  
}); 
return false;
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
			type: 'POST',
			success: function(data) {
				$('#main').html(data);
			},
			error : function() {
				alert('Loading failed');
			}
		});
	}
	
	function viewArticle(oid) {
		$('#oid').val(oid);
		$('#articleForm').submit();
	}
	
	function quickViewArticle() {
		$('#oid').val($('#quickOid').val());
		$('#articleForm').submit();
	}
	
	function editArticle(oid) {
		$('#edit.oid').val(oid);
		$('#editArticleForm').submit();
	}
	
	function displaytagform(formname, fields) {
		$('#'+formname+' .distagArea').children().remove();
		for (j=fields.length-1;j>=0;j--) {
			$('#'+formname+' .distagArea').append('<input type=\"hidden\" name=\"'+fields[j].f+'\" value=\"'+fields[j].v+'\"/>');
		}
		$('#' + formname).submit();
	}
</script>
</head>
<body>
	<form id="menuForm" >
	</form>
	<s:form id="articleForm" namespace="/" action="searchArticle!detail" theme="simple">
		<input id="oid" type="hidden" name="oid" />
	</s:form>
	<s:form id="editArticleForm" namespace="/" action="edit!load" theme="simple">
		<input id="edit.oid" type="hidden" name="oid" />
	</s:form>
	<div id="header" style="height: 90px; width: 90%; position: relative;">
		<div id="loginDiv" style="margin-left: 500px;">
			<s:if test="@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
			Hi! <s:property value="@tw.com.dsc.util.ThreadLocalHolder@getUser().name" /> <a href="#login-box" class="login-window">Login</a>
				<div id="login-box" class="login-popup">
					<a href="#" class="close"><img src="images/close_pop.png" class="btn_close" title="Close Window" alt="Close" /></a>
					<form method="post" class="signin" action="${ctx}/system!login.action">
						<fieldset class="textbox">
							<label class="username"> <span>Username or email</span> <input id="username" name="user.account" value=""
								type="text" autocomplete="on" placeholder="Username">
							</label> <label class="password"> <span>Password</span> <input id="password" name="user.password" value=""
								type="password" placeholder="Password">
							</label>
							<input class="submit button" type="submit" value="Login" />
						</fieldset>
					</form>
				</div>
			</s:if>
			<s:elseif test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
			<a href="#" onclick="switchMenu('m3', 'edit!list.action');">Home</a>
			<a href="${ctx}/system!logout.action">Logout</a><br/>
			Welcome! <s:property value="@tw.com.dsc.util.ThreadLocalHolder@getUser().account" /><br/>
			Article#:<input type="text" id="quickOid" name="quickOid" size="6" maxlength="6"/><input type="button" onclick="quickViewArticle();" value="Go"/><br/>
			Role:<select name="role"> <option value="L3Admin">L3Admin</option><option value="L2Admin">L2Admin</option></select>
			</s:elseif>
		</div>
	</div>
	<div id="menu" style="height:100px; width: 90%; position: relative;">
		<ul id="sample-menu-4" class="sf-menu sf-navbar">
			<li><a class="sf-with-ul" href="#">Article<span class="sf-sub-indicator"> &#187;</span></a>
				<ul>
					<li><a id="m1" href="javascript:switchMenu('m1', 'searchArticle!index.action');">Search</a></li>
					<s:if test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
					<li><a id="m2" href="javascript:switchMenu('m2', 'edit!empty.action');">Create New Article</a></li>
					<li><a id="m3" href="javascript:switchMenu('m3', 'edit!list.action');">Unpublished Articles</a></li>
					</s:if>
				</ul>
			</li>
			<s:if test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
			<li><a class="sf-with-ul" href="#">System<span class="sf-sub-indicator"> &#187;</span></a>
				<ul>
					<li><a id="m4" href="javascript:switchMenu('m4', 'language!index.action');">Language</a></li>
				</ul>
			</li>
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
	</div>
	
	<div id="main" style="width: 90%; position: relative;">
	</div>
	<div id="footer"></div>
</body>
</html>
