package Experiment4;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/21/19:41
 * @Description:
 */
public class SyntaxMachine {
    public boolean SyntaxAnalysis(String str){
        if (str.length() == 0)
            return false;
        PATable pat = new PATable();
        Deque<String> stack = new LinkedList<>();
        //初始化开始符号到栈中
        stack.push("#");
        stack.push("S");
        //指向串的指针
        int i = 0;
        while (i < str.length()){
            //匹配时，i++
            if(stack.getFirst().equals(str.charAt(i)+"")){
                System.out.println(stack.getFirst()+"匹配");
                stack.pop();
                i++;
            }else {
                //不匹配，则去查表
                //如果查出错误
                if(pat.tab[pat.row(stack.getFirst())][pat.col(str.charAt(i)+"")].equals("E")){
                    System.out.println("Error: Can't find correct rule");
                    return false;
                //如果查出空
                }else if(pat.tab[pat.row(stack.getFirst())][pat.col(str.charAt(i)+"")].equals("$")){
                    System.out.println(stack.getFirst() + "->$");
                    stack.pop();
                //如果查出规则
                }else {
                    System.out.println(stack.getFirst() + "->" + pat.tab[pat.row(stack.getFirst())][pat.col(str.charAt(i)+"")]);
                    //将规则产生的字符，反向存入栈中
                    int j = pat.tab[pat.row(stack.getFirst())][pat.col(str.charAt(i)+"")].length() - 1;
                    //出栈
                    String temp = stack.pop();
                    while ( j >= 0 ){
                        stack.push(pat.tab[pat.row(temp)][pat.col(str.charAt(i)+"")].charAt(j)+"");
                        j--;
                    }
                }
            }
        }
        return true;
    }
}
//预测分析表定义
class PATable {
    public String tab[][]={
            {"E","E","AT","E","AT","E"},
            {"E","E","BU","E","BU","E"},
            {"+AT","E","E","$","E","$"},
            {"$","*BU","E","$","E","$"},
            {"E","E","(S)","E","m","E"}};

    //获取行坐标
    public int row(String c){
        switch (c){
            case "S":
                return 0;
            case "A":
                return 1;
            case "T":
                return 2;
            case "U":
                return 3;
            case "B":
                return 4;
        }
        return -1;
    }

    //获取列坐标
    public int col(String c){
        switch (c){
            case "+":
                return 0;
            case "*":
                return 1;
            case "(":
                return 2;
            case ")":
                return 3;
            case "m":
                return 4;
            case "#":
                return 5;
        }
        return -1;
    }

}
