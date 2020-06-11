package Lab7.ModuleServer;

import Lab7.CommandsM.General.Executable;
import java.nio.channels.SocketChannel;


import java.util.concurrent.RecursiveAction;

public class InquiryProcessingHandler extends RecursiveAction{
    private final Executable command;
    private final SocketChannel channel;

    public InquiryProcessingHandler(Executable command, SocketChannel channel){
        this.command = command;
        this.channel = channel;
    }
    @Override
    protected void compute() {
        command.execute(ServerSide.list);
        System.out.println("Выполнена команда " + command.getName());
        new InquiryAnswerSendHandler(command, channel).start();
    }
}

