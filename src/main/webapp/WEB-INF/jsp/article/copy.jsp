<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script type="text/javascript">
<!--
	$().ready(function() {
		$('#copyEditForm').ajaxForm({
        	target: '#main'
    	});
	});
	function previewCopy() {
		cloneForm('#copyEditForm', '#previewForm');
		$("#previewForm").submit();
	}

	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val())
	    });
	}
//-->
</script>
<s:form id="copyEditForm" namespace="/" action="article!copy" theme="simple">
	<table>
		<tr>
			<td colspan="4" align="center"><input type="button" value="Copy All" onclick="copyall();" /></td>
		</tr>
		<tr>
			<td>ArticleID:</td>
			<td><s:textfield name="article.id" readonly="true" /></td>
			<td></td>
			<td>${article.id}</td>
		</tr>
		<tr>
			<td>Language:</td>
			<td><s:select list="{'English', 'Chinese'}" value="article.language" /></td>
			<td></td>
			<td><s:select name="copy.language" list="{'English', 'Chinese'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Type:</td>
			<td><s:select list="{'General', 'FAQ'}" value="article.type" /></td>
			<td></td>
			<td><s:select name="copy.type" list="{'General', 'FAQ'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Summary:</td>
			<td><s:textfield name="article.summary" readonly="true" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:textfield name="copy.summary" /></td>
		</tr>
		<tr>
			<td>EntryDate:</td>
			<td><s:date name="article.entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
			<td></td>
			<td><s:date name="copy.entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>Question:</td>
			<td><s:textarea name="article.question" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:textarea name="copy.question" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Answer:</td>
			<td><s:textarea name="article.answer" readonly="true" cols="40" rows="8" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:textarea name="copy.answer" cols="40" rows="8" /></td>
		</tr>
		<tr>
			<td>Technology:</td>
			<td><s:textarea name="article.technology" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:textarea name="copy.technology" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Product:</td>
			<td><s:textarea name="article.product" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:textarea name="copy.product" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Firmware:</td>
			<td><s:textfield name="article.firmware" readonly="true" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:textfield name="copy.firmware" /></td>
		</tr>
		<tr>
			<td>View Level:</td>
			<td><s:select list="{'Sub/Partner', 'L2', 'L3'}" value="article.level" /></td>
			<td><input type="button" value="Copy >>" /></td>
			<td><s:select name="copy.level" list="{'Sub/Partner', 'L2', 'L3'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Save As:</td>
			<td></td>
			<td></td>
			<td><s:radio list="{'Final & Publish', 'Final', 'Draft'}" name="copy.state" /></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>
				<input type="button" value="Cancel" onclick="viewArticle('${article.oid}')" />
				<input type="button" value="Preview" onclick="previewCopy()" /> 
				<s:submit value="Submit" />
			</td>
		</tr>
	</table>
</s:form>

<s:form id="previewForm" namespace="/" action="article!previewCopy" theme="simple" target="_blank">
	<input type="hidden" name="copy.language" />
	<input type="hidden" name="copy.type" />
	<input type="hidden" name="copy.summary" />
	<input type="hidden" name="copy.question" />
	<input type="hidden" name="copy.answer" />
	<input type="hidden" name="copy.technology" />
	<input type="hidden" name="copy.product" />
	<input type="hidden" name="copy.firmware" />
	<input type="hidden" name="copy.level" />
	<input type="hidden" name="copy.state" />
</s:form>