package Experiment1;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Piweiii
 * @Date: 2021/01/20/17:44
 * @Description:
 */
public class TypeNStr{
    private Integer typecode;
    private String word;

    public Integer getTypecode() {
        return typecode;
    }

    public void setTypecode(Integer typecode) {
        this.typecode = typecode;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "("+getTypecode()+","+getWord()+")";
    }
}