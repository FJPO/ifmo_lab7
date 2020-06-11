package Lab7.ModuleServer.OBSOLETE_FileInteraction;

import Lab7.Source.LabWork;
import Lab7.Source.Xml_Interchangeable;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 Класс отвечает за работу с файлом
 */
public class FileInteractor {

    private static String path = null;

    public static String getCurrentFile(){
        return path;
    }

    /**
     * Извлекает коллекцию элементов LabWork из файла
     * @return ArrayList из элементов LabWork
     */
    public static ArrayList<LabWork> readData(String path) throws IOException {

        class LabDateCreationComparator implements Comparator<LabWork> {
            @Override
            public int compare(LabWork labWork, LabWork labWork2) {
                return labWork.getCreationDate().compareTo(labWork2.getCreationDate());
            }
        }

        FileInteractor.path = path;
        if (!(new File(path).exists())) throw new IOException();
        if (new File(path).length() == 0) return new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<LabWork> elementsList = new ArrayList<>();
            String line;
            String closeTag = "Elements";
            if ((!"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>".equals(reader.readLine()) || (!"<Elements>".equals(reader.readLine())))) {
                throw new IOException();
            }

            while (!closeTag.equals(XmlTools.getTag(line = reader.readLine())))
                if (XmlTools.getTag(line).equals("LabWork")) {
                    elementsList.add(ClassesCreator.createLabWork(reader));
                }
            reader.close();
            elementsList.sort(new LabDateCreationComparator());
            return elementsList;
        }catch(Exception e){
            System.out.println("Ошибка чтения файла, создана пустая коллекция");
            return new ArrayList<>();
        }
    }


    /**
     * Сохраняет коллекцию в файл
     * @param elements ArrayList из жлементов LabWork
     * @throws IOException елси во время записи данных произойдет ошибка
     */
    public static void writeData(List<LabWork> elements, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        writer.write("<Elements>\n");
        for (Object element: elements){
            writer.write(((Xml_Interchangeable)element).createXml());
        }
        writer.write("</Elements>");
        writer.close();
    }
}
