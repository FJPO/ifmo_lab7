package Lab7.ModuleServer;

public class Main {

    final static int PORT = 7779;

    public static void main(String[] args) {

        new ServerSide().loop(PORT);

    }
}
