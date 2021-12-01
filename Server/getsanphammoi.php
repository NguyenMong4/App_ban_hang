<?php
	include "connect.php";

	$mangspmoinhat = array();
	$query = "select * from sanpham order by id desc limit 6";
	$data = mysqli_query($conn,$query);
	while ($row = mysqli_fetch_assoc($data)) {
		// code...
		array_push($mangspmoinhat, new sanphammoi($row['id'],
													$row['tensp'],
													$row['giasp'],
													$row['hinhanhsp'],
													$row['mota'],
													$row['idsanpham']));
	}

	echo json_encode($mangspmoinhat);

	class sanphammoi{
		function sanphammoi($id,$tensp,$giasp,$hinhanhsp, $mota,$idsanpham){
			$this->id=$id;
			$this->tensp=$tensp;
			$this->giasp=$giasp;
			$this->hinhanhsp=$hinhanhsp;
			$this->mota=$mota;
			$this->idsanpham=$idsanpham;
		}
	}
?>