<?php
	include "connect.php";

	$page = $_GET['page'];
	$idsp = $_POST['idsanpham'];
	$space = 5;
	$limit = ($page-1)*$space;


	$mangsp = array();
	$query = "select * from sanpham where idsanpham = $idsp limit $limit,$space";
	$data = mysqli_query($conn,$query);
	while ($row = mysqli_fetch_assoc($data)) {
		// code...
		array_push($mangsp, new sanphammoi($row['id'],
													$row['tensp'],
													$row['giasp'],
													$row['hinhanhsp'],
													$row['mota'],
													$row['idsanpham']));
	}

	echo json_encode($mangspmoinhat);
	class sanpham{
		function sanpham($id,$tensp,$giasp,$hinhanhsp, $mota,$idsanpham){
			$this->id=$id;
			$this->tensp=$tensp;
			$this->giasp=$giasp;
			$this->hinhanhsp=$hinhanhsp;
			$this->mota=$mota;
			$this->idsanpham=$idsanpham;
		}
	}
?>