import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Arrays;

public class Panel extends JPanel implements ActionListener {
    static final int WIDTH =600;
    static final int HEIGTH=600;

//how big we what the object in the game(materix format)
  static final int UNIT_SIZE=50;
  //how many objects we can actually fit on the screen
    static final int GAME_UNIT=(WIDTH*HEIGTH)/UNIT_SIZE;
    static final int DEALY=175;
    //arrays x,y these two arrays hold all of the codinents for all of the body parts of the snake including the head of the snake
    final int x[]=new int[GAME_UNIT];
    final int y[]=new int[GAME_UNIT];
    int bodyParts=6;
    int applesEaten;
    int applex;
    int appley;
    char direction='R';

     boolean running = false;
     Timer timer;
     Random random;

    Panel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGTH));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        random =new Random();
        startGame();
    }
public void startGame(){
        newApple();
        running =true;
    timer =new Timer(DEALY,this);
        timer.start();
}
public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){
           /*
        for(int i=0;i<SCREEN_HEIGTH/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGTH);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);

        }*/
            //apply colour
        g.setColor(Color.red);
        g.fillOval(applex,appley,UNIT_SIZE,UNIT_SIZE);
        //head of the snake and body
        for(int i=0;i<bodyParts;i++){
            if(i== 0){
                g.setColor(Color.black);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }
            else{
                g.setColor(new Color(50,80,0));
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);

            }
        }
        //score to increase
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score:"+applesEaten,(WIDTH-metrics.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());

        }
        else{
            gamerOver(g);
        }
    }


    public void newApple(){
    applex= random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley= random.nextInt((int)(HEIGTH/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for(int i =bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
            y[0]=y[0]-UNIT_SIZE;
            break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }

    }

    public void checkApple(){
        if((x[0]==applex) &&(y[0]==appley)){
              bodyParts++;
             applesEaten++;
            newApple();
            }

        }

    public void checkCollisions(){
        //checks if head collides with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        //check if head touches left border
        if(x[0]<0) {
            running =false;
        }
        //check if head touches rigth border
        if(x[0]>WIDTH){
            running=false;
        }
        //check if head touches top border
        if(y[0]<0) {
            running =false;
        }
        //check if head touches bottom border
        if(y[0]>HEIGTH) {
            running =false;
        }
        if(!running) {
            timer.stop();

        }
        }
    public void gamerOver(Graphics g){
        //display on the screen of Score
        g.setColor(Color.green);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score:"+applesEaten,(WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());

        //GameOver
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Game Over",(WIDTH-metrics2.stringWidth("Game Over"))/2,HEIGTH/2);

    }


//for moving the snake we use actionperforming method
    public void actionPerformed(ActionEvent e) {
    if(running){
        move();
        checkApple();
        checkCollisions();
    }
    repaint();
    }
    //to controll the snake direction
    public class MyKeyAdapter extends KeyAdapter implements KeyPressed {
        @Override
        public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(direction !='R'){
                    direction ='L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(direction !='L'){
                direction ='R';
            }
            break;
            case KeyEvent.VK_UP:
                if(direction !='D'){
                    direction ='U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(direction !='U'){
                    direction ='D';
                }
                break;


        }
        }
    }
}
