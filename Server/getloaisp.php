<?php

	include "connect.php";
	$query ="SELECT * FROM loaisp";
	$data = mysqli_query($conn,$query);

	
	
	
	class Loaisp{
		function Loaisp($id,$tenloaisp,$hinhanhloaisp){
			$this->id=$Id;
			$this->tenloaisp=$Tenloaisp;
			$this->hinhanhloaisp=$Hinhanhloaisp;
		}
	}
	$arraysp = array();
	while ($row =mysqli_fetch_assoc($data)) {
		array_push($arraysp, new Loaisp($row['id'],
			$row['tenloaisp'],
			$row['hinhanhloaisp']));
	}
	echo json_encode($arraysp);
?>