<?php
    include ('functions.php');
    include ('database.php');

    $iduser=$_REQUEST[id];
    $pass=$_REQUEST[password];

    updateUserPassword($link,$iduser,$pass);
    echo(json_encode("done"));
?>