<?php
include_once "ProjectConstants.php";
include_once "HelperFunctions.php";

/* 
 * Base class to be subclassed by each
 * page. Will handle header and footer 
 * construction
 */
class BasePageFormat {
	private $pageName;	
	private $baseHeaderImg;
	private $lang;
	
	// Constructor
	// Should take the name of the current page to make
	// as non link.
	function __construct($currentPageName = "") {
		if ($currentPageName == "") {
			$currentFile = $_SERVER["SCRIPT_NAME"];
			$parts = Explode('/', $currentFile);
    		$this->pageName = $parts[count($parts) - 1];
		}
		else {
			$this->pageName = $currentPageName;	
		}
		
		$this->setLang();		
		$this->baseHeaderImg = ProjectConstants::ROOT . '/images/defaultHeader.jpg';
	}
	
	// Construct the header based on number of 
	// pages in the pageList.xml
	public function constructHeader($headerImage = '', $javaScriptLink = "") {
		$pages = $this->parsePageList();
		
		echo '<html>';
		echo '<head>';
		echo '<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=utf-8">';
		echo '<link rel="stylesheet" type="text/css" href="' . ProjectConstants::ROOT . '/public/baseSettings.css"/>';
		
		// Stub to add java script functions into header if required
		if ($javaScriptLink != "") {
			echo $javaScriptLink;
		}
		
		echo '<title>Mooney Home Network</title>';
		echo '</head>';
		echo '<body>'; 
		$this->displayLangSettings();
		echo '<div id="content-shadow_right">'; 
		echo '<div id="content-shadow_left">'; 

		
		if ($headerImage == '') {
			$headerImage = $this->baseHeaderImg;
		}
		echo '<img src="' . $headerImage . '" id="headerImage"/>';
		echo '<div id="headerLinks">';
		foreach($pages as $page) {
			$linkName = $this->getLinkName($page);
			
			if ($page . '.php' == $this->pageName) {
				echo ' <a id="header_current">' . $page . '</a> ';
			}
			else {
				echo ' <a id="header" href="' . ProjectConstants::ROOT . 'application/' . $linkName . '.php">' . $page . '</a> ';
			}
		}
		
		echo '</div>';
	}
	
	public function constructFooter() {
		echo '</div>';
		echo '</div>';
		echo '<div id="footer"><a href="mailto:brian_satchwannabe@hotmail.com">Email to Webmaster</a><a id="upLink" href="#">^</a></div>';
		echo '</body>';
		echo '</html>';
	}
	
	// Construct the list of links in the header
	// from the list of pages in the xml
	private function parsePageList() {
		// Init blank array to hold pages
		// and grab xml object from pageList file
		$pages = array();
		$pagesXML = new SimpleXMLElement(ProjectConstants::PAGE_LIST_FILE_LOC, null, true);
		
		// loop through pages and assign to array
		foreach($pagesXML->pageList as $pageList) {
			if ($pageList['lang'] == ProjectConstants::$lang) {
				foreach($pageList->page as $page) {
					array_push($pages, $page['value']);
				}
			}
		}
                
		return $pages;
	}
	
	private function getLinkName($pageName) {
		$pagesXML = new SimpleXMLElement(ProjectConstants::PAGE_LIST_FILE_LOC, null, true);
		
		foreach($pagesXML as $pageList) {
			if ($pageList['lang'] == ProjectConstants::$lang) {
				foreach($pageList->page as $page) {
					if (trim($page['value']) == trim($pageName)) {
						return $page['link'];
					}
				}
			}
		}
	}
	
	// Generate the html to display the language change option
	private function displayLangSettings() {
		if(ProjectConstants::$lang == 'cn') {
			$changeToLang = 'English';
			$changeLang = 'en';
		}
		else {
			$changeToLang = '汉语';
			$changeLang = 'cn';
		}
		
		echo '<a href="?lang=' . $changeLang . '">' . $changeToLang . '</a>';
	}
	
	// Sets cookies on client side to track language selections throughout site visit
	private function setLang() {
		if (isset($_GET['lang'])) {
			$selectedLang = $_GET['lang'];
			
			if (isset($_COOKIE[ProjectConstants::COOKIE_LANG_PREF])) {
				setcookie(ProjectConstants::COOKIE_LANG_PREF, '', -3600, '/'); # Delete the cookie
				setcookie(ProjectConstants::COOKIE_LANG_PREF, $selectedLang, time()+2592000, '/'); # Re-create cookie with new value, 30 day expire
			}
			else {
				setcookie(ProjectConstants::COOKIE_LANG_PREF, $selectedLang, time()+2592000, '/'); # Expires in 30days
			}
			
			HelperFunctions::setLang($selectedLang);
		}
		else { // check which lang to use
			if (isset($_COOKIE[ProjectConstants::COOKIE_LANG_PREF])) {
				HelperFunctions::setLang($_COOKIE[ProjectConstants::COOKIE_LANG_PREF]); 
			}
			else {
				HelperFunctions::setLang();
				setcookie(ProjectConstants::COOKIE_LANG_PREF, ProjectConstants::$lang, time()+2592000, '/'); /* Expires in 30days */
				
			}
		}
	}
}

?>