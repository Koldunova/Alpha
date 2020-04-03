<?php
    include ('functions.php');
    include ('database.php');

    $id=$_REQUEST[id];

    deleteNoteById($link,$id);

    echo(json_encode("done"));
?>