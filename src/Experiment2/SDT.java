package Experiment2;

import Experiment1.LexicalMachine;
import Experiment1.TypeNStr;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/20/15:09
 * @Description:
 */
public class SDT {
    public static void main(String[] args) {
        LexicalMachine LM = new LexicalMachine();
        SDTMachine SDTM = new SDTMachine();
        String test = "4 - 5 * 6 DIV 4 + 8 MOD 2";
        List<TypeNStr> result = LM.LexicalAnalysis(test);
        System.out.println(SDTM.SDTAnalysis(result));
    }
}

