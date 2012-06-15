<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ZyXEL KB</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/smoothness/jquery-ui-1.8.18.custom.css" />" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/superfish-navbar.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/menu.css" />" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/newMenu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.css" />" />
<!-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/displaytag.css" />" />-->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.datepick.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.multiselect.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.multiselect.filter.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/ufd-base.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/plain/plain.css" />" />
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-1.7.2.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery-ui-1.8.18.custom.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.validate.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.datepick.min.js" />"></script>
<!-- <script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.dataTables.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/hoverIntent.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/superfish.js" />"></script>-->
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/select-chain.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/ckeditor/ckeditor.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/ckeditor/adapters/jquery.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/ckeditor/config.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.multiselect.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.multiselect.filter.min.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.ui.ufd.js" />"></script>
<script language="JavaScript" type="text/javascript" src="<c:url value="/js/jquery.numeric.js" />"></script>
<style type="text/css">
    #ui-datepicker-div {
        z-index: 9999999;
    }
    
    button.ui-datepicker-current { 
    	display: none; 
    }
</style>
<script>
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	$('.cancel').live('click', function() {return confirm('Cancel?');});
	$('.delete').live('click', function() {return confirm('Delete?');});
	$('.save').live('click', function() {return confirm('Save?');});
	//控制 DataGrid
	$('.odd').live({
		mouseenter:
            function()
            {
			$(this).addClass("oddselect");
            },
         mouseleave:
            function()
            {
        	 $(this).removeClass("oddselect");
            }
        });
	$('.even').live({
		mouseenter:
            function()
            {
			$(this).addClass("evenselect");
            },
         mouseleave:
            function()
            {
        	 $(this).removeClass("evenselect");
            }
        });
	$().ready(function() {
		/*
		$("#menu ul.sf-menu").superfish({
			pathClass : 'current'
		});
		*/
		$('#ajaxArticleForm').ajaxForm({
            target: '#detail',
            success : $.unblockUI
        });
		
		$('#articleForm').ajaxForm({
            target: '#main',
            success : $.unblockUI
        });
		
		$('#editArticleForm').ajaxForm({
            target: '#main',
            success : $.unblockUI
        });
		/*
		$('#previewArticleForm').ajaxForm({
            target: '#main',
            success : $.unblockUI
        });
		*/
		
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
	}); // end of ready
	
	function switchMenu(menuId, action) {
		
		$('#menu li.current').removeClass('current');
		/*('#menu li').removeClass('sf-breadcrumb');*/
		$('#'+menuId).parents("li").addClass('current');
		/*
		$("#menu ul.sf-menu").superfish({
			pathClass : 'current'
		});
		*/
		if (action) {
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
	}
	
	function switchLanguage(lang) {
		$.post('ajax!ajaxSwitchLanguage.action', {languageOid:lang.value});
	}
	
	function switchRole(userRole) {
		$('#switchRoleName').val($('#userRole').val());
		$('#switchRoleForm').submit();
		/*
		$.ajax({
			url: '${ctx}/system!switchRole.action?userRole='+$('#userRole').val(),
			type: 'POST',
			success: function(data) {
				//alert('Switch Role Success!')
			},
			error : function() {
				alert('Loading failed');
			}
		});
		*/
	}
	
	function viewAjaxArticle(oid) {
		$('#ajaxDetailOid').val(oid);
		$('#ajaxDetailArticleId').val('');
		$('#search').hide();
		$('#articles').hide();
		$('#ajaxArticleForm').submit();
	}
	function closeAjaxArticle() {
		if ($('#search').length > 0) {
			$('#search').show();
			$('#articles').show();
			$('#detail').html('');
		} else {
			switchMenu('m1', 'searchArticle!index.action');
		}
	}
	
	function viewArticle(oid) {
		$('#detailOid').val(oid);
		$('#detailArticleId').val('');
		$('#articleForm').submit();
	}
	
	function quickViewArticle() {
		viewArticle($('#quickOid').val());
	}
	
	function quickViewArticleByArticleId() {
		$('#detailOid').val('');
		$('#detailArticleId').val($('#quickOid').val());
		$('#articleForm').submit();
	}
	
	function editArticle(oid, comefrom) {
		$('#editOid').val(oid);
		if (comefrom) {
			$('#comefrom').val(comefrom);
		} else {
			$('#comefrom').val('');
		}
		$('#editArticleForm').submit();
	}
	
	function previewArticle(oid) {
		$('#previewOid').val(oid);
		$('#previewArticleForm').submit();
	}
	
	function viewExportPackage(oid) {
		$('#viewPackageOid').val(oid);
		$('#viewPackageForm').submit();
	}
	
	function viewArticleLog(oid) {
		$('#viewLogOid').val(oid);
		$('#viewLogForm').attr('action','${ctx}/edit!viewLogs.action');
		$('#viewLogForm').submit();
	}
	
	function viewRejectLogs(oid) {
		$('#viewLogOid').val(oid);
		$('#viewLogForm').attr('action','${ctx}/edit!viewRejectLogs.action');
		$('#viewLogForm').submit();
	}
	
	function displaytagform(formname, fields) {
		$('#'+formname+' .distagArea').children().remove();
		for (j=fields.length-1;j>=0;j--) {
			$('#'+formname+' .distagArea').append('<input type=\"hidden\" name=\"'+fields[j].f+'\" value=\"'+fields[j].v+'\"/>');
		}
		$('#' + formname).submit();
	}
	
	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val());
	    });
	  //Fix radio checked bug
	    $(':input[name]:checked', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val());
	    });
	}
</script>
</head>
<body>
	<form id="menuForm" >
	</form>
	<s:form id="articleForm" namespace="/" action="searchArticle!detail" theme="simple" target="_blank">
		<input id="detailOid" type="hidden" name="oid" />
		<input id="detailArticleId" type="hidden" name="articleId" />
	</s:form>
	<s:form id="editArticleForm" namespace="/" action="edit!load" theme="simple">
		<input id="comefrom" type="hidden" name="comefrom" />
		<input id="editOid" type="hidden" name="oid" />
	</s:form>
	<s:form id="previewArticleForm" namespace="/" target="_blank" action="edit!preview" theme="simple">
		<input id="previewOid" type="hidden" name="oid" />
	</s:form>
	<s:form id="viewPackageForm" namespace="/" target="_blank" action="news!viewPackage" theme="simple">
		<input id="viewPackageOid" type="hidden" name="epOid" />
	</s:form>
	<s:form id="viewLogForm" namespace="/" target="_blank" action="edit" theme="simple">
		<input id="viewLogOid" type="hidden" name="oid" />
	</s:form>
	<s:form id="switchRoleForm" namespace="/" action="system!switchRole" theme="simple">
		<input id="switchRoleName" type="hidden" name="userRole" />
	</s:form>
	<s:form id="ajaxArticleForm" namespace="/" action="searchArticle!detail" theme="simple">
		<input id="ajaxDetailOid" type="hidden" name="oid" />
		<input id="ajaxDetailArticleId" type="hidden" name="articleId" />
	</s:form>
<div class="page">
	<div id="header" style="width: 98%; position: relative;" class="datagrid">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="menu" style="width: 98%; position: relative;" class="datagrid">
		<tiles:insertAttribute name="menu" />
	</div>
	
	<div id="main" class="contents viewmycart" >
		<tiles:insertAttribute name="main" />
	</div>
	<div id="footer" class="footer fix">
		<tiles:insertAttribute name="footer" />
	</div>
</div>
</body>
</html>
