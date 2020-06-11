package Lab7.CommandsM;

import Lab7.CommandsM.General.Executable;
import Lab7.Source.LabWork;

import java.util.List;

/**
 * Класс, осущеествляющий вывод информацию о коллекции
 */
public class Info implements Executable {

    private String answer;

    @Override
    public String getName() {
        return "INFO";
    }

    @Override
    public void execute(List<LabWork> list) {

        if(list == null || list.size()==0){
            answer = "Пустая коллекция";
        }else {
            java.time.LocalDateTime minDate = list.get(0).getCreationDate();
            for (LabWork labWork : list)
                if (labWork.getCreationDate().compareTo(minDate) < 0) minDate = labWork.getCreationDate();

            answer = "Коллекция из элементов LabWork\nСоздана " + minDate + "\nРазмер: " + list.size() + " элементов";
        }
    }

    @Override
    public void printAnswer() {
        System.out.println(answer);
    }
}
