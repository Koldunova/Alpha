<?php
    include ('functions.php');
    include ('database.php');

    $person_name=$_REQUEST[person_name];

    $person=selectPersonByName($link,$person_name);

    echo(json_encode($person));

?>