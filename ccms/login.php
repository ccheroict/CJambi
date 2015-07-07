<?php
error_reporting(0);

include("db_config.php");
$username=$_GET['username'];
$password=$_GET['password'];

// array for JSON response
$response = array();

// get all items from myorder table
$result = mysqli_query($conn, "SELECT initial, uuid, company_id FROM `user` u left join `office` o on u.office_id = o.id WHERE isActive = 1 AND BINARY username = '$username' AND BINARY password = '$password'") or die(mysqli_error($conn));
if (mysqli_num_rows($result) > 0) {
  
      while ($row = mysqli_fetch_array($result)) {
             $response['initial'] = $row['initial'];
             $response['uuid'] = $row['uuid'];
             $response['companyId'] = $row['company_id'];
           }
      // success
     $response["success"] = 1;
}
else {
     // order is empty 
      $response["success"] = 0;
      $response["message"] = "No User Found";
}
// echoing JSON response
echo json_encode($response);
mysqli_close($db);
?>