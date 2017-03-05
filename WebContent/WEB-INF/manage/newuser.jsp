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
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Add New Author</h1>
				<p>Use this page to add a new author to the blog.</p>
				
				<s:if test="hasActionErrors()">
				   <s:iterator value="actionErrors">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-red w3-text-red w3-border-red" onclick="this.style.display='none'" data-close="">
						<span class="icon-cross w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>
				<s:if test="hasActionMessages()">
				   <s:iterator value="actionMessages">
					<p class="w3-padding w3-border w3-card-2 w3-round w3-pale-green w3-text-green w3-border-green" onclick="this.style.display='none'" data-close="">
						<span class="icon-checkmark w3-large w3-margin-right"></span><s:property/></p>
					</s:iterator>
				</s:if>	
		
				<!-- REGISTER BEGIN -->
				<div class="w3-container w3-padding-0 w3-twothird w3-border-0">
					<form action="/manage/newuser" method="post">
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="name">Full Name:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="name" id="name" value="<s:property value="name" />" required placeholder="John Doe" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="footnote quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="#request.author.uriName" />" required placeholder="rambling-man" class="w3-input w3-round-large w3-border" />
						<span class="footnote"><%=request.getScheme()+"://"+request.getServerName() %>/author/<s:property value="#request.author.uriName" /></span>
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="username">Username:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="username" id="username" value="<s:property value="#session.USER.username" />" required class="w3-input w3-round-large w3-border" />
						<span class="footnote">Your username is used to login.</span>
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="email">Email:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="100" name="email" id="email" value="<s:property value="email" />" required placeholder="jdoe@mail.com" class="w3-input w3-round-large w3-border" />
						<span class="footnote">Your email address is used to validate your identity.</span>
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="password">Password:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="password" size="50" maxlength="100" name="password" id="password" value="" required placeholder="" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="password2">Password verify:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="password" size="50" maxlength="100" name="password2" id="password2" value="" required placeholder="Same as above" class="w3-input w3-round-large w3-border" />
					</p>
					
					<hr />
					<button class="w3-btn w3-right w3-round w3-card w3-pale-green" type="submit" value="Submit" title="Submit">
						<span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" value="Back" title="Go back">
						<span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="reset" value="Reset" title="Reset search fields">
						<span class="icon-bin w3-large w3-margin-right"></span>Clear</button>
					
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