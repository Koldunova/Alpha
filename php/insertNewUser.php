<?php
    include ('functions.php');
    include ('database.php');

    $login=$_REQUEST[login];
    $pass=$_REQUEST[password];


    insertNewUser($link,$login,$pass);
    echo(json_encode("done"));
?>