import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private Socket socket;
    private ClientSideConnection player2;
    public List<ReceiveListener> listeners;


    public Client(){
        this.listeners = new ArrayList<>();
    }

    public void addReceiveListener(ReceiveListener listener)
    {
        listeners.add(listener);
        player2.setListeners(listeners);
    }

    public void writeIntCells(boolean isLeft, int myX,int myY){
        player2.writeIntData(isLeft,myX,myY);
    }

    public boolean[][] getBoard(){
        return player2.getObjIn();
    }


    public void requestConnection(String host,int port){

        System.out.println("----Client-----");
        try
        {

            socket = new Socket(host,port);
            player2 = new ClientSideConnection(socket);

        }
        catch (IOException ex)
        {
            System.out.println("Exception from client side connection constructor");
        }

    }
    public void requestingData() {
        Thread t = new Thread(player2);
        t.start();
    }

    private static class ClientSideConnection implements Runnable {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private ObjectInputStream objIn;
        private List<ReceiveListener> listeners;

        public ClientSideConnection(Socket s)
        {
            socket=s;

            try
            {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                objIn = new ObjectInputStream(socket.getInputStream());
            }
            catch (IOException ex)
            {
                System.out.println("Exception from csc constructor");
            }
        }

        public void run()
        {
            try
            {

                while(true) {
                    while (dataIn.available() > 0){

                        boolean leftOrRight = dataIn.readBoolean();
                        int oppX = dataIn.readInt();
                        int oppY = dataIn.readInt();
                        boolean received = dataIn.readBoolean();

                        if(received) {
                            for (ReceiveListener listener : listeners)
                                listener.ReceiveData(leftOrRight, oppX, oppY);
                        }
                        received =false;
                    }
                }
            }
            catch (IOException ex)
            {
                System.out.println("Exception from run() ");
            }
        }

        public void setListeners(List<ReceiveListener> listeners) {

            this.listeners = listeners;
        }

        public void writeIntData(boolean LOrR, int myX,int myY){
            try
            {
                dataOut.writeBoolean(LOrR);
                dataOut.writeInt(myX);
                dataOut.writeInt(myY);
                dataOut.writeBoolean(true);
                dataOut.flush();
            }
            catch (IOException ex)
            {
                System.out.println("Exception from client side write data");
            }
        }

        public boolean[][] getObjIn(){
            boolean[][] board = new boolean[0][];
            try {

                Board inBoard = (Board)objIn.readObject();
                board = inBoard.getBoard();

                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j])
                            System.out.print("1");
                        else
                            System.out.print("0");
                    }
                    System.out.println();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return board;
        }
    }


}
