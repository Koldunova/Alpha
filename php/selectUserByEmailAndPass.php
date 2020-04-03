<?php
    include ('functions.php');
    include ('database.php');

    $email=$_REQUEST[Email];
    $pass=$_REQUEST[Password];

    $user=selectUserByEmailAndPass($link,$email,$pass);

    echo(json_encode($user));

?>