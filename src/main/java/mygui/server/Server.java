package mygui.server;

public class Server {
    public static void main(String[] args) {
        new MultiServerFrame();
        ServerHandler.listenToClientConnect();
    }
}
