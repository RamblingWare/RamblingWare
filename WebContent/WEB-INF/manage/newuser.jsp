<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Register - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<!-- META_END -->
</head>
<body class="w3-theme-dark">

	<!-- HEADER_BEGIN -->
	<%@include file="/WEB-INF/fragment/header.jspf"%>
	<!-- HEADER_END -->
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<!-- TABS_BEGIN -->
			<%@include file="/WEB-INF/fragment/tabs.jspf"%>
			<!-- TABS_END -->
		
			<div id="page-content" class="w3-col m8 w3-container w3-padding">
			
				<!-- ADMIN TABS BEGIN -->
				<%@include file="/WEB-INF/fragment/admin-tabs.jspf"%>
				<!-- ADMIN TABS END -->
				
				<h1>Register New Admin</h1>
				<p>Use this page to add new administrators to the webapp.</p>
				
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
						<p class="error"><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
						<p class="info"><s:property/></p>
					</s:iterator>
				</s:if>
		
				<!-- REGISTER BEGIN -->
				<div class="w3-container w3-padding-0 w3-twothird w3-border-0">
					<form action="/manage/newuser" method="post">
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="name">Full Name:</label>
						<input type="text" size="50" maxlength="100" name="name" id="name" value="<s:property value="name" />" required placeholder="John Doe" class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:</label>
						<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required placeholder="jdoe@mail.com" class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="password">Password:</label>
						<input type="password" size="50" maxlength="100" name="password" id="password" value="" required placeholder="" class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="password2">Password verify:</label>
						<input type="password" size="50" maxlength="100" name="password2" id="password2" value="" required placeholder="Same as above" class="w3-input w3-round-large w3-hover-light-grey w3-border" />
					</p>
					
					<hr />
					<button class="icon-redo w3-btn w3-right w3-round w3-card w3-green w3-hover-text-white" type="submit" value="Submit" title="Submit">Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="icon-undo w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" value="Back" title="Go back">Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="icon-delete w3-btn w3-round w3-card w3-theme-light" type="reset" value="Reset" title="Reset search fields">Clear</button>
					
					</form>
				</div>
				<!-- REGISTER END -->
				
				<br />
				<br />
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>