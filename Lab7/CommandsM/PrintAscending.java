package Lab7.CommandsM;

import Lab7.Source.LabWork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс, осуществлящщий фильтрацию элементов коллекции по заданной возрастанию
 */
public class PrintAscending extends Show {

    static class AscendingComportaor implements Comparator<LabWork>{
        @Override
        public int compare(LabWork lab, LabWork lab2) {
            return lab.getName().compareTo(lab2.getName()) ;
        }
    }

    @Override
    public String getName() {
        return "PRINT_ASCENDING";
    }

    @Override
    public void execute(List<LabWork> list) {
        ArrayList<LabWork> tempo = new ArrayList<>(list);
        tempo.sort(new AscendingComportaor());
        super.execute(tempo);
    }

    @Override
    public void printAnswer() {
        System.out.println("Коллекция отсортирована по возрастанию.(А-Я)");
        super.printAnswer();
    }
}
