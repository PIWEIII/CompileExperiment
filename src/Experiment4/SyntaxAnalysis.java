package Experiment4;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/21/19:40
 * @Description:
 */
public class SyntaxAnalysis {
    public static void main(String[] args) {
        SyntaxMachine SM = new SyntaxMachine();
        SM.SyntaxAnalysis("m+m*m#");
    }
}
