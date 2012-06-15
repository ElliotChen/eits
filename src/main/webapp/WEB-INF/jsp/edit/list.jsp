<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		switchMenu('m3');
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
<s:form id="searchUnpublishedForm" namespace="/" action="edit!searchUnpublished" theme="simple">
<table class="conditionborder">
	<tr><th colspan="7">Unpublished Articles</th></tr>
	<tr>
		<td>ID:</td><td><input type="text" name="example1.articleId.oid" size="6" maxlength="6" class="numeric"/></td>
		<td>Summary:</td><td><input type="text" name="example1.summary" size="30"/></td>
		<td>Agent:</td><td><input type="text" name="example1.entryUser" size="15"/></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>Entry Date:</td><td><input type="text" name="beginDate" size="10" id="beginDate1" readonly="readonly" class="calendar"/>~<input type="text" name="endDate" size="10" id="endDate1" readonly="readonly" class="calendar"/></td>
		<td>Prod. Series:</td><td><input type="text" name="example1.product" size="30"/></td>
		<td>Status:</td>
			<td>
			<s:select name="example1.status" list="unpublishedStatus" listValue="%{getText('enum.Status.'+toString())}" headerKey="" headerValue="----"></s:select>
			</td>
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

<div class="condition">
<s:form id="searchDraftForm" namespace="/" action="edit!searchDraft" theme="simple">
<table class="conditionborder">
	<tr><th colspan="6">Latest Draft Articles</th></tr>
	<tr>
		<td>ID:</td><td><input type="text" name="example2.articleId.oid" size="6" maxlength="6" class="numeric"/></td>
		<td>Summary:</td><td><input type="text" name="example2.summary" size="30"/></td>
		<td>Agent:</td><td><input type="text" name="example2.entryUser" size="15"/></td></tr>
	<tr>
	<tr>
		<td>Entry Date:</td><td><input type="text" name="beginDate" size="10" id="beginDate2" readonly="readonly" class="calendar"/>~<input type="text" name="endDate" size="10" id="endDate2" readonly="readonly" class="calendar"/></td>
		<td>Prod. Series:</td><td><input type="text" name="example2.product" size="30"/></td>
		<td colspan="2" align="right"><s:submit value="Search" /></td>
	<tr>
</table>
</s:form>

<s:form id="draftForm" namespace="/" action="edit!searchDraft" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
</div>

<div id="draftDiv" class="datagrid">
	<jsp:include page="draft.jsp" />
</div>


<div class="condition">
<s:form id="searchExpiredForm" namespace="/" action="edit!searchExpired" theme="simple">
<table class="conditionborder">
	<tr><th colspan="6">Expired Article List</th></tr>
	<tr>
		<td>ID:</td><td><input type="text" name="example3.articleId.oid" size="6" maxlength="6" class="numeric"/></td>
		<td>Summary:</td><td><input type="text" name="example3.summary" size="30"/></td>
		<td>Agent:</td><td><input type="text" name="example3.entryUser" size="15"/></td></tr>
	<tr>
	<tr>
		<td>Entry Date:</td><td><input type="text" name="beginDate" size="10" id="beginDate3" readonly="readonly" class="calendar"/>~<input type="text" name="endDate" size="10" id="endDate3" readonly="readonly" class="calendar"/></td>
		<td>Prod. Series:</td><td><input type="text" name="example3.product" size="30"/></td>
		<td colspan="2" align="right"><s:submit value="Search" /></td>
	<tr>
</table>
</s:form>

<s:form id="expiredForm" namespace="/" action="edit!searchExpired" theme="simple">
	<div id="distagArea" class="distagArea"></div>
</s:form>
</div>
<div id="expiredDiv" class="datagrid">
	<jsp:include page="expired.jsp" />
</div>
