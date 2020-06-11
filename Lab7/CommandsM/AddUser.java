package Lab7.CommandsM;

import Lab7.CommandsM.General.ExecutableWithInput;
import Lab7.ModuleServer.DataBaseHandler;
import Lab7.Sercurity.User;
import Lab7.Source.LabWork;

import java.util.List;
import java.util.Scanner;

public class AddUser implements ExecutableWithInput {
    private User user;
    private String answer;

    @Override
    public String getName() {
        return "REGISTER";
    }

    @Override
    public boolean isDataCorrect() {
        return true;
    }

    @Override
    public void collectData(String arg) {
        String login, password;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Регистрация нового пользователя\nВведите логин:");
        login = scanner.nextLine();
        System.out.println("Введите пароль:");
        //password = scanner.nextLine();
        password = new String(System.console().readPassword());
        user = new User(login, User.getPasswordHash(password));
    }

    @Override
    public void recordData(String arg) {
        throw new RuntimeException("Вызов этого метода не предполагается");
    }

    @Override
    public void execute(List<LabWork> list) {
        if (DataBaseHandler.addUser(user)) answer = "Пользователь зарегистриворан";
        else answer = "Такое имя уже занято";
    }

    @Override
    public void printAnswer() {
        System.out.println(answer);
    }
}
