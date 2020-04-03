<?php
    include ('functions.php');
    include ('database.php');

    $idUser=$_REQUEST[id];
    $code=$_REQUEST[code]; 

    $codeResult=switchTypeCodeAndNextOperation($link,$id,$code);

    echo(json_encode($codeResult));

?>