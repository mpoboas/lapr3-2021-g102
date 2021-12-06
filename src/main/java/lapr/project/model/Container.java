package lapr.project.model;

public class Container {
    private final String id;
    private final String type;
    private final String isoCode;
    private final int tare;
    private final int payload;
    private final int gross;
    public Container(String id, String type, String isoCode, int tare, int payload, int gross){
        this.id = id;
        this.type = type;
        this.isoCode = isoCode;
        this.tare = tare;
        this.payload = payload;
        this.gross = gross;
    }
    public String getId(){
        return this.id;
    }
    public String getType(){
        return this.type;
    }
    public String getIsoCode(){
        return this.isoCode;
    }
    public int getTare(){
        return this.tare;

    }
    public int getPayload(){
        return this.payload;

    }
    public int getGross(){
        return this.gross;
    }
    public String toString(){
        String s = "";
        s = s + "Identification: " + id + "\n";
        s = s + "Type: " + type + "\n";
        s = s + "ISO code: " + isoCode + "\n";
        s = s + "Tare: " + tare + "\n";
        s = s + "Payload: " + payload + "\n";
        s = s + "Gross: " + gross + "\n";
        return s;

    }
}
