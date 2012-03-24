<?php
	require_once("BasePageFormat.php");
	include_once("HelperFunctions.php");
		
	$homePage = new BasePageFormat();
	$homePage->constructHeader();
?>	
	<div id="content">
		<h2><?php HelperFunctions::loadText("HomeHeading")?></h2>
		
		<p>
			<?php HelperFunctions::loadText("Home_Introduction")?>
		</p>
		
		<a href="HomeSubs/Home_MeetFamily.php"><?php HelperFunctions::loadText("Home_MeetFamily_Heading")?></a>
	</div>
	
	

<?php	
	$homePage->constructFooter();
?>