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
		$.post('/eits/article!rating.action', {
			oid : '${article.oid}',
			ratingNumber : $('input[name=ratingNumber]:checked').val()
		}, function(data) {
			$('#ratingMsg').append(data.message);
		}, 'json');
	}
//-->
</script>
<s:form id="transform" action="article!precopy" theme="simple">
	<s:hidden name="oid" value="article.oid" />
<table>
	<tr><td><input type="submit" value="Translate"/></td></tr>
</table>
</s:form>
<table>
	<tr>
		<th>Article ID</th>
		<td>${article.id}</td>
		<th>Type</th>
		<td>General Type</td>
		<th>Language</th>
		<td><s:select name="oid" list="#{'1':'English','5':'Chinese'}"
				value="oid" onchange="viewArticle(this.value);"></s:select>
	</tr>
	<tr>
		<th>Level</th>
		<td>CSO</td>
		<th>Firmware</th>
		<td>n/a</td>
	</tr>
	<tr>
		<th>View</th>
		<td>5</td>
		<th>Rating</th>
		<td>1</td>
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
		Excellent <input type="button" value="Rating" onclick="rating();" />
		<div id="ratingMsg" style="color: blue;"></div>
	</fieldset>
</div>

<div id="suggestionDiv">
	<fieldset>
		<legend>Suggest new content or let us know we can imporve
			this content:</legend>
		<textarea id="suggestion" name="suggestion" rows="10" cols="80"></textarea>
		<input type="button" value="Send" />
	</fieldset>
</div>