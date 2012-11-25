package replace;

public class replacetest {
    public static void main(String[] args) {
        // System.out.println("hiu2".replace("@", ""));
        String g = "#####a##";

        // System.out.println(g.replace('#', 'v'));
        System.out.println(g.replaceFirst("#", "v"));
        System.out.println(g.replace("#", "v"));
        
        for (int i = 0; i < g.length(); i++) {
            if(g.charAt(i)=='#'){
                
            }else{
                
            }
            System.out.println(  );
        }
    }
}
