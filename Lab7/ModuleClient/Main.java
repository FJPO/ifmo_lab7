package Lab7.ModuleClient;

public class Main {

    final static int PORT = 7788;
    final static String ADDRESS = "127.0.0.1";

    public static void main(String[] args){

        new ClientSide().loop(ADDRESS, PORT);


    }



}
