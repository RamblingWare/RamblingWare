<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>Edit Author - <%=Application.getString("name")%></title>

<script src="/vendor/ckeditor/ckeditor.js"></script>
<script>
function validate() {
	var pattern = /^((https):\/\/)/;
	if(document.getElementById('thumbnail').value.length > 0) {
		if(!pattern.test(document.getElementById('thumbnail').value)) {
			if(!confirm('Thumbnail is not secure (HTTPS). Do you want to continue?')) {
				document.getElementById('thumbnail').focus();
				return false;
			}
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
		desc = "This author hasn't provided a bio yet.";
	document.getElementById('previewDesc').innerHTML = desc;
	
	var src = document.getElementById('thumbnail').value;
	if(src.length<=1)
		src = "/img/placeholder-200.png";
	document.getElementById('previewImg').src = src;
}
</script>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Edit Author</h1>
				
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/edituser/<s:property value="#request.author.uri" />" method="post">
					<input type="hidden" name="submitForm" value="true" />
					<input type="hidden" name="id" value="<s:property value="#request.author.id" />" />
					
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="title">Author Name:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="#request.author.name" />" onkeypress="preview()" onchange="preview()" required placeholder="Rambling Man" class="w3-input w3-round-large w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uri">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uri" id="uri" value="<s:property value="#request.author.uri" />" required placeholder="rambling-man" class="w3-input w3-round-large w3-border" />
						<a href="#" class="w3-medium"><%=Application.getString("url")%>/author/<s:property value="#request.author.uri" /></a>
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="description">Description:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="description" id="description" value="<s:property value="#request.author.description" />" onkeyup="preview()" onchange="preview()" placeholder="A quick description for RSS and social media..." class="w3-input w3-round-large w3-border" />
					</p>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="thumbnail">Thumbnail Image URL</label>
						<input type="text" size="50" maxlength="200" name="thumbnail" id="thumbnail" value="<s:property value="#request.author.thumbnail" />" onchange="preview()" placeholder="https://example.com/image-200x200.png" class="w3-input w3-round-large  w3-border" />
					</p>
					
					<h3>Social Card Preview</h3>
					<div class="w3-container w3-padding-0">
						
						<div class="w3-col s12 m10 l7 w3-padding-0 w3-margin-0 w3-round w3-hover-shadow w3-card">
							<div class="w3-col s3 m3 l3 w3-padding-16">
								<a href="#"><img id="previewImg" class="w3-round w3-margin-left" style="width: 75%;" alt="Profile" src="<s:property value="thumbnail" />"  onerror="this.src='/img/error-200.png';this.title='Failed to load image.'" /></a>
							</div>
							<div class="w3-col s9 m9 l9 w3-padding-16">
								<div class="w3-small w3-text-grey w3-margin-0 w3-padding-right">
								<h3 class="w3-padding-0 w3-margin-0"><a href="#"><span id="previewTitle"><s:property value="Name" /></span></a></h3>
								<p id="previewDesc" class="w3-small w3-margin-0"><s:property value="description" /></p> 
								</div>
							</div>					
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
					
					<br />
					<hr />
					<button class="w3-btn w3-right w3-round w3-green w3-hover-teal" type="submit" title="Submit" onclick="return validate()">Save Changes</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" value="Back" title="Go back"><span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-deep-orange w3-hover-red" type="submit" onclick="return confirm('Are you sure you want to delete?\n\nAny blog posts by this author will still exist.')" name="delete" value="Delete" title="Delete this Author">Delete</button>
					
					</form>
				</div>
				<script>
					preview();
				</script>
			</div>
		</div>
	</article>
	
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>