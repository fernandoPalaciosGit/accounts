<?php
	require_once("../BasePageFormat.php");
	include_once("../HelperFunctions.php");
		
	$homePage = new BasePageFormat();
	$homePage->constructHeader();
?>	
	<div id="content">
		<h2><?php HelperFunctions::loadText("Home_MeetFamily_Heading", "en")?></h2>
		
		<br/>
		<h3><?php HelperFunctions::loadText("BrianName", "en")?></h3>
		<img src="../../images/familyProfile/brian_head.png" class="floatLeft">
		<p id="entry">
			<?php HelperFunctions::loadText("InfoBrian", "en")?>
		</p>
		
		<br/>
		<h3><?php HelperFunctions::loadText("LingName", "en")?></h3>
		<img src="../../images/familyProfile/ling_head.png" class="floatRight">
		<p id="entry">
			<?php HelperFunctions::loadText("InfoLing", "en")?>
		</p>
		
		<br/>
		<h3>Jenny</h3>
		<img src="../../images/familyProfile/jenny_head.png" class="floatLeft">
		<p id="entry">
			<?php HelperFunctions::loadText("InfoJenny", "en")?>
		</p>
	</div>
	
	

<?php	
	$homePage->constructFooter();
?>