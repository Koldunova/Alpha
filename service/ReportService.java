package service;

import dao.ReportDao;
import domain.Report;

public class ReportService {
    public static Report selectReportById (int id) {
        ReportDao reportDao = new ReportDao();
        return reportDao.selectAllReportForPerson(id);
    }
}
