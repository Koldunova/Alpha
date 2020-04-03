<?php
    include ('functions.php');
    include ('database.php');

    $id=$_REQUEST[id]; 

    $notes=selectExamByIdExam($link,$id);

    echo(json_encode($notes));

?>