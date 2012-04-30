<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script type="text/javascript">
<!--
	$().ready(function() {
		$('#transform').ajaxForm({
			target : '#main'
		});
	});
	function rating() {
		$.post('/eits/edit!rating.action', {
			oid : '${article.oid}',
			ratingNumber : $('input[name=ratingNumber]:checked').val()
		}, function(data) {
			$('#ratingMsg').html(data.message);
			$('#ratingButton').remove();
		}, 'json');
	}
	
	function suggest() {
		if ($('#suggestion').val().trim() == "") {
			alert('Suggestion could not be empty!');
			return false;
		}
		$.post('/eits/edit!suggest.action', {
			oid : '${article.oid}',
			suggestion : $('#suggestion').val()
		}, function(data) {
			$('#suggestMsg').html(data.message);
			$('#suggestion').remove();
			$('#sugButton').remove();
		}, 'json');
	}
//-->
</script>
<s:if test="!@tw.com.dsc.util.ThreadLocalHolder@getUser().guest">
<s:form id="transform" action="edit!preCopy" theme="simple">
	<input type="hidden" name="sourceOid" value="${article.oid}" />
<table>
	<tr><td><input type="submit" value="Translate"/><input type="button" value="View Log" onclick="viewArticleLog('${oid}')"/></td></tr>
</table>
</s:form>
</s:if>

<s:form id="detailform" action="article!preCopy" theme="simple">
<table style="border: none">
	<tr>
		<th>Article ID</th>
		<td>${article.articleId.oid}</td>
		<th>Type</th>
		<td>${article.type}</td>
		<th>Language</th>
		<td><s:select list="sameArticles" listKey="oid" listValue="language.name" onchange="viewArticle(this.value)" value="oid"></s:select></td>
	</tr>
	<tr>
		<th>Level</th>
		<td>${article.level}</td>
		<th>Firmware</th>
		<td><c:if test="${null != article.firmware}"><a href="${article.firmware.uri}" target="_blank">${article.firmware.name}</a></c:if></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<th>View</th>
		<td>${article.hitCount}</td>
		<th>Rating</th>
		<td>${article.ratingInfo}</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
</table>
<fieldset>
	<legend>Question:</legend>
	${article.question}
</fieldset>

<fieldset>
	<legend>Answer:</legend>
	${article.answer}
</fieldset>

<div id="ratingDiv">
	<fieldset>
		<legend>Rating:</legend>
		Did you find this article helpful? <br /> Poor <input
			name="ratingNumber" type="radio" value="1" />1 <input
			name="ratingNumber" type="radio" value="2" />2 <input
			name="ratingNumber" type="radio" value="3" />3 <input
			name="ratingNumber" type="radio" value="4" />4 <input
			name="ratingNumber" type="radio" value="5" checked="checked" />5
		Excellent <input type="button" id="ratingButton" value="Rating" onclick="rating();" />
		<div id="ratingMsg" style="color: blue;"></div>
	</fieldset>
</div>

<div id="suggestionDiv">
	<fieldset>
		<legend>Suggest new content or let us know we can imporve
			this content:</legend>
		<textarea id="suggestion" name="suggestion" rows="10" cols="80"></textarea>
		<input type="button" id="sugButton" value="Send" onclick="suggest();"/>
		<div id="suggestMsg" style="color: blue;"></div>
	</fieldset>
	
</div>
</s:form>