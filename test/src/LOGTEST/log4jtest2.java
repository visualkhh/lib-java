package LOGTEST;

import org.apache.log4j.Logger;

public class log4jtest2 {
    public static void main(String[] args) {
        System.out.println("a");
        
         Logger logger = Logger.getLogger(log4jtest2.class);
         logger.fatal("fatal!!");
         logger.fatal("fatal2!!", new NullPointerException("널입니다요"));
         logger.error("error!", new NumberFormatException());
         logger.error("error!2");
         logger.warn("warn");
         logger.info("info");
         logger.debug("debug");
    }
}
