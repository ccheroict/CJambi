<?php
error_reporting(0);

include("db_config.php");
$uuid = $_GET['uuid'];
$productId = $_GET['productId'];
$requiredPack = $_GET['requiredPack'];
$quantity = $_GET['quantity'];
$price = $_GET['price'];
$value = $_GET['value'];
$orderId = $_GET['orderId'];
// array for JSON response
$response = array();
$result = mysqli_query($conn, "SELECT 1 FROM user WHERE BINARY uuid = '$uuid'") or die(mysqli_error($conn));
if (mysqli_num_rows($result) > 0 ) {
	$result = mysqli_query($conn, "INSERT INTO `item`(product_id, requiredPack, quantity, price, value, order_id) VALUES($productId, $requiredPack, $quantity, $price, $value, $orderId)") or die(mysqli_error($conn));
	$response['success'] = 1;
	$response['item_id'] = mysqli_insert_id($conn);
} else {
	$response['success'] = 0;
}
echo json_encode($response);
mysqli_close($conn);

?>