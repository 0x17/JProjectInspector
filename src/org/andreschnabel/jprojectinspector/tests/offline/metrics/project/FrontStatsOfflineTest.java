package org.andreschnabel.jprojectinspector.tests.offline.metrics.project;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.metrics.project.FrontStats;
import org.junit.Test;

public class FrontStatsOfflineTest {

	@Test
	public void testGetNumberOfPullRequests() throws Exception {
		Assert.assertEquals(0, FrontStats.getNumberOfPullRequests(thisProjHtml));
	}

	@Test
	public void testGetNumberOfIssues() throws Exception {
		Assert.assertEquals(0, FrontStats.getNumberOfIssues(thisProjHtml));
	}

	@Test
	public void testGetNumberOfStars() throws Exception {
		Assert.assertEquals(0, FrontStats.getNumberOfStars(thisProjHtml));
	}

	@Test
	public void testGetNumberOfCommits() throws Exception {
		Assert.assertTrue(162 < FrontStats.getNumberOfCommits(thisProjHtml));
	}

	@Test
	public void testGetNumberOfBranches() throws Exception {
		Assert.assertEquals(1, FrontStats.getNumberOfBranches(thisProjHtml));
	}

	@Test
	public void testGetNumberOfForks() throws Exception {
		Assert.assertEquals(0, FrontStats.getNumberOfForks(thisProjHtml));
	}

	private final static String thisProjHtml = "\n" +
			"  \n" +
			"\n" +
			"\n" +
			"<!DOCTYPE html>\n" +
			"<html>\n" +
			"  <head prefix=\"og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# githubog: http://ogp.me/ns/fb/githubog#\">\n" +
			"    <meta charset='utf-8'>\n" +
			"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
			"        <title>0x17/JProjectInspector</title>\n" +
			"    <link rel=\"search\" type=\"application/opensearchdescription+xml\" href=\"/opensearch.xml\" title=\"GitHub\" />\n" +
			"    <link rel=\"fluid-icon\" href=\"https://github.com/fluidicon.png\" title=\"GitHub\" />\n" +
			"    <link rel=\"apple-touch-icon\" sizes=\"57x57\" href=\"/apple-touch-icon-114.png\" />\n" +
			"    <link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"/apple-touch-icon-114.png\" />\n" +
			"    <link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"/apple-touch-icon-144.png\" />\n" +
			"    <link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"/apple-touch-icon-144.png\" />\n" +
			"    <link rel=\"logo\" type=\"image/svg\" href=\"http://github-media-downloads.s3.amazonaws.com/github-logo.svg\" />\n" +
			"    <link rel=\"xhr-socket\" href=\"/_sockets\">\n" +
			"    <meta name=\"msapplication-TileImage\" content=\"/windows-tile.png\">\n" +
			"    <meta name=\"msapplication-TileColor\" content=\"#ffffff\">\n" +
			"\n" +
			"    \n" +
			"    \n" +
			"    <link rel=\"icon\" type=\"image/x-icon\" href=\"/favicon.ico\" />\n" +
			"\n" +
			"    <meta content=\"authenticity_token\" name=\"csrf-param\" />\n" +
			"<meta content=\"YoYISyh+VnNIou7HXPCJ4LuKrXaoFErL+W/nmcGgkPM=\" name=\"csrf-token\" />\n" +
			"\n" +
			"    <link href=\"https://a248.e.akamai.net/assets.github.com/assets/github-163fe15b781cac15ef5f7ebda7f3b471e514caed.css\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"    <link href=\"https://a248.e.akamai.net/assets.github.com/assets/github2-3f38effb52355c191d32c7d619457584b9f50394.css\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />\n" +
			"    \n" +
			"\n" +
			"\n" +
			"      <script src=\"https://a248.e.akamai.net/assets.github.com/assets/frameworks-010d500708696b4ecee44478b5229d626367e844.js\" type=\"text/javascript\"></script>\n" +
			"      <script src=\"https://a248.e.akamai.net/assets.github.com/assets/github-bae4ac31b049def91ae4dd91cb45e96b205ab800.js\" type=\"text/javascript\"></script>\n" +
			"      \n" +
			"      <meta http-equiv=\"x-pjax-version\" content=\"4a86bf050b3c00684cfb9af6ad7de6bb\">\n" +
			"\n" +
			"        <link data-pjax-transient rel='permalink' href='/0x17/JProjectInspector/tree/ea055852dc2db937ecc47cbeb914f2654c2e099f'>\n" +
			"    <meta property=\"og:title\" content=\"JProjectInspector\"/>\n" +
			"    <meta property=\"og:type\" content=\"githubog:gitrepository\"/>\n" +
			"    <meta property=\"og:url\" content=\"https://github.com/0x17/JProjectInspector\"/>\n" +
			"    <meta property=\"og:image\" content=\"https://secure.gravatar.com/avatar/e66e9d87171fd68ef38753f643c68c27?s=420&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\"/>\n" +
			"    <meta property=\"og:site_name\" content=\"GitHub\"/>\n" +
			"    <meta property=\"og:description\" content=\"JProjectInspector - Gather data from GitHub projects to determine testing need.\"/>\n" +
			"    <meta property=\"twitter:card\" content=\"summary\"/>\n" +
			"    <meta property=\"twitter:site\" content=\"@GitHub\">\n" +
			"    <meta property=\"twitter:title\" content=\"0x17/JProjectInspector\"/>\n" +
			"\n" +
			"    <meta name=\"description\" content=\"JProjectInspector - Gather data from GitHub projects to determine testing need.\" />\n" +
			"\n" +
			"  <link href=\"https://github.com/0x17/JProjectInspector/commits/master.atom\" rel=\"alternate\" title=\"Recent Commits to JProjectInspector:master\" type=\"application/atom+xml\" />\n" +
			"\n" +
			"  </head>\n" +
			"\n" +
			"\n" +
			"  <body class=\"logged_in  windows vis-public env-production  \">\n" +
			"    <div id=\"wrapper\">\n" +
			"\n" +
			"      \n" +
			"\n" +
			"      \n" +
			"      \n" +
			"      \n" +
			"\n" +
			"      <div class=\"header header-logged-in true\">\n" +
			"  <div class=\"container clearfix\">\n" +
			"\n" +
			"    <a class=\"header-logo-blacktocat\" href=\"https://github.com/\">\n" +
			"  <span class=\"mega-icon mega-icon-blacktocat\"></span>\n" +
			"</a>\n" +
			"\n" +
			"    <div class=\"divider-vertical\"></div>\n" +
			"\n" +
			"      <a href=\"/notifications\" class=\"notification-indicator tooltipped downwards\" title=\"You have no unread notifications\">\n" +
			"    <span class=\"mail-status all-read\"></span>\n" +
			"  </a>\n" +
			"  <div class=\"divider-vertical\"></div>\n" +
			"\n" +
			"\n" +
			"      <div class=\"command-bar js-command-bar  \">\n" +
			"            <form accept-charset=\"UTF-8\" action=\"/search\" class=\"command-bar-form\" id=\"top_search_form\" method=\"get\">\n" +
			"  <a href=\"/search/advanced\" class=\"advanced-search-icon tooltipped downwards command-bar-search\" id=\"advanced_search\" title=\"Advanced search\"><span class=\"mini-icon mini-icon-advanced-search \"></span></a>\n" +
			"\n" +
			"  <input type=\"text\" data-hotkey=\"/ s\" name=\"q\" id=\"js-command-bar-field\" placeholder=\"Search or type a command\" tabindex=\"1\" data-username=\"0x17\" autocapitalize=\"off\">\n" +
			"\n" +
			"  <span class=\"mini-icon help tooltipped downwards\" title=\"Show command bar help\">\n" +
			"    <span class=\"mini-icon mini-icon-help\"></span>\n" +
			"  </span>\n" +
			"\n" +
			"  <input type=\"hidden\" name=\"ref\" value=\"cmdform\">\n" +
			"\n" +
			"    <input type=\"hidden\" class=\"js-repository-name-with-owner\" value=\"0x17/JProjectInspector\"/>\n" +
			"    <input type=\"hidden\" class=\"js-repository-branch\" value=\"master\"/>\n" +
			"    <input type=\"hidden\" class=\"js-repository-tree-sha\" value=\"13c5d721d5cd9fa81e0288cfd7a7bbaa5c5dc490\"/>\n" +
			"\n" +
			"  <div class=\"divider-vertical\"></div>\n" +
			"</form>\n" +
			"        <ul class=\"top-nav\">\n" +
			"            <li class=\"explore\"><a href=\"https://github.com/explore\">Explore</a></li>\n" +
			"            <li><a href=\"https://gist.github.com\">Gist</a></li>\n" +
			"            <li><a href=\"/blog\">Blog</a></li>\n" +
			"          <li><a href=\"http://help.github.com\">Help</a></li>\n" +
			"        </ul>\n" +
			"      </div>\n" +
			"\n" +
			"    \n" +
			"\n" +
			"  \n" +
			"\n" +
			"    <ul id=\"user-links\">\n" +
			"      <li>\n" +
			"        <a href=\"https://github.com/0x17\" class=\"name\">\n" +
			"          <img height=\"20\" src=\"https://secure.gravatar.com/avatar/e66e9d87171fd68ef38753f643c68c27?s=140&amp;d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\" width=\"20\" /> 0x17\n" +
			"        </a>\n" +
			"      </li>\n" +
			"\n" +
			"        <li>\n" +
			"          <a href=\"/new\" id=\"new_repo\" class=\"tooltipped downwards\" title=\"Create a new repo\">\n" +
			"            <span class=\"mini-icon mini-icon-create\"></span>\n" +
			"          </a>\n" +
			"        </li>\n" +
			"\n" +
			"        <li>\n" +
			"          <a href=\"/settings/profile\" id=\"account_settings\"\n" +
			"            class=\"tooltipped downwards\"\n" +
			"            title=\"Account settings \">\n" +
			"            <span class=\"mini-icon mini-icon-account-settings\"></span>\n" +
			"          </a>\n" +
			"        </li>\n" +
			"        <li>\n" +
			"          <a class=\"tooltipped downwards\" href=\"/logout\" data-method=\"post\" id=\"logout\" title=\"Sign out\">\n" +
			"            <span class=\"mini-icon mini-icon-logout\"></span>\n" +
			"          </a>\n" +
			"        </li>\n" +
			"\n" +
			"    </ul>\n" +
			"\n" +
			"\n" +
			"<div class=\"js-new-dropdown-contents hidden\">\n" +
			"  <ul class=\"dropdown-menu\">\n" +
			"    <li>\n" +
			"      <a href=\"/new\"><span class=\"mini-icon mini-icon-create\"></span> New repository</a>\n" +
			"    </li>\n" +
			"    <li>\n" +
			"        <a href=\"https://github.com/0x17/JProjectInspector/issues/new\"><span class=\"mini-icon mini-icon-issue-opened\"></span> New issue</a>\n" +
			"    </li>\n" +
			"    <li>\n" +
			"    </li>\n" +
			"    <li>\n" +
			"      <a href=\"/organizations/new\"><span class=\"mini-icon mini-icon-u-list\"></span> New organization</a>\n" +
			"    </li>\n" +
			"  </ul>\n" +
			"</div>\n" +
			"\n" +
			"\n" +
			"    \n" +
			"  </div>\n" +
			"</div>\n" +
			"\n" +
			"      \n" +
			"\n" +
			"      \n" +
			"\n" +
			"      \n" +
			"\n" +
			"\n" +
			"            <div class=\"site hfeed\" itemscope itemtype=\"http://schema.org/WebPage\">\n" +
			"      <div class=\"hentry\">\n" +
			"        \n" +
			"        <div class=\"pagehead repohead instapaper_ignore readability-menu \">\n" +
			"          <div class=\"container\">\n" +
			"            <div class=\"title-actions-bar\">\n" +
			"              \n" +
			"\n" +
			"\n" +
			"<ul class=\"pagehead-actions\">\n" +
			"\n" +
			"    <li class=\"nspr\">\n" +
			"      <a href=\"/0x17/JProjectInspector/pull/new/master\" class=\"button minibutton btn-pull-request\" icon_class=\"mini-icon-pull-request\"><span class=\"mini-icon mini-icon-pull-request\"></span>Pull Request</a>\n" +
			"    </li>\n" +
			"\n" +
			"    <li class=\"subscription\">\n" +
			"      <form accept-charset=\"UTF-8\" action=\"/notifications/subscribe\" data-autosubmit=\"true\" data-remote=\"true\" method=\"post\"><div style=\"margin:0;padding:0;display:inline\"><input name=\"authenticity_token\" type=\"hidden\" value=\"YoYISyh+VnNIou7HXPCJ4LuKrXaoFErL+W/nmcGgkPM=\" /></div>  <input id=\"repository_id\" name=\"repository_id\" type=\"hidden\" value=\"6873344\" />\n" +
			"\n" +
			"    <div class=\"select-menu js-menu-container js-select-menu\">\n" +
			"      <span class=\"minibutton select-menu-button js-menu-target\">\n" +
			"        <span class=\"js-select-button\">\n" +
			"          <span class=\"mini-icon mini-icon-unwatch\"></span>\n" +
			"          Unwatch\n" +
			"        </span>\n" +
			"      </span>\n" +
			"\n" +
			"      <div class=\"select-menu-modal-holder js-menu-content\">\n" +
			"        <div class=\"select-menu-modal\">\n" +
			"          <div class=\"select-menu-header\">\n" +
			"            <span class=\"select-menu-title\">Notification status</span>\n" +
			"            <span class=\"mini-icon mini-icon-remove-close js-menu-close\"></span>\n" +
			"          </div> <!-- /.select-menu-header -->\n" +
			"\n" +
			"          <div class=\"select-menu-list js-navigation-container\">\n" +
			"\n" +
			"            <div class=\"select-menu-item js-navigation-item js-navigation-target \">\n" +
			"              <span class=\"select-menu-item-icon mini-icon mini-icon-confirm\"></span>\n" +
			"              <div class=\"select-menu-item-text\">\n" +
			"                <input id=\"do_included\" name=\"do\" type=\"radio\" value=\"included\" />\n" +
			"                <h4>Not watching</h4>\n" +
			"                <span class=\"description\">You only receive notifications for discussions in which you participate or are @mentioned.</span>\n" +
			"                <span class=\"js-select-button-text hidden-select-button-text\">\n" +
			"                  <span class=\"mini-icon mini-icon-watching\"></span>\n" +
			"                  Watch\n" +
			"                </span>\n" +
			"              </div>\n" +
			"            </div> <!-- /.select-menu-item -->\n" +
			"\n" +
			"            <div class=\"select-menu-item js-navigation-item js-navigation-target selected\">\n" +
			"              <span class=\"select-menu-item-icon mini-icon mini-icon-confirm\"></span>\n" +
			"              <div class=\"select-menu-item-text\">\n" +
			"                <input checked=\"checked\" id=\"do_subscribed\" name=\"do\" type=\"radio\" value=\"subscribed\" />\n" +
			"                <h4>Watching</h4>\n" +
			"                <span class=\"description\">You receive notifications for all discussions in this repository.</span>\n" +
			"                <span class=\"js-select-button-text hidden-select-button-text\">\n" +
			"                  <span class=\"mini-icon mini-icon-unwatch\"></span>\n" +
			"                  Unwatch\n" +
			"                </span>\n" +
			"              </div>\n" +
			"            </div> <!-- /.select-menu-item -->\n" +
			"\n" +
			"            <div class=\"select-menu-item js-navigation-item js-navigation-target \">\n" +
			"              <span class=\"select-menu-item-icon mini-icon mini-icon-confirm\"></span>\n" +
			"              <div class=\"select-menu-item-text\">\n" +
			"                <input id=\"do_ignore\" name=\"do\" type=\"radio\" value=\"ignore\" />\n" +
			"                <h4>Ignoring</h4>\n" +
			"                <span class=\"description\">You do not receive any notifications for discussions in this repository.</span>\n" +
			"                <span class=\"js-select-button-text hidden-select-button-text\">\n" +
			"                  <span class=\"mini-icon mini-icon-mute\"></span>\n" +
			"                  Stop ignoring\n" +
			"                </span>\n" +
			"              </div>\n" +
			"            </div> <!-- /.select-menu-item -->\n" +
			"\n" +
			"          </div> <!-- /.select-menu-list -->\n" +
			"\n" +
			"        </div> <!-- /.select-menu-modal -->\n" +
			"      </div> <!-- /.select-menu-modal-holder -->\n" +
			"    </div> <!-- /.select-menu -->\n" +
			"\n" +
			"</form>\n" +
			"    </li>\n" +
			"\n" +
			"    <li class=\"js-toggler-container js-social-container starring-container \">\n" +
			"      <a href=\"/0x17/JProjectInspector/unstar\" class=\"minibutton js-toggler-target star-button starred upwards\" title=\"Unstar this repo\" data-remote=\"true\" data-method=\"post\" rel=\"nofollow\">\n" +
			"        <span class=\"mini-icon mini-icon-remove-star\"></span>\n" +
			"        <span class=\"text\">Unstar</span>\n" +
			"      </a>\n" +
			"      <a href=\"/0x17/JProjectInspector/star\" class=\"minibutton js-toggler-target star-button unstarred upwards\" title=\"Star this repo\" data-remote=\"true\" data-method=\"post\" rel=\"nofollow\">\n" +
			"        <span class=\"mini-icon mini-icon-star\"></span>\n" +
			"        <span class=\"text\">Star</span>\n" +
			"      </a>\n" +
			"      <a class=\"social-count js-social-count\" href=\"/0x17/JProjectInspector/stargazers\">0</a>\n" +
			"    </li>\n" +
			"\n" +
			"        <li>\n" +
			"          <a href=\"/0x17/JProjectInspector/fork\" class=\"minibutton js-toggler-target fork-button lighter upwards\" title=\"Fork this repo\" rel=\"nofollow\" data-method=\"post\">\n" +
			"            <span class=\"mini-icon mini-icon-branch-create\"></span>\n" +
			"            <span class=\"text\">Fork</span>\n" +
			"          </a>\n" +
			"          <a href=\"/0x17/JProjectInspector/network\" class=\"social-count\">0</a>\n" +
			"        </li>\n" +
			"\n" +
			"\n" +
			"</ul>\n" +
			"\n" +
			"              <h1 itemscope itemtype=\"http://data-vocabulary.org/Breadcrumb\" class=\"entry-title public\">\n" +
			"                <span class=\"repo-label\"><span>public</span></span>\n" +
			"                <span class=\"mega-icon mega-icon-public-repo\"></span>\n" +
			"                <span class=\"author vcard\">\n" +
			"                  <a href=\"/0x17\" class=\"url fn\" itemprop=\"url\" rel=\"author\">\n" +
			"                  <span itemprop=\"title\">0x17</span>\n" +
			"                  </a></span> /\n" +
			"                <strong><a href=\"/0x17/JProjectInspector\" class=\"js-current-repository\">JProjectInspector</a></strong>\n" +
			"              </h1>\n" +
			"            </div>\n" +
			"\n" +
			"            \n" +
			"  <ul class=\"tabs\">\n" +
			"    <li><a href=\"/0x17/JProjectInspector\" class=\"selected\" highlight=\"repo_source repo_downloads repo_commits repo_tags repo_branches\">Code</a></li>\n" +
			"    <li><a href=\"/0x17/JProjectInspector/network\" highlight=\"repo_network\">Network</a></li>\n" +
			"    <li><a href=\"/0x17/JProjectInspector/pulls\" highlight=\"repo_pulls\">Pull Requests <span class='counter'>0</span></a></li>\n" +
			"\n" +
			"      <li><a href=\"/0x17/JProjectInspector/issues\" highlight=\"repo_issues\">Issues <span class='counter'>0</span></a></li>\n" +
			"\n" +
			"      <li><a href=\"/0x17/JProjectInspector/wiki\" highlight=\"repo_wiki\">Wiki</a></li>\n" +
			"\n" +
			"\n" +
			"    <li><a href=\"/0x17/JProjectInspector/graphs\" highlight=\"repo_graphs repo_contributors\">Graphs</a></li>\n" +
			"\n" +
			"      <li>\n" +
			"        <a href=\"/0x17/JProjectInspector/settings\">Settings</a>\n" +
			"      </li>\n" +
			"\n" +
			"  </ul>\n" +
			"  \n" +
			"  <div id=\"repo_details\" class=\"metabox clearfix\n" +
			"        not-editable\">\n" +
			"      <div id=\"repo_details_loader\" class=\"context-loader\" style=\"display:none\">Sending Request&hellip;</div>\n" +
			"\n" +
			"        <div class=\"repo-desc-homepage\">\n" +
			"          \n" +
			"    <div class=\"repository-lang-stats\">\n" +
			"      <div class=\"repository-lang-stats-graph\">\n" +
			"      <span class=\"language-color\" style=\"width:81.9%; background-color:#b07219;\" itemprop=\"keywords\">Java</span><span class=\"language-color\" style=\"width:18.1%; background-color:#0298c3;\" itemprop=\"keywords\">Perl</span>\n" +
			"      </div>\n" +
			"      <ol class=\"list-tip\">\n" +
			"        <li>\n" +
			"            <a href=\"/languages/Java\">\n" +
			"              <span class=\"color-block language-color\" style=\"background-color:#b07219;\"></span>\n" +
			"              <span class=\"lang\">Java</span>\n" +
			"              <span class=\"percent\">81.9%</span>\n" +
			"            </a>\n" +
			"        </li>\n" +
			"        <li>\n" +
			"            <a href=\"/languages/Perl\">\n" +
			"              <span class=\"color-block language-color\" style=\"background-color:#0298c3;\"></span>\n" +
			"              <span class=\"lang\">Perl</span>\n" +
			"              <span class=\"percent\">18.1%</span>\n" +
			"            </a>\n" +
			"        </li>\n" +
			"      </ol>\n" +
			"    </div>\n" +
			"\n" +
			"<div id=\"repository_description\" class=\"repository-description\">\n" +
			"    <p>Gather data from GitHub projects to determine testing need.\n" +
			"      <span id=\"read_more\" style=\"display:none\">&mdash; <a href=\"#readme\">Read more</a></span>\n" +
			"    </p>\n" +
			"</div>\n" +
			"\n" +
			"\n" +
			"<div class=\"repository-homepage\" id=\"repository_homepage\">\n" +
			"  <p></p>\n" +
			"</div>\n" +
			"\n" +
			"  <button type=\"button\" class=\"minibutton edit-button js-edit-details\">Edit</button>\n" +
			"  <!-- <a href=\"#\" class=\"minibutton edit-button js-edit-details\">Edit</a> -->\n" +
			"\n" +
			"        </div>\n" +
			"\n" +
			"      <div class=\"edit-repo-desc-homepage\" style=\"display:none\">\n" +
			"        <form accept-charset=\"UTF-8\" action=\"/0x17/JProjectInspector/settings/update_meta\" id=\"js-update-repo-meta-form\" method=\"post\"><div style=\"margin:0;padding:0;display:inline\"><input name=\"_method\" type=\"hidden\" value=\"put\" /><input name=\"authenticity_token\" type=\"hidden\" value=\"YoYISyh+VnNIou7HXPCJ4LuKrXaoFErL+W/nmcGgkPM=\" /></div>\n" +
			"          <p class=\"error\" style=\"display:none\">Sorry, but there was a problem saving your changes.</p>\n" +
			"          <input type=\"text\" id=\"repository-description-field\" class=\"description-field\" name=\"repo_description\" value=\"Gather data from GitHub projects to determine testing need.\" placeholder=\"Add a description to this repository\">\n" +
			"          <input type=\"text\" id=\"repository-homepage-field\" class=\"homepage-field\" name=\"repo_homepage\" value=\"\" placeholder=\"Add a website to this repository\">\n" +
			"\n" +
			"          <div class=\"edit-repo-actions\">\n" +
			"            <button type=\"submit\" class=\"button save-button\">Save changes</button>\n" +
			"            <span class=\"cancel\">\n" +
			"              <a href=\"#\" class=\"button danger\">Cancel</a>\n" +
			"            </span>\n" +
			"          </div>\n" +
			"</form>      </div>\n" +
			"\n" +
			"      \n" +
			"<div class=\"url-box js-url-box\">\n" +
			"  <ul class=\"native-clones\">\n" +
			"      <li><a href=\"http://windows.github.com\" class=\"button minibutton \" icon_class=\"mini-icon-windows\"><span class=\"mini-icon mini-icon-windows\"></span>Clone in Windows</a></li>\n" +
			"      <li><a href=\"/0x17/JProjectInspector/archive/master.zip\" class=\"button minibutton \" icon_class=\"mini-icon-download\" rel=\"nofollow\" title=\"Download this repository as a zip file\"><span class=\"mini-icon mini-icon-download\"></span>ZIP</a>\n" +
			"  </ul>\n" +
			"\n" +
			"  <div class=\"clone-urls js-clone-urls clone-urls-windows\">\n" +
			"    <span class=\"http_clone_url clone-url-button js-clone-url-button selected\"><a href=\"https://github.com/0x17/JProjectInspector.git\" class=\"js-git-protocol-selector\" data-permission=\"Read+Write\" data-url=\"/users/set_protocol?protocol_selector=http&amp;protocol_type=push\">HTTP</a></span>\n" +
			"<span class=\"private_clone_url clone-url-button js-clone-url-button\"><a href=\"git@github.com:0x17/JProjectInspector.git\" class=\"js-git-protocol-selector\" data-permission=\"Read+Write\" data-url=\"/users/set_protocol?protocol_selector=ssh&amp;protocol_type=push\">SSH</a></span>\n" +
			"<span class=\"public_clone_url clone-url-button js-clone-url-button\"><a href=\"git://github.com/0x17/JProjectInspector.git\" class=\"js-git-protocol-selector\" data-permission=\"Read-Only\">Git Read-Only</a></span>\n" +
			"    <span class=\"clone-url\">\n" +
			"      <input type=\"text\" readonly spellcheck=\"false\" class=\"url-field js-url-field\" value=\"https://github.com/0x17/JProjectInspector.git\" >\n" +
			"    </span>\n" +
			"    <span class=\"clone-url-button\">\n" +
			"      <span class=\"js-zeroclipboard url-box-clippy zeroclipboard-button\" data-clipboard-text=\"https://github.com/0x17/JProjectInspector.git\" data-copied-hint=\"copied!\" title=\"copy to clipboard\"><span class=\"mini-icon mini-icon-clipboard\"></span></span>\n" +
			"    </span>\n" +
			"  </div>\n" +
			"  <p class=\"url-description\"><span class=\"bold js-clone-url-permission\">Read+Write</span> access</p>\n" +
			"</div>\n" +
			"\n" +
			"        </div>\n" +
			"\n" +
			"<div class=\"tabnav\">\n" +
			"\n" +
			"  <span class=\"tabnav-right\">\n" +
			"    <ul class=\"tabnav-tabs\">\n" +
			"          <li><a href=\"/0x17/JProjectInspector/tags\" class=\"tabnav-tab\" highlight=\"repo_tags\">Tags <span class=\"counter blank\">0</span></a></li>\n" +
			"    </ul>\n" +
			"    \n" +
			"  </span>\n" +
			"\n" +
			"  <div class=\"tabnav-widget scope\">\n" +
			"\n" +
			"\n" +
			"    <div class=\"select-menu js-menu-container js-select-menu js-branch-menu\">\n" +
			"      <a class=\"minibutton select-menu-button js-menu-target\" data-hotkey=\"w\" data-ref=\"master\">\n" +
			"        <span class=\"mini-icon mini-icon-branch\"></span>\n" +
			"        <i>branch:</i>\n" +
			"        <span class=\"js-select-button\">master</span>\n" +
			"      </a>\n" +
			"\n" +
			"      <div class=\"select-menu-modal-holder js-menu-content js-navigation-container\">\n" +
			"\n" +
			"        <div class=\"select-menu-modal\">\n" +
			"          <div class=\"select-menu-header\">\n" +
			"            <span class=\"select-menu-title\">Switch branches/tags</span>\n" +
			"            <span class=\"mini-icon mini-icon-remove-close js-menu-close\"></span>\n" +
			"          </div> <!-- /.select-menu-header -->\n" +
			"\n" +
			"          <div class=\"select-menu-filters\">\n" +
			"            <div class=\"select-menu-text-filter\">\n" +
			"              <input type=\"text\" id=\"commitish-filter-field\" class=\"js-filterable-field js-navigation-enable\" placeholder=\"Find or create a branch…\">\n" +
			"            </div>\n" +
			"            <div class=\"select-menu-tabs\">\n" +
			"              <ul>\n" +
			"                <li class=\"select-menu-tab\">\n" +
			"                  <a href=\"#\" data-tab-filter=\"branches\" class=\"js-select-menu-tab\">Branches</a>\n" +
			"                </li>\n" +
			"                <li class=\"select-menu-tab\">\n" +
			"                  <a href=\"#\" data-tab-filter=\"tags\" class=\"js-select-menu-tab\">Tags</a>\n" +
			"                </li>\n" +
			"              </ul>\n" +
			"            </div><!-- /.select-menu-tabs -->\n" +
			"          </div><!-- /.select-menu-filters -->\n" +
			"\n" +
			"          <div class=\"select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket css-truncate\" data-tab-filter=\"branches\">\n" +
			"\n" +
			"            <div data-filterable-for=\"commitish-filter-field\" data-filterable-type=\"substring\">\n" +
			"\n" +
			"                <div class=\"select-menu-item js-navigation-item js-navigation-target selected\">\n" +
			"                  <span class=\"select-menu-item-icon mini-icon mini-icon-confirm\"></span>\n" +
			"                  <a href=\"/0x17/JProjectInspector/tree/master\" class=\"js-navigation-open select-menu-item-text js-select-button-text css-truncate-target\" data-name=\"master\" rel=\"nofollow\" title=\"master\">master</a>\n" +
			"                </div> <!-- /.select-menu-item -->\n" +
			"            </div>\n" +
			"\n" +
			"              <form accept-charset=\"UTF-8\" action=\"/0x17/JProjectInspector/branches\" class=\"js-create-branch select-menu-item select-menu-new-item-form js-navigation-item js-navigation-target js-new-item-form\" method=\"post\"><div style=\"margin:0;padding:0;display:inline\"><input name=\"authenticity_token\" type=\"hidden\" value=\"YoYISyh+VnNIou7HXPCJ4LuKrXaoFErL+W/nmcGgkPM=\" /></div>\n" +
			"\n" +
			"                <span class=\"mini-icon mini-icon-branch-create select-menu-item-icon\"></span>\n" +
			"                <div class=\"select-menu-item-text\">\n" +
			"                  <h4>Create branch: <span class=\"js-new-item-name\"></span></h4>\n" +
			"                  <span class=\"description\">from ‘master’</span>\n" +
			"                </div>\n" +
			"                <input type=\"hidden\" name=\"name\" id=\"name\" class=\"js-new-item-value\">\n" +
			"                <input type=\"hidden\" name=\"branch\" id=\"branch\" value=\"master\" />\n" +
			"                <input type=\"hidden\" name=\"path\" id=\"branch\" value=\"\" />\n" +
			"              </form> <!-- /.select-menu-item -->\n" +
			"\n" +
			"          </div> <!-- /.select-menu-list -->\n" +
			"\n" +
			"\n" +
			"          <div class=\"select-menu-list select-menu-tab-bucket js-select-menu-tab-bucket css-truncate\" data-tab-filter=\"tags\">\n" +
			"            <div data-filterable-for=\"commitish-filter-field\" data-filterable-type=\"substring\">\n" +
			"\n" +
			"            </div>\n" +
			"\n" +
			"            <div class=\"select-menu-no-results\">Nothing to show</div>\n" +
			"\n" +
			"          </div> <!-- /.select-menu-list -->\n" +
			"\n" +
			"        </div> <!-- /.select-menu-modal -->\n" +
			"      </div> <!-- /.select-menu-modal-holder -->\n" +
			"    </div> <!-- /.select-menu -->\n" +
			"\n" +
			"  </div> <!-- /.scope -->\n" +
			"\n" +
			"  <ul class=\"tabnav-tabs\">\n" +
			"    <li><a href=\"/0x17/JProjectInspector\" class=\"selected tabnav-tab\" highlight=\"repo_source\">Files</a></li>\n" +
			"    <li><a href=\"/0x17/JProjectInspector/commits/master\" class=\"tabnav-tab\" highlight=\"repo_commits\">Commits</a></li>\n" +
			"    <li><a href=\"/0x17/JProjectInspector/branches\" class=\"tabnav-tab\" highlight=\"repo_branches\" rel=\"nofollow\">Branches <span class=\"counter \">1</span></a></li>\n" +
			"  </ul>\n" +
			"\n" +
			"</div>\n" +
			"\n" +
			"  \n" +
			"  \n" +
			"  \n" +
			"\n" +
			"\n" +
			"            \n" +
			"          </div>\n" +
			"        </div><!-- /.repohead -->\n" +
			"\n" +
			"        <div id=\"js-repo-pjax-container\" class=\"container context-loader-container\" data-pjax-container>\n" +
			"          \n" +
			"\n" +
			"\n" +
			"<!-- tree commit key: views10/v8/tree:v21:8cd2d61e559d544ec83bd512d4296686 -->\n" +
			"\n" +
			"  <div id=\"slider\">\n" +
			"      <div class=\"frame-meta\">\n" +
			"          \n" +
			"\n" +
			"\n" +
			"\n" +
			"          <p class=\"history-link js-history-link-replace\">\n" +
			"<a href=\"/0x17/JProjectInspector/commits/master\">                <span class=\"mini-icon mini-icon-history tooltipped\" title=\"Browse commits for this branch\"></span>\n" +
			"                163 commits\n" +
			"</a>          </p>\n" +
			"\n" +
			"        <div class=\"breadcrumb\">\n" +
			"          <span class='bold'><span itemscope=\"\" itemtype=\"http://data-vocabulary.org/Breadcrumb\"><a href=\"/0x17/JProjectInspector\" class=\"js-slide-to\" data-branch=\"master\" data-direction=\"back\" itemscope=\"url\"><span itemprop=\"title\">JProjectInspector</span></a></span></span><span class=\"separator\"> / </span><form action=\"/0x17/JProjectInspector/new/master\" class=\"js-new-blob-form tooltipped rightwards new-file-link\" method=\"post\" title=\"Create a new file here\"><span class=\"js-new-blob-submit mini-icon mini-icon-new-file\"></span></form>\n" +
			"        </div>\n" +
			"\n" +
			"        <a href=\"/0x17/JProjectInspector/find/master\"\n" +
			"           class=\"js-slide-to\" data-hotkey=\"t\" style=\"display:none\">Show File Finder</a>\n" +
			"\n" +
			"      </div><!-- ./.frame-meta -->\n" +
			"\n" +
			"      \n" +
			"\n" +
			"  <div class=\"frames\">\n" +
			"    <div class=\"frame\" data-permalink-url=\"/0x17/JProjectInspector/tree/ea055852dc2db937ecc47cbeb914f2654c2e099f\" data-title=\"0x17/JProjectInspector · GitHub\" data-type=\"tree\">\n" +
			"\n" +
			"      <div class=\"bubble tree-browser-wrapper\">\n" +
			"\n" +
			"      <table class=\"tree-browser css-truncate\" cellpadding=\"0\" cellspacing=\"0\">\n" +
			"        <thead>\n" +
			"            <div class=\"commit commit-loader commit-tease js-details-container js-deferred-content\" data-url=\"/0x17/JProjectInspector/tree-commit/master\">\n" +
			"              <p class=\"commit-title blank\">\n" +
			"                Fetching latest commit…\n" +
			"              </p>\n" +
			"              <div class=\"commit-meta\">\n" +
			"                <p class=\"loader-loading\"><img alt=\"Octocat-spinner-32-eaf2f5\" height=\"16\" src=\"https://a248.e.akamai.net/assets.github.com/images/spinners/octocat-spinner-32-EAF2F5.gif?1360648843\" width=\"16\" /></p>\n" +
			"                <p class=\"loader-error\">Cannot retrieve the latest commit at this time</p>\n" +
			"              </div>\n" +
			"            </div>\n" +
			"        </thead>\n" +
			"\n" +
			"        \n" +
			"\n" +
			"<tbody class=\"tree-entries js-deferred-content\"\n" +
			"    data-url=\"/0x17/JProjectInspector/tree-commits/master\">\n" +
			"\n" +
			"\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-directory\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/tree/master/.idea\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"95b55b1e3a304f4e340394a679893575-8a946c4e93c02787a8017560d72b6e52121fde8a\" title=\".idea\">.idea</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"\n" +
			"          <span class=\"loading\">\n" +
			"            Loading commit data...\n" +
			"            <img alt=\"Octocat-spinner-32-eaf2f5\" height=\"16\" src=\"https://a248.e.akamai.net/assets.github.com/images/spinners/octocat-spinner-32-EAF2F5.gif?1360648843\" width=\"16\" />\n" +
			"          </span>\n" +
			"          <span class=\"error\">\n" +
			"            Something went wrong.\n" +
			"            <img alt=\"Error\" src=\"https://a248.e.akamai.net/assets.github.com/images/modules/ajax/error.png?1360648843\" />\n" +
			"          </span>\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-directory\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/tree/master/.settings\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"80dbd6eeac5e4fcdc70419e17b1b45cd-1b48aae84c722a5aaeec2883f76528b96513afb4\" title=\".settings\">.settings</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-directory\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/tree/master/data\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"8d777f385d3dfec8815d20f7496026dc-d30909a7003a6b00c80e4cfa855b5b138253bf8d\" title=\"data\">data</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-directory\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/tree/master/libs\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"cb4cb2401e6e43cf74a54523b8da5f02-a655f248af7ef564c351c0766837df8bab76b955\" title=\"libs\">libs</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-directory\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/tree/master/src\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"25d902c24283ab8cfbac54dfa101ad31-df2040c459470bccd6ce391e2340707929d22bfc\" title=\"src\">src</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-directory\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/tree/master/testdata\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"ef654c40ab4f1747fc699915d4f70902-47e1f335c03d373f58f619aef0a009549345bcc1\" title=\"testdata\">testdata</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-text-file\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/blob/master/.classpath\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"8dfc6e5ce89c2ff81650de110d327112-25d00af7d9f789d0b8f99dd293842555c319f4b1\" title=\".classpath\">.classpath</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-text-file\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/blob/master/.gitignore\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"a084b794bc0759e7a6b77810e01874f2-88082badd4a688f98385ddebec39a480f0d0de1a\" title=\".gitignore\">.gitignore</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-text-file\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/blob/master/.project\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"267c7db53673cb611d5c91164fdd974b-bf8e3c7b1f6c561af36b848b22fa26ded2e7defa\" title=\".project\">.project</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-text-file\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/blob/master/JProjectInspector.iml\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"14cc86e06afaa03acb064bc209956121-b44616bea8a1bc8560882076f2b35df438bbb490\" title=\"JProjectInspector.iml\">JProjectInspector.iml</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"    <tr>\n" +
			"      <td class=\"icon\"><span class=\"mini-icon mini-icon-text-file\"></span></td>\n" +
			"      <td class=\"content\"><a href=\"/0x17/JProjectInspector/blob/master/README.md\" class=\"js-directory-link js-slide-to css-truncate-target\" id=\"04c6e90faac2675aa89e2176d2eec7d8-ec3fb535e36afb3b54d8890a9b92b5f0c11b2106\" title=\"README.md\">README.md</a></td>\n" +
			"      <td class=\"age\"></td>\n" +
			"\n" +
			"      <td class=\"message\">\n" +
			"      </td>\n" +
			"    </tr>\n" +
			"</tbody>\n" +
			"\n" +
			"      </table>\n" +
			"      </div>\n" +
			"\n" +
			"        <div id=\"readme\" class=\"clearfix announce instapaper_body md\">\n" +
			"          <span class=\"name\"><span class=\"mini-icon mini-icon-readme\"></span> README.md</span><article class=\"markdown-body entry-content\" itemprop=\"mainContentOfPage\"><h1>\n" +
			"<a name=\"jprojectinspector\" class=\"anchor\" href=\"#jprojectinspector\"><span class=\"mini-icon mini-icon-link\"></span></a>JProjectInspector</h1>\n" +
			"\n" +
			"<p>Gather data from GitHub projects to determine factors influencing testing need.</p>\n" +
			"\n" +
			"<h2>\n" +
			"<a name=\"dependencies\" class=\"anchor\" href=\"#dependencies\"><span class=\"mini-icon mini-icon-link\"></span></a>Dependencies</h2>\n" +
			"\n" +
			"<ul>\n" +
			"<li><a href=\"http://code.google.com/p/google-gson/\">gson</a></li>\n" +
			"<li><a href=\"http://www.eclipse.org/jgit/\">JGit</a></li>\n" +
			"<li><a href=\"https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core\">egit-github</a></li>\n" +
			"<li><a href=\"https://github.com/kevinsawicki/gitective\">gitective</a></li>\n" +
			"</ul></article>\n" +
			"        </div>\n" +
			"    </div>\n" +
			"  </div>\n" +
			"  <br style=\"clear:both;\">\n" +
			"\n" +
			"\n" +
			"<br style=\"clear:both;\">\n" +
			"\n" +
			"<div id=\"js-frame-loading-template\" class=\"frame frame-loading large-loading-area\" style=\"display:none;\">\n" +
			"  <img class=\"js-frame-loading-spinner\" src=\"https://a248.e.akamai.net/assets.github.com/images/spinners/octocat-spinner-128.gif?1360648843\" height=\"64\" width=\"64\">\n" +
			"</div>\n" +
			"\n" +
			"\n" +
			"  </div>\n" +
			"\n" +
			"        </div>\n" +
			"      </div>\n" +
			"      <div class=\"context-overlay\"></div>\n" +
			"    </div>\n" +
			"\n" +
			"      <div id=\"footer-push\"></div><!-- hack for sticky footer -->\n" +
			"    </div><!-- end of wrapper - hack for sticky footer -->\n" +
			"\n" +
			"      <!-- footer -->\n" +
			"      <div id=\"footer\">\n" +
			"  <div class=\"container clearfix\">\n" +
			"\n" +
			"      <dl class=\"footer_nav\">\n" +
			"        <dt>GitHub</dt>\n" +
			"        <dd><a href=\"https://github.com/about\">About us</a></dd>\n" +
			"        <dd><a href=\"https://github.com/blog\">Blog</a></dd>\n" +
			"        <dd><a href=\"https://github.com/contact\">Contact &amp; support</a></dd>\n" +
			"        <dd><a href=\"http://enterprise.github.com/\">GitHub Enterprise</a></dd>\n" +
			"        <dd><a href=\"http://status.github.com/\">Site status</a></dd>\n" +
			"      </dl>\n" +
			"\n" +
			"      <dl class=\"footer_nav\">\n" +
			"        <dt>Applications</dt>\n" +
			"        <dd><a href=\"http://mac.github.com/\">GitHub for Mac</a></dd>\n" +
			"        <dd><a href=\"http://windows.github.com/\">GitHub for Windows</a></dd>\n" +
			"        <dd><a href=\"http://eclipse.github.com/\">GitHub for Eclipse</a></dd>\n" +
			"        <dd><a href=\"http://mobile.github.com/\">GitHub mobile apps</a></dd>\n" +
			"      </dl>\n" +
			"\n" +
			"      <dl class=\"footer_nav\">\n" +
			"        <dt>Services</dt>\n" +
			"        <dd><a href=\"http://get.gaug.es/\">Gauges: Web analytics</a></dd>\n" +
			"        <dd><a href=\"http://speakerdeck.com\">Speaker Deck: Presentations</a></dd>\n" +
			"        <dd><a href=\"https://gist.github.com\">Gist: Code snippets</a></dd>\n" +
			"        <dd><a href=\"http://jobs.github.com/\">Job board</a></dd>\n" +
			"      </dl>\n" +
			"\n" +
			"      <dl class=\"footer_nav\">\n" +
			"        <dt>Documentation</dt>\n" +
			"        <dd><a href=\"http://help.github.com/\">GitHub Help</a></dd>\n" +
			"        <dd><a href=\"http://developer.github.com/\">Developer API</a></dd>\n" +
			"        <dd><a href=\"http://github.github.com/github-flavored-markdown/\">GitHub Flavored Markdown</a></dd>\n" +
			"        <dd><a href=\"http://pages.github.com/\">GitHub Pages</a></dd>\n" +
			"      </dl>\n" +
			"\n" +
			"      <dl class=\"footer_nav\">\n" +
			"        <dt>More</dt>\n" +
			"        <dd><a href=\"http://training.github.com/\">Training</a></dd>\n" +
			"        <dd><a href=\"https://github.com/edu\">Students &amp; teachers</a></dd>\n" +
			"        <dd><a href=\"http://shop.github.com\">The Shop</a></dd>\n" +
			"        <dd><a href=\"/plans\">Plans &amp; pricing</a></dd>\n" +
			"        <dd><a href=\"http://octodex.github.com/\">The Octodex</a></dd>\n" +
			"      </dl>\n" +
			"\n" +
			"      <hr class=\"footer-divider\">\n" +
			"\n" +
			"\n" +
			"    <p class=\"right\">&copy; 2013 <span title=\"0.39280s from fe3.rs.github.com\">GitHub</span>, Inc. All rights reserved.</p>\n" +
			"    <a class=\"left\" href=\"https://github.com/\">\n" +
			"      <span class=\"mega-icon mega-icon-invertocat\"></span>\n" +
			"    </a>\n" +
			"    <ul id=\"legal\">\n" +
			"        <li><a href=\"https://github.com/site/terms\">Terms of Service</a></li>\n" +
			"        <li><a href=\"https://github.com/site/privacy\">Privacy</a></li>\n" +
			"        <li><a href=\"https://github.com/security\">Security</a></li>\n" +
			"    </ul>\n" +
			"\n" +
			"  </div><!-- /.container -->\n" +
			"\n" +
			"</div><!-- /.#footer -->\n" +
			"\n" +
			"\n" +
			"    <div class=\"fullscreen-overlay js-fullscreen-overlay\" id=\"fullscreen_overlay\">\n" +
			"  <div class=\"fullscreen-container js-fullscreen-container\">\n" +
			"    <div class=\"textarea-wrap\">\n" +
			"      <textarea name=\"fullscreen-contents\" id=\"fullscreen-contents\" class=\"js-fullscreen-contents\" placeholder=\"\" data-suggester=\"fullscreen_suggester\"></textarea>\n" +
			"          <div class=\"suggester-container\">\n" +
			"              <div class=\"suggester fullscreen-suggester js-navigation-container\" id=\"fullscreen_suggester\"\n" +
			"                 data-url=\"/0x17/JProjectInspector/suggestions/commit\">\n" +
			"              </div>\n" +
			"          </div>\n" +
			"    </div>\n" +
			"  </div>\n" +
			"  <div class=\"fullscreen-sidebar\">\n" +
			"    <a href=\"#\" class=\"exit-fullscreen js-exit-fullscreen tooltipped leftwards\" title=\"Exit Zen Mode\">\n" +
			"      <span class=\"mega-icon mega-icon-normalscreen\"></span>\n" +
			"    </a>\n" +
			"    <a href=\"#\" class=\"theme-switcher js-theme-switcher tooltipped leftwards\"\n" +
			"      title=\"Switch themes\">\n" +
			"      <span class=\"mini-icon mini-icon-brightness\"></span>\n" +
			"    </a>\n" +
			"  </div>\n" +
			"</div>\n" +
			"\n" +
			"\n" +
			"\n" +
			"    <div id=\"ajax-error-message\" class=\"flash flash-error\">\n" +
			"      <span class=\"mini-icon mini-icon-exclamation\"></span>\n" +
			"      Something went wrong with that request. Please try again.\n" +
			"      <a href=\"#\" class=\"mini-icon mini-icon-remove-close ajax-error-dismiss\"></a>\n" +
			"    </div>\n" +
			"\n" +
			"    \n" +
			"    \n" +
			"    <span id='server_response_time' data-time='0.39344' data-host='fe3'></span>\n" +
			"    \n" +
			"  </body>\n" +
			"</html>\n" +
			"\n";




}
