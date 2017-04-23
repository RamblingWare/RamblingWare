<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Edit Author - RamblingWare</title>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta-manage.jspf"%>
<script src="/ckeditor/ckeditor.js"></script>
<script>
function validate() {
	var pattern = /^((https):\/\/)/;
	if(!pattern.test(document.getElementById('thumbnail').value)) {
		if(!confirm('Thumbnail is not secure (HTTPS). Do you want to continue?')) {
			document.getElementById('thumbnail').focus();
			return false;
		}
	}
	if(document.getElementById('htmlContent').value.length > 12288) {
		alert('Sorry! The content is too long.\nMax length = 12288 chars\Page length = '+document.getElementById('htmlContent').value.length+'\n\nPlease shorten your page content.');
		return false;
	}
	return true;
}
function preview() {
	var title = document.getElementById('title').value;
	if(title.length<=1)
		title = "Your Name Here";
	document.getElementById('previewTitle').innerHTML = title;
	
	var desc = document.getElementById('description').value;
	if(desc.length<=1)
		desc = "About You Description.";
	document.getElementById('previewDesc').innerHTML = desc;
	
	var src = document.getElementById('thumbnail').value;
	if(src.length<=1)
		src = "https://i.imgur.com/pHKz09F.png";
	document.getElementById('previewImg').src = src;
	changeForm();
}
preview();
</script>
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
				
				<h1>Edit Author</h1>
				
				<!-- EDIT AUTHOR BEGIN -->
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/edituser/<s:property value="#request.author.uriName" />" method="post">
					<input type="hidden" name="submitForm" value="true" />
					<input type="hidden" name="id" value="<s:property value="#request.author.id" />" />
					
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="title">Author Name:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="#request.author.name" />" onkeypress="preview()" onchange="preview()" required placeholder="Rambling Man" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="#request.author.uriName" />" required placeholder="rambling-man" class="w3-input w3-round-large w3-border" />
						<span class="w3-small w3-text-grey"><%=request.getScheme()+"://"+request.getServerName() %>/author/<s:property value="#request.author.uriName" /></span>
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="description">Description:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="description" id="description" value="<s:property value="#request.author.description" />" onkeyup="preview()" onchange="preview()" required placeholder="A quick description for RSS and social media..." class="w3-input w3-round-large w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="thumbnail">Thumbnail Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="200" name="thumbnail" id="thumbnail" value="<s:property value="#request.author.thumbnail" />" onkeyup="preview()" onchange="preview()" required placeholder="https://imgur.com/image.png" class="w3-input w3-round-large  w3-border" />
					</p>
					
					<h3>About You Preview</h3>
					<div class="w3-container w3-padding-0">
						
						<div class="w3-col s12 m10 l7 w3-padding-0 w3-margin-0 w3-round w3-hover-shadow w3-card">
							<a href="#">
							<span class="w3-col s3 m3 l3 w3-padding-16">
								<img id="previewImg" class="w3-round w3-margin-left" style="width: 75%;" alt="Profile Picture" src="<s:property value="thumbnail" />">
							</span>
							<span class="w3-col s9 m9 l9 w3-padding-16">
								<p class="w3-small w3-text-grey w3-margin-0 w3-padding-right">
								<b><span id="previewTitle"><s:property value="Name" /></span></b><br />
								<span id="previewDesc" class="w3-small"><s:property value="description" /></span> 
								</p>
							</span>
							</a>						
						</div>
						
					</div>
					
					<br />
					<hr />
					
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="htmlContent">Page Content:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: Max 12288 chars.)</span></label>
						<textarea name="htmlContent" id="htmlContent" rows="10" cols="100" style="width:100%" maxlength="12288">
						<s:property value="#request.author.htmlContent" />
						</textarea>
			            <script>
			                // Replace the <textarea id="htmlContent"> with a CKEditor instance, using default configuration.
			                CKEDITOR.replace('htmlContent', {
								language: 'en',
								height: 500,
								toolbarCanCollapse: true,
								codeSnippet_theme: 'dark'
							});
			            </script>
					</p>
					
					
					<hr />
					<button class="w3-btn w3-right w3-round w3-card w3-pale-green" type="submit" title="Submit" onclick="return validate()">Save Changes</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" value="Back" title="Go back"><span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-pale-red" type="submit" onclick="return confirm('Are you sure you want to delete?\n\nAny blog posts by this author will still exist.')" name="delete" value="Delete" title="Delete this Author">Delete</button>
					
					</form>
				</div>
				<script>
					preview();
				</script>
				<!-- EDIT AUTHOR END -->
			</div>
		</div>
	</article>
	
	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>