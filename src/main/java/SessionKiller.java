import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class SessionKiller implements  Runnable{
    List<Session> listOfSession;
    private Thread killer;

    SessionKiller(List<Session> requestInProgress) {
        listOfSession = requestInProgress;
    }

    public void start ()
    {
        killer = new Thread (this);
        killer.start ();
    }
    @Override
    public void run() {
        while(true) {
            for(int i = 0; i < listOfSession.size(); i++) {
                System.out.println(Duration.between(listOfSession.get(i).getlastEntryTime(), LocalDateTime.now()).getSeconds());
                if(Duration.between(listOfSession.get(i).getlastEntryTime(), LocalDateTime.now()).getSeconds() > 60) {
                    listOfSession.remove(i);
                }
            }
            try {
                Thread.sleep (5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
