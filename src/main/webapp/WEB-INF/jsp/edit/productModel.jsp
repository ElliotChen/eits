<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<script>
	$().ready(function() {
		$('#productSelect').multiselect({beforeclose: function(){
			$('#product').val($('#productSelect').val());
		   },position: {
			      my: 'left bottom',
			      at: 'left top'
			   }}).multiselectfilter();
	});
</script>


<select id="productSelect" name="productSelect" multiple="true">
	<s:iterator value="products" var="product">
		<optgroup label="<s:property value="name" />">
			<s:iterator value="#product.models" var="model">
				<option
					value="<s:property value="#product.name" />--<s:property value="name" />">
					<s:property value="name" />
				</option>
			</s:iterator>
		</optgroup>
	</s:iterator>
</select>