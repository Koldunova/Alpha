package dao;

import java.sql.ResultSet;

import com.mysql.cj.jdbc.CallableStatement;

import DB.MyConnection;
import domain.Dna;
import domain.Person;

public class PersonDao {
    
    public String selectAddInfAboutFinger(String print) {
        String sql="call alpha.selectPersonByPrint('"+print+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("title");
            }
        }catch (Exception e) {
            System.out.println("Exception in PersonDao selectAddInfAboutFinger");
            System.out.println(e.getMessage());
        }
        return "";
    }
    
    public Person selectUserByPrint(String print) {
        String sql="call alpha.selectPersonByPrint('"+print+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            Person person=new Person();
            if (resultSet.next()) {
                person.setIdPerson(resultSet.getInt("id_person"));
                person.setPersonName(resultSet.getString("person_name"));
                person.setPosition(resultSet.getString("position"));
                person.setDescription(resultSet.getString("description"));
                person.setPic(resultSet.getObject("picture"));
                
                Dna dna = new Dna();
                dna.setTitle(resultSet.getString("DNA_title"));
                dna.setDescription(resultSet.getString("DNA_description"));
                dna.setPic(resultSet.getObject("DNA_picture"));
                
                person.setDna(dna);
                return person;
            }

            return person;
        }catch (Exception e) {
            System.out.println("Exception in PersonDao selectUserByPrint");
            System.out.println(e.getMessage());
            return new Person();
        }
    }
    
    public Person selectUserByDna(String personDNA) {
        String sql="call alpha.selectPersonByDNA('"+personDNA+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            Person person=new Person();
            if (resultSet.next()) {
                person.setIdPerson(resultSet.getInt("id_person"));
                person.setPersonName(resultSet.getString("person_name"));
                person.setPosition(resultSet.getString("position"));
                person.setDescription(resultSet.getString("description"));
                person.setPic(resultSet.getObject("picture"));
                
                Dna dna = new Dna();
                dna.setTitle(resultSet.getString("DNA_title"));
                dna.setDescription(resultSet.getString("DNA_description"));
                dna.setPic(resultSet.getObject("DNA_picture"));
                
                person.setDna(dna);
                return person;
            }
            person.setIdPerson(-1);
            return person;
        }catch (Exception e) {
            System.out.println("Exception in PersonDao selectUserByDNA");
            System.out.println(e.getMessage());
            return new Person();
        }
    }
    
    public Person selectUserByName(String personName) {
        String sql="call alpha.selectPersonByName('"+personName+"');";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery(sql);
            Person person=new Person();
            if (resultSet.next()) {
                person.setIdPerson(resultSet.getInt("id_person"));
                person.setPersonName(resultSet.getString("person_name"));
                person.setPosition(resultSet.getString("position"));
                person.setDescription(resultSet.getString("description"));
                person.setPic(resultSet.getObject("picture"));
                
                Dna dna = new Dna();
                dna.setTitle(resultSet.getString("DNA_title"));
                dna.setDescription(resultSet.getString("DNA_description"));
                dna.setPic(resultSet.getObject("DNA_picture"));
                
                person.setDna(dna);
                return person;
            }
            person.setIdPerson(-1);
            return person;
        }catch (Exception e) {
            System.out.println("Exception in PersonDao selectUserByName");
            System.out.println(e.getMessage());
            return new Person();
        }
    }
}
