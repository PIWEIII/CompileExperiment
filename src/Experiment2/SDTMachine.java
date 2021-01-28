package Experiment2;

import Experiment1.LexicalMachine;
import Experiment1.TypeNStr;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/20/18:38
 * @Description:
 */
public class SDTMachine {
    //定义判断操作符优先级的方法
    public boolean isSeniorOP(String opr){
        if(opr.equals("DIV") || opr.equals("MOD") || opr.equals("*")){
            return true;
        }else {
            return false;
        }
    }
    //SDT分析过程
    public String SDTAnalysis(List<TypeNStr> LMResult){
        //如果LMResult长度为0，存在问题返回空串
        if (LMResult.size() == 0)
            return null;
        //当出现非法字符，LexicalAnalysis会出现null
        for (int i = 0; i < LMResult.size(); i++) {
            if(LMResult.get(i).getTypecode() == null){
                System.out.println("Error : Lexical Error,Exist illegal word");
                return null;
            }
        }
        LexicalMachine LM = new LexicalMachine();
        List<String> result = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        //遇见符号，入栈，但是栈中不能存在比该符号优先级高的符号，否则直接输出
        for (int i = 0; i < LMResult.size(); i++) {
            //如果是数字,这里用到了实验一中的种别表,则直接输出
            if (LMResult.get(i).getTypecode() == 11){
                System.out.print(LMResult.get(i).getWord());
                result.add(LMResult.get(i).getWord());
            }else {
                //当目前是高级运算符
                if (isSeniorOP(LMResult.get(i).getWord())){
                    //栈空 直接入栈
                    if (stack.isEmpty()){
                        stack.push(LMResult.get(i).getWord());
                    }else{
                        //栈顶是同级运算符时，栈顶出，该符号入栈
                        if (isSeniorOP(stack.getFirst())){
                            System.out.print(stack.getFirst());
                            result.add(stack.pop());
                            stack.push(LMResult.get(i).getWord());
                        }else {
                            //如果栈顶是低级运算符
                            stack.push(LMResult.get(i).getWord());
                        }
                    }
                }
                //当前是低级运算符
                else {
                    //栈空，直接入栈
                    if (stack.isEmpty()){
                        stack.push(LMResult.get(i).getWord());
                    }else{
                        //栈顶为同级、高级,都得出栈FILO，并且低级运算符入栈
                        while (!stack.isEmpty()){
                            System.out.print(stack.getFirst());
                            result.add(stack.pop());
                        }
                        stack.push(LMResult.get(i).getWord());
                    }
                }
            }
        }
        //最后，串扫描结束，将栈中所有符号出栈
        while (!stack.isEmpty()){
            System.out.print(stack.getFirst());
            result.add(stack.pop());
        }
        System.out.println();
        return result.toString();
    }
}
