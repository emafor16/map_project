package QTServer.data;

public abstract class Attribute {

    private String name;
    private int index;

    Attribute(String name, int index){
        this.name=name;
        this.index=index;
    }

    String getName(){
        return name;
    }

    int getIndex(){
        return index;
    }

    public String toString(){
        return name;
    }


}
