<?php
include ('functions.php');
include ('database.php');

$input_login="php_test";
$input_pass_f="php";
$input_pass_s="php";

if ($input_pass_f == $input_pass_s){
    $result=selectUserByEmail($link,$input_login);
    if ($result == "exist"){
        var_dump("create new login");
    }else{
        insertNewUser($link,$input_login,$input_pass_f);
        var_dump("created");
    }
}else{
    var_dump("input correct password.");
}

?>