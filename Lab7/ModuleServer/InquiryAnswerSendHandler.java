package Lab7.ModuleServer;

import Lab7.CommandsM.General.Executable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class InquiryAnswerSendHandler extends Thread {
    private final Executable command;
    private final SocketChannel channel;

    public InquiryAnswerSendHandler(Executable command, SocketChannel channel){
        this.command = command;
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            sendObject(command);
        } catch (IOException e) {
            System.out.println("Проблемы в передаче данных");
        }
    }

    private void sendObject(Object obj) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteArray);
        stream.writeObject(obj);
        channel.write(ByteBuffer.wrap(byteArray.toByteArray()));
    }
}
