<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/base.jsp"%>
<div class="logo_zyxel">
	<a href="http://www.zyxel.com.tw/"><img src="${ctx}/images/logo_zyxel.jpg" alt="ZyXEL Logo"></a>
	<s:if test="user.guest">
			Hi! Guest
	</s:if>
	<s:else>
			<s:property value="user.name" />, welcome to eITS!
	</s:else>
</div>
<div id="loginDiv" style="margin-left: 600px;">
	<s:if test="user.guest">
		<a href="#login-box" class="login-window">Login</a>
		<div id="login-box" class="login-popup">
			<a href="#" class="close"><img src="images/close_pop.png" class="btn_close" title="Close Window" alt="Close" /></a>
			<form method="post" class="signin" action="${ctx}/system!login.action">
				<fieldset class="textbox">
					<label class="username"> <span>Account</span> <input id="username" name="loginUser.account" value=""
						type="text" autocomplete="on" placeholder="Account">
					</label> <label class="password"> <span>Password</span> <input id="password" name="loginUser.password" value=""
						type="password" placeholder="Password">
					</label> <input class="submit button" type="submit" value="Login" />
				</fieldset>
			</form>
		</div>
	</s:if>
	<s:else>
		<a href="#" onclick="switchMenu('m3', 'edit!list.action');">Home</a>
		<a href="${ctx}/system!logout.action">Logout</a>
		<br />
			Article#:<input type="text" id="quickOid" name="quickOid" size="6" maxlength="6" />
		<input type="button" onclick="quickViewArticle();" value="Go" />
		<br />
			Role:<s:select id="userRole" name="userRole" list="user.userRoles" listKey="role" listValue="role" onchange="switchRole()" value="user.currentUserRole.role"/>
	</s:else>
</div>