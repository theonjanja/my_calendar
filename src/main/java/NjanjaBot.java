import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

// import org.telegram.telegrambots.bots.TelegramLongPollingBot;
public class NjanjaBot extends TelegramLongPollingBot {
    List<Session> requestInProgress = new LinkedList();
    Session session;

    NjanjaBot() {
        SessionKiller sessionKiller = new SessionKiller(requestInProgress);
        sessionKiller.start();
    }

    private int isExist(long chatId, List<Session> liste) {
        for(int i = 0; i < liste.size(); i++) {
            if(chatId == liste.get(i).getChatId()) {
                return i;
            }
        }
        return -1;
    }

    public String getBotUsername() {
        return "njanja_1bot";
    }

    public String getBotToken() {
        return "2025716039:AAH_kern5emEUBKqqDtAtSiqktTI-KlFy6s";
    }

    public void onUpdateReceived(Update update) {
        String retour;
        System.out.println(update);
        SendMessage message = new SendMessage();
        message.setParseMode("markdown");
        try {
            String request = LocalDateTime.now() + " - " + update.getMessage().getFrom().getFirstName() + " ";
            request += update.getMessage().getFrom().getLastName() + " - ";
            request += update.getMessage().getChatId() + " - ";
            request += update.getMessage().getText() + "\n";

            File file = new File("logs.txt");
            // créer le fichier s'il n'existe pas
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(request);
            bw.close();
            if(isExist(update.getMessage().getChatId(), requestInProgress) != -1) {
                retour = requestInProgress.get(isExist(update.getMessage().getChatId(), requestInProgress)).buildResponse(update.getMessage().getText(), LocalDateTime.now());
            }else {
                session = new Session(update);
                requestInProgress.add(session);
                retour = requestInProgress.get(requestInProgress.size() - 1).buildResponse(update.getMessage().getText(), LocalDateTime.now());
            }
            message.setText(retour);
            // message.setText("*retour*");
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}

