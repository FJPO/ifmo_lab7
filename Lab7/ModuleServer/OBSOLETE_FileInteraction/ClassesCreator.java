package Lab7.ModuleServer.OBSOLETE_FileInteraction;

import Lab7.Sercurity.User;
import Lab7.Source.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
Создает классы элементов, получая на вход экземпляр BufferedReader
 */
public class ClassesCreator {
    public static Coordinates createCoordinates(BufferedReader reader) throws IOException {
        Float x = Float.parseFloat(XmlTools.getData(reader.readLine()));
        int y = Integer.parseInt(XmlTools.getData(reader.readLine()));
        reader.readLine();

        return new Coordinates(x, y);
    }

    public static Difficulty createDifficulty(BufferedReader reader) throws IOException{
        Difficulty difficulty = Difficulty.valueOf(reader.readLine());
        reader.readLine();
        return difficulty;
    }

    public static Location createLocation(BufferedReader reader) throws IOException{
        Float x = Float.parseFloat(XmlTools.getData(reader.readLine()));
        int y = Integer.parseInt(XmlTools.getData(reader.readLine()));
        Long z = Long.parseLong(XmlTools.getData(reader.readLine()));
        reader.readLine();
        return new Location(x, y, z);

    }

    public static Person createPerson(BufferedReader reader) throws IOException{
        String name = XmlTools.getData(reader.readLine());
        long weight = Long.parseLong(XmlTools.getData(reader.readLine()));
        reader.readLine();
        Location location = createLocation(reader);
        reader.readLine();
        return new Person(name, weight, location);
    }

    public static LabWork createLabWork(BufferedReader reader) throws IOException{

        int id = Integer.parseInt(XmlTools.getData(reader.readLine()));
        String name = XmlTools.getData(reader.readLine());
        reader.readLine();
        Coordinates coordinates = createCoordinates(reader);


        java.time.LocalDateTime creationTime = java.time.LocalDateTime.parse(XmlTools.getData(reader.readLine()));

        Double minimalPoint = Double.parseDouble(XmlTools.getData(reader.readLine()));
        int tunedInWorks = Integer.parseInt(XmlTools.getData(reader.readLine()));
        Double averagePoint = Double.parseDouble(XmlTools.getData(reader.readLine()));
        reader.readLine();
        Difficulty difficulty = createDifficulty(reader);
        reader.readLine();
        Person author = createPerson(reader);
        reader.readLine();
        return new LabWork(id, name, coordinates, creationTime,
                minimalPoint, tunedInWorks, averagePoint, difficulty, author, new User("SuperUser", "qwerty"));
    }
}
