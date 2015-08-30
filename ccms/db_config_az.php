<?php
 
/*
 * All database connection variables
 */
$mysql_hostname = "mn11.webd.pl";
$mysql_user = "ccms_ccms";
$mysql_password = "ChungCC2015";
$mysql_database = "ccms_ccms";
$conn = mysqli_connect($mysql_hostname, $mysql_user, $mysql_password, $mysql_database);

/* check connection */
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}  

?>