<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script type="text/javascript">
<!--
	$().ready(function() {
		
		$('#copyEditForm').ajaxForm({
        	target: '#main',
        	beforeSubmit: function() {
			    $('#copyEditForm').validate({ rules : {
					id : {required:true, number:true},
					summary : {required:true},
					keywords : {required:true},
					question : {required:true},
					answer : {required:true},
					product : {required:true},
					technology : {required:true},
					firmware : {required:true}
				} });
			    return $('#copyEditForm').valid();
			  }
    	});
		
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		$('#question').ckeditor();
		$('#answer').ckeditor();
		
		$('#techSelect').multiselect({beforeclose: function(){
			$('#technology').val($('#techSelect').val());
		   }}).multiselectfilter();
		$('#productSelect').multiselect({beforeclose: function(){
			$('#product').val($('#productSelect').val());
		   }}).multiselectfilter();
	});
	function previewCopy() {
		cloneForm('#copyEditForm', '#previewForm');
		$("#previewForm").submit();
	}

	function cloneForm(sourceForm, targetForm) {
	    $(':input[name]', sourceForm).each(function() {
	        $('[name=\'' + $(this).attr('name') +'\']', targetForm).val($(this).val());
	    });
	}
	function copyAll() {
		copyField('summary');
		copyField('language');
		copyField('type');
		copyField('question');
		copyField('answer');
		copyField('technology');
		copyField('product');
		copyField('firmware');
		copyField('level');
	}
	function copyField(fname) {
		$('[name =\''+fname+'\']', $("#copyEditForm")).val($('[name =\'sarticle.'+fname+'\']', $("#copyEditForm")).val());
	}
//-->
</script>
<s:form id="copyEditForm" namespace="/" action="article!copy" theme="simple">
	<table>
		<tr>
			<td colspan="4" style="text-align: center;"><input type="button" value="Copy All" onclick="copyAll();" /></td>
		</tr>
		<tr>
			<td>ArticleID:</td>
			<td><s:textfield name="sarticle.id" readonly="true" /></td>
			<td></td>
			<td>${sarticle.id}</td>
		</tr>
		<tr>
			<td>Language:</td>
			<td><s:select list="{'English', 'Chinese'}" name="sarticle.language" /></td>
			<td></td>
			<td><s:select name="language" list="{'English', 'Chinese'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Type:</td>
			<td><s:select list="{'General', 'FAQ'}" name="sarticle.type" /></td>
			<td></td>
			<td><s:select name="type" list="{'General', 'FAQ'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Summary:</td>
			<td><s:textfield name="sarticle.summary" readonly="true" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('summary')"/></td>
			<td><s:textfield name="summary" /></td>
		</tr>
		<tr>
			<td>EntryDate:</td>
			<td><s:date name="sarticle.entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
			<td></td>
			<td><s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>Question:</td>
			<td><s:textarea name="sarticle.question" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('question')" /></td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" /></td>
		</tr>
		<tr>
			<td>Answer:</td>
			<td><s:textarea name="sarticle.answer" readonly="true" cols="40" rows="8" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('answer')"/></td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr>
			<td>Technology:</td>
			<td><s:textarea name="sarticle.technology" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('technology')"/></td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" />
				<select id="techSelect" name="techSelect" multiple="multiple">
						<option value="Tech1">Tech1</option>
						<option value="Tech2">Tech2</option>
						<option value="Tech3">Tech3</option>
						<option value="Tech4">Tech4</option>
				</select></td>
		</tr>
		<tr>
			<td>Product:</td>
			<td><s:textarea name="sarticle.product" readonly="true" cols="40" rows="4" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('product')"/></td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" />
				<select id="productSelect" name="productSelect" multiple="multiple">
					<optgroup label="ProductA">
						<option value="Model1">Model1</option>
						<option value="Model2">Model2</option>
					</optgroup>
					<optgroup label="ProductB">
						<option value="Model3">Model3</option>
						<option value="Model4">Model4</option>
					</optgroup>
				</select>
			</td>
		</tr>
		<tr>
			<td>Firmware:</td>
			<td><s:textfield name="sarticle.firmware" readonly="true" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('firmware')"/></td>
			<td><s:textfield name="firmware" /></td>
		</tr>
		<tr>
			<td>View Level:</td>
			<td><s:select list="{'Sub/Partner', 'L2', 'L3'}" value="sarticle.level" /></td>
			<td><input type="button" value="Copy >>" onclick="copyField('level')"/></td>
			<td><s:select name="level" list="{'Sub/Partner', 'L2', 'L3'}" headerKey="-1" headerValue="--Select--"
					emptyOption="------" /></td>
		</tr>
		<tr>
			<td>Save As:</td>
			<td></td>
			<td></td>
			<td><s:radio list="{'Final & Publish', 'Final', 'Draft'}" name="state" /></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>
				<input type="button" value="Cancel" onclick="viewArticle('${oid}')"/>
				<input type="button" value="Preview" onclick="previewCopy()" /> 
				<s:submit value="Submit" cssClass="save"/>
			</td>
		</tr>
	</table>
</s:form>

<s:form id="previewForm" namespace="/" action="article!previewCopy" theme="simple" target="_blank">
	<input type="hidden" name="language" />
	<input type="hidden" name="type" />
	<input type="hidden" name="summary" />
	<input type="hidden" name="question" />
	<input type="hidden" name="answer" />
	<input type="hidden" name="technology" />
	<input type="hidden" name="product" />
	<input type="hidden" name="firmware" />
	<input type="hidden" name="level" />
	<input type="hidden" name="state" />
</s:form>