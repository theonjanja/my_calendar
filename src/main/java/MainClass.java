import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MainClass {

    public static void main(String[] args) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate date1 = currentTime.toLocalDate();
        LocalDateTime tomorrow = currentTime.plusDays(1);
        System.out.println(date1);
        System.out.println(tomorrow.toLocalDate());
        date1 = LocalDate.parse("2021-01-10");
        LocalDate date2 = LocalDate.parse("2021-01-15");
        System.out.println(date1);
        System.out.println("Ecart en jour \u0289\u030c: " + ChronoUnit.DAYS.between(date2, date1)%3);
        Duration duree = Duration.between(currentTime, tomorrow);
        System.out.println(duree);
        System.out.println(duree.getSeconds());

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new NjanjaBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
