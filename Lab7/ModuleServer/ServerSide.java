package Lab7.ModuleServer;

import Lab7.Source.LabWork;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Основной класс сервера
 */
public class ServerSide {

    protected static ExecutorService inquiryReaderPool;
    protected static ForkJoinPool processingInquiryPool;

    protected static List<LabWork> list;

    //Многопоточный
    public void loop(int port){



        inquiryReaderPool = Executors.newFixedThreadPool(101);
        processingInquiryPool = ForkJoinPool.commonPool();



        list = Collections.synchronizedList(DataBaseHandler.getLabCollection());
        System.out.println("Коллекция загружена из датабазы");
        System.out.printf("Адрес %s; порт %d\n", "127.0.0.1", port);
        System. out.println("\nДля регистрации онвого пользователья используйте команду REGISTER, команды, изменяющие коллекцию, требубют аутентификации");

        try(ServerSocketChannel channel = ServerSocketChannel.open().bind(new InetSocketAddress(port))) {
            for(;;){
                SocketChannel channel1 = channel.accept();
                InquiryInputHandler input = new InquiryInputHandler(channel1);
                inquiryReaderPool.execute(input);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
