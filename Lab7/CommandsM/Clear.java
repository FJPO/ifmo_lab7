package Lab7.CommandsM;

import Lab7.CommandsM.General.Executable;
import Lab7.CommandsM.General.ExecutableWithRightsNeeded;
import Lab7.ModuleServer.DataBaseHandler;
import Lab7.Sercurity.User;
import Lab7.Source.LabWork;

import java.util.List;

/**
 * Класс, осуществляющий очищение коллекции
 */
public class Clear implements Executable, ExecutableWithRightsNeeded {
    private User user;
    private String answer;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return "CLEAR";
    }

    @Override
    public void execute(List<LabWork> list) {
        if(!DataBaseHandler.checkLoginAccuracy(user)){
            answer = "Неверный логин и пароль";
            return;
        }
        for(int i = list.size()-1; i>=0; i--){
            if(list.get(i).getCreator().equals(user)){
                DataBaseHandler.removeLabWork(list.get(i));
                list.remove(i);
            }
        }
        answer = "Ваши элементы удалены из данной коллекции";

    }

    @Override
    public void printAnswer() {
        System.out.println(answer);
    }
}
