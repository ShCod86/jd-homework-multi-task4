import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Analyzer {

    private static ArrayBlockingQueue<String> charA = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> charB = new ArrayBlockingQueue<>(100);
    private static ArrayBlockingQueue<String> charC = new ArrayBlockingQueue<>(100);

    private static int maxLenA;
    private static int maxLenB;
    private static int maxLenC;
    private static String textA;
    private static String textB;
    private static String textC;

    public static void main(String[] args) throws InterruptedException {
        Thread generator = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String gen = generateText("abc", 100_000);
                try {
                    charA.put(gen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    charB.put(gen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    charC.put(gen);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread analyzeA = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    String str = charA.take();
                    int maxSubLen = maxRepeatInString(str, 'a');
                    if (maxSubLen > maxLenA) {
                        maxLenA = maxSubLen;
                        textA = str;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread analyzeB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    String str = charB.take();
                    int maxSubLen = maxRepeatInString(str, 'b');
                    if (maxSubLen > maxLenB) {
                        maxLenB = maxSubLen;
                        textB = str;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread analyzeC = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    String str = charC.take();
                    int maxSubLen = maxRepeatInString(str, 'c');
                    if (maxSubLen > maxLenC) {
                        maxLenC = maxSubLen;
                        textC = str;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        generator.start();
        analyzeA.start();
        analyzeB.start();
        analyzeC.start();
        generator.join();
        analyzeA.join();
        analyzeB.join();
        analyzeC.join();
        System.out.println("Максимальное количество подряд повторяющихся символов а = " + maxLenA +
                " в строке: " + textA.substring(0, 100));
        System.out.println("Максимальное количество подряд повторяющихся символов b = " + maxLenB +
                " в строке: " + textB.substring(0, 100));
        System.out.println("Максимальное количество подряд повторяющихся символов c = " + maxLenC +
                " в строке: " + textC.substring(0, 100));
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int maxRepeatInString(String str, char ch) {
        int count = 0;
        int countTemp = 0;
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (aChar == ch) {
                countTemp++;
            } else {
                if (countTemp > count)
                    count = countTemp;
                countTemp = 0;
            }
        }
        return count;
    }
}
