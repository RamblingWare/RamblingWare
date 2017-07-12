<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"
	errorPage="/WEB-INF/error/error.jsp"%>
<%@ page import="com.rant.config.Utils" %>
<!DOCTYPE HTML>
<html>
<head>
<%@include file="/WEB-INF/fragment/meta/meta-manage.jspf"%>

<title>New Post - <%=Application.getSetting("name")%></title>

<script src="/vendor/ckeditor/ckeditor.js"></script>
<s:if test="usedUris != null && !usedUris.isEmpty()">
<script> var usedUris = [
<s:iterator value="usedUris" status="u">
	"<s:property />",
</s:iterator>
""];</script>
</s:if>

<s:if test="usedTags != null && !usedTags.isEmpty()">
<script> var usedTags = [
<s:iterator value="usedTags" status="t">
	<s:set var="tval" value="usedTags[#t.index].substring(0,usedTags[#t.index].lastIndexOf(' ('))" />"<s:property value="tval" />",
</s:iterator>
""];</script>
</s:if>
<script>
function changeForm() {
	if(document.getElementById('hasBanner').checked)
	{
		document.getElementById('bannerDiv1').style.display = 'block';
		document.getElementById('bannerDiv2').style.display = 'block';
		document.getElementById('banner').disabled = false;
		document.getElementById('banner').required = true;
		document.getElementById('bannerCaption').disabled = false;
	}
	else
	{
		document.getElementById('banner').disabled = true;
		document.getElementById('banner').required = false;
		document.getElementById('bannerCaption').disabled = true;
		document.getElementById('bannerDiv1').style.display = 'none';
		document.getElementById('bannerDiv2').style.display = 'none';
	}
}
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
	if(document.getElementById('hasBanner').checked) {
		if(document.getElementById('banner').value.length <= 0) {
			alert('Banner is empty! Please provide a URL to an image.');
			return false;
		}
		if(!pattern.test(document.getElementById('banner').value)) {
			if(!confirm('Banner is not secure (HTTPS). Do you want to continue?')) {
				document.getElementById('banner').focus();
				return false;
			}
		}
	}
	var uri = document.getElementById('uriName').value;
	for(var i=0; i<usedUris.length; i++) {
		if(usedUris[i] == uri) {
			alert('Sorry! That URI is already taken by another blog post.\n\nPlease change the URI.');
			return false;
		}
	}	
	if(document.getElementById('htmlContent').value.length > 12288) {
		alert('Sorry! The post is too long.\nMax length = 12288 chars\nPost length = '+document.getElementById('htmlContent').value.length+'\n\nPlease shorten your post.');
		return false;
	}
	return true;
}
function makeUri() {
	var title = document.getElementById('title').value;
	title = title.replace(/[^0-9a-z]/gi, ' ');
	title = title.replace(/\s+/g, '-').toLowerCase();
	document.getElementById('uriName').value = title;
}
function addTag(tag) {
	var tags = document.getElementById('tags').value;
	// chop off [ ] if they were added
	if(tags.startsWith('['))
		tags = tags.substring(1);
	if(tags.endsWith(']'))
		tags = tags.substring(0,tags.length-1);
	
	if(tags.length<=1)
		tags = tag;
	else
		tags += ', '+tag;
		
	document.getElementById('tags').value = tags;
	preview();
}
function preview() {
	var title = document.getElementById('title').value;
	if(title.length<=1)
		title = "Blog Post Title";
	document.getElementById('previewTitle').innerHTML = title;
	document.getElementById('previewTitle2').innerHTML = title;	
	
	var desc = document.getElementById('description').value;
	if(desc.length<=1)
		desc = "Blog Post Description.";
	document.getElementById('previewDesc').innerHTML = desc;
	
	var cat = document.getElementById('category').value;
	if(cat.length<=1)
		cat = "Category";
	document.getElementById('previewCategory').innerHTML = cat;
	
	var tags = document.getElementById('tags').value;
	// chop off [ ] if they were added
	if(tags.startsWith('['))
		tags = tags.substring(1);
	if(tags.endsWith(']'))
		tags = tags.substring(0,tags.length-1);
	var tagArray = tags.split(', ');
	var tagHtml = "";
	for (var i = 0; i < tagArray.length; i++) {
		if(tagArray[i].length>0)
		tagHtml += "<a class=\"w3-text-theme\" href=\"#\">"+tagArray[i]+"</a>&nbsp;";
	}
	if(tagHtml<=1)
		tagHtml = "<a class=\"w3-text-theme\" href=\"#\">Tag</a>&nbsp;";
	document.getElementById('previewTags').innerHTML = tagHtml;
	
	var usedTagHtml = "";
	for (var i = 0; i < usedTags.length && i < 15; i++) {
		if(usedTags[i].length > 1 && tagArray.indexOf(usedTags[i])<0)
			usedTagHtml += "<a class=\"tag w3-round w3-theme w3-hover-light-grey w3-hover-shadow\" href=\"javascript:void(0)\" onclick=\"addTag('"+usedTags[i]+"')\">"+usedTags[i]+"</a>&nbsp;";
	}
	if(usedTagHtml<=1)
		usedTagHtml = "No suggested tags found.";
	document.getElementById('previewUsedTags').innerHTML = usedTagHtml;
	
	var src = document.getElementById('thumbnail').value;
	if(src.length<=1)
		src = "/img/placeholder-640.png";
	document.getElementById('previewImg').src = src;
	changeForm();
}
</script>
</head>
<body class="w3-theme-dark">

	<%@include file="/WEB-INF/fragment/header.jspf"%>
	
	<article class="w3-theme-light">
		<div class="page w3-row">
		
			<%@include file="/WEB-INF/fragment/tabs/tabs.jspf"%>
		
			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">
				
				<h1>Create New Post</h1>
		
				<div class="w3-container w3-padding-0 w3-border-0">
					<form action="/manage/newpost" method="post">
					<input type="hidden" name="submitForm" value="true" />
					
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="title">Post Title:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="title" id="title" value="<s:property value="title" />" onkeyup="makeUri()" onkeypress="preview()" onchange="preview()" required placeholder="How to make a blog post!" class="w3-input w3-round-large  w3-border" />
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="uriName">URI:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: This must be lowercase and unique!)</span></label>
						<input type="text" size="50" maxlength="300" name="uriName" id="uriName" value="<s:property value="uriName" />" required placeholder="how-to-make-a-blog-post" class="w3-input w3-round-large  w3-border" />
						<a href="#" class="w3-medium"><%=Application.getSetting("url")%>/blog/<s:property value="uriName" /></a>
					</p>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="description">Description:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="300" name="description" id="description" value="<s:property value="description" />" onkeyup="preview()" onchange="preview()" placeholder="A quick description for RSS and social media..." class="w3-input w3-round-large  w3-border" />
					</p>
					<div class="w3-row">
					<p class="w3-col m12 l4 w3-padding-right">
						<label class="w3-validate w3-text-grey-light w3-large" for="category">Category:&nbsp;<span class="w3-small w3-text-grey quote">(Note: Only one.)</span></label>
						<input type="text" size="50" maxlength="100" name="category" id="category" value="<s:property value="category" />" onkeyup="preview()" onchange="preview()" placeholder="Advice / Code / Other ..." class="w3-input w3-round-large  w3-border" />
						<span class="w3-small w3-text-grey">Suggested:</span>&nbsp;<span id="previewUsedCategories" class="w3-small"></span>
					</p>
					<p class="w3-col m12 l8">
						<label class="w3-validate w3-text-grey-light w3-large" for="tags">Tags:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: Separated by commas.)</span></label>
						<input type="text" size="50" maxlength="200" name="tags" id="tags" value="<s:property value="tags" />" onkeyup="preview()" onchange="preview()" required placeholder="java, interview, funny" class="w3-input w3-round-large  w3-border" />
						<span class="w3-small w3-text-grey">Suggested:</span>&nbsp;<span id="previewUsedTags" class="w3-small"></span>
					</p>
					</div>
					<p>   
						<label class="w3-validate w3-text-grey-light w3-large" for="thumbnail">Thumbnail Image URL:</label>
						<input type="text" size="50" maxlength="200" name="thumbnail" id="thumbnail" value="<s:property value="thumbnail" />" onchange="preview()" placeholder="https://example.com/image-640x420.png" class="w3-input w3-round-large  w3-border" />
					</p>							
					
					<h3>Social Card Preview</h3>
					<div class="w3-container w3-round w3-border w3-card w3-hover-shadow w3-padding-0">
						
						<div class="w3-container w3-round-large w3-col s12 m3 l4 w3-padding-0 w3-center w3-theme-l4" style="overflow: hidden;">
							<a href="#">
							<img id="previewImg" class="thumbnail" src="<s:property value="thumbnail" />" alt="Thumbnail" title="Thumbnail" onerror="this.src='/img/error-640.png';this.title='Failed to load image.'" />
							</a>
						</div>
												
						<div class="w3-container w3-round w3-col s12 m9 l8 w3-padding-16">
						<h3 class="w3-padding-0 w3-margin-0"><a id="previewTitle" href="#"><s:property value="title" /></a></h3>
						<p id="previewDesc" class="w3-small w3-margin-0"><s:property value="description" /></p>
						
						<p class="w3-small w3-text-theme w3-padding-top">
							<s:if test="#session.USER.thumbnail != null && !#session.USER.thumbnail.trim().isEmpty()">
								<span class="w3-padding-square"><img class="w3-round" alt="Author" title="Author" style="vertical-align: middle;" src="<s:property value="#session.USER.thumbnail" />" height="16" width="16"></span>
							</s:if>
							<s:else>
								<span class="icon-author w3-medium w3-text-theme w3-padding-square" title="Author"></span>
							</s:else>
							<a href="#" title="Author" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"><s:property value="#session.USER.name" /></a>
							
							<span class="icon-time w3-medium w3-text-theme w3-padding-square" title="Publish Date"></span>
							<a href="#" title="Date Published" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"><%=Utils.formatReadableDate(new java.util.Date(System.currentTimeMillis())) %></a>
						</p>
						<p class="w3-small w3-text-theme">
							<span class="icon-folder w3-medium w3-text-theme w3-padding-square" title="Category"></span>
							<a id="previewCategory" href="#" title="Category" class="w3-text-theme" style="vertical-align: middle; white-space:nowrap;"></a>
							
							<span class="icon-tag w3-medium w3-text-theme w3-padding-square" title="Tags"></span>
							<span id="previewTags"><a class="w3-text-theme" href="#">tag</a>&nbsp;</span>
							<span class="icon-eye w3-medium w3-text-theme w3-padding-square" title="Views"></span><span title="Views" class="w3-text-theme">0</span>
							<span class="icon-comments w3-medium w3-text-theme w3-padding-square" title="Comments"></span><a class="w3-small w3-text-grey" href="#comments">0</a>
						</p>
						
						</div>
					</div>
					
					<br />
					<hr />
					
					<p>
						<input type="checkbox" name="hasBanner" id="hasBanner" class="w3-check" onchange="changeForm()" value="true" />
						<label class="w3-validate w3-text-grey-light w3-large" for="hasBanner">Add a banner image?&nbsp;<span class="w3-small w3-text-grey quote">(A large image above the title of the post.)</span></label>
					</p>
					<p id="bannerDiv1" style="display:none" class="w3-animate-right">   
						<label class="w3-validate w3-text-grey-light w3-large" for="banner">Banner Image URL:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" disabled="disabled" size="50" maxlength="200" name="banner" id="banner" value="<s:property value="banner" />" placeholder="https://example.com/image-1024x680.png" class="w3-input w3-round-large  w3-border" />
					</p>
					<p id="bannerDiv2" style="display:none" class="w3-animate-right">
						<label class="w3-validate w3-text-grey-light w3-large" for="bannerCaption">Banner Caption/Credit:&nbsp;</label>
						<input type="text" disabled="disabled" size="50" maxlength="200" name="bannerCaption" id="bannerCaption" value="<s:property value="bannerCaption" />" placeholder="Image Source: Example.com" class="w3-input w3-round-large  w3-border" />
					</p>
					<h2 id="previewTitle2">Post Title</h2>
					<p>
						<label class="w3-validate w3-text-grey-light w3-large" for="htmlContent">Post Content:&nbsp;<span class="w3-text-red">*</span>&nbsp;<span class="w3-small w3-text-grey quote">(Note: Max 12288 chars.)</span></label>
						<textarea name="htmlContent" id="htmlContent" rows="10" cols="100" maxlength="12288" style="width:100%"><s:property value="htmlContent" /></textarea>
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
					
					<div class="w3-row">
					<p class="w3-col m12 l4 w3-padding-right">
						<label class="w3-validate w3-text-grey-light w3-large" for="publishDate">Publish Date:&nbsp;<span class="w3-text-red">*</span></label>
						<input type="text" size="50" maxlength="20" name="publishDate" id="publishDate" value="<s:property value="publishDateReadable" />" required placeholder="MM-DD-YYYY" class="w3-input w3-round-large w3-border" />
					</p>
					<p class="w3-col m12 l8">
						<br>
						<label class="w3-text-theme w3-large" for="createDate">Create Date:&nbsp;<span class="w3-small w3-text-grey quote"></span></label>
						<span title="<s:property value="#request.post.createDate" />" class="w3-text-theme">Today</span>
						<br>
						<label class="w3-text-theme w3-large" for="modifyDate">Modify Date:&nbsp;<span class="w3-small w3-text-grey quote"></span></label>
						<span title="<s:property value="#request.post.modifyDate" />" class="w3-text-theme">&nbsp;</span>
					</p>
					</div>
					<p>
						<input type="checkbox" name="visible" id="visible" class="w3-check" value="true" />
						<label class="w3-validate w3-text-grey-light w3-large" for="visible">Make this post publicly visible?&nbsp;<span class="icon-eye w3-large w3-text-black w3-padding-square"></span><span class="w3-small w3-text-grey quote">(You can make it public later if you want.)</span></label>
					</p>
					<p>
						<input type="checkbox" name="featured" id="featured" class="w3-check" value="true" />
						<label class="w3-validate w3-text-grey-light w3-large" for="featured">Make this a "Featured" post?&nbsp;<span class="icon-star w3-large w3-text-yellow w3-padding-square"></span><span class="w3-small w3-text-grey quote">(Gets put on the Featured sidebar of every page.)</span></label>
					</p>
					
					<br />
					<hr />
					<button class="w3-btn w3-right w3-round w3-green w3-hover-teal" type="submit" title="Submit" onclick="return validate()">
						<span class="icon-checkmark w3-large w3-margin-right"></span>Submit</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="button" onclick="history.back();" title="Go back">
						<span class="icon-arrow-left w3-large w3-margin-right"></span>Back</button>
					<span>&nbsp;&nbsp;</span>
					<button class="w3-btn w3-round w3-card w3-theme-light" type="reset" onclick="return confirm('Are you sure you want to reset?')" title="Reset search fields">Clear</button>
					
					</form>
				</div>
				<script type="text/javascript" src="/vendor/moment/moment.js"></script>
				<script type="text/javascript" src="/vendor/pikaday/pikaday.js"></script>
				<script type="text/javascript" src="/vendor/highlight/highlight.pack.js"></script>
				<script>hljs.initHighlightingOnLoad();</script>
				<script>
					preview();
					var picker = new Pikaday({
				        i18n: {
						    previousMonth : 'Previous Month',
						    nextMonth     : 'Next Month',
						    months        : ['January','February','March','April','May','June','July','August','September','October','November','December'],
						    weekdays      : ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
						    weekdaysShort : ['Sun','Mon','Tue','Wed','Thu','Fri','Sat']
						},
						field: document.getElementById('publishDate'),
				        format: 'MMM D YYYY',
				        minDate: new Date(),
				        maxDate: new Date(2038, 12, 31),
				        yearRange: [2000,2038],
				        theme: 'triangle-theme'
				    });				
				</script>
			</div>
		</div>
	</article>

	<%@include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>