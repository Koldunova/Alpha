import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import DB.MyConnection;
import domain.Dna;
import domain.Exam;
import domain.Note;
import domain.Person;
import domain.Report;
import domain.User;
import service.ExamService;
import service.NoteService;
import service.PersonService;
import service.ReportService;
import service.UserService;

public class MyMain {

    private static boolean auth = false;
    private static boolean exit =false;
    private static String currentUser="";
    
    public static void main(String[] args) {
        System.out.println("����� ���������� � �������� ���������� �������� ���������� ���������� �����");
        while (!exit) {
            try {
                showMenu();
            } catch (IOException e) {
                System.out.println("������ � ����");
            }    
        }
        System.out.println("�� ����� �� ���������");
    }
    
    public static void showInfAboutPerson(Person person) {
        System.out.println(person.getPersonName());
        System.out.println(person.getPosition());
        System.out.println(person.getDescription());
        Dna dna = person.getDna();
        System.out.println("���");
        System.out.println(dna.getTitle());
        System.out.println(dna.getDescription());
    }
    
    public static void showMenuAuth() {
        System.out.println("��� �� ������ �������?");
        System.out.println("1-����� ��������� �� �����");
        System.out.println("2-����� ��������� �� ��������� ������");
        System.out.println("3-����� ��������� �� ���");
        System.out.println("4-������� ���� �������");
        System.out.println("5-���������� ��� �������");
        System.out.println("6-������� �������");
        System.out.println("7-����� ����� �� ������");
        System.out.println("8-����� ������ �� ������");
        System.out.println("0-�������� ���������");
        System.out.print("��� �����: ");
    }
    
    public static void showMenuNotAuth() {
        System.out.println("��������, ��� ��� ����������:");
        System.out.println("1-��������������");
        System.out.println("2-������������������");
        System.out.println("0-�����");
        System.out.print("��� �����: ");
    }
    
    public static void showMenu() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (auth==false) {
            //�� ���������������� ������������
            showMenuNotAuth();
            int key=Integer.parseInt(reader.readLine());        
            switch (key) {
            case 0:
                exit=true;
                break;
            case 1:
                //�����������
                System.out.print("������� �����:");
                String login=reader.readLine();
                System.out.print("������� ������:");
                String password=reader.readLine();
                
                int id=UserService.selectUserByLoginAndPass(login, password);
                System.out.println("------------------------------");
                if (id!=-1) {
                    System.out.println("�� �����");
                    currentUser=Integer.toString(id);
                    auth=true;
                }else {
                    System.out.println("�������� ����� ��� ������");
                }
                break;
            case 2:
                //�����������
                System.out.print("������� �����:");
                String loginReg=reader.readLine();
                System.out.print("������� ������:");
                String passwordReg=reader.readLine();
                System.out.print("������� ������ ��� ���:");
                String passwordRep=reader.readLine();
                System.out.println("------------------------------");
                if (passwordReg.equals(passwordRep)) {
                    User user = UserService.newUser(loginReg, passwordReg);
                    if (user.getIdUser()==-1) {
                        System.out.println("������������ ����������");
                    }else {
                        System.out.println("�� ������� ������������������, �������� � ���� �������.");
                    }                    
                }else {
                    System.out.println("������ �� ���������");
                }
                break;
            default:
                System.out.print("�� ������ ��������, ���������� ��� ���");
            }
            System.out.println("------------------------------");
        }else {
            //���������������� ������������
            showMenuAuth();
            int key=Integer.parseInt(reader.readLine());
            switch (key) {
            case 0:
                exit=true;
                try {
                    MyConnection.getConnection().close();
                }catch (Exception e) {
                    System.out.println("������ ��� �������� �����������");
                }
                break;
            case 1:
                //����� ��������� �� �����
                findPerosnByName(reader);
                break;
            case 2:
                //����� ��������� �� ���������
                findPerosnByPrint(reader);
                break;
            case 3:
                //����� ��������� �� ���
                findPersonByDNA(reader);
                break;
            case 4:
                //������� �������
                createNote(reader);
                break;
            case 5:
                //�������� �������
                findNotes();
                break;
            case 6:
                //�������� �������
                deleteNote(reader);
                break;
            case 7:
                //����� ������ �� id
                findReportById(reader);
                break;
            case 8:
                //����� ������� �� id
                findExById(reader);
                break;
            default:
                System.out.println("�� ������ ��������, ���������� ��� ���");
                break;
            }
            System.out.println("------------------------------");
        }
    }
    
    public static void findPerosnByName(BufferedReader reader) {
        try {
            System.out.print("������� ��� ���������: ");
            String personName = reader.readLine();
            Person person = PersonService.selectByName(personName);
            if(person.getIdPerson()==-1) {
                System.out.println("������ � ��������� ���� ���");  
                System.out.println("------------------------------");
            }else {
                System.out.println("����� �������� ���������. ��� ���������� ����������� � ���");
                System.out.println("------------------------------");
                showInfAboutPerson(person);
            }        
        } catch (Exception e) {
            System.out.println("Exception in findPerosnByName");
        }
    }

    public static void findPerosnByPrint(BufferedReader reader) {
        try {
            System.out.print("������� ��� ��������� ������: ");
            String personPrint=reader.readLine();
            Person personNamePrint = PersonService.selectByPrint(personPrint);
            if(personNamePrint.getIdPerson()==-1) {
                System.out.println("������ � ��������� ���� ���");  
                System.out.println("------------------------------");
            }else {
                System.out.println("����� �������� ���������. ��� ���������� ����������� � ���");
                System.out.println("------------------------------");
                showInfAboutPerson(personNamePrint);
                String addInf=PersonService.selectMoreIntByPrint(personPrint);
                System.out.println("��������� � "+ addInf);
            }
            System.out.println("------------------------------");
            
        } catch (Exception e) {
            System.out.println("Exception in findPerosnByPrint");
        }
    }
    
    public static void findPersonByDNA(BufferedReader reader) {
        try {
            System.out.print("������� ����� ���: ");
            String DNA = reader.readLine();
            Person personByDNA= PersonService.selectByDNA(DNA);
            if(personByDNA.getIdPerson()==-1) {
                System.out.println("������ � ��������� ���� ���");  
                System.out.println("------------------------------");
            }else {
                System.out.println("����� �������� ���������. ��� ���������� ����������� � ���");
                System.out.println("------------------------------");
                showInfAboutPerson(personByDNA);
            }
        }catch(Exception e) {
            System.out.println("Exception in findPersonByDNA");
        }
    }
    
    public static void createNote(BufferedReader reader) {
        try {
            System.out.println("�������� ������ ����� ������ �� ����");
            System.out.print("������� �������� �������:");
            String title=reader.readLine();
            System.out.println("������� ����� �������:");
            String content = reader.readLine();
            Note note = new Note();
            note.setIdUser(currentUser);
            note.setTextNote(content);
            note.setTitle(title);
            NoteService.createNote(note);
            System.out.println("------------------------------");
            System.out.println("������� �������");
            System.out.println("------------------------------");
        }catch (Exception e) {
            System.out.println("Exception in createNote");
        }
    }
    
    public static void findNotes() {
        System.out.println("���� �������");
        System.out.println("------------------------------");
        ArrayList<Note> notes = NoteService.selectNotes(currentUser);
        for (Note noteUser : notes) {
            System.out.println("����� �������: "+noteUser.getIdNote());
            System.out.println("���������:"+noteUser.getTitle());
            System.out.println("����� �������: "+noteUser.getTextNote());
            System.out.println("����: "+noteUser.getDate());
            System.out.println();
        }
    }
    
    public static void deleteNote(BufferedReader reader) {
        try {
        System.out.println("����� ������� �������?");
        int idNoteForDelete =Integer.parseInt(reader.readLine());
        NoteService.deleteNote(idNoteForDelete);
        System.out.println("������� �������");
        }catch (Exception e) {
            System.out.println("Exception in deleteNote");
        }
    }
    
    public static void findExById(BufferedReader reader) {
        try {
            System.out.print("������� ����� �������:");
            int idex=Integer.parseInt(reader.readLine());
            Exam exam = ExamService.selectExamById(idex);
            System.out.println("������: "+ exam.getPersonName());
            System.out.println("�������: "+ exam.getTitle());
            System.out.println("��������: "+exam.getDescription());
            System.out.println("����� � ������� ���������� ������");
        } catch (Exception e) {
            System.out.println("Exception in findExById");
        }
    }
    
    public static void findReportById(BufferedReader reader) {
        try {
            System.out.print("������� ����� ������:");
            int idReport = Integer.parseInt(reader.readLine());
            Report report=ReportService.selectReportById(idReport);
            System.out.println("����� �"+idReport);
            System.out.println("�� ����: "+ report.getPersonName());
            System.out.println("� ��������� � ������� ���������� ��� ����������� ������");
        }catch (Exception e) {
            System.out.println("Exception in findReportById");
        }
        }

}
