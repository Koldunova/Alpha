<?php
    include ('functions.php');
    include ('database.php');

    $id=$_REQUEST[id]; 

    $notes=selectNoteByIdUser($link,$id);

    echo(json_encode($notes));

?>