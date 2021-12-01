<?php
	
	require "conect.php";
	$tenkh = "nguyen";
	$email ="nguyen@gmail.com";
	$sdt = "036589713";
	if(strlen($tenkh)>0 && strlen($email) && strlen($sdt)){
		$query = "INSERT INTO donhang(id,tenkh,sdt,email) VALUES (null,'$tenkh','$sdt','$email')";
		if (mysqli_query($conn,$query)) {
			// code...
			$iddonhang = $conn->insert_id;
			echo $iddonhang;
		}else {
			// code...
			echo "fail";
		}
	}
	else{
		echo "kiem tra lai data";
	}
?>