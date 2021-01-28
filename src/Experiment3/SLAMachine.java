package Experiment3;

import Experiment1.LexicalMachine;
import Experiment1.TypeNStr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/21/12:15
 * @Description:
 */
public class SLAMachine extends LexicalMachine {
    private HashMap<String,Integer> TypeSheet = new HashMap<String,Integer>(){{
        put("begin",1);
        put("if",2);
        put("then",3);
        put("while",4);
        put("do",5);
        put("end",6);
        put("char",8);
        put("string",9);
        put("id",10);
        put("integer",11);
        put("float",12);
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

    @Override
    public boolean isWord(String word) {
        return super.isWord(word);
    }

    @Override
    public boolean isDigit(String digit) {
        return super.isDigit(digit);
    }

    @Override
    public boolean isKeyWord(String word) {
        return super.isKeyWord(word);
    }

    @Override
    //const count=10,sum=81.5,char1='f',string1="hj", max=169;
    public boolean isOperator(String word) {
        if (word.equals("'") || word.equals("\""))
            return false;
        return super.isOperator(word);
    }

    @Override
    public boolean isRangeSign(String RSign) {
        if (RSign.equals(","))
            return true;
        else
            return super.isRangeSign(RSign);
    }

    //判断一个数字串是不是浮点数
    public boolean isFloat(String a_float){
        for (int i = 0; i < a_float.length(); i++) {
            if(a_float.charAt(i) == '.')
                return true;
        }
        return false;
    }
    //判断一个符号是否是char范围符号
    public boolean isCharRange(String rg_char){
        if(rg_char.equals("'"))
            return true;
        else
            return false;
    }
    //判断一个符号是否是string范围符号
    public boolean isStringRange(String rg_string){
        if(rg_string.equals("\""))
            return true;
        else
            return false;
    }
    //判断一个字符串是不是字符型数据
    public boolean isChar(String word){
        if(word.charAt(0) == '\'' && word.charAt(word.length()-1) == '\'')
            return true;
        else
            return false;
    }
    //判断一个字符串是不是字符串型数据
    public boolean isString(String word){
        if(word.charAt(0) == '\"' && word.charAt(word.length()-1) == '\"')
            return true;
        else
            return false;
    }
    @Override
    public Integer getTypeCode(String key) {
        if (isChar(key))
            return TypeSheet.get("char");
        if (isString(key))
            return TypeSheet.get("string");
        if (isFloat(key))
            return TypeSheet.get("float");
        else
            return super.getTypeCode(key);
    }

    @Override
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
                }else if(isCharRange(temp) || isStringRange(temp)){
                    divideMode = "CorSRGSign";
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
                    //在数字模式下，如果下一个字符是 “.”则是浮点数的一部分
                    if (i < inputString.length() - 1 && (inputString.charAt(i+1)+"").equals(".")){
                        break;
                    //在数字模式下，如果下一个字符不是数字，则截取到该字符之前
                    }else if (i < inputString.length() - 1 && !isDigit(inputString.charAt(i+1)+"")){
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
                    if((inputString.charAt(i)+"").equals(" ") || (inputString.charAt(i)+"").equals(",")){
                        t = i + 1;
                        changeFlag = true;
                        break;
                    }
                    result.add(inputString.substring(i,i+1));
                    t = i + 1;
                    changeFlag = true;
                    break;
                case "CorSRGSign":
                    //在 ' 或 " 的模式下，遇到另一个成对的  ' 或 " 即可结束
                    if(i < inputString.length() - 1 && ((inputString.charAt(i+1)+"").equals("'") || (inputString.charAt(i+1)+"").equals("\""))){
                        result.add(inputString.substring(t,i+2));
                        i = i + 1;
                        t = i + 1;
                        changeFlag = true;
                        break;
                    }
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
    //规范输出说明赋值语句
    public boolean StatementManager(List<TypeNStr> result){
        if (result.size() == 0){
            System.out.println("Error:处理SLA词法分析结果出错");
            return false;
        }
        if (!result.get(0).getWord().equals("const")){
            System.out.println("Error:SLA自动机检测到当前输入结果集非const描述语句，无法处理");
            return false;
        }
        String type,value;
        for (int i = 1; i < result.size(); i++) {
            //不打印 =
            if (result.get(i).getWord().equals("=")){
                continue;
            }
            //标识符，则直接打印
            if (result.get(i).getTypecode() == 10){
                System.out.print(result.get(i).getWord());
            }
            //如果是integer，规范输出
            else if (result.get(i).getTypecode() == 11){
                type = "integer";
                value = result.get(i).getWord();
                System.out.println("("+type+","+value+")");
            //如果是float，规范输出
            }else if (result.get(i).getTypecode() == 12){
                type = "float";
                value = result.get(i).getWord();
                System.out.println("("+type+","+value+")");
            //如果是char，规范输出
            }else if (result.get(i).getTypecode() == 8){
                type = "char";
                value = result.get(i).getWord();
                System.out.println("("+type+","+value+")");
            //如果是string，规范输出
            }else if (result.get(i).getTypecode() == 9){
                type = "string";
                value = result.get(i).getWord();
                System.out.println("("+type+","+value+")");
            }
        }
        return true;
    }
}
