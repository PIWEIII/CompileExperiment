package Frame;

import Experiment1.LexicalMachine;
import Experiment1.TypeNStr;
import Experiment2.SDTMachine;
import Experiment3.SLAMachine;
import Experiment4.SyntaxMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/21/22:51
 * @Description:
 */
public class MyFrame extends JFrame {
    //标签
    JLabel label1 = new JLabel("实验1：词法分析");
    JLabel label2 = new JLabel("实验2：语法制导翻译");
    JLabel label3 = new JLabel("实验3：说明语句的词法分析");
    JLabel label4 = new JLabel("实验4：预测分析");
    //文本输入，宽度显示
    JTextField textField1 = new JTextField(16);
    JTextField textField2 = new JTextField(16);
    JTextField textField3 = new JTextField(16);
    JTextField textField4 = new JTextField(16);
    //按钮
    JButton myButton1 = new JButton("处理");
    JButton myButton2 = new JButton("处理");
    JButton myButton3 = new JButton("处理");
    JButton myButton4 = new JButton("处理");
    //显示控件
    JTextArea forshow = new JTextArea("结果：");


    public MyFrame(String title){
        super(title);

        //内容面板
        Container contentPane = getContentPane();

        //面板布局
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        contentPane.setLayout(layout);

        //实验1控件
        contentPane.add(label1);
        contentPane.add(textField1);
        contentPane.add(myButton1);

        contentPane.add(label2);
        contentPane.add(textField2);
        contentPane.add(myButton2);

        contentPane.add(label3);
        contentPane.add(textField3);
        contentPane.add(myButton3);

        contentPane.add(label4);
        contentPane.add(textField4);
        contentPane.add(myButton4);


        //显示控件
        forshow.setLineWrap(true);
        forshow.setColumns(32);
        contentPane.add(forshow);

        //转交监听器
        myButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonOK1();
            }
        });
        myButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonOK2();
            }
        });
        myButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonOK3();
            }
        });
        myButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onButtonOK4();
            }
        });

    }

    //点击按钮事件处理
    private void onButtonOK1() {
        LexicalMachine LM = new LexicalMachine();
        String str = textField1.getText();
        List<TypeNStr> result = LM.LexicalAnalysis(str);
        System.out.println(result.toString());
        forshow.setText("词法分析结果："+result.toString());
        System.out.println("输入了"+str);
    }

    private void onButtonOK2() {
        LexicalMachine LM = new LexicalMachine();
        SDTMachine SDTM = new SDTMachine();
        String str = textField2.getText();
        List<TypeNStr> result = LM.LexicalAnalysis(str);
        forshow.setText("后缀表达式："+SDTM.SDTAnalysis(result));
        System.out.println(SDTM.SDTAnalysis(result));
    }

    private void onButtonOK3() {
        SLAMachine SLM = new SLAMachine();
        String str = textField3.getText();
        List<TypeNStr> result = SLM.LexicalAnalysis(str);
        forshow.setText(result.toString()+"请在控制台检查进一步成果");
        SLM.StatementManager(result);
    }

    private void onButtonOK4() {
        SyntaxMachine SM = new SyntaxMachine();
        String str = textField4.getText();
        SM.SyntaxAnalysis(str);
        forshow.setText("请在控制台查看结果！");
    }
}
