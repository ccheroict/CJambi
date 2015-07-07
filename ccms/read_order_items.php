<?php
error_reporting(0);
include("db_config.php");

// array for JSON response
$response = array();
$id=$_GET['id'];
// get all items from myorder table
$result = mysqli_query($conn, "SELECT i.id, i.product_id, p.code, i.requiredPack, i.quantity, i.price, i.value FROM `item` i left join `product` p on i.product_id = p.id  WHERE i.isActive = 1 and i.order_id = '$id' ") or die(mysqli_error($conn));
$response["success"] = 1;
$response["items"] = array();
if (mysqli_num_rows($result) > 0) {

    while ($row = mysqli_fetch_array($result)) {
            // temp user array
            $item = array();
            $item["id"] = $row["id"];            
			      $item["productId"] = $row["product_id"];
            $item["code"] = $row["code"];
			      $item["requiredPack"] = $row["requiredPack"];
			      $item["quantity"] = $row["quantity"];
			      $item["price"] = $row["price"];
			      $item["value"] = $row["value"];
         
            // push ordered items into response array 
            array_push($response["items"], $item);
           }
      // success
     
} 
// echoing JSON response
echo json_encode($response);
mysqli_close($conn);
?>