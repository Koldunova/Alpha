<?php

    include ('functions.php');
    include ('database.php');

    $print=$_REQUEST[personPrint];

    $person=selectPersonByPrint($link,$print);

    echo(json_encode($person));
?>