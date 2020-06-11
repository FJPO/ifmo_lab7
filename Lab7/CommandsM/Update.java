package Lab7.CommandsM;

import Lab7.ModuleServer.DataBaseHandler;
import Lab7.Source.LabWork;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, осуществляющий перезапись элемента по его id
 */
public class Update extends AddElement {

    private int id;
    private String answer;

    @Override
    public String getName() {
        return "UPDATE";
    }

    @Override
    public void execute(List<LabWork> list) {
        //int[] index = list.stream().filter(element -> element.getId() == id).mapToInt(list::indexOf).toArray();
        LabWork lab = null;
        for (LabWork l : list)
            if(l.getId() == id) {
                lab = l;
                break;
            }

        if(lab != null) {
            if(!lab.getCreator().equals(getUser())){
                answer = "Недостаточно прав для изменения этого элемента";
                return;
            }
            DataBaseHandler.replaceLabWork(id, getToAdd());
            getToAdd().setId(id);
            list.add(list.indexOf(lab), getToAdd());
            list.remove(lab);

            answer = "Элемент обновлен";
        }
        else answer = "Такой элемент не найден";

    }

    @Override
    public void collectData(String arg) {
        try{
            id = Integer.parseInt(arg);
        } catch(NumberFormatException e){
            System.out.println("Введите id элемента:");
            collectData(new Scanner(System.in).nextLine());
            return;
        }
        super.collectData(arg);
    }

    @Override
    public void recordData(String arg) {
        try {
            id = Integer.parseInt(String.valueOf(arg.toCharArray(), 0, arg.indexOf(" ")));
            super.recordData(String.valueOf(arg.toCharArray(), arg.indexOf(" ") + 1, arg.length() - arg.indexOf(" ") - 1));
        }catch(Exception e){
            setIfCorrect(false);
        }
    }

    @Override
    public void printAnswer() {
        System.out.println(answer);
    }
}
