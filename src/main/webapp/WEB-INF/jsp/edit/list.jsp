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
<div>
Unpublished
<s:form id="searchUnpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<input type="text" name="example1.articleId.oid" size="6"/>
	<input type="text" name="example1.summary" size="40"/>
	<input type="text" name="example1.entryUser" size="6"/>
	<input type="text" name="example1.entryDate" size="10"/>
	<input type="text" name="example1.product" size="26"/>
	<input type="text" name="example1.status" size="15"/>
	<s:submit value="Search" />
</s:form>

<s:form id="unpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="unpublishedDiv">
	<jsp:include page="unpublished.jsp" />
</div>
</div>
<div>
Draft
<s:form id="searchDraftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<input type="text" name="example2.articleId.oid" size="9"/>
	<input type="text" name="example2.summary" size="18"/>
	<input type="text" name="example2.entryUser" size="8"/>
	<input type="text" name="example2.entryDate" size="16"/>
	<input type="text" name="example2.product" size="17"/>
	<s:submit value="Search" />
</s:form>

<s:form id="draftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="draftDiv">
	<jsp:include page="draft.jsp" />
</div>
</div>

<div>Expired
<s:form id="searchExpiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<input type="text" name="example3.articleId.oid" size="9"/>
	<input type="text" name="example3.summary" size="18"/>
	<input type="text" name="example3.entryUser" size="8"/>
	<input type="text" name="example3.entryDate" size="16"/>
	<input type="text" name="example3.product" size="17"/>
	<s:submit value="Search" />
</s:form>

<s:form id="expiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
<div id="expiredDiv">
	<jsp:include page="expired.jsp" />
</div>
</div>