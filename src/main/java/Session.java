import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Session {
    // private String firstName;
    // private String lastName;
    // private String parcourt;
    private LocalDateTime lastEntryTime;
    private long chatId;
    Update update;
    SendMessage message = new SendMessage();
    private Village villagesList[] = new Village[14];
    private LocalDate referenceDate = LocalDate.parse("2021-01-01");
    private LocalDate today = LocalDate.now();
    private String daysOfweek[][] = {
            //Bafung
            {"Nkap", "Mbhi'i", "Shwi'i", "Lepare", "Legho", "Ncwi", "Pombwo'o", "Chore"},
            //Fe'efe'e
            {"Nth\u0289\u0302'kwɑ","Nth\u0289\u0302'ntāā (Líé'ngā')","Li\u0301e\u0301'nkwe'","Nk\u03b1\u0301\u03b1\u0301t\u0065\u0304\u0065\u0304","Ncwi\u0304e'ko","Nco\u0302mnt\u0065\u0304\u0065\u0304","Nzi\u0302ng\u016B","Nzi\u0302s\u014D"},
            //Ghomala
            {"Dzə̂dzə", "Ntâmdzə", "Sɛ̂sǔ", "Gɔ̂sʉɔ̌", "Dzə̂mtɔ̌", "Ntâmgǒ", "Tyə̌’pfô", "Shyə̌ŋkǔ’"},
            //Gomba’a
            {"Seugouè", "Njeula", "Kateu", "Kapot", "Metagoué", "Tsure", "Pobuo", "Mota"},
            //Medumba
            {"Nga", "Nk\u0254\u0302nt\u0289","Nzi\u030cnyam (Nsigh\u00F2g)","Nt\u03b1\u0302nbu\u0300’","Nt\u03b1\u0302nt\u0259’","Nt\u03b1nla’","Nsigha","Nse\u030cmnt\u0259’"},
            //Mengaka
            {"Yenkopié", "Yengwié", "Yentete", "Yenkap", "Yessi", "Yenkeosi", "Yenkinze", "Yepié"},
            //Nguemba
            {"Duchu", "Djola'a (Djedjuku'u)", "Mumetè", "Mametè", "Kuétsit", "Fessâ", "Fessap", "Cheidâ"},
            //Nguimba’a
            {"Njyonze", "Metuaze", "Cheatchie", "Tcheaze'a", "Nkouotchia", "Ngahan'ha", "Ncheazeme", "Nzemezeme"},
            //Yemba
            {"Meta\u0301", "Mbu\u0254ŋkh\u016B", "Mbuoc\u016B", "Efa’a\u0301", "Njhʉεl\u0101", "Nga\u0301\u014b", "mbu\u0254\u014bw\u0101", "mbuol\u00F3"}
    };
    private String semaineGregorien[] = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    private Village villages[] = new Village[73];
    private int choosenLangageIndex = 0;
    Noeud noeudCourant, noeudAuxiliaire, arbre, leftUnderNode, rightUnderNode, middleUnderNode;
    Village village;
    String dateSaisie = "";
    String dateReformatee = "";
    String messageAccueil = "1 => Calendrier Bamiléké\n" + "2 => Jours sacrés selon le calendrier Bamiléké\n" + "3 => Jours du marché à l'Ouest-Cameroun\n";
    String jourSemaine;

    Session (Update update){
        this.chatId = update.getMessage().getChatId();
        // this.lastEntryTime = LocalDateTime.now();
        this.update = update;
        // this.firstName = "";
        // this.lastName = "";
        // this.parcourt = "0";

        //Construction du sous arbre gauche.
        arbre = new Noeud();
        arbre.numNoeud = 12;
        arbre.leftChild = null;
        arbre.rightChild = null;
        arbre.middleChild = null;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 9;
        arbre.fatherNode.leftChild = arbre;
        arbre = arbre.fatherNode;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 5;
        arbre.fatherNode.rightChild = arbre;
        arbre = arbre.fatherNode;

        arbre.leftChild = new Noeud();
        arbre.leftChild.numNoeud = 8;
        arbre.leftChild.fatherNode = arbre;

        arbre.middleChild = new Noeud();
        arbre.middleChild.numNoeud = 13;
        arbre.middleChild.fatherNode = arbre;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 2;
        arbre.fatherNode.leftChild = arbre;
        arbre = arbre.fatherNode;

        leftUnderNode = arbre; //Fin de construction du sous arbre gauche;

        //Construction du sous arbre central.
        arbre = new Noeud();
        arbre.numNoeud = 10;
        arbre.leftChild = null;
        arbre.rightChild = null;
        arbre.middleChild = null;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 6;
        arbre.fatherNode.leftChild = arbre;
        arbre = arbre.fatherNode;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 3;
        arbre.fatherNode.leftChild = arbre;
        arbre = arbre.fatherNode;

        middleUnderNode = arbre; //Fin de construction du sous arbre central;

        //Construction du sous arbre droit.
        arbre = new Noeud();
        arbre.numNoeud = 11;
        arbre.leftChild = null;
        arbre.rightChild = null;
        arbre.middleChild = null;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 7;
        arbre.fatherNode.leftChild = arbre;
        arbre = arbre.fatherNode;

        arbre.fatherNode = new Noeud();
        arbre.fatherNode.numNoeud = 4;
        arbre.fatherNode.leftChild = arbre;
        arbre = arbre.fatherNode;

        rightUnderNode = arbre; //Fin de construction du sous arbre droit;

        //Construction du noeud racine.
        arbre = new Noeud();
        arbre.numNoeud = 1;
        arbre.leftChild = leftUnderNode;
        arbre.rightChild = rightUnderNode;
        arbre.middleChild = middleUnderNode;
        leftUnderNode.fatherNode = arbre;
        rightUnderNode.fatherNode = arbre;
        middleUnderNode.fatherNode = arbre;
        //Ici, arbre pointe sur la racine et représente notre arbre entier.

        noeudCourant = new Noeud();  // Ceci est un objet créé juste pour tester le début du parcourt.
        noeudCourant.numNoeud = -1;
        noeudAuxiliaire = noeudCourant;

        {
            village = new Village((byte) 0, "Bachingou", new byte[]{ 7 }, new byte[]{0, 5});
            villages[0] = village;
            village = new Village((byte)0, "Badounga", new byte[]{ 2 }, new byte[]{5});
            villages[1] = village;
            village = new Village((byte)0, "Bafang", new byte[]{6}, new byte[]{});
            villages[2] = village;
            village = new Village((byte)0, "Bafou Meya", new byte[]{0 }, new byte[]{});
            villages[3] = village;
            village = new Village((byte)0, "Bafou", new byte[]{2 }, new byte[]{0, 4 });
            villages[4] = village;
            village = new Village((byte)0, "Bafounda", new byte[]{ 2 }, new byte[]{});
            villages[5] = village;
            village = new Village((byte)0, "Bafoussam", new byte[]{ 2, 6 }, new byte[]{});
            villages[6] = village;
            village = new Village((byte)0, "Bagnoun", new byte[]{ 7}, new byte[]{0, 5});
            villages[7] = village;
            village = new Village((byte)0, "Baham", new byte[]{}, new byte[]{0, 1, 7});
            villages[8] = village;
            village = new Village((byte)0, "Bahouan", new byte[]{ 5}, new byte[]{});
            villages[9] = village;
            village = new Village((byte)0, "Bahouoc", new byte[]{}, new byte[]{0, 2, 3, 5});
            villages[10] = village;
            village = new Village((byte)0, "Baïgom", new byte[]{11}, new byte[]{});
            villages[11] = village;
            village = new Village((byte)0, "Bakassa", new byte[]{ 5 }, new byte[]{});
            villages[12] = village;
            village = new Village((byte)0, "Bakong", new byte[]{}, new byte[]{ 2, 3, 5});
            villages[13] = village;
            village = new Village((byte)0, "Bakou", new byte[]{1}, new byte[]{});
            villages[14] = village;
            village = new Village((byte)0, "Baleng", new byte[]{ 1 }, new byte[]{});
            villages[15] = village;
            village = new Village((byte)0, "Balengou", new byte[]{ 4 }, new byte[]{0, 3});
            villages[16] = village;
            village = new Village((byte)0, "Balessing", new byte[]{0 }, new byte[]{});
            villages[17] = village;
            village = new Village((byte)0, "Baleveng", new byte[]{ 1 }, new byte[]{});
            villages[18] = village;
            village = new Village((byte)0, "Baloum", new byte[]{ 1 }, new byte[]{});
            villages[19] = village;
            village = new Village((byte)0, "Bamaha", new byte[]{}, new byte[]{ 5 });
            villages[20] = village;
            village = new Village((byte)0, "Bameka", new byte[]{7}, new byte[]{});
            villages[21] = village;
            village = new Village((byte)0, "Bamena", new byte[]{ 4, 5 }, new byte[]{0, 3, 5});
            villages[22] = village;
            village = new Village((byte)0, "Bamendjo", new byte[]{ 4 }, new byte[]{});
            villages[23] = village;
            village = new Village((byte)0, "Bamendjou", new byte[]{ 1 }, new byte[]{});
            villages[24] = village;
            village = new Village((byte)0, "Bamendou", new byte[]{ 3 }, new byte[]{});
            villages[25] = village;
            village = new Village((byte)0, "Bamenkombo Chefferie", new byte[]{ 5 }, new byte[]{});
            villages[26] = village;
            village = new Village((byte)0, "Bamenkombo", new byte[]{ 1 }, new byte[]{});
            villages[27] = village;
            village = new Village((byte)0, "Bamesso", new byte[]{ 5 }, new byte[]{});
            villages[28] = village;
            village = new Village((byte)0, "Bandeng", new byte[]{ 4 }, new byte[]{});
            villages[29] = village;
            village = new Village((byte)0, "Bandja", new byte[]{ 3 }, new byte[]{});
            villages[30] = village;
            village = new Village((byte)0, "Bandjoun", new byte[]{0, 4 }, new byte[]{0, 1, 7});
            villages[31] = village;
            village = new Village((byte)0, "Bandrefam", new byte[]{ 1 }, new byte[]{});
            villages[32] = village;
            village = new Village((byte)0, "Banegang coop.", new byte[]{ 7}, new byte[]{});
            villages[33] = village;
            village = new Village((byte)0, "Bangam", new byte[]{0 }, new byte[]{});
            villages[34] = village;
            village = new Village((byte)0, "Bangang", new byte[]{ 3, 7}, new byte[]{});
            villages[35] = village;
            village = new Village((byte)0, "Bangang-Fokam", new byte[]{0}, new byte[]{0, 3});
            villages[36] = village;
            village = new Village((byte)0, "Bangangté", new byte[]{10, 13}, new byte[]{0, 3, 5});
            villages[37] = village;
            village = new Village((byte)0, "Bangangté (Chefferie)", new byte[] {}, new byte[]{0, 2, 3, 5});
            villages[38] = village;
            village = new Village((byte)0, "Bangou", new byte[]{ 3, 7}, new byte[]{});
            villages[39] = village;
            village = new Village((byte)0, "Bangou (chefferie)", new byte[]{ 1 }, new byte[]{});
            villages[40] = village;
            village = new Village((byte)0, "Bangou ville", new byte[]{ 6 }, new byte[]{});
            villages[41] = village;
            village = new Village((byte)0, "Bangoulap", new byte[]{ 12 }, new byte[]{5, 6});
            villages[42] = village;
            village = new Village((byte)0, "Bangourain", new byte[]{8}, new byte[]{});
            villages[43] = village;
            village = new Village((byte)0, "Bangwa", new byte[]{ 3, 7}, new byte[]{0, 5});
            villages[44] = village;
            village = new Village((byte)0, "Bankouop", new byte[]{12}, new byte[]{});
            villages[45] = village;
            village = new Village((byte)0, "Bapa", new byte[]{ 5 }, new byte[]{});
            villages[46] = village;
            village = new Village((byte)0, "Bassamba", new byte[]{9, 10}, new byte[]{});
            villages[47] = village;
            village = new Village((byte)0, "Batcham", new byte[]{14}, new byte[]{});
            villages[48] = village;
            village = new Village((byte)0, "Bati", new byte[]{ 6 }, new byte[]{});
            villages[49] = village;
            village = new Village((byte)0, "Batié", new byte[]{ 2 }, new byte[]{});
            villages[50] = village;
            village = new Village((byte)0, "Batoufam", new byte[]{ 5 }, new byte[]{});
            villages[51] = village;
            village = new Village((byte)0, "Bawock (Mfengamfa)", new byte[]{}, new byte[]{ 5 });
            villages[52] = village;
            village = new Village((byte)0, "Bayangam", new byte[]{ 3 }, new byte[]{});
            villages[53] = village;
            village = new Village((byte)0, "Bayangam", new byte[]{}, new byte[]{0, 1 });
            villages[54] = village;
            village = new Village((byte)0, "Bazou", new byte[]{0}, new byte[]{0, 3, 5});
            villages[55] = village;
            village = new Village((byte)0, "Djinso", new byte[]{ 2 }, new byte[]{});
            villages[56] = village;
            village = new Village((byte)0, "Fongo Tongo", new byte[]{ 2 }, new byte[]{});
            villages[57] = village;
            village = new Village((byte)0, "Fotouni", new byte[]{ 2 }, new byte[]{});
            villages[58] = village;
            village = new Village((byte)0, "Foumban", new byte[]{13}, new byte[]{});
            villages[59] = village;
            village = new Village((byte)0, "Foumbot", new byte[]{14}, new byte[]{});
            villages[60] = village;
            village = new Village((byte)0, "Founban", new byte[]{10}, new byte[]{});
            villages[61] = village;
            village = new Village((byte)0, "Kekem", new byte[]{13}, new byte[]{});
            villages[62] = village;
            village = new Village((byte)0, "Koutaba", new byte[]{9}, new byte[]{});
            villages[63] = village;
            village = new Village((byte)0, "Magba", new byte[]{13}, new byte[]{});
            villages[64] = village;
            village = new Village((byte)0, "Malatouen", new byte[]{12}, new byte[]{});
            villages[65] = village;
            village = new Village((byte)0, "Massagam", new byte[]{10}, new byte[]{});
            villages[66] = village;
            village = new Village((byte)0, "Mataba", new byte[]{12}, new byte[]{});
            villages[67] = village;
            village = new Village((byte)0, "Mbouda", new byte[]{ 3, 7}, new byte[]{});
            villages[68] = village;
            village = new Village((byte)0, "Penka Michel", new byte[]{ 6 }, new byte[]{});
            villages[69] = village;
            village = new Village((byte)0, "Teyedi", new byte[]{10}, new byte[]{});
            villages[70] = village;
            village = new Village((byte)0, "Tonga", new byte[]{13}, new byte[]{});
            villages[71] = village;
            village = new Village((byte)0, "Toumaka", new byte[]{ 6 }, new byte[]{});
            villages[72] = village;
        }


    }

    public String menuAccueil(String firstName, String lastName){
        return "Bienvenu Mr " + (firstName != null? firstName : "") + " " + (lastName != null? lastName : "") + " dans l'application Calendrier Bamiléké. Faites votre choix:\n\n"
                + "1 => Calendrier Bamiléké\n2 => Jours sacrés selon le calendrier Bamiléké\n3 => Jours du marché à l'Ouest-Cameroun";
    }
    public String listeLangues(){
        return "Veuillez choisir une langue:\n\n" + "1 => Bafung\n2 => Fe'efe'e\n3 => Ghomala\n4 => Gomba’a" +
                "\n5 => Mə̀dʉ̂mbὰ\n6 => Mengaka\n7 => Nguemba\n8 => Nguimba’a\n9 => Yemba\n\n99 => Accueil";
    }
    public String listeVillages(String raison){
        String chaineRetournee = "Veuillez choisir un village:\n\n";
        if(raison == "joursSacrés"){
            for(int i = 0; i < villages.length; i++) {
                if(villages[i].holidays.length != 0){
                    chaineRetournee += (i+1) + " => " + villages[i].name + "\n";
                }
            }
            chaineRetournee += "\n77 => Précédent\n99 => Accueil";
        }else{
            for(int i = 0; i < villages.length; i++) {
                if(villages[i].marketdays.length != 0){
                    chaineRetournee += (i+1) + " => " + villages[i].name + "\n";
                }
            }
            chaineRetournee += "\n77 => Précédent\n99 => Accueil";
        }
        return chaineRetournee;
    }
    public String dayOfChoice(LocalDate date){
        return "Le " + dateReformatee + " " + ((int) ChronoUnit.DAYS.between(this.today, date)%8 >= 0 ? "est un " : "était un ")
                +  "*" + jourDeLaSemaine(date) + "*\n\n77 => Précédent\n99 => Accueil";
    }
    String jourDeLaSemaine(LocalDate date) {
        return daysOfweek[choosenLangageIndex]
                [(int)ChronoUnit.DAYS.between(this.referenceDate, date)%8 >= 0 ?
                    (int)ChronoUnit.DAYS.between(this.referenceDate, date)%8 :
                    (int)ChronoUnit.DAYS.between(this.referenceDate, date)%8 + 8
                ];
    }
    boolean jourCourant(String jourSemaine) {
        if(jourSemaine == jourDeLaSemaine(today))
            return true;
        return false;
    }
    String dateOfday(String jourSemaine) {
        LocalDateTime currentTime = LocalDateTime.now();
        String response = "";
        int indexDayOfToday = 0;
        int indexSearchDay = 0;
        for(int i = 0; i < daysOfweek[choosenLangageIndex].length; i++)
            if(daysOfweek[choosenLangageIndex][i] == jourDeLaSemaine(today))
                indexDayOfToday = i;
        for(int i = 0; i < daysOfweek[choosenLangageIndex].length; i++)
            if(daysOfweek[choosenLangageIndex][i] == jourSemaine)
                indexSearchDay = i;

        switch (indexSearchDay - indexDayOfToday) {
            case 1:
            case -7: currentTime = currentTime.plusDays(1); break;
            case 2:
            case -6: currentTime = currentTime.plusDays(2); break;
            case 3:
            case -5: currentTime = currentTime.plusDays(3); break;
            case 4:
            case -4: currentTime = currentTime.plusDays(4); break;
            case 5:
            case -3: currentTime = currentTime.plusDays(5); break;
            case 6:
            case -2: currentTime = currentTime.plusDays(6); break;
            case 7:
            case -1: currentTime = currentTime.plusDays(7); break;
        }
        response += currentTime.getDayOfMonth() < 10 ? "0" + currentTime.getDayOfMonth() + "-": currentTime.getDayOfMonth() + "-";
        response += currentTime.getMonthValue() < 10 ? "0" + currentTime.getMonthValue() + "-": currentTime.getMonthValue() + "-";
        response += currentTime.getYear();
        return response;
    }
    String afficherSemaine() {
        String semaine = "";
        for(int i = 0; i < daysOfweek[choosenLangageIndex].length; i++) {
            semaine += (jourCourant(daysOfweek[choosenLangageIndex][i])? "*": "")
                        + daysOfweek[choosenLangageIndex][i] + " (" + dateOfday(daysOfweek[choosenLangageIndex][i]) + ")"
                        + (jourCourant(daysOfweek[choosenLangageIndex][i])? "*": "") +"\n";
        }
        return semaine;
    }
    public String listeChoixDate(){
        return "Faites un choix:\n\n" + "1 => Jour courant\n2 => Un autre jour\n3 => Afficher la semaine\n\n77 => Précédent\n99 => Accueil";
    }
    public boolean dateCorrecte(String date){
        int jour;
        int mois;
        int annee;
        if(date.split("-").length != 3)
            return false;
        if((!date.split("-")[0].matches("-?\\d+"))||(!date.split("-")[1].matches("-?\\d+"))||(!date.split("-")[2].matches("-?\\d+")))
            return false;
        jour = Integer.parseInt(date.split("-")[0]);
        mois = Integer.parseInt(date.split("-")[1]);
        annee = Integer.parseInt(date.split("-")[2]);
        if((jour < 1) || (jour > 31)||(mois < 1) || (mois > 12)||(annee < 1))
            return false;
        switch(mois){
            case 2:
            case 4:
            case 6:
            case 9:
            case 11: if(jour > 30)
                return  false;
        }
        if((mois == 2) && (jour > 29))
            return false;
        if((annee %4 != 0) && (mois == 2) && (jour > 28))
            return false;
        dateSaisie = annee + "-" + (mois < 10? "0" + mois: mois) + "-" + (jour < 10? "0" + jour: jour);
        dateReformatee = (jour < 10? "0" + jour: jour) + "-" + (mois < 10? "0" + mois: mois) + "-" + annee;
        System.out.println("Voici la date entrée: " + dateSaisie);
        return true;
    }

    String buildResponse(String msgRecu, LocalDateTime lastAccess) {
        String retour = "";
        lastEntryTime = lastAccess;

        switch (noeudCourant.numNoeud){
            case -1: noeudCourant = arbre;
                retour = menuAccueil(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName());
                break;
            case 1 : if(msgRecu.equals("1")){
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeLangues();
                    }
                    if(msgRecu.equals("2")){
                        noeudCourant = noeudCourant.middleChild;
                        retour = listeLangues();
                    }
                    if(msgRecu.equals("3")){
                        noeudCourant = noeudCourant.rightChild;
                        retour = listeLangues();
                    }
                    if(!((msgRecu.equals("1"))||(msgRecu.equals("2"))||(msgRecu.equals("3")))){
                        retour = "Vous n'avez fait aucun choix.\n" + messageAccueil;
                    }
                    break;
            case 2 : switch(msgRecu){
                    case "1": choosenLangageIndex = 0;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "2": choosenLangageIndex = 1;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "3": choosenLangageIndex = 2;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "4": choosenLangageIndex = 3;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "5": choosenLangageIndex = 4;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "6": choosenLangageIndex = 5;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "7": choosenLangageIndex = 6;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "8": choosenLangageIndex = 7;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "9": choosenLangageIndex = 8;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeChoixDate();
                        break;
                    case "99": noeudCourant = noeudCourant.fatherNode;
                        retour = messageAccueil;
                        break;
                    default: retour = "Vous n'avez fait aucun choix.\n" + listeLangues();
                }
                    break;
            case 3 :switch(msgRecu){
                    case "1": choosenLangageIndex = 0;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "2": choosenLangageIndex = 1;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "3": choosenLangageIndex = 2;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "4": choosenLangageIndex = 3;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "5": choosenLangageIndex = 4;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "6": choosenLangageIndex = 5;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "7": choosenLangageIndex = 6;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "8": choosenLangageIndex = 7;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "9": choosenLangageIndex = 8;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursSacrés");
                        break;
                    case "99": noeudCourant = noeudCourant.fatherNode;
                        retour = messageAccueil;
                        break;
                    default: retour = "Vous n'avez fait aucun choix.\n" + listeLangues();
                }
                    break;
            case 4 : switch(msgRecu){
                    case "1": choosenLangageIndex = 0;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "2": choosenLangageIndex = 1;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "3": choosenLangageIndex = 2;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "4": choosenLangageIndex = 3;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "5": choosenLangageIndex = 4;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "6": choosenLangageIndex = 5;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "7": choosenLangageIndex = 6;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "8": choosenLangageIndex = 7;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "9": choosenLangageIndex = 8;
                        noeudCourant = noeudCourant.leftChild;
                        retour = listeVillages("joursMarché");
                        break;
                    case "99": noeudCourant = noeudCourant.fatherNode;
                        retour = messageAccueil;
                        break;
                    default: retour = "Vous n'avez fait aucun choix.\n" + listeLangues();
                }
                    break;
            case 5 : switch(msgRecu){
                    case "1": noeudCourant = noeudCourant.leftChild;
                        int jour = LocalDate.now().getDayOfMonth();
                        int mois = LocalDate.now().getMonthValue();
                        int annee = LocalDate.now().getYear();
                        dateReformatee = (jour < 10? "0" + jour: jour) + "-" + (mois < 10? "0" + mois: mois) + "-" + annee;
                        retour = dayOfChoice(LocalDate.now());
                        break;
                    case "2": noeudCourant = noeudCourant.rightChild;
                        retour = "Entrez la date au format jj-mm-aaaa (Ex: 10-04-1984)\n\n77 => Précédent\n99 => Accueil";
                        break;
                    case "3": noeudCourant = noeudCourant.middleChild;
                    retour = "Semaine courante\n\n" + afficherSemaine() + "\n77 => Précédent\n99 => Accueil";
                    break;
                    case "77": noeudCourant = noeudCourant.fatherNode;
                        retour = listeLangues();
                        break;
                    case "99": noeudCourant = arbre;
                        retour = messageAccueil;
                        break;
                    default: retour = "Vous n'avez fait aucun choix.\n" + listeChoixDate();
                }
                break;
            case 6 : if(msgRecu.matches("-?\\d+")){
                        switch (msgRecu) {
                            case "77":
                                noeudCourant = noeudCourant.fatherNode;
                                retour = listeLangues();
                                break;
                            case "99":
                                noeudCourant = arbre;
                                retour = messageAccueil;
                                break;
                            default:
                                if (villages[Integer.parseInt(msgRecu) - 1].holidays.length == 0) {
                                    retour = "Vous n'avez fait aucun choix.\n" + listeVillages("joursSacrés");
                                } else {
                                    noeudCourant = noeudCourant.leftChild;
                                    retour = "Jour(s) sacré(s) à *" + villages[Integer.parseInt(msgRecu) - 1].name + "*:\n";
                                    for (int i = 0; i < villages[Integer.parseInt(msgRecu) - 1].holidays.length; i++) {
                                        jourSemaine = daysOfweek[choosenLangageIndex][villages[Integer.parseInt(msgRecu) - 1].holidays[i]];
                                        retour += (jourCourant(jourSemaine)? "*": "")
                                                + "- " + jourSemaine + " (" + dateOfday(jourSemaine) + ")"
                                                + (jourCourant(jourSemaine)? "*": "") + "\n";
                                    }
                                    retour += "\n77 => Précédent\n99 => Accueil";
                                }
                        }
                    }else{
                        retour = "Vous n'avez fait aucun choix.\n" + listeVillages("joursSacrés");
                    }
                    break;
            case 7 : if(msgRecu.matches("-?\\d+")){
                        switch (msgRecu){
                            case "77": noeudCourant = noeudCourant.fatherNode;
                                retour = listeLangues();
                                break;
                            case "99": noeudCourant = arbre;
                                retour = messageAccueil;
                                break;
                            default:
                                if(villages[Integer.parseInt(msgRecu) - 1].marketdays.length == 0){
                                    retour = "Vous n'avez fait aucun choix.\n" + listeVillages("joursMarché");
                                }else{
                                    noeudCourant = noeudCourant.leftChild;
                                    retour = "Jour(s) du marché à *" + villages[Integer.parseInt(msgRecu) - 1].name + "*:\n";
                                    for(int i = 0; i < villages[Integer.parseInt(msgRecu) - 1].marketdays.length; i++)
                                        if((villages[Integer.parseInt(msgRecu) - 1].marketdays[i] >= 0) && (villages[Integer.parseInt(msgRecu) - 1].marketdays[i] <= 7)) {
                                            jourSemaine = daysOfweek[choosenLangageIndex][villages[Integer.parseInt(msgRecu) - 1].marketdays[i]];
                                            retour += (jourCourant(jourSemaine)? "*": "")
                                                      + "- " + jourSemaine + " (" + dateOfday(jourSemaine) + ")"
                                                      + (jourCourant(jourSemaine)? "*": "") + "\n";
                                        }else
                                            retour += "- " + semaineGregorien[villages[Integer.parseInt(msgRecu) - 1].marketdays[i] - 8] + "\n";
                                    retour += "\n77 => Précédent\n99 => Accueil";
                                }
                        }
                    }else{
                        retour = "Vous n'avez fait aucun choix.\n" + listeVillages("joursMarché");
                    }
                        break;

            case 8 :
            case 13:
                if(msgRecu.equals("77")){
                noeudCourant = noeudCourant.fatherNode;
                retour = listeChoixDate();
                }else{
                    noeudCourant = arbre;
                    retour = messageAccueil;
                }
                break;
            case 9 : switch (msgRecu){
                case "77": noeudCourant = noeudCourant.fatherNode;
                    retour = listeChoixDate();
                    break;
                case "99": noeudCourant = arbre;
                    retour = messageAccueil;
                    break;
                    default:
                    if(dateCorrecte(msgRecu)) {
                        retour = dayOfChoice(LocalDate.parse(dateSaisie));
                        noeudCourant = noeudCourant.leftChild;
                    }else{
                        retour = "Date erronée\n" + "Entrez la date au format jj-mm-aaaa (Ex: 10-04-1984)";
                    }
                }
                break;
            case 10 : if(msgRecu.equals("77")){
                noeudCourant = noeudCourant.fatherNode;
                retour = listeVillages("joursSacrés");
                }else{
                    noeudCourant = arbre;
                    retour = messageAccueil;
                }
                    break;
            case 11 : if(msgRecu.equals("77")){
                noeudCourant = noeudCourant.fatherNode;
                retour = listeVillages("joursMarché");
                }else{
                    noeudCourant = arbre;
                    retour = messageAccueil;
                }break;
            case 12 : if(msgRecu.equals("77")){
                noeudCourant = noeudCourant.fatherNode;
                retour = "Entrez la date au format jj-mm-aaaa (Ex: 10-04-1984)\n\n77 => Précédent\n99 => Accueil";
                }else{
                    noeudCourant = arbre;
                    retour = messageAccueil;
                }
                break;
        }

        return retour;
    }

    long getChatId(){
        return this.chatId;
    }
    LocalDateTime getlastEntryTime(){
        return this.lastEntryTime;
    }
    void setChatId(long chatId){
        this.chatId = chatId;
    }
    /* void setLastEntryTime(LocalDateTime lastTime){
        this.lastEntryTime = lastTime;
    }*/

}
