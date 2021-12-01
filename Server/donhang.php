<?php

	require "conect.php";
	$json ='
		[{"madonhang":"2","masanpham":2,"tensanpham":"TOKIBOKI","giasanpham":12000,"soluongsanpham":2},{"madonhang":"3","masanpham":3,"tensanpham":"Trà sữa trân châu đường đen","giasanpham":12000,"soluongsanpham":2}]'

	$data = json_decode($json,true);
	foreach ($data as $value) {
		// code...
		$madonhang = $value['madonhang'];
		$masp = $value['masp'];
		$tensp = $value['tensp'];
		$giasp = $value['giasp'];
		$soluongsp = $value['soluongsp'];
		$query = "insert into donhang (id,madonhang,masp,tensp,giasp,soluongsp) Values(null,'$madonhang','$masp','$tensp','$giasp','$soluongsp')";
		$dta = mysqli_query($conn,$query);
		if ($dta) {
			// code...
			echo "1";
		}else {
			// code...
			echo "0";
		}
	}
?>