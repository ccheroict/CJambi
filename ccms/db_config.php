<?php
 
/*
 * All database connection variables
 */
$mysql_hostname = "localhost";
$mysql_user = "17843548_0000002";
$mysql_password = "ChungCC2015";
$mysql_database = "17843548_0000002";
$conn = mysqli_connect($mysql_hostname, $mysql_user, $mysql_password, $mysql_database);

/* check connection */
if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error());
    exit();
}  

?>