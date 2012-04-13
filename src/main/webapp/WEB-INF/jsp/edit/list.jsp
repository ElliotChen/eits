<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchUnpublishedForm').ajaxForm({
            target: '#unpublishedDiv'
        });
		$('#unpublishedForm').ajaxForm({
            target: '#unpublishedDiv'
        });
		
		$('#searchDraftForm').ajaxForm({
            target: '#draftDiv'
        });
		$('#draftForm').ajaxForm({
            target: '#draftDiv'
        });
		
		$('#searchExpiredForm').ajaxForm({
            target: '#expiredDiv'
        });
		$('#expiredForm').ajaxForm({
            target: '#expiredDiv'
        });
	});
	
	function displaytagform(formname, fields) {
		$('#'+formname+' .distagArea').children().remove();
		for (j=fields.length-1;j>=0;j--) {
			$('#'+formname+' .distagArea').append('<input type=\"hidden\" name=\"'+fields[j].f+'\" value=\"'+fields[j].v+'\"/>');
		}
		$('#' + formname).submit();
	}
</script>

<s:form id="searchUnpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<input type="text" name="example.id" />
	<input type="text" name="example.summary" />
	<input type="text" name="example.entryUser" />
	<input type="text" name="example.entryDate" />
	<s:submit value="Search" />
</s:form>

<s:form id="unpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="unpublishedDiv">
	<jsp:include page="unpublished.jsp" />
</div>

<s:form id="searchDraftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<input type="text" name="example.id" />
	<input type="text" name="example.summary" />
	<s:submit value="Search" />
</s:form>

<s:form id="draftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="draftDiv">
	<jsp:include page="draft.jsp" />
</div>

<s:form id="searchExpiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<input type="text" name="example.id" />
	<input type="text" name="example.summary" />
	<s:submit value="Search" />
</s:form>

<s:form id="expiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="expiredDiv">
	<jsp:include page="expired.jsp" />
</div>