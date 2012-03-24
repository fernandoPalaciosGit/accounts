<?php
include_once "ProjectConstants.php";
include_once 'Database/DBConnection.php';

class HelperFunctions {
	public static function loadText($entryTitle) {
		# Commenting out and changing to private call of db implemented localised text
		//$fileName = ProjectConstants::ENTRY_LOCALISED_TEXT_FOLDER . ProjectConstants::$lang . '/' . $entryTitle . '.ent';
		//echo file_get_contents($fileName);
		
		HelperFunctions::loadTextDb($entryTitle);
	}
	
	private static function loadTextDb($entryTitle) {
		$conn = new DBConnection('localisedtext');
		
		$lang = ProjectConstants::$lang;
		
		if ($lang == 'cn') {
			$conn->query("SET NAMES 'utf8'");
		}
		$result = $conn->query("select text from webpagetext where stringName = '$entryTitle' and lang = '$lang'");
		$row = $result->fetch_assoc();
		echo $row['text'];
	}
	
	public static function setLang($selectedLang = '') {
		if ($selectedLang == '') {
			$ip = $_SERVER['REMOTE_ADDR'];
			
			$host = ProjectConstants::GEO_LOC_API . $ip;
			$geoLocData_serialised = file_get_contents($host, 'r');
			$geoLocData = unserialize($geoLocData_serialised);
		
			$countryCode = $geoLocData['geoplugin_countryCode'];
			
			if ($countryCode == 'CN') {
				ProjectConstants::$lang = 'cn';	
			}
			else {
				ProjectConstants::$lang = 'en';
			}	
		}
		else {
			ProjectConstants::$lang = $selectedLang;
		}
	}
}