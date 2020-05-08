import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main implements Runnable{

    GUI gui=new GUI();






    public static void main(String[] args) {
        Main m=new Main();
        new Thread(m).start();

    }

    @Override
    public void run() {

    while (true) {
        gui.repaint();



        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    }
}

