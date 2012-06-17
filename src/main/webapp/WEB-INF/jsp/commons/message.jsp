<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionErrors()">
	<script>
	$(function() {
		$( "#errormsg" ).dialog();
	});
	</script>
   <div id="errormsg">
      <s:actionerror/>
   </div>
</s:if>
<s:elseif test="hasActionMessages()">
	<script>
	$(function() {
		$( "#actionmsg" ).dialog();
	});
	</script>
   <div id="actionmsg">
      <s:actionmessage/>
   </div>
</s:elseif>