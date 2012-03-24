<?php
	require_once("BasePageFormat.php");
	include_once("HelperFunctions.php");
		
	$homePage = new BasePageFormat();
	$homePage->constructHeader();
?>	
	<div id="content">
		<h2><?php HelperFunctions::loadText("Acknowledgements_Header")?></h2>
		
		<a href="http://www.geoplugin.com/" target="_new" title="geoPlugin for IP geolocation">Geolocation by geoPlugin</a>
	</div>
	
	

<?php	
	$homePage->constructFooter();
?>