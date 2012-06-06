<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<s:if test="hasActionErrors()">
   <div>
      <s:actionerror/>
   </div>
</s:if>
<s:else>
<script>
	$().ready(function() {
		switchMenu('m1', 'searchArticle!index.action');
	});
</script>
</s:else>