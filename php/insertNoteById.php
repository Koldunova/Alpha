<?php
    include ('functions.php');
    include ('database.php');

    $id=$_REQUEST[id];
    $title=$_REQUEST[title];
    $text=$_REQUEST[text];

    insertNoteById($link,$title,$text,$id);
    echo(json_encode("done"));
?>