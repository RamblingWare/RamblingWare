<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!-- META_BEGIN -->
<%@include file="/WEB-INF/fragment/meta.jspf"%>
<!-- META_END -->
<title>About - RamblingWare</title>
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
			
			<div id="page-content" class="w3-col m8 w3-container w3-border w3-padding w3-card-2">
							
				<h1><font class="footnote w3-right">Mar 12th, 2014</font>
				About</h1>
				
				<div class="w3-container w3-padding">
				<p>
					<img class="w3-round w3-card-4 w3-margin-right w3-margin-bottom w3-left" style="max-height: 200px" alt="Profile Picture" src="/img/author/AustinDelamar.jpg">
					
					<span class="bold">Code.&nbsp;Test.&nbsp;Refract.&nbsp;<span class="footnote">(Repeat...)</span></span>
					<br />
					<br />
					These daily activities for programmers are my favorite things about developing software. Like painting on a canvas, or writing a book, 
					developing software is the process of careful planning, execution, and evaluation. My passion is in providing that level of detailed 
					work to clients, and helping automate their business needs through computer software.<br />
					<br />
					My name is Austin Delamar, and I am a programmer, 
					web developer, and all-around computer enthusiast.
					<br />
					
					<br />
				
					Born in October 1990; I grew up in Zeeland, Michigan (USA), with two brothers and one sister. In High School, I played a lot of calculator 
					games on my Ti-83 and TI-89. That's when I first realized I enjoyed tinkering with electronics, and ultimately, computers. 
					At the time, I didn't know it would lead me to this career path. I graduated in 2009, and had completed multiple Advanced Placement courses,
					that would give me a head start in college.
					<br /><br />
					In Fall of 2009, I attended Grand Valley State University, and started taking courses aligned with a Bachelor's degree in Engineering.
					After two semesters, I quickly changed majors, into Computer Science. I knew where I excelled in, but it just wasn't engineering. The
					courses offered for Comp Sci majors highly interested me, such as, UX Design, Linux Security, Mobile development, Structures and Logic,
					and more. I loved taking the C programming class and UX Design. They were well-defining classes that shaped my thinking.
					In December 2012, I graduated early and frantically applied for jobs.
					<br /><br />
					Currently I work at IBM in East Lansing, Michigan, as a Developer. I love what I do, and look forward to whatevers next!
				</p>
				</div>
				<br />		
				<div class="w3-padding w3-container w3-small">
					<div class="w3-third w3-padding-8">
						<h3>I specialize in:</h3>
						<ul>
							<li>Java EE Programming</li>
							<li>WebSphere Application Server</li>
							<li>DB2 Database</li>
						</ul>
					</div>
					<div class="w3-third w3-padding-8">
						<h3>Favorite Quote:</h3>
						<p class="quote">"Efficiency is doing things right;<br />effectiveness is doing the right things."<br /><br />-&nbsp;Peter Drucker</p>
					</div>
					<div class="w3-third w3-padding-8">
						<h3>Most Recent Work:</h3>
						<p>I'm currently supporting multiple J2EE web applications as the lead developer, at IBM.</p>
					</div>
				</div>
				
				<div class="w3-container">
					<h3>Contact</h3>
					<p>You can reach me by:<br />
					<button class="w3-btn w3-round w3-theme-dark" onclick="window.location.href='mailto:austin.delamar@gmail.com'">Email</button> 
					&nbsp;&nbsp;
					<button class="w3-btn w3-round w3-blue" onclick="window.location.href='https://www.linkedin.com/in/austindelamar'">LinkedIn</button>
					&nbsp;&nbsp;
					<button class="w3-btn w3-round w3-light-blue" onclick="window.location.href='https://twitter.com/AustinDelamar'">Twitter</button>
					</p>
				</div>
				<br />
				<br />
				<div class="w3-container">
					
					<p class="footnote quote">Any opinions expressed here are solely my own, and do not express the views or opinions of any current or previous employer.</p>
					
				</div>
				<%! int hits = 1; %>
				<!-- About JSP Hits: <%=hits++ %>  -->
				<br />
			</div>
			
			<!-- ARCHIVE BEGIN -->
			<%@include file="/WEB-INF/fragment/archive.jspf" %>
			<!-- ARCHIVE END -->
			
		</div>
	</article>

	<!-- FOOTER_BEGIN -->
	<%@include file="/WEB-INF/fragment/footer.jspf"%>
	<!-- FOOTER_END -->
	
</body>
</html>