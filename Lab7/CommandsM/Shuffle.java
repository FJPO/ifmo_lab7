package Lab7.CommandsM;

import Lab7.CommandsM.General.Executable;
import Lab7.Source.LabWork;

import java.util.Collections;
import java.util.List;

/**
 * Класс, осуществляющий перемещивание элементов в случайном порядке
 */
public class Shuffle implements Executable {
    @Override
    public String getName() {
        return "SHUFFLE";
    }

    @Override
    public void execute(List<LabWork> list) {
        Collections.shuffle(list);
    }

    @Override
    public void printAnswer() {
        System.out.println("Элементы коллекции перемешаны");
    }
}
