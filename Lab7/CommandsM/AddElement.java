package Lab7.CommandsM;

import Lab7.CommandsM.General.ExecutableWithRightsNeeded;
import Lab7.CommandsM.General.ExecutableWithInput;
import Lab7.ModuleServer.DataBaseHandler;
import Lab7.Sercurity.User;
import Lab7.Source.*;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, осуществляющий добавление в коллекцию
 */
public class AddElement implements ExecutableWithInput, ExecutableWithRightsNeeded {

    private LabWork toAdd;
    private String answer;
    private boolean success = true;
    private User user = null;

    @Override
    public String getName() {
        return "ADD";
    }

    @Override
    public void execute(List<LabWork> list) {
        System.out.println("Начали");
        if(!DataBaseHandler.checkLoginAccuracy(user)){
            answer = "Неверный логин и пароль";
            return;
        }
        list.add(toAdd);
        System.out.println("перед добавлением");
        DataBaseHandler.addLabWork(toAdd);

        System.out.println("Добавлили");
        answer = "Новый элемент добавлен в коллекцию";

    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    protected User getUser(){
        return user;
    }

    @Override
    public void collectData(String arg) {

        if(user == null) throw new IllegalArgumentException();

        Scanner scanner;
        String[] line;
        String labName = null;
        Coordinates coordinates = null;
        Double minimalPoint = null;
        Integer tunedInWorks = null;
        Double averagePoint = null;
        Difficulty difficulty = null;
        Location location = null;
        Person author;
        String authorName = null;
        Long authorHeight = null;

        int errorTag = 0;
        for (; ; ) {
            try {
                scanner = new Scanner(System.in);
                if (errorTag == 0) {
                    System.out.print("Введите имя: ");
                    labName = scanner.nextLine();
                    if (labName.equals("")) {
                        System.out.println("Имя не может быть null");
                        continue;
                    }
                    ++errorTag;
                } else System.out.println("Имя: " + labName);

                if (errorTag == 1) {
                    System.out.print("\nВведите координаты x и y через пробел: ");
                    line = scanner.nextLine().split(" ");
                    if (Integer.parseInt(line[1]) > 668) {
                        System.out.println("Поле y не может быть больше 668");
                        continue;
                    }
                    if (Float.parseFloat(line[0]) > 226) {
                        System.out.println("Поле не может быть больше 226");
                        continue;
                    }
                    coordinates = new Coordinates(Float.parseFloat(line[0]), Integer.parseInt(line[1]));
                    ++errorTag;
                } else System.out.println(coordinates);

                if (errorTag == 2) {
                    System.out.print("\nВведите минимальный балл: ");
                    minimalPoint = Double.parseDouble(scanner.nextLine());
                    if (minimalPoint <= 0) {
                        System.out.println("Поле не может быть меньше нуля");
                        minimalPoint = null;
                        continue;
                    }
                    ++errorTag;
                } else System.out.println("Минимальный балл: " + minimalPoint);

                if (errorTag == 3) {
                    System.out.print("\nВведите значение поля tunedInWorks: ");
                    tunedInWorks = Integer.parseInt(scanner.nextLine());
                    ++errorTag;
                } else System.out.println("tunedInworks: " + tunedInWorks);

                if (errorTag == 4) {
                    System.out.print("\nВведите средний балл: ");
                    String tempoLine = scanner.nextLine();
                    if (!tempoLine.equals("")) {
                        averagePoint = Double.parseDouble(tempoLine);
                        if (averagePoint <= 0) {
                            System.out.println("Поле не может быть меньше нуля");
                            averagePoint = null;
                            continue;
                        }
                    }
                    ++errorTag;
                } else System.out.println("Средний балл: " + averagePoint);

                if (errorTag == 5) {
                    System.out.print("\nВведите сложность: (на английском)\n");
                    for (Difficulty dif : Difficulty.values()) System.out.println(dif );
                    String tempo = scanner.nextLine();
                    if (!tempo.equals("")) difficulty = Difficulty.valueOf(tempo.toUpperCase());
                    ++errorTag;
                } else System.out.println(difficulty);

                if (errorTag == 6) {
                    System.out.print("\nВведите имя автора: ");
                    authorName = scanner.nextLine();
                    if (authorName.equals("")) {
                        System.out.println("Имя не может быть null");
                        authorName = null;
                        continue;
                    }
                    ++errorTag;
                } else System.out.println("Имя автора: " + authorName);

                if (errorTag == 7) {
                    System.out.print("\nВведите рост автора: ");
                    authorHeight = Long.parseLong(scanner.nextLine());
                    if (authorHeight < 0) {
                        System.out.println("Рост не сожет быть меньше нуля");
                        authorHeight = null;
                        continue;
                    }
                    ++errorTag;
                } else System.out.println("Рост автора: " + authorHeight);

                if (errorTag == 8) {
                    System.out.print("\nВведите местонахождение автора(x, y, z через пробел): ");
                    line = scanner.nextLine().split(" ");
                    location = new Location(Float.parseFloat(line[0]), Integer.parseInt(line[1]), Long.parseLong(line[2]));
                    ++errorTag;
                }
                if (errorTag == 9){
                    author = new Person(authorName, authorHeight, location);
                    toAdd = new LabWork(labName, coordinates, minimalPoint, tunedInWorks, averagePoint, difficulty, author, user);
                    break;
                }
            } catch (Exception e) {
                if (errorTag == 9)
                    System.out.println("Произошла внутренняя ошибка работы программы");
                System.out.println("\nОшибка ввода!\n");

            }
        }
    }

    @Override
    public void recordData(String arg) {
        try {

            String[] args = arg.split(" ");

            for (Difficulty dif : Difficulty.values())
                if (dif.getName().toUpperCase().equals(args[6])) args[6] = dif.name();
            Difficulty tempoDif;
            if (args[6].equals("")) tempoDif = null;
            else tempoDif = Difficulty.valueOf(args[6].toUpperCase());

            if ((args[0].equals("")) || (Float.parseFloat(args[1]) > 226) || (Integer.parseInt(args[2]) > 668) ||
                    (Double.parseDouble(args[3]) < 0) || (args[7].equals("")) || (Long.parseLong(args[8]) < 0)) return;

            if (!(args[5].equals("")) && (Double.parseDouble(args[5]) < 0)) return;


            toAdd = new LabWork(args[0],
                    new Coordinates(Float.parseFloat(args[1]), Integer.parseInt(args[2])),
                    Double.parseDouble(args[3]),
                    Integer.parseInt(args[4]),
                    (args[5].equals("")) ? null : Double.parseDouble(args[5]),
                    tempoDif,
                    new Person(args[7], Long.parseLong(args[8]),
                            new Location(Float.parseFloat(args[9]),
                                    Integer.parseInt(args[10]),
                                    Long.parseLong(args[11])
                            )), user);
        } catch (Exception e){
            success = false;
        }

    }

    @Override
    public void printAnswer() {
        System.out.println(answer);
    }

    @Override
    public boolean isDataCorrect() {
        return success;
    }

    protected void setIfCorrect(boolean b){
        success = b;
    }

    protected LabWork getToAdd() {
        return toAdd;
    }

    protected void setAnswer(String answer) {
        this.answer = answer;
    }
}
