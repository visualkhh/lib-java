package stringtest;


public class replacetest {
public static void main(String[] args) {
//    String logformat = "%d [%l] %c(line %n) %m   %e ";
//    
//    String g ="!@#$%^&*()-=_+[]{}\\|;':\",./<>?";
//    g = ConversionUtil.rexCharToSpcChar(g);
//    String a  =  logformat.replaceAll("%m",g);
//    System.out.println(a);
//    
    
    
    

    String a ="/Appl/usrtech/web/index.jsp.pdf";
    
    String [] as  =  a.split("\\.");
    String ex = as[as.length-1];
    
    System.out.println(ex);
    
    if( "hwp".equalsIgnoreCase(ex)  || "xls".equalsIgnoreCase(ex)  || "xlsx".equalsIgnoreCase(ex) || "pdf".equalsIgnoreCase(ex)){
        System.out.println("겹");
    }
    
    
}
}
