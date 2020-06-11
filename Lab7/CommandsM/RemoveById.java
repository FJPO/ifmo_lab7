package Lab7.CommandsM;

import Lab7.CommandsM.General.ExecutableWithInput;
import Lab7.CommandsM.General.ExecutableWithRightsNeeded;
import Lab7.ModuleServer.DataBaseHandler;
import Lab7.Sercurity.User;
import Lab7.Source.LabWork;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, осуществлящий удаление элемента по его id
 */
public class RemoveById implements ExecutableWithInput, ExecutableWithRightsNeeded {

    private int id = -1;
    private String answer;
    private boolean success = true;
    private User user;

    @Override
    public String getName() {
        return "REMOVE_BY_ID";
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void execute(List<LabWork> list) {
        if(!DataBaseHandler.checkLoginAccuracy(user)){
            answer = "Неверный логин и пароль";
            return;
        }
        Object[] tempo = list.stream().filter(element -> element.getId() == id).toArray();
        if (tempo.length != 0) {
            if(!((LabWork)tempo[0]).getCreator().equals(user)){
                answer = "Недостаточно прав для удаления этого элемента";
                return;
            }
            DataBaseHandler.removeLabWork((LabWork) tempo[0]);
            list.remove(tempo[0]);
            answer = String.format("Элемент с id %d удален", id );
        } else answer = "Элемент с таким Id не найден";
    }

    @Override
    public void collectData(String arg) {
        recordData(arg);
        while(!isDataCorrect()){
            System.out.println("Введите id элемента:");
            collectData(new Scanner(System.in).nextLine());
        }

    }

    @Override
    public void recordData(String arg) {
        try {
            id = Integer.parseInt(arg);
            success = true;
        }catch(NumberFormatException e){
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
}
