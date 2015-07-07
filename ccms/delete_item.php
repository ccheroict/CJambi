<?php
error_reporting(0);

include("db_config.php");
$uuid = $_GET['uuid'];
$id = $_GET['id'];
 // array for JSON response
$response = array();
$result = mysqli_query($conn, "SELECT 1 FROM user WHERE BINARY uuid = '$uuid'") or die(mysqli_error($conn));
if (mysqli_num_rows($result) > 0 ) {
	$result = mysqli_query($conn, "UPDATE `item` SET isActive = 0 WHERE id = $id") or die(mysqli_error($conn));
	$response['success'] = 1;
} else {
	$response['success'] = 0;
}
echo json_encode($response);
mysqli_close($conn);

?>