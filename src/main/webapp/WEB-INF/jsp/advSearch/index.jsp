<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#searchForm').ajaxForm({
            target: '#articles',
            success : $.unblockUI
        });
		$('#psForm').ajaxForm({
            target: '#productSelectDiv',
            success : $.unblockUI
        });
		$('#groupList').ufd();
		$('#accountList').ufd();
		$(".numeric").numeric({ decimal: false, negative: false });
		$(".float").numeric({ negative: false });
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
	function switchSource() {
		if ('Project' == $('input:[name="source"]:checked').val()) {
			$('#projectCode').removeAttr('disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).attr('disabled', 'disabled');
				} else {
					$(this).attr('selected', 'selected');
				}
			});
			switchProjectCode();
		} else {
			$('#projectCode').attr('disabled', 'disabled');
			$('#level').children('option').each(function() {
				if('L3CSO' != $(this).val()) {
					$(this).removeAttr('disabled');
				}
			});
			showAllModel();
		}
	}
	function switchProjectCode() {
		$('#pCode').val($('#projectCode').val());
		$('#psForm').submit();
	}
	function showAllModel() {
		$('#pCode').val('');
		$('#psForm').submit();
	}
</script>

<s:form id="psForm" namespace="/" action="edit!listModels" theme="simple">
	<input id="pCode" type="hidden" name="example.projectCode" />
</s:form>
<div id="search" class="condition">
	<s:form id="searchForm" namespace="/" action="advSearchArticle!search" theme="simple">
		<table class="conditionborder">
			<tr>
				<td>LANGUAGE:</td>
				<td><s:select list="languages" listKey="oid" listValue="name" name="languageOid" value="user.defaultLanguageOid"/></td>
			</tr>
			<s:if test="user.l3">
			<tr>
				<td>SOURCE:</td>
				<td><input type="radio" name="sourceType" value="" checked="checked" onchange="switchSource()"/>Any
					<input type="radio" name="sourceType" value="OBM" onchange="switchSource()"/>OBM
					<input type="radio" name="sourceType" value="Project" onchange="switchSource()"/>Project<s:select id="projectCode" name="projectCode" disabled="true" list="projects" listKey="oid" listValue="projectCode" onchange="switchProjectCode()"/>
				</td>
			</tr>
			</s:if>
			<s:else>
			<input type="hidden" name="source" value="OBM"/>
			<input type="hidden" name="projectCode" value=""/>
		</s:else>
			<s:if test="user.l3">
		<tr>
			<td>ZyTech News:</td>
			<td><s:radio name="news" list="#{'true':'Yes','false':'No'}" value="false"/>
			</td>
		</tr>
		</s:if>
		<s:else>
			<input type="hidden" name="news" value="false"/>
		</s:else>
		<tr>
			<td>TYPE:</td>
			<td><s:select id="type" name="type" list="@tw.com.dsc.domain.ArticleType@values()" listValue="%{getText('enum.ArticleType.'+toString())}" headerKey="" headerValue="----" /></td>
		</tr>
			<tr>
				<td>DATE RANGE:</td>
				<td><input type="radio" name="dateType" value="entryDate"/>Entry Date
					<input type="radio" name="dateType" value="updateDate"/>Last Update
					<input type="radio" name="dateType" value="publishDate"/>Publish Date
					<input type="text" name="beginDate" size="10" id="beginDate" readonly="readonly" class="calendar"/>~<input type="text" name="endDate" size="10" id="endDate" readonly="readonly" class="calendar"/>
				</td>
			</tr>
			<tr>
				<td>APPROVE:</td>
				<td><input type="radio" name="apType" value="" checked="checked"/>Any
					<input type="radio" name="apType" value="publishedType"/>Published <s:select name="published" list="#{'true':'Yes','false':'No'}" headerKey="" headerValue="----"/>
					<input type="radio" name="apType" value="statusType"/>Status 
					<s:select name="status" list="user.advSearchStatus" listValue="%{getText('enum.Status.'+toString())}" headerKey="" headerValue="----" />
				</td>
			</tr>
			<tr>
				<td rowspan="3">AGENT:</td>
				<td>
					<input type="radio" name="agentSearchType" value="group"/>by Team
					<s:select id="groupList" name="group" list="groups" listKey="id" listValue="name" headerKey="" headerValue="----"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="agentSearchType" value="agent"/>by Agent
					<s:select id="accountList" name="account" list="accounts" listKey="id" listValue="name" headerKey="" headerValue="----"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="radio" name="agentSearchType" value="self" checked="checked"/>myself
				</td>
			</tr>
			
			<tr>
				<td>VIEW LEVEL:</td>
				<td><s:checkboxlist list="user.availableLevels" listValue="%{getText('enum.Level.'+toString())}" name="levels" ></s:checkboxlist> </td>
			</tr>
			<tr>
			<td>TECHNOLOGY:</td>
			<td><s:textarea id="technology" name="technology" cols="40" rows="4" readonly="true"/>
				<div>
				<select id="techSelect" name="techSelect" multiple="true">
					<s:iterator value="technologies" var="tech">
						<optgroup label="<s:property value="technology" />">
							<s:iterator value="#tech.items" var="item">
								<option value="<s:property value="#tech.technology" />--<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>PRODUCT:</td>
			<td><s:textarea id="product" name="product" cols="40" rows="4" readonly="true"/>
				<div id="productSelectDiv">
				<select id="productSelect" name="productSelect" multiple="true">
					<s:iterator value="products" var="product">
						<optgroup label="<s:property value="name" />">
							<s:iterator value="#product.models" var="model">
								<option value="<s:property value="#product.name" />--<s:property value="name" />"><s:property value="name" /></option>
							</s:iterator>
						</optgroup>
					</s:iterator>
				</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>FIRMWARE:</td>
			<td><s:textfield name="firmware" size="40" maxlength="50"/></td>
		</tr>
		<tr>
			<td>VIEWS:</td>
			<td><s:select name="viewsType" list="#{'GE':'>=','EQ':'=','LE':'<='}" /> <input type="text" name="hitCount" size="6" class="numeric" /></td>
		</tr>
		<tr>
			<td>RATING:</td>
			<td><s:select name="ratingType" list="#{'GE':'>=','EQ':'=','LE':'<='}" /> <input type="text" name="avgRate" size="6" class="float" /></td>
		</tr>
		 <tr>
			<td colspan="2" align="center"><s:submit value="Search" /></td>
		</tr>
		</table>
	</s:form>
</div>
<div id="articles">
	
</div>
<div id="detail">
</div>