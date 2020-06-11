package Lab7.ModuleClient;

import Lab7.CommandsM.ExecuteScript;
import Lab7.CommandsM.Exit;
import Lab7.CommandsM.General.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Основной класс клиента
 */
public class ClientSide {

    private InputStream input = null;
    private OutputStream output = null;
    private String address = null;
    private int port = -1;

    /**
     * Метод, отвечающий за общую работу клиента
     * @param address String-адрес сервера
     * @param port Номер порта сервера
     */
    public void loop(String address, int port){

        this.address = address;
        this.port = port;
        System.out.printf("Подключение по адресу %s и порту %d ...\n", address, port);

        try(Socket socket = new Socket(address, port);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream()
        ){
            this.input = input;
            this.output = output;

            System.out.println("Подключено");

            Scanner scanner = new Scanner(System.in);

            while(socket.isConnected()){
                System.out.println("Введите команду");
                String line = scanner.nextLine();
                String commandName, arg;
                if(line.contains(" ")){
                    commandName = String.valueOf(line.toCharArray(), 0, line.indexOf(" ")).trim();
                    arg = String.valueOf(line.toCharArray(), line.indexOf(" ") + 1,
                            line.length() - line.indexOf(" ") - 1);
                }
                else{
                    commandName = line;
                    arg = "";
                }
                Executable command = CommandKeeper.getPreparedCommand(commandName, arg);
                if(command == null){
                    System.out.println("Команда не найдена");
                    continue;
                }

                performCommand(command);
            }


        }catch (Exception e) {
            errorHandler(e);
        }
        finally{
            try {
                sendObject(new Exit());
            } catch (IOException e) {}
        }
    }

    /**
     * Метод отправляет объект на сервер
     * @param obj Объект, который должен быть отправлен
     */
    private void sendObject(Object obj) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objOutput = new ObjectOutputStream(byteStream);
        objOutput.writeObject(obj);
        output.write(byteStream.toByteArray());
    }

    /**
     * Метод получает объект
     * @return Объект, полученный от сервера
     */
    private Object receiveObject() throws IOException, ClassNotFoundException{
        ObjectInputStream objInput = new ObjectInputStream(input);
        return objInput.readObject();
    }

    /**
     * Метод отправляет команду, получает ответ и выводит ео в консоль
     * @param command команда для отправки
     */
    private void performCommand(Executable command) throws IOException, ClassNotFoundException{
        if(command instanceof ExecutableCommandMaker) {
            for (Executable tiny : ((ExecutableCommandMaker) command).getCommands()) performCommand(tiny);
            FileOpenedController.removeFromOpened(((ExecuteScript) command).getFileReading().getName());
        }
        else{
            sendObject(command);
            Executable received = ((Executable)receiveObject());
            received.printAnswer();
        }

    }


    /**
     * Обрабатывает ошибки
     */
    private void errorHandler(Exception e){
        System.err.println("Произвошла ошибка");
        if(e instanceof ClassNotFoundException) {
            System.err.println("Произошла ошибка целостности пакета, нужная команда не найдена на стороне сервера");
        } else if(e instanceof IOException){
            System.out.println("Выберите действие:\n(1)Повторить попытку соединения\n(2)Изменить адрес и порт");
            int answer;
            while(true) {
                try {
                    answer = Integer.parseInt(new Scanner(System.in).nextLine());
                    break;
                } catch (Exception ex){
                    System.out.println("Введите 1 или 2");
                }
            }
            if(answer == 1) loop(address, port);
            else if( answer == 2){
                setAndRestart();
            }else{
                System.out.println("Такого варианта нет");
                errorHandler(e);
            }
        } else{
            System.err.println("Произошла неизвестная ошибка");
            e.printStackTrace();
        }
    }

    /**
     * Переустанавливает адрес и порт и перезапускает клиент
     */
    private void setAndRestart(){
        Scanner scanner = new Scanner(System.in);
        int port; String address;

        while (true) {
            try {
                System.out.println("Введите адрес:");
                address = scanner.nextLine();
                break;
            } catch (Exception ignored) {}
        }
        while (true){
            try {
                System.out.println("Введите номер порта:");
                port = Integer.parseInt(scanner.nextLine());
                break;
            }catch(Exception ignored){}
        }
        loop(address, port);
    }

}
