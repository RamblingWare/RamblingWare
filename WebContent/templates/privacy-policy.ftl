<!DOCTYPE HTML>
<html>
<head>
<#include "/templates/fragment/meta/meta.ftl">

<title>Privacy Policy - ${(name)!'Oddox'}</title>
</head>
<body class="w3-theme-dark">

	<#include "/templates/fragment/header.ftl">

	<article class="w3-theme-light">
		<div class="page w3-row">

			<#include "/templates/fragment/tabs.ftl">

			<div id="page-content" class="w3-col m8 l8 w3-container w3-padding">

				<h1><span class="w3-small w3-text-grey w3-right">June 17th, 2017</span>
				Privacy Policy</h1>
				<p>
					<span class="bold">Personal Info.</span> This website does not
					collect or disclose personal information of users. If you choose to
					share personal information in the comments or elsewhere, it is at
					your own risk.<br />
					<br /> 
					<span class="bold">Cookies.</span> This website may make
					use of cookies, but they are not necessary for browsing it.<br />
					<br /> 
					<span class="bold">JavaScript.</span> JavaScript is not necessary for browsing this site.<br />
					<br /> 
					<span class="bold">Log Files.</span> This website may
					gather certain information automatically and store it in log files.
					This information may include Internet protocol (IP) addresses,
					browser type, internet service provider (ISP), referring/exit
					pages, operating system, date/time stamp, and/or clickstream data.
					This information is not shared with third-party services, and is
					not used to identify users in any way. In fact, I personally don't
					even look at it.<br />
					<br /> 
					<span class="bold">Third-Parties.</span> Because this site
					is hosted or using third-party services, its my recommendation to
					read each of their privacy policies as well. <br />
				</p>
				<ul>
					<li>IBM Cloud <a target="_Blank" href="http://www.ibm.com/privacy">Privacy Policy</a></li>
					<li>Cloudflare <a target="_Blank" href="https://www.cloudflare.com/security-policy/">Privacy &amp; Security Policy</a></li>
					<li>BackBlaze <a target="_Blank" href="https://www.backblaze.com/company/privacy.html">Privacy Policy</a></li>
				</ul>
				<p>
					<span class="bold">Changes to Privacy Policy.</span> We feel we may
					not change this policy going forward, however if we needed to, we
					may change this policy at any time, without notice. The modified
					Policy will be posted here along with the effective date of any
					necessary edits.<br />
					<br />
					<span class="bold">Effective Date.</span> June 18th, 2017<br />
					<br />
					<span class="bold">Contact.</span> ${(name)!'Oddox'} <a href="mailto:${(email)!''}">${(email)!''}</a>
				</p>
				
				<br />
				<hr />
				
				<p>
					Please be sure to read our <a href="/terms-of-use">Terms of use</a> as well.
				</p>

			</div>

			<#include "/templates/fragment/archive.ftl">
		</div>
	</article>

	<#include "/templates/fragment/footer.ftl">
</body>
</html>