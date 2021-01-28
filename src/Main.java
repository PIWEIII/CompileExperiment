import javax.swing.*;
import java.awt.*;
import Frame.MyFrame;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/21/21:24
 * @Description:
 */
public class Main {
    public static void createGUI(){
        MyFrame frame = new MyFrame("编译简单分析器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置窗口大小
        frame.setSize(1550,720);
        //设置可见
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }
}
