<?php
    include ('functions.php');
    include ('database.php');

    $id=$_REQUEST[id];

    $report=selectReportById($link,$id);

    echo(json_encode($report));

?>