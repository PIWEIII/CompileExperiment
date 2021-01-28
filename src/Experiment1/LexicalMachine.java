package Experiment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/20/17:45
 * @Description:
 */
public class LexicalMachine{
    //定义词法分析器中的关键字
    private String[] KeyWords = {"begin","if","then","while","do","end"};
    //定义种别表
    private HashMap<String,Integer> TypeSheet = new HashMap<String,Integer>(){{
        put("begin",1);
        put("if",2);
        put("then",3);
        put("while",4);
        put("do",5);
        put("end",6);
        put("id",10);
        put("digit",11);
        put("+",13);
        put("-",14);
        put("*",15);
        put("/",16);
        put(":",17);
        put(":=",18);
        put("<",20);
        put("<>",21);
        put("<=",22);
        put(">",23);
        put(">=",24);
        put("=",25);
        put(";",26);
        put("(",27);
        put(")",28);
        put("#",29);
    }};

    //判断一个单词是否是符合正则表达式的单词
    public boolean isWord(String word){
        //word不能以数字开头，取word首字母，转换为数字后如果在0-9之间，那么就是不合法的
        if(word.charAt(0) >= '0'&&
                word.charAt(0) <= '9'){
            //System.out.println("标识符不可以数字开头");
            return false;
        }
        //word不能是关键字
/*        for (int i = 0; i < KeyWords.length; i++) {
            if(word.equals(KeyWords[i])){
                //System.out.println("标识符不能与关键字相同");
                return false;
            }
        }*/
        //word中不能含有运算符、界符、空格
        //把输入的word分解为字符数组，判断其中是否含有操作符
        char temp[] = word.toCharArray();
        for (int i = 0; i < temp.length; i++) {
            //如果是字母，则一定不是操作符
            if(temp[i] >= 'a' && temp[i] <= 'z'){
                continue;
            }
            //如果是数字，则一定不是操作符
            else if(temp[i] >= '0' && temp[i] <= '9'){
                continue;
            }
            //如果是大写字母，则也不是操作符
            else if (temp[i] >= 'A' && temp[i] <= 'Z'){
                continue;
            }
            //如果既不是数字也不是字母，那么一定是个符号，肯定不是word
            //System.out.println("标识符中可能含有操作符");
            return false;
        }
        //若经过上面检查全部通过，则一定是单词
        return true;
    }
    //判断一个数字是否是符合正则表达式的数字
    public boolean isDigit(String digit){
        boolean flag = true;
        //对数字的每一位进行检查，若发现非数字成分，修改flag标记为false并返回
        for (int i = 0; i < digit.length(); i++) {
            if(digit.charAt(i) >= '0' && digit.charAt(i) <= '9'){

            }else{
                flag = false;
            }
        }
        return flag;
    }
    //判断一个单词是否是关键字
    public boolean isKeyWord(String word){
        for (int i = 0; i < KeyWords.length; i++) {
            if(word.equals(KeyWords[i]))
                return true;
        }
        return false;
    }
    //判断一个单词是否是操作符
    public boolean isOperator(String word){
        if(!isWord(word) && !isRangeSign(word) && !isDigit(word) && !isKeyWord(word)){
            return true;
        }else {
            return false;
        }
    }
    //判断一个符号是否是界符
    public boolean isRangeSign(String RSign){
        if(RSign.equals(" ") || RSign.equals(";") || RSign.equals("#"))
            return true;
        else
            return false;
    }
    //得到一个word对应的种别码
    public Integer getTypeCode(String key){
        //如果是关键字，则直接返回种别码
        if(isKeyWord(key))
            return TypeSheet.get(key);
        //如果是单词，则直接返回id对应的种别码
        if(isWord(key))
            return TypeSheet.get("id");
        //如果是数字，则直接返回digit对应的种别码
        if(isDigit(key))
            return TypeSheet.get("digit");
        //都不是，那么应是符号
        return TypeSheet.get(key);
    }
    //扫描输入的串，截取出所有的部分
    public List<TypeNStr> LexicalAnalysis(String inputString){
        if(inputString.length() == 0) {
            System.out.println("不能输入空串");
            return null;
        }
        List<String> result = new ArrayList<>();
        //指向上一次切分（空格切分则是上一次之后的）的字符的指针
        int t = 0;
        //changeFlag用于记录t指针的位置是否发生改变，
        //如果改变，则同时改变t指针的属性，用于判断在何处划分，以此划分整个串
        boolean changeFlag = true;
        String divideMode = "";
        for (int i = 0; i < inputString.length(); i++) {
            if(changeFlag == true){
                String temp = inputString.charAt(i)+"";
                //根据当前t指针所指字符的属性，切换划分属性
                if(isWord(temp)){
                    divideMode = "word";
                }else if(isDigit(temp)){
                    divideMode = "digit";
                }else if(isRangeSign(temp)){
                    divideMode = "RSign";
                }else {
                    divideMode = "operator";
                }
                changeFlag = false;
            }
            //根据划分模式，在遇到不同的字符、符号时，决定是否划分
            switch (divideMode){
                case "word":
                    //在word的情况下遭遇了空格
                    if(inputString.charAt(i) == ' '){
                        result.add(inputString.substring(t,i));
                        t = i + 1;
                        changeFlag = true;
                        break;
                    }
                    //在word的情况下遭遇了";"
                    //或者既不是界符，也不是word和digit，那么一定是在word的情况下，遭遇了操作符
                    if(inputString.charAt(i) == ';' || inputString.charAt(i) == '#' || (!isWord(inputString.charAt(i)+"") && !isDigit(inputString.charAt(i)+""))){
                        result.add(inputString.substring(t,i));
                        t = i;
                        i = i - 1;  //保证 t，i在划分后，指向一致
                        changeFlag = true;
                        break;
                    }
                    //如果依然是word和digit，那么这个单词仍然是一体的，继续循环，直到找到划分点
                    break;
                case "digit":
                    //在数字模式下，如果下一个字符不是数字，则截取到该字符之前
                    if(i < inputString.length() - 1 && !isDigit(inputString.charAt(i+1)+"")){
                        result.add(inputString.substring(t,i+1));
                        t = i + 1;
                        changeFlag = true;
                    //如果此时已经到了串的末尾并且末尾也是数字，则全部截取下来
                    }else if(i == inputString.length() - 1 && isDigit(inputString.charAt(i)+"")) {
                        result.add(inputString.substring(t,i+1));
                    }
                    break;
                case "operator":
                    //如果下一个字符也是操作符，那么该操作符是双字符
                    if(i <= inputString.length()-2 && isOperator(inputString.charAt(i+1)+"")){
                        result.add(inputString.substring(i, i + 2));
                        i += 1;
                        t = i + 1;
                        changeFlag = true;
                        break;
                    }else {
                        result.add(inputString.substring(i, i + 1));
                        t = i + 1;
                        changeFlag = true;
                        break;
                    }
                case "RSign":
                    if((inputString.charAt(i)+"").equals(" ")){
                        t = i + 1;
                        changeFlag = true;
                        break;
                    }
                    result.add(inputString.substring(i,i+1));
                    t = i + 1;
                    changeFlag = true;
                    break;
            }
        }
        if(result.size() == 0)
            return null;
        List<TypeNStr> LAResult = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            TypeNStr T = new TypeNStr();
            T.setTypecode(getTypeCode(result.get(i)));
            T.setWord(result.get(i));
            LAResult.add(T);
            //System.out.println("("+getTypeCode(result.get(i))+","+result.get(i)+")");
        }
        return LAResult;
    }
}
