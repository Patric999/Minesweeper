import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import  java.awt.*;


public class GUI extends JFrame{

    int spacing=2;

    Random rand = new Random();
    Date start=new Date();

    public long sec=0;
    public long min=0;

    public boolean defeat=false;
    public boolean victory=false;

    int minex;
    int miney;
    int numofmines = 10;
    int cellx=9;
    int celly=16;

    public int GUIwidth=400;
    public int GUIheight=500;

    int[][] mines= new int[cellx][celly];
    int[][] neighbours = new int[cellx][celly];
    boolean[][] revealed= new boolean[cellx][celly];
    boolean[][] flags= new boolean[cellx][celly];
    int numofrev=0;

    public GUI() {
        this.setTitle("BRSZTA 1.cs Aknakereső");
        this.setSize(GUIwidth, GUIheight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        for (int i = 0; i < numofmines; i++) {
            do {
                minex = rand.nextInt(cellx);
                miney = rand.nextInt(celly);
            } while (mines[minex][miney] == 1);
            mines[minex][miney] = 1;

            addNeighbours(minex, miney);

        }


        IBoard board = new IBoard();
        this.setContentPane(board);

        Mouse move = new Mouse();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

    }

    public class IBoard extends JPanel{
        public void paintComponent(Graphics g) {


            g.setColor(Color.gray);
            g.fillRect(0, 0, GUIwidth, GUIheight);
            Color myBlue = new Color(107, 168, 235);

            if (defeat == false && victory==false) {
                setTime();
            }
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.setColor(Color.RED);

                if (sec < 10) {
                    g.drawString(Long.toString(min) + ":0" + Long.toString(sec), GUIwidth - 65, 40);
                } else {
                    g.drawString(Long.toString(min) + ":" + Long.toString(sec), GUIwidth - 65, 40);
                }






            for (int i=0;i<cellx;i++) {
                for (int j = 0; j < celly; j++) {
                    if (revealed[i][j]) {
                        if (mines[i][j] == 1) {
                            g.setColor(Color.RED);
                            g.fill3DRect(spacing + i * 25, 40 + spacing + j * 25, 25 - 2 * spacing, 25 - 2 * spacing, true);
                            g.setColor(Color.BLACK);
                            g.fillArc(spacing + i * 25 + 2, 40 + spacing + j * 25 + 2, 17, 17, 0, 360);
                            /////vége
                        } else {

                            g.setFont(new Font("Tahoma", Font.BOLD, 10));
                            switch (neighbours[i][j]) {
                                case 0:
                                    g.setColor(Color.BLUE);
                                    break;
                                case 1:
                                    g.setColor(Color.RED);
                                    break;
                                case 2:
                                    g.setColor(Color.orange);
                                    break;
                                case 3:
                                    g.setColor(Color.GREEN);
                                    break;
                                case 4:
                                    g.setColor(Color.CYAN);
                                    break;
                                case 5:
                                    g.setColor(Color.magenta);
                                    break;
                                case 6:
                                    g.setColor(Color.pink);
                                    break;
                                case 7:
                                    g.setColor(Color.BLACK);
                                    break;
                                case 8:
                                    g.setColor(Color.RED);
                                    break;
                            }
                            g.drawString(Integer.toString(neighbours[i][j]), spacing + i * 25 + (25 - 2 * spacing) / 3, 40 + spacing + j * 25 + (25 - 2 * spacing) * 2 / 3);
                            g.setColor(myBlue);
                            g.draw3DRect(spacing+i*25,40+spacing+j*25,25-2*spacing,25-2*spacing,true);
                        }

                    }
                    else{
                        g.setColor(myBlue);
                        g.fill3DRect(spacing+i*25,40+spacing+j*25,25-2*spacing,25-2*spacing,true);
                    }
                    if(flags[i][j]){
                        g.setColor(Color.orange);
                        g.fill3DRect(spacing+i*25,40+spacing+j*25,25-2*spacing,25-2*spacing,true);
                        g.setColor(Color.BLACK);
                        g.fill3DRect(spacing+i*25+10,40+spacing+j*25+4,2,16,true);
                        g.setColor(Color.red);
                        int[] x={spacing+i*25+10+2,spacing+i*25+10-8,spacing+i*25+10+2};
                        int[] y={40+spacing+j*25+2, 40+spacing+j*25+5, 40+spacing+j*25+8 };
                        g.fillPolygon(x,y,3);
                    }




                }
            }
            if (defeat) {
                g.setColor(Color.black);
                g.fill3DRect((GUIwidth-180)/2,GUIheight/2-40,160,50,true);
                g.setColor(Color.RED);
                g.setFont(new Font("Tahoma", Font.BOLD, 40));
                g.drawString("Balfasz", (GUIwidth-160)/2, GUIheight/2);
            }
            if (victory) {
                g.setColor(Color.black);
                g.fill3DRect((GUIwidth-180)/2,GUIheight/2-40,160,50,true);
                g.setColor(Color.RED);
                g.setFont(new Font("Tahoma", Font.BOLD, 40));
                g.drawString("Grat!", (GUIwidth-160)/2, GUIheight/2);
            }

        }
    }

    public class Mouse implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
    public class Click implements MouseListener{

        int x;
        int y;
        int nx;
        int ny;

        @Override
        public void mouseClicked(MouseEvent e) {

            if (SwingUtilities.isLeftMouseButton(e)) {
                x = e.getX();
                y = e.getY();

                nx = getCellx(x);
                ny = getCelly(y);


                if (defeat || victory){
                    reset();
                }else {

                if (nx != -1 && ny != -1 && (!revealed[nx][ny]) && (!flags[nx][ny]) && !defeat && !victory)  {
                    revealed[nx][ny] = true;
                    numofrev++;
                    if (neighbours[nx][ny] == 0) {

                        revealNeighbours(nx, ny);


                    }
                    if(mines[nx][ny] == 1){
                        defeat=true;
                        for (int i=0;i<cellx;i++) {
                            for (int j = 0; j < celly; j++) {
                                if (mines[i][j] == 1) {
                                    revealed[i][j]=true;
                                }
                            }
                        }

                    }
                    if(numofrev==cellx*celly-numofmines)
                    {
                        victory=true;
                    }
                }
                }
            }
            if (SwingUtilities.isRightMouseButton(e)){
                x = e.getX();
                y = e.getY();

                nx = getCellx(x);
                ny = getCelly(y);
                if (nx != -1 && ny != -1 && (!revealed[nx][ny]) && !defeat && !victory) {
                    if (flags[nx][ny]) {
                        flags[nx][ny] = false;

                    }
                    else
                    {
                        flags[nx][ny] = true;
                    }

                }

            }
        }


        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }


    public int getCellx(int x) {
        for(int i=0;i<cellx;i++) {
            if (x>=spacing+i*25+8 && x<= spacing+i*25+25-2*spacing+8){
                return i;
            }
        }
        return -1;
    }

    public int getCelly(int y) {
        for(int i=0;i<celly;i++) {
            if (y>=40+spacing+i*25+30 && y<= 40+spacing+i*25+25-2*spacing+30){
                return i;
            }
        }
        return -1;
    }

    public void addNeighbours(int minex,int miney){


        int x;
        int y;
        int xh;
        int yh;

        if(minex==0){
            x=0;
            xh=1;
        }
        else if (minex==(cellx-1)){
            x=-1;
            xh=0;
        }
        else{
            x=-1;
            xh=1;
        }


        while (x != xh+1) {

            if(miney==0){
                y=0;
                yh=1;
            }
            else if(miney==(celly-1)){
                y=-1;
                yh=0;
            }
            else{
                y=-1;
                yh=1;
            }

            while (y != yh+1) {
                if(!(x==0 && y==0)) {
                    neighbours[minex + x][miney + y]++;

                }

                y++;
            }
            x++;
        }

    }

    public void revealNeighbours(int nx,int ny) {

            int x;
            int y;
            int xh;
            int yh;

            if (revealed[nx][ny]==true && neighbours[nx][ny]==0) {
            if (nx == 0) {
                x = 0;
                xh = 1;
            } else if (nx == (cellx - 1)) {
                x = -1;
                xh = 0;
            } else {
                x = -1;
                xh = 1;
            }


            while (x != xh + 1) {

                if (ny == 0) {
                    y = 0;
                    yh = 1;
                } else if (ny == (celly - 1)) {
                    y = -1;
                    yh = 0;
                } else {
                    y = -1;
                    yh = 1;
                }

                while (y != yh + 1) {
                    if (!(x == 0 && y == 0) && (revealed[nx + x][ny + y] == false)) {
                        revealed[nx + x][ny + y] = true;
                        numofrev++;
                        revealNeighbours(nx + x,ny + y);
                    }

                    y++;
                }
                x++;
            }
        }


    }

    public void setTime(){

        sec=((new Date().getTime()-start.getTime())/1000)%60;
        min=((new Date().getTime()-start.getTime())/1000)/60;

    }

    public void reset(){
        for (int i=0;i<cellx;i++) {
            for (int j = 0; j < celly; j++) {
                revealed[i][j] = false;
                flags[i][j] = false;
                mines[i][j]=0;
                neighbours[i][j]=0;
            }
        }
        start=new Date();
        numofrev=0;
        defeat=false;
        victory= false;
        for (int i = 0; i < numofmines; i++) {
            do {
                minex = rand.nextInt(cellx);
                miney = rand.nextInt(celly);
            } while (mines[minex][miney] == 1);
            mines[minex][miney] = 1;

            addNeighbours(minex, miney);

        }
    }

}
