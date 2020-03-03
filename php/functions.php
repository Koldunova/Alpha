<?php

function selectPersonByDNA($link,$DNA){
    $sql = "CALL alpha.selectPersonByDNA($DNA)";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        $row=mysqli_fetch_array($result);
        $person['id_person']=mb_convert_encoding($row['id_person'], 'UTF-8', 'UTF-8');
        $person['person_name']=mb_convert_encoding($row['person_name'], 'UTF-8', 'UTF-8');
        $person['position']=mb_convert_encoding($row['position'], 'UTF-8', 'UTF-8');
        $person['description']=mb_convert_encoding($row['description'], 'UTF-8', 'UTF-8');
        $person['picture']=mb_convert_encoding($row['picture'], 'UTF-8', 'UTF-8');
        $person['DNA_title']=mb_convert_encoding($row['DNA_title'], 'UTF-8', 'UTF-8');
        $person['DNA_picture']=mb_convert_encoding($row['DNA_picture'], 'UTF-8', 'UTF-8');
        $person['DNA_description']=mb_convert_encoding($row['DNA_description'], 'UTF-8', 'UTF-8');
        return $person;
    }
    return "none exist";
}
function selectPersonByName($link,$name){
    $sql = "CALL alpha.selectPersonByName('$name')";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        $row=mysqli_fetch_array($result);
        $person['id_person']=mb_convert_encoding($row['id_person'], 'UTF-8', 'UTF-8');
        $person['person_name']=mb_convert_encoding($row['person_name'], 'UTF-8', 'UTF-8');
        $person['position']=mb_convert_encoding($row['position'], 'UTF-8', 'UTF-8');
        $person['description']=mb_convert_encoding($row['description'], 'UTF-8', 'UTF-8');
        $person['picture']=mb_convert_encoding($row['picture'], 'UTF-8', 'UTF-8');
        $person['DNA_title']=mb_convert_encoding($row['DNA_title'], 'UTF-8', 'UTF-8');
        $person['DNA_picture']=mb_convert_encoding($row['DNA_picture'], 'UTF-8', 'UTF-8');
        $person['DNA_description']=mb_convert_encoding($row['DNA_description'], 'UTF-8', 'UTF-8');
        return $person;
    }
    return "none exist";
}
function selectPersonByPrint($link,$print){
    $sql = "CALL alpha.selectPersonByPrint('$print')";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        $row=mysqli_fetch_array($result);
        $person['id_person']=mb_convert_encoding($row['id_person'], 'UTF-8', 'UTF-8');
        $person['person_name']=mb_convert_encoding($row['person_name'], 'UTF-8', 'UTF-8');
        $person['position']=mb_convert_encoding($row['position'], 'UTF-8', 'UTF-8');
        $person['description']=mb_convert_encoding($row['description'], 'UTF-8', 'UTF-8');
        $person['picture']=mb_convert_encoding($row['picture'], 'UTF-8', 'UTF-8');
        $person['DNA_title']=mb_convert_encoding($row['DNA_title'], 'UTF-8', 'UTF-8');
        $person['DNA_picture']=mb_convert_encoding($row['DNA_picture'], 'UTF-8', 'UTF-8');
        $person['DNA_description']=mb_convert_encoding($row['DNA_description'], 'UTF-8', 'UTF-8');
        $person['title']=mb_convert_encoding($row['title'], 'UTF-8', 'UTF-8');
        return $person;
    }
    return "none exist";
}

function selectUserByEmail($link,$email){
    $sql = "CALL alpha.selectUserByEmail('$email')";
    $result=mysqli_query($link,$sql);
    
    if (mysqli_num_rows($result) > 0){
        return "exist";
    }
    return "none exist";
}

function selectUserByEmailAndPass($link,$email,$pass){
    $sql = "CALL alpha.selectUserByEmailAndPass('$email','$pass')";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        $row=mysqli_fetch_array($result);
        $user['id_user']=mb_convert_encoding($row['id_user'], 'UTF-8', 'UTF-8');
        return $user;
    }
    return "none exist";
}

function selectReportById($link,$id){
    $sql = "CALL alpha.selectRepordById($id)";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        $row=mysqli_fetch_array($result);
        $report['document']=mb_convert_encoding($row['document'], 'UTF-8', 'UTF-8');
        $report['person_name']=mb_convert_encoding($row['person_name'], 'UTF-8', 'UTF-8');
        return $report;
    }
    return "none exist";
}

function selectNoteByIdUser($link,$id){
    $sql = "CALL alpha.selectNoteByIdUser($id)";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        while ($row=mysqli_fetch_array($result)){
           $note['id_note']=mb_convert_encoding($row['id_note'], 'UTF-8', 'UTF-8');
           $note['title']=mb_convert_encoding($row['title'], 'UTF-8', 'UTF-8');
           $note['text_note'] =mb_convert_encoding($row['text_note'], 'UTF-8', 'UTF-8');
           $note['date_note']=mb_convert_encoding($row['date_note'], 'UTF-8', 'UTF-8');
        }
        return $note;
    }
    return "none exist";
}

function selectExamByIdExam($link,$idEx){
    $sql = "CALL alpha.selectExamByIdExam($idEx)";
    $result=mysqli_query($link,$sql);
    if (mysqli_num_rows($result) > 0){
        $row=mysqli_fetch_array($result);
        $exam['title']=mb_convert_encoding($row['title'], 'UTF-8', 'UTF-8');
        $exam['audio']=mb_convert_encoding($row['audio'], 'UTF-8', 'UTF-8');
        $exam['description']=mb_convert_encoding($row['description'], 'UTF-8', 'UTF-8');
        $exam['person_name']=mb_convert_encoding($row['person_name'], 'UTF-8', 'UTF-8');
        return $exam;
    }
    return "none exist";
}

function insertNoteById($link,$title,$text,$id){
    $sql = "CALL alpha.insertNoteById('$title','$text',$id)";
    for(; mysqli_next_result($link) == 0;)
    mysqli_query($link,$sql);
    mysqli_query($link,$sql);
}

function insertNewUser($link,$login,$pass){
    $sql = "CALL alpha.insertNewUser('$login','$pass')";
    for(; mysqli_next_result($link) == 0;)
    mysqli_query($link,$sql);
    mysqli_query($link,$sql);
}

function deleteNoteById($link,$id){
    $sql = "CALL alpha.deleteNoteById($id)";
    for(; mysqli_next_result($link) == 0;)
    mysqli_query($link,$sql);
    mysqli_query($link,$sql);
}
?>
