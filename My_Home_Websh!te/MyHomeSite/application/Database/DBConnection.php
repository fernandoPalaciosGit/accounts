<?php

include_once 'Database/DB_Constants.php';

class DBConnection extends mysqli implements DB_Constants {
	
	function __construct($dbName) {
		parent::__construct(DB_Constants::SQL_HOST, DB_Constants::USER, DB_Constants::PASSWORD, $dbName);
		
		if(mysqli_connect_error()) {
			require_once("../BasePageFormat.php");
			
			$homePage = new BasePageFormat();
			$homePage->constructHeader();
			?>
				<div id="content">
					<strong>Oops, some DB sh!t be going wrong... 
						<?php echo mysqli_connect_errno() . ' ' . mysqli_connect_error()?>
					</strong>
				</div>
			<?php
			$homePage->constructFooter();
		}
	}
}
?>