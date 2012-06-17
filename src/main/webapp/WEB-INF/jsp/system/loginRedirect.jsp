<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<jsp:include page="/WEB-INF/jsp/commons/message.jsp"></jsp:include>
<script>
	$().ready(function() {
		switchMenu('m1', 'searchArticle!index.action');
	});
</script>
