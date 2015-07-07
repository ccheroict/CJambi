<?php
error_reporting(0);
include("db_config.php");
$initial = $_GET['initial'];

// array for JSON response
$response = array();

// get all items from myorder table
$result = mysqli_query($conn, "SELECT * FROM `order` WHERE isActive = 1 AND status_id = 1 AND createdDate >= CURDATE() AND code LIKE '".$initial."%'") or die(mysqli_error($conn));
$response["success"] = 1;
$response["orders"] = array();
if (mysqli_num_rows($result) > 0) {
     while ($row = mysqli_fetch_array($result)) {
            // temp user array
            $order = array();
            $order["id"] = $row["id"];            
			$order["code"] = $row["code"];
			$order["packQuantity"] = $row["packQuantity"];
			$order["productQuantity"] = $row["productQuantity"];
			$order["total"] = $row["total"];
			$order["value"] = $row["value"];
			$order["createdDate"] = $row["createdDate"];
        
            // push ordered items into response array 
            array_push($response["orders"], $order);
           }
}

// echoing JSON response
echo json_encode($response);
mysqli_close($conn);

?>