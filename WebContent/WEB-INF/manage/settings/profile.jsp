<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Profile - <%=Application.getString("name")%></title>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Profile</h1>
				
				<div class="w3-row">	
				<div class="w3-container w3-padding w3-col s12 m12 l12">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">About you</h3>
						</div>
						<div class="w3-container w3-padding w3-small w3-theme-light">
							
							<span class="w3-col s3 m3 l3 w3-padding-16">
								<img class="w3-round w3-margin-left" style="width: 75%;" alt="Profile" src="<s:property value="#session.USER.thumbnail" />" onerror="this.src='/img/error-200.png'"/>
							</span>
							<span class="w3-col s9 m9 l9 w3-padding-16">
								<span class="w3-small w3-text-grey w3-padding-right">
								<b><s:property value="#session.USER.name" /></b><br />
								<span class="w3-small"><s:property value="#session.USER.description" /></span> 
								</span>
							</span>								
							<hr />
							<p>
								<a class="w3-btn w3-small w3-round w3-card w3-theme-light" title="Edit author page" href="/manage/edituser/<s:property value="#session.USER.uriName" />"><span class="icon-quill w3-large w3-margin-right"></span>Edit</a>
								<a class="w3-btn w3-small w3-round w3-card w3-theme-light" title="Go to the author page" href="/author/<s:property value="#session.USER.uriName" />">View My Page</a>
							</p>
						
						</div>
					</div>
				
				</div>
				<div class="w3-container w3-padding w3-col s12 m12 l12">
				
					<div class="w3-border w3-round">
						<div class="w3-margin-0 w3-padding-0 w3-theme-light w3-center">
							<h3 class="w3-margin-0 w3-padding uppercase">Extras</h3>
						</div>
						<div class="w3-padding w3-small w3-theme-light">
						
							<p class="w3-small">
							Date your Account was Created:&nbsp;<span class="bold"><s:property value="#session.USER.createDateReadable"/></span>
							<br />
							Date your Account was Last Modified:&nbsp;<span class="bold"><s:property value="#session.USER.modifyDateReadable"/></span>
							<br />
							Date of your Last Login:&nbsp;<span class="bold"><s:property value="#session.USER.lastLoginDateReadable" /></span>
							</p>
							
						
						</div>
					</div>
				
				</div>				
				</div>
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>