<?php
    include ('database.php');

    $input="t";
    $sql = "SELECT * FROM users WHERE email LIKE '$input'";
    $query=mysqli_query($link,$sql);
    $result=mysqli_num_rows($query);
    
    var_dump("Поиск пользователя с именем $input");

    if ($result>0){
        var_dump("Такой есть");
        var_dump("Замена");
        $new_email="test";
        $sql="UPDATE `users` SET `email`='$new_email', WHERE email like '$input';";
        $query=mysqli_query($link,$sql);
        var_dump("Все успешно прошлой");
        exit;
    }
    var_dump("Такого нет");
   

?>