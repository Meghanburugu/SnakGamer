import javax.swing.JFrame;
import java.awt.*;

public class Frame extends JFrame{
    Frame(){
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new Panel());
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

    }

}
