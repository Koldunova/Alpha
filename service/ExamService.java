package service;

import dao.ExamDao;
import domain.Exam;

public class ExamService {
    public static Exam selectExamById(int id) {
        ExamDao examDao = new ExamDao();
        return examDao.selectExamById(id);
    }
}
