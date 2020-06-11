package Lab7.ModuleServer;

import Lab7.CommandsM.Exit;
import Lab7.CommandsM.General.Executable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class InquiryInputHandler implements Runnable {
    private final SocketChannel channel;

    public InquiryInputHandler(SocketChannel channel){
        this.channel = channel;
    }

    @Override
    public void run() {
        if (channel == null) throw new IllegalArgumentException("Channel is null");
        try {

            while (!channel.socket().isClosed()) {
                Object object = receiveObject();
                if (!(object instanceof Executable)) continue;
                ServerSide.processingInquiryPool.execute(new InquiryProcessingHandler((Executable) object, channel));
                if(object instanceof Exit) break;
            }
        } catch (IOException e) {
        }
    }

    /**
     * Метод получает объект
     *
     * @return Объект, полученный от сервера
     */
    private Object receiveObject() throws IOException {
        if (channel == null) {
            System.err.println("Соединене не создано");
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        channel.read(buffer);


        try {
            ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return objStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }


}
