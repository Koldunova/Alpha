<?php
    include ('functions.php');
    include ('database.php');

    $email=$_REQUEST[Email];

    $exist=selectUserByEmail($link,$email);

    echo(json_encode($exist));

?>