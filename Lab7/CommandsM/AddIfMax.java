package Lab7.CommandsM;

import Lab7.Source.Difficulty;
import Lab7.Source.LabWork;

import java.util.List;

/**
 * Класс, осуществляющий добавление в коллекцию при условии, что доавляемый элемент максимальный по сложности
 */
public class AddIfMax extends AddElement {

    @Override
    public String getName() {
        return "ADD_IF_MAX";
    }


    @Override
    public void execute(List<LabWork> list) {
        Difficulty difficulty = getToAdd().getDifficulty();
        if (list.stream().noneMatch(element -> {
            if(element.getDifficulty() != null) return element.getDifficulty().compareTo(difficulty) > 0;
            else return false;
        }))
            super.execute(list);
        else
            setAnswer("Элемент не максимальный по сложности, он не добавлен");

    }
}
