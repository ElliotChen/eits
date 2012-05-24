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
		$( "#entryDate1" ).datepicker(options);
		$( "#entryDate2" ).datepicker(options);
		$( "#entryDate3" ).datepicker(options);
	});
	
</script>
<s:if test="hasActionMessages()">
   <div>
      <s:actionmessage/>
   </div>
</s:if>
<div  class="condition">
<s:form id="searchUnpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
<table>
	<tr><th colspan="7">Unpublished</th></tr>
	<tr>
	<td width="50"><input type="text" name="example1.articleId.oid" size="6"/></td>
	<td width="300"><input type="text" name="example1.summary" size="30"/></td>
	<td width="50"><input type="text" name="example1.entryUser" size="6"/></td>
	<td width="90"><input type="text" name="example1.entryDate" size="10" id="entryDate1" readonly="readonly"/></td>
	<td width="200"><input type="text" name="example1.product" size="26"/></td>
	<td width="120"><s:select name="example1.status" list="{'WaitForApproving', 'WaitForProofRead', 'ReadyToUpdate', 'ReadyToPublish'}" listValue="%{getText('enum.Status.'+toString())}" headerKey="" headerValue="----"></s:select></td>
	<td><s:submit value="Search" /></td>
	</tr>
</table>
</s:form>

<s:form id="unpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
</div>
<div id="unpublishedDiv" class="datagrid">
	<jsp:include page="unpublished.jsp" />
</div>

<div class="datagrid">
Draft
<s:form id="searchDraftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<input type="text" name="example2.articleId.oid" size="9"/>
	<input type="text" name="example2.summary" size="18"/>
	<input type="text" name="example2.entryUser" size="8"/>
	<input type="text" name="example2.entryDate" size="16" id="entryDate2" readonly="readonly"/>
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

<div class="datagrid">
Expired
<s:form id="searchExpiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<input type="text" name="example3.articleId.oid" size="9"/>
	<input type="text" name="example3.summary" size="18"/>
	<input type="text" name="example3.entryUser" size="8"/>
	<input type="text" name="example3.entryDate" size="16" id="entryDate3" readonly="readonly"/>
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