package dao;

import java.sql.ResultSet;
import com.mysql.cj.jdbc.CallableStatement;

import DB.MyConnection;
import domain.Report;

public class ReportDao {
    public Report selectAllReportForPerson(int id) {
        String sql="call alpha.selectRepordById("+id+");";
        try {
            CallableStatement callableStatement = (CallableStatement) MyConnection.getConnection().prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            Report report = new Report();
            if (resultSet.next()) {
                report.setPersonName(resultSet.getString("person_name"));
                report.setDocument(resultSet.getObject("document"));
            }
            return report;
        }catch (Exception e) {
            System.out.println("Exception in ReportDao selectAllReportForPerson");
            System.out.println(e.getMessage());
            return new Report();
        }
    }
}
