package dao;

import java.sql.ResultSet;

import com.mysql.cj.jdbc.CallableStatement;

import DB.MyConnection;
import domain.Exam;

public class ExamDao {
    public Exam selectExamById(int id) {
            String sql="call alpha.selectExamByIdExam("+id+");";
            try {
                CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
                ResultSet resultSet = callableStatement.executeQuery();
                Exam exam= new Exam();
                if (resultSet.next()) {
                   exam.setPersonName(resultSet.getString("person_name"));
                   exam.setTitle(resultSet.getString("title"));
                   exam.setAudio(resultSet.getObject("audio"));
                   exam.setDescription(resultSet.getString("description"));
                }
                return exam;
            }catch (Exception e) {
                System.out.println("Exception in ExamUser selectExamById(");
                System.out.println(e.getMessage());
                return new Exam();
            }
        
    }
}
