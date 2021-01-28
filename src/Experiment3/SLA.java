package Experiment3;

import Experiment1.LexicalMachine;
import Experiment1.TypeNStr;

import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/21/12:14
 * @Description:
 */
public class SLA {
    public static void main(String[] args) {
        SLAMachine SLM = new SLAMachine();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入代码段：");
        String test = "const count=10,sum=81.5,char1='f',max=169,str1=\"h*54 2..4S!AAsj\", char2='@',str2=\"aa!+h\";";
        //test = sc.nextLine();
        System.out.println("翻译结果如下：");
        List<TypeNStr> result = SLM.LexicalAnalysis(test);
        System.out.println(result.toString());
        SLM.StatementManager(result);
    }
}
