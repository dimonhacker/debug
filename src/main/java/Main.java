import javax.sound.midi.Soundbank;
import java.net.Socket;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        String format = "tel1: 795355$$rand[55555;70000],tel2: 795355$$rand[66666;70000]";
        for (int i = 0; i < 10; i++) {
            String result = replaceRandTemplate(format);
            try {
                System.out.println(Integer.parseInt(result));
            } catch (Exception e) {
                System.out.println(result);
            }
        }

    }

    private static String replaceRandTemplate(String format) {
        Pattern pattern = Pattern.compile("\\$\\$rand\\[\\d*;{1}\\d*]");
        Matcher matcher = pattern.matcher(format);
        if (matcher.find()) {
            String shablon = format.substring(matcher.start(), matcher.end());
            Pattern pattern1 = Pattern.compile("\\[.*]");
            Matcher matcher1 = pattern1.matcher(shablon);
            matcher1.find();
            long chislo = 0;
            try {
                chislo = getRandomInRange(shablon.substring(matcher1.start() + 1, matcher1.end() - 1));
                format = format.replace(shablon, String.valueOf(chislo));
                if (format.contains("$$"))
                    return replaceRandTemplate(format);
                else return format;
            } catch (IllegalArgumentException e) {
                return "format-error";
            }
        }
        else
        return "format-error";
    }

    private static long getRandomInRange(String format) {
            String[] arr = format.split(";");
            long from = Long.parseLong(arr[0]);
            long to = Long.parseLong(arr[1]);
            return ThreadLocalRandom.current().nextLong(to - from) + from;
    }
}
