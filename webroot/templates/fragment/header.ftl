<nav id="sidebarMenu" class="sidebar shadow-right">
    <div class="nav-item-close">
        <a class="float-right" title="Close Menu" href="javascript:closeSidebar()" aria-label="Close">
            <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"><path d="M2.757 14.657L8 9.414l5.243 5.243 1.414-1.414L9.414 8l5.243-5.243-1.414-1.414L8 6.586 2.757 1.343 1.343 2.757 6.586 8l-5.243 5.243z" fill-rule="evenodd"></path></svg>
        </a>
    </div>
    <div class="nav-list text-center" onclick="closeSidebar()">
        <a class="nav-item" href="/blog/">Home</a>
        <br/>
        <a class="nav-item" href="/blog/author/">Authors</a>
		<a class="nav-item" href="/blog/search">Search</a>
		<br/>
		<a class="nav-item" href="/blog/year/">Years</a>
		<a class="nav-item" href="/blog/category/">Categories</a>
		<a class="nav-item" href="/blog/tag/">Tags</a>
		<hr/>
		<a class="nav-item text-tertiary" target="_blank" href="https://amdelamar.com/oddox-website/" title="Oddox">Powered by Oddox</a>
    </div>
</nav>
<nav class="navbar border-bottom">
    <div class="navbar-body container">
        <div class="nav-item-logo">
            <a href="/blog/"><span class="text-medium text-uppercase text-wide">${(context.name)!'Oddox'}</span></a>
        </div>
        <div class="nav-small-menu">
            <a class="nav-item" title="Open Menu" href="javascript:openSidebar()">
                <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"><path d="M0 7h16v2H0V7zm0-6h16v2H0V1zm0 12h16v2H0v-2z" fill-rule="evenodd"></path></svg>
            </a>
        </div>
        <div class="nav-group nav-large-menu text-bold">
            <form action="/blog/search" method="post" class="nav-item">
				<i class="icon-search padding"></i>
				<input id="searchHead" name="q" maxlength="50" placeholder="Search..." style="max-height:28px;" type="text">
			</form>
        </div>
    </div>
</nav>
