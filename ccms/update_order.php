<?php
error_reporting(0);

include("db_config.php");
$uuid = $_GET['uuid'];
$orderId = $_GET['orderId'];
$packQuantity = $_GET['packQuantity'];
$productQuantity = $_GET['productQuantity'];
$value = $_GET['value'];
// array for JSON response
$response = array();
$result = mysqli_query($conn, "SELECT 1 FROM user WHERE BINARY uuid = '$uuid'") or die(mysqli_error($conn));
if (mysqli_num_rows($result) > 0 ) {
	$result = mysqli_query($conn, "UPDATE `order` SET lastChangedDate = NOW(), packQuantity = $packQuantity, productQuantity = $productQuantity, total = $value, value = $value WHERE id = $orderId AND isActive = 1") or die(mysqli_error($conn));
	$response['success'] = 1;
} else {
	$response['success'] = 0;
}
echo json_encode($response);
mysqli_close($conn);

?>