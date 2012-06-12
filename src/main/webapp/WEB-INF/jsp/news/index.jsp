<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		switchMenu('m7');
		$('#exportNewsForm').validate({
				errorClass: "errorField",
		    	rules : {
		    		types : { required:true},
		    		beginDate : { required:true},
		    		endDate : { required:true}
		    		}
		    	});
		$('#exportPackageForm').ajaxForm({
            target: '#exportPackageDiv'
        });
		
		var datepicker_CurrentInput;
		options = {
				dateFormat : 'yy/mm/dd',
				showButtonPanel: true,
				closeText: 'Clear',
				beforeShow: function (input, inst) { datepicker_CurrentInput = input; }
		};
		$(".ui-datepicker-close").live("click", function () {
			datepicker_CurrentInput.value = "";
		});
		$( ".calendar" ).datepicker(options);
		$(".numeric").numeric({ decimal: false, negative: false });
	});
	
</script>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>
<div  class="condition">
<s:form id="exportNewsForm" namespace="/" action="news!export" theme="simple" target="_blank">
<table class="conditionborder">
	<tr><th colspan="2">Export Condition</th></tr>
	<tr>
		<td>ZyTech News</td><td><s:radio name="news" list="#{'true':'Yes','false':'No'}" value="true"/></td><td align="right"><s:submit value="Export for Proofread" /></td>
	</tr>
	<tr>
		<td>FROM</td><td><input type="text" name="beginDate" size="10" id="beginDate1" readonly="readonly" class="calendar"/> TO <input type="text" name="endDate" size="10" id="endDate1" readonly="readonly" class="calendar"/></td>
	</tr>
	<tr>
		<td>TYPE</td><td><s:checkboxlist name="types" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" value="@tw.com.dsc.domain.ArticleType@values()"/></td>
	</tr>
</table>
</s:form>

<s:form id="exportPackageForm" namespace="/" action="news!searchExportPackage" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
</div>
<div id="exportPackageDiv" class="datagrid">
	<jsp:include page="packageList.jsp" />
</div>