public class Village {
    public byte index;
    public String name;
    public byte marketdays[];
    public byte holidays[];

    Village(byte index, String name, byte[] joursMarche, byte[] joursSacres){
        this.index = index;
        this.name = name;
        this.marketdays = joursMarche;
        this.holidays = joursSacres;
    }

    /*byte getIndex(){
        return this.index;
    }
    String getName(){
        return this.name;
    }
    byte[] getHolidays(){return this.holidays;}
    byte[] getMarketdays(){
        return this.marketdays;
    }*/
}
