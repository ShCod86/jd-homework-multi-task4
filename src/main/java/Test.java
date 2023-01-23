public class Test {
    public static void main(String[] args) {
        String s = "aabacaaaaaaaaabbbasjasjaajaajajajajahashjsjjsjsjsaaaac";
        int res = Analyzer.maxRepeatInString(s, 'a');
        System.out.println(res);
    }
    public static int maxRepeat(String str, char ch){
        int count = 0;
        int countTemp = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ch) {
                countTemp++;
            } else {
                if (count > countTemp)
                    count = countTemp;
                countTemp = 0;
            }
        }
        return count;
    }
}
