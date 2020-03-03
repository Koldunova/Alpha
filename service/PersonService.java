package service;

import dao.PersonDao;
import domain.Person;

public class PersonService {
    
    public static Person selectByName(String personName) {
        PersonDao personDao = new PersonDao();
        Person person=personDao.selectUserByName(personName);
        return person;
    }
    
    public static Person selectByDNA(String dna) {
        PersonDao personDao = new PersonDao();
        Person person= personDao.selectUserByDna(dna);
        return person;
    }
    
    public static Person selectByPrint(String print) {
        PersonDao personDao = new PersonDao();
        Person person= personDao.selectUserByPrint(print);
        return person;
    }
    
    public static String selectMoreIntByPrint(String print) {
        PersonDao personDao = new PersonDao();
        String moreInf=personDao.selectAddInfAboutFinger(print);
        return moreInf;
    }

}
