import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private ServerSocket serverSocket;
    private int numplayers;
    private ServerSideConnection player1;
    public List<ReceiveListener> listeners;



    public void addReceiveListener(ReceiveListener listener)
    {
        listeners.add(listener);
        player1.setListeners(listeners);
    }

    public void writeIntCells(boolean isLeft, int myX,int myY){
        player1.writeIntData(isLeft,myX,myY);
    }

    public Server(){
        System.out.println("-----Game Server-----");
        this.numplayers =1;
        this.listeners = new ArrayList<>();

        try
        {
            serverSocket=new ServerSocket(51734);
        }
        catch (IOException ex)
        {
            System.out.println("Exception from constructor");
        }
    }

    public void acceptConnection(boolean[][] mines)
    {
        try
        {
            System.out.println("Waiting for connections....");
            while(numplayers<2)
            {
                Object cancel="Mégse";
                JOptionPane.showMessageDialog(null,"Várakozás a másik játékosra...","Kapcsolódás",JOptionPane.INFORMATION_MESSAGE);
                Socket socket=serverSocket.accept();
                numplayers++;
                System.out.println("Player"+numplayers+". has connected");
                player1 = new ServerSideConnection(socket,mines);
                Thread t = new Thread(player1);
                t.start();

            }
            System.out.println("Max num of players. No longer accepting connections.");
        }
        catch (IOException ex)
        {
            System.out.println("Exception from acceptConnecton()");
        }
    }


     private static class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private ObjectOutputStream objOut;
        private List<ReceiveListener> listeners;

        public ServerSideConnection(Socket s,boolean[][] mines)
        {
            socket=s;

            try
            {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                objOut= new ObjectOutputStream(socket.getOutputStream());
                sendBoard(mines);
            }
            catch (IOException ex)
            {
                System.out.println("Exception from ssc constructor");
            }
        }

        public void run()
        {
            try
            {

                while(true) {
                    while (dataIn.available() > 0){
                        boolean LeftOrRight=dataIn.readBoolean();
                        int x = dataIn.readInt();
                        int y = dataIn.readInt();
                        boolean received = dataIn.readBoolean();

                        if(received) {
                            for (ReceiveListener listener : listeners)
                                listener.ReceiveData(LeftOrRight,x, y);
                        }
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

         public void sendBoard(boolean[][] mines){
            try {

                Board board = new Board(mines);
                for (int i = 0; i < mines.length; i++) {
                    for (int j = 0; j < mines[i].length; j++) {
                        if (mines[i][j])
                            System.out.print("1");
                        else
                            System.out.print("0");
                    }
                    System.out.println();
                }
                objOut.writeObject(board);

            } catch (IOException e) {
                e.printStackTrace();
            }


         }


     }



}
