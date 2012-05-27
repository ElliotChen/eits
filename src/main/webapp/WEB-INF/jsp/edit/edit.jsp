<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>

<script type="text/javascript">
<!--
	$().ready(function() {
		$('#editForm').ajaxForm({
			target: '#main',
			  beforeSubmit: function() {
			    $('#editForm').validate({ rules : {
					summary : {required:true},
					keywords : {required:true},
					question : {required:true},
					answer : {required:true},
					product : {required:true},
					technology : {required:true},
					rejectReason : {required:function(element) {
				        return $("#statusAction").val() == 'Draft';
				      }}
				} });
			    return $('#editForm').valid();
			  }
			});
		delete CKEDITOR.instances['question'];
		delete CKEDITOR.instances['answer'];
		$('#question').ckeditor();
		$('#answer').ckeditor();
		switchType();
		switchSource();
		$('#techSelect').multiselect({beforeclose: function(){
			$('#technology').val($('#techSelect').val());
		   }, position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
		$('#productSelect').multiselect({beforeclose: function(){
			$('#product').val($('#productSelect').val());
		   },position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
	});
	function previewSave() {
		cloneForm('#editForm', '#editPreviewForm');
		$("#editPreviewForm").submit();
	}

	function cancelSave() {
		$('#editForm').val('action', '${ctx}/edit!list.action');
		$('#editForm').submit();
	}
	
	function switchType() {
		$('.ArticleType').hide();
		$('.'+$('#type').val()).show();
	}
	
	function switchSource() {
		<s:if test="user.l3">
		if ('OBM' == $('input:[name="source"]:checked').val()) {
			$('#projectCode').attr('disabled', 'disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).removeAttr('disabled');
				}
			});
		} else {
			$('#projectCode').removeAttr('disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).attr('disabled', 'disabled');
				} else {
					$(this).attr('selected', 'selected');
				}
			});
		}
		</s:if>
	}
	
	function checkAction(op) {
		if ('Draft' == op.value) {
			$('.rejectArea').show();
		} else {
			$('.rejectArea').hide();
		}
	}
	
	
//-->
</script>
<input type="button" value="View Log" onclick="viewArticleLog('${oid}')"/>
<input type="button" value="View Reason" onclick="viewRejectLogs('${oid}')"/>
<div class="condition">
<s:form id="editForm" namespace="/" action="edit!save" theme="simple" method="POST" enctype ="multipart/form-data">
	<s:hidden name="oid" />
	<table class="conditionborder">
		<tr>
			<td>ARTICLE ID:</td>
			<td><s:textfield name="articleId.oid" readonly="true" maxlength="6" size="6"/> (${article.status} - ${article.agentType})</td>
		</tr>
		<tr>
			<td>LANGUAGE:</td>
			<td><s:select list="languages" listKey="oid" listValue="name" name="languageOid" value="language.oid"/></td>
		</tr>
		<s:if test="user.l3">
		<tr>
			<td>SOURCE:</td>
			<td><s:radio name="source" list="@tw.com.dsc.domain.Source@values()"
					onchange="switchSource()" />
				<s:select id="projectCode" name="projectCode" list="projects" disabled="true" listKey="oid" listValue="projectCode"></s:select>
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="source" value="OBM"/>
		</s:else>
		
		<s:if test="user.l3">
		<tr>
			<td>ZyTech News:</td>
			<td><s:radio name="news" list="#{'true':'Yes','false':'No'}" />
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="news" value="false"/>
		</s:else>
		
		<tr>
			<td>TYPE:</td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" onchange="switchType();" /></td>
		</tr>
		<tr>
			<td>SUMMARY:</td>
			<td><s:textfield name="summary" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>Expire After:</td>
			<td><s:select list="@tw.com.dsc.domain.ExpireType@values()" listValue="%{getText('enum.ExpireType.'+toString())}" name="expireType" /></td>
		</tr>
		<tr>
			<td colspan="2">ENTRY DATE:<s:date name="entryDate" format="yyyy/MM/dd HH:mm:ss" />　　　　LAST UPDATE:<s:date name="updateDate" format="yyyy/MM/dd HH:mm:ss" />　　　　PUBLISH DATE:<s:date name="publishDate" format="yyyy/MM/dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>KEYWORDS:</td>
			<td><s:textfield name="keywords" size="40" maxlength="50" /></td>
		</tr>
		<tr class="ArticleType SpecInfo">
			<td>eITS TICKET ID:</td>
			<td><s:textfield name="ticketId" maxlength="10"/></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>QUESTION:</td>
			<td><s:textarea id="question" name="question" cols="40" rows="4" /></td>
		</tr>
		<tr class="ArticleType GeneralInfo SpecInfo">
			<td>ANSWER:</td>
			<td><s:textarea id="answer" name="answer" cols="40" rows="8" /></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SCENARIO DESCRIPTION:</td>
			<td><s:textfield name="scenario" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>SETUP/STEP BY STEP PROCEDURE:</td>
			<td><s:textfield name="step" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Application TroubleShooting">
			<td>VERIFICATION:</td>
			<td><s:textfield name="verification" size="40" maxlength="50"/></td>
		</tr>
		
		<tr class="ArticleType Issue">
			<td>PROBLEM DESCRIPTION:</td>
			<td><s:textfield name="problem" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>SOLUTION:</td>
			<td><s:textfield name="solution" size="40" maxlength="50"/></td>
		</tr>
		<tr class="ArticleType Issue">
			<td>CONDITION/REPRODUCE PROCEDURE:</td>
			<td><s:textfield name="procedure" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>VIEW LEVEL:</td>
			<td><s:select id="level" name="level" list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" /></td>
		</tr>
		<tr>
			<td>TECHNOLOGY:</td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" />
				<select id="techSelect" name="techSelect" multiple="true">
					<s:iterator value="technologies" var="tech">
						<optgroup label="<s:property value="technology" />">
							<s:iterator value="#tech.items" var="item">
								<option value="<s:property value="#tech.technology" />-<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>PRODUCT:</td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" />
				<select id="productSelect" name="productSelect" multiple="true">
					<s:iterator value="products" var="product">
						<optgroup label="<s:property value="name" />">
							<s:iterator value="#product.models" var="model">
								<option value="<s:property value="#product.name" />-<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>FIRMWARE:</td>
			<td><s:file name="upload"/>
			
			<c:if test="${null != firmware}"><a href="${firmware.uri}" target="_blank">${firmware.name}</a></c:if>
			</td>
		</tr>

		<tr>
			<td>STATUS ACTION:</td>
			<td>
				<s:select id="statusAction" name="statusAction" list="availableStatus" listValue="%{getText('edit.statusAction.'+toString())}" headerKey="" headerValue="-----" onchange="checkAction(this)" />
			</td>
		</tr>
		<s:if test="user.leader">
		<tr class="rejectArea" style="display: none;">
			<td>REJECT REASON:</td>
			<td>
				<s:textarea name="rejectReason" cols="40" rows="4" />
			</td>
		</tr>
		</s:if>
		<tr>
			<td colspan="2" align="right">
				<s:submit value="Update" cssClass="save" />
				<s:submit value="Delete" cssClass="delete" action="edit" method="disable"/>
				<input type="button" value="Preview" onclick="previewSave()" />
				<input type="button" value="Do Nothing" onclick="switchMenu('m3', 'edit!list.action');" />			  
			</td>
		</tr>
	</table>
</s:form>

<s:form id="editPreviewForm" namespace="/" action="edit!previewSave" theme="simple" target="_blank">
	<input type="hidden" name="articleId.oid" />
	<input type="hidden" name="languageOid" />
	<input type="hidden" name="source" />
	<input type="hidden" name="news" />
	<input type="hidden" name="type" />
	<input type="hidden" name="summary" />
	<input type="hidden" name="expireType" />
	<input type="hidden" name="entryDate" />
	<input type="hidden" name="keywords" />
	<input type="hidden" name="ticketId" />
	<input type="hidden" name="question" />
	<input type="hidden" name="answer" />
	<input type="hidden" name="scenario" />
	<input type="hidden" name="step" />
	<input type="hidden" name="verification" />
	<input type="hidden" name="problem" />
	<input type="hidden" name="solution" />
	<input type="hidden" name="procedure" />
	<input type="hidden" name="level" />
	<input type="hidden" name="technology" />
	<input type="hidden" name="product" />
</s:form>
</div>