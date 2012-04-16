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
	
</script>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>

<s:form id="searchUnpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<input type="text" name="example.id" size="8"/>
	<input type="text" name="example.summary" size="16"/>
	<input type="text" name="example.entryUser" size="7"/>
	<input type="text" name="example.entryDate" size="14"/>
	<input type="text" name="example.product" size="15"/>
	<input type="text" name="example.state" size="5"/>
	<s:submit value="Search" />
</s:form>

<s:form id="unpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="unpublishedDiv">
	<jsp:include page="unpublished.jsp" />
</div>

<s:form id="searchDraftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<input type="text" name="example.id" size="9"/>
	<input type="text" name="example.summary" size="18"/>
	<input type="text" name="example.entryUser" size="8"/>
	<input type="text" name="example.entryDate" size="16"/>
	<input type="text" name="example.product" size="17"/>
	<s:submit value="Search" />
</s:form>

<s:form id="draftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="draftDiv">
	<jsp:include page="draft.jsp" />
</div>

<s:form id="searchExpiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<input type="text" name="example.id" size="9"/>
	<input type="text" name="example.summary" size="18"/>
	<input type="text" name="example.entryUser" size="8"/>
	<input type="text" name="example.entryDate" size="16"/>
	<input type="text" name="example.product" size="17"/>
	<s:submit value="Search" />
</s:form>

<s:form id="expiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="expiredDiv">
	<jsp:include page="expired.jsp" />
</div>