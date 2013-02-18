-- --------------------------------------------------------
-- app_setting initial volumization 
-- script
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;
/*!40000 ALTER TABLE `app_setting` DISABLE KEYS */;
INSERT INTO `app_setting` (`key_name`, `value`, `inserted`, `updated`) VALUES
	('accounts.encryption.type', 'MD5', now(), now()),
	( 'accounts.smtp.server', 'smtp.gmail.com', now(), now()),
	( 'accounts.smtp.server.port', '587', now(), now()),
	( 'accounts.smtp.user', 'briansatchwannabe@gmail.com', now(), now()),
	( 'accounts.smtp.password', 'lingling', now(), now()),
	( 'accounts.smtp.mail.from_addr', 'doNotReply@mooney-server.tk', now(), now()),
	( 'accounts.smtp.mail.template.user_actv.subject', 'Home Accounts - User Activation', now(), now()),
	( 'accounts.smtp.mail.template.user_actv.body', '<p>Hello %FIRSTNAME%</p><p>Someone using this email address has registered you for the <b><i>Home Accounts</i></b> software hosted on %ACCOUNTS_HOST%. If this was not you, please ignore this email. If you are trying to register for <b><i>Home Accounts</i></b> then please click on the below link or open it in a browser to confirm your activation.</p><br/><p>%LINK%</p><br/><br/><p>Please do not reply to this email as the mailbox is not monitored</p>Regards<br/><b><i>Home Accounts</i></b> Team', now(), now()),
	('accounts.app.server.url', 'http://www.mooney-server.tk', now(), now()),
	('accounts.smtp.mail.template.pwd_reset.subject', 'Home Accounts - Password Reset', now(), now()),
	('accounts.smtp.mail.template.pwd_reset.body', '<p>Hello %FIRSTNAME%</p><p>You''re password has been reset. Please find below you''re new password:<br/><br/>%NEW_PASSWORD%</p><br/><br/><p>Please do not reply to this email as the mailbox is not monitored</p>Regards<br/><b><i>Home Accounts</i></b> Team', now(), now()),
	('accounts.smtp.timeout', '60000', now(), now());
/*!40000 ALTER TABLE `app_setting` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
