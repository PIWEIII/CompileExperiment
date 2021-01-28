package Experiment1;

import java.util.List;
import java.util.Scanner;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/18/8:31
 * @Description:词法分析程序设计
 */
public class Lexical {
    public static void main(String[] args) {
        LexicalMachine LM = new LexicalMachine();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入代码段：");
        String test = "";
        //begin x:=9;if x>0 then x:=2*x+1/3;end#
        test = sc.nextLine();
        System.out.println("翻译结果如下：");
        List<TypeNStr> result = LM.LexicalAnalysis(test);
        System.out.println(result.toString());
    }
}

