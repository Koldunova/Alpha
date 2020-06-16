<?php
    include ('functions.php');
    include ('database.php');

    $idNote=$_REQUEST[id];
    $title=$_REQUEST[title];
    $text=$_REQUEST[text];

    updateNoteById($link,$idNote,$title,$text);
    echo(json_encode("done"));
?>