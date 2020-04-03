<?php
    include ('functions.php');
    include ('database.php');

    $DNA=$_REQUEST[DNA];
    $person=selectPersonByDNA($link,$DNA);

    echo(json_encode($person));
?>