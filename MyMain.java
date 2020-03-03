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
        System.out.println("Добро пожаловать в основной функционал будущего мобильного приложения АЛЬФА");
        while (!exit) {
            try {
                showMenu();
            } catch (IOException e) {
                System.out.println("Ошибка в меню");
            }    
        }
        System.out.println("Вы вышли из программы");
    }
    
    public static void showInfAboutPerson(Person person) {
        System.out.println(person.getPersonName());
        System.out.println(person.getPosition());
        System.out.println(person.getDescription());
        Dna dna = person.getDna();
        System.out.println("ДНК");
        System.out.println(dna.getTitle());
        System.out.println(dna.getDescription());
    }
    
    public static void showMenuAuth() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1-Найти персонажа по имени");
        System.out.println("2-Найти персонажа по отпечатку пальца");
        System.out.println("3-Найти персонажа по ДНК");
        System.out.println("4-Создать свою заметку");
        System.out.println("5-Посмотреть мои заметки");
        System.out.println("6-Удалить заметку");
        System.out.println("7-Найти отчет по номеру");
        System.out.println("8-Найти допрос по номеру");
        System.out.println("0-Покинуть помещение");
        System.out.print("Ваш выбор: ");
    }
    
    public static void showMenuNotAuth() {
        System.out.println("Выберите, что вам необходимо:");
        System.out.println("1-Авторизоваться");
        System.out.println("2-Зарегистрироваться");
        System.out.println("0-Выход");
        System.out.print("Ваш выбор: ");
    }
    
    public static void showMenu() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (auth==false) {
            //не авторизированный пользователь
            showMenuNotAuth();
            int key=Integer.parseInt(reader.readLine());        
            switch (key) {
            case 0:
                exit=true;
                break;
            case 1:
                //авторизация
                System.out.print("Введите логин:");
                String login=reader.readLine();
                System.out.print("Введите пароль:");
                String password=reader.readLine();
                
                int id=UserService.selectUserByLoginAndPass(login, password);
                System.out.println("------------------------------");
                if (id!=-1) {
                    System.out.println("Вы вошли");
                    currentUser=Integer.toString(id);
                    auth=true;
                }else {
                    System.out.println("Неверный логин или пароль");
                }
                break;
            case 2:
                //регистрация
                System.out.print("Введите логин:");
                String loginReg=reader.readLine();
                System.out.print("Введите пароль:");
                String passwordReg=reader.readLine();
                System.out.print("Введите пароль еще раз:");
                String passwordRep=reader.readLine();
                System.out.println("------------------------------");
                if (passwordReg.equals(passwordRep)) {
                    User user = UserService.newUser(loginReg, passwordReg);
                    if (user.getIdUser()==-1) {
                        System.out.println("Пользователь существует");
                    }else {
                        System.out.println("Вы успешно зарегистрировались, заходите в свой аккаунт.");
                    }                    
                }else {
                    System.out.println("Пароли не совпадают");
                }
                break;
            default:
                System.out.print("Вы видимо ошиблись, попробуйте еще раз");
            }
            System.out.println("------------------------------");
        }else {
            //авторизированный пользователь
            showMenuAuth();
            int key=Integer.parseInt(reader.readLine());
            switch (key) {
            case 0:
                exit=true;
                try {
                    MyConnection.getConnection().close();
                }catch (Exception e) {
                    System.out.println("ошибка при закрытии подключения");
                }
                break;
            case 1:
                //найти персонажа по имени
                findPerosnByName(reader);
                break;
            case 2:
                //найти персонажа по отпечатку
                findPerosnByPrint(reader);
                break;
            case 3:
                //найти персонажа по днк
                findPersonByDNA(reader);
                break;
            case 4:
                //создать заметку
                createNote(reader);
                break;
            case 5:
                //просмотр заметок
                findNotes();
                break;
            case 6:
                //удаление заметки
                deleteNote(reader);
                break;
            case 7:
                //поиск отчета по id
                findReportById(reader);
                break;
            case 8:
                //поиск допроса по id
                findExById(reader);
                break;
            default:
                System.out.println("Вы видимо ошиблись, попробуйте еще раз");
                break;
            }
            System.out.println("------------------------------");
        }
    }
    
    public static void findPerosnByName(BufferedReader reader) {
        try {
            System.out.print("Введите имя персонажа: ");
            String personName = reader.readLine();
            Person person = PersonService.selectByName(personName);
            if(person.getIdPerson()==-1) {
                System.out.println("Такого в персонажа базе нет");  
                System.out.println("------------------------------");
            }else {
                System.out.println("Такой персонаж обнаружен. Вот продробная инфформация о нем");
                System.out.println("------------------------------");
                showInfAboutPerson(person);
            }        
        } catch (Exception e) {
            System.out.println("Exception in findPerosnByName");
        }
    }

    public static void findPerosnByPrint(BufferedReader reader) {
        try {
            System.out.print("Введите код отпечатка пальца: ");
            String personPrint=reader.readLine();
            Person personNamePrint = PersonService.selectByPrint(personPrint);
            if(personNamePrint.getIdPerson()==-1) {
                System.out.println("Такого в персонажа базе нет");  
                System.out.println("------------------------------");
            }else {
                System.out.println("Такой персонаж обнаружен. Вот продробная инфформация о нем");
                System.out.println("------------------------------");
                showInfAboutPerson(personNamePrint);
                String addInf=PersonService.selectMoreIntByPrint(personPrint);
                System.out.println("Отпечаток с "+ addInf);
            }
            System.out.println("------------------------------");
            
        } catch (Exception e) {
            System.out.println("Exception in findPerosnByPrint");
        }
    }
    
    public static void findPersonByDNA(BufferedReader reader) {
        try {
            System.out.print("Введите номер ДНК: ");
            String DNA = reader.readLine();
            Person personByDNA= PersonService.selectByDNA(DNA);
            if(personByDNA.getIdPerson()==-1) {
                System.out.println("Такого в персонажа базе нет");  
                System.out.println("------------------------------");
            }else {
                System.out.println("Такой персонаж обнаружен. Вот продробная инфформация о нем");
                System.out.println("------------------------------");
                showInfAboutPerson(personByDNA);
            }
        }catch(Exception e) {
            System.out.println("Exception in findPersonByDNA");
        }
    }
    
    public static void createNote(BufferedReader reader) {
        try {
            System.out.println("ВНИМАНИЕ ПИСАТЬ МОЖНО ТОЛЬКО НА АНГЛ");
            System.out.print("Введите название заметки:");
            String title=reader.readLine();
            System.out.println("Введите текст заметки:");
            String content = reader.readLine();
            Note note = new Note();
            note.setIdUser(currentUser);
            note.setTextNote(content);
            note.setTitle(title);
            NoteService.createNote(note);
            System.out.println("------------------------------");
            System.out.println("Заметка создана");
            System.out.println("------------------------------");
        }catch (Exception e) {
            System.out.println("Exception in createNote");
        }
    }
    
    public static void findNotes() {
        System.out.println("Ваши заметки");
        System.out.println("------------------------------");
        ArrayList<Note> notes = NoteService.selectNotes(currentUser);
        for (Note noteUser : notes) {
            System.out.println("Номер заметки: "+noteUser.getIdNote());
            System.out.println("Заголовок:"+noteUser.getTitle());
            System.out.println("Текст заметки: "+noteUser.getTextNote());
            System.out.println("Дата: "+noteUser.getDate());
            System.out.println();
        }
    }
    
    public static void deleteNote(BufferedReader reader) {
        try {
        System.out.println("Какую заметку удалить?");
        int idNoteForDelete =Integer.parseInt(reader.readLine());
        NoteService.deleteNote(idNoteForDelete);
        System.out.println("Заметка удалена");
        }catch (Exception e) {
            System.out.println("Exception in deleteNote");
        }
    }
    
    public static void findExById(BufferedReader reader) {
        try {
            System.out.print("Введите номер допроса:");
            int idex=Integer.parseInt(reader.readLine());
            Exam exam = ExamService.selectExamById(idex);
            System.out.println("Допрос: "+ exam.getPersonName());
            System.out.println("Заговок: "+ exam.getTitle());
            System.out.println("Описание: "+exam.getDescription());
            System.out.println("Аудио в консоли прослушать нельзя");
        } catch (Exception e) {
            System.out.println("Exception in findExById");
        }
    }
    
    public static void findReportById(BufferedReader reader) {
        try {
            System.out.print("Введите номер отчета:");
            int idReport = Integer.parseInt(reader.readLine());
            Report report=ReportService.selectReportById(idReport);
            System.out.println("Отчет №"+idReport);
            System.out.println("По делу: "+ report.getPersonName());
            System.out.println("К сожалению в консоли прослушать или просмотреть нельзя");
        }catch (Exception e) {
            System.out.println("Exception in findReportById");
        }
        }

}
