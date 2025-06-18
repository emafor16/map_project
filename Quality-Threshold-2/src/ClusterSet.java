public class ClusterSet {

    private Cluster C[]= new Cluster[0];

    public ClusterSet() {
        // Initialize an empty cluster set
        C = new Cluster[0];
    }

    public void add(Cluster c){
        Cluster tempC[]=new Cluster[C.length+1];
        for(int i=0;i<C.length;i++)
            tempC[i]=C[i];
        tempC[C.length]=c;
        C=tempC;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < C.length; i++) {
            str.append("Cluster ").append(i).append(": ").append(C[i].toString()).append("\n");
        }
        return str.toString();
    }

    public String toString(Data data) {
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+=i+":"+C[i].toString(data)+"\n";
            }
        }
        return str;
    }
}
