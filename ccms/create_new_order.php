<?php
error_reporting(0);

include("db_config.php");
$uuid = $_GET['uuid'];
$initial = $_GET['initial'];
$company_id = $_GET['company_id'];
// array for JSON response
$response = array();
$result = mysqli_query($conn, "SELECT 1 FROM user WHERE BINARY uuid = '$uuid'") or die(mysqli_error($conn));
if (mysqli_num_rows($result) > 0 ) {
	$result = mysqli_query($conn, "SELECT COUNT(1) AS order_count FROM `order` WHERE createdDate >= CURDATE()") or die(mysqli_error($conn));
	$row = mysqli_fetch_array($result);
	$code = $initial."/".($row['order_count']);
	$result = mysqli_query($conn, "INSERT INTO `order`(code, createdDate, lastChangedDate, company_id) VALUES('$code', NOW(), NOW(), $company_id)") or die(mysqli_error($conn));
	$response['success'] = 1;
	$response['order_id'] = mysqli_insert_id($conn);
} else {
	$response['success'] = 0;
}
echo json_encode($response);
mysqli_close($conn);

?>