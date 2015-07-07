<?php
error_reporting(0);
include("db_config.php");
$id = $_GET['id'];
// array for JSON response
$response = array();
$result = mysqli_query($conn, "SELECT p.code, p.packSize, p.finalPrice, s.code as supplier_code FROM `product` p JOIN `supplier` s ON p.supplier_id = s.id WHERE p.isActive = 1 AND p.id = '$id'") or die(mysqli_error($conn));
if (mysqli_num_rows($result) > 0 ) {
	$row = mysqli_fetch_array($result);
	$response['success'] = 1;
	$response['product_code'] = $row['code'];
	$response['product_pack_size'] = $row['packSize'];
	$response['finalPrice'] = $row['finalPrice'];
	$response['supplier_code'] = $row['supplier_code'];
} else {
	$response['success'] = 0;
}
echo json_encode($response);
mysqli_close($conn);
?>