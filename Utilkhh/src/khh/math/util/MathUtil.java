package khh.math.util;

import khh.std.realworld.TPoint;

public class MathUtil {

    // ////////////////GPS 좌표로 구하는 Util //Location !!!!!!!!!!!
    // %해당지점 거리 구하는 함수
    public static double gpsdist(double lat1, double lon1, double lat2, double lon2) {
        return gpsdist(lat1, lon1, lat2, lon2, "m");
    }

    public static double gpsdist(double lat1, double lon1, double lat2, double lon2, String unit) {
        // //double dist=[]; 오차가 심함..
        // double GR=6378137.0; //% 지구반경 단위:미터
        // double pi = Math.PI;
        //
        // //% (고객위도 - 내위치위도) * 지구반경
        // double Dx= (DesPOS_x - RefPOS_x)*GR *Math.cos(RefPOS_y*pi/180) * pi /
        // 180;
        // double Dy=(DesPOS_y - RefPOS_y) * GR * pi / 180;
        // double distance = Math.sqrt ( Math.pow(Dx,2) + Math.pow(Dy,2));
        //
        // return distance;

        double theta = lon1 - lon2;
        double dist = Math.sin(degTorad(lat1)) * Math.sin(degTorad(lat2)) + Math.cos(degTorad(lat1)) * Math.cos(degTorad(lat2))
                * Math.cos(degTorad(theta));
        dist = Math.acos(dist);
        dist = radTodeg(dist);
        dist = dist * 60 * 1.1515;
        if ( unit == "K" || unit.equals("K")) {
            dist = dist * 1.609344;
        } else if ( unit == "N" || unit.equals("N") ) {
            dist = dist * 0.8684;
        } else if ( unit == "m" || unit.equals("m") ) {
            dist = dist * 1.609344;
            dist = dist * 1000;
        }
        return (dist);

    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts decimal degrees to radians : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    public static double degTorad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    /* :: This function converts radians to decimal degrees : */
    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    public static double radTodeg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    
    //%반경function [P1 P2]=
    public static  double[][] findrange(double curPOS_x,double curPOS_y, int radius){
//  %현재 위치와,반경(m)을 입력하여 gps 범위 계산
//  % curPOS.x : 내위치의 위도
//  %  curPOS.y : 내위치의 경도
//  %  dist :  반경 ,단위( 미터)
     double P1_x = 0 ; 
     double P1_y = 0 ; 
     double P2_x = 0 ; 
     double P2_y = 0 ;  
    if (radius <= 0){
        P1_x=curPOS_x;
        P1_y=curPOS_y;
        P2_x=curPOS_x;
        P2_y=curPOS_y;
    }
    double GR=6378137.0;  //% 지구반경 단위:미터
    double pi = Math.PI;
    
    double diffy = radius / GR / pi * 180;
    double diffx = radius / GR / pi * 180 / Math.cos(curPOS_y*pi/180);
//
    P1_x=curPOS_x-diffx;
    P2_x=curPOS_x+diffx;
    P1_y=curPOS_y-diffy;
    P2_y=curPOS_y+diffy;
    
    double [][] Point={
            {P1_x,P1_y},
            {P2_x,P2_y},
    };
    
    return Point;
    
    }   
    
    ///////////////MATH OR UI
    //비례식
    public static TPoint getRotatePosition(int centerX,int centerY,int circleXR,int circleYR,double angle){
        double applyAngle =  angle * Math.PI/180;   //라디안으로 바꾼다
        TPoint item =new TPoint();
        item.x =  (int)(centerX + circleXR * Math.cos(applyAngle));
        item.y = (int)(centerY + circleYR * Math.sin(applyAngle));
//      Log.d("",angle+"  "+item.x+" "+item.y +"   "+circleXR+"  "+circleYR);
        return item;
    }
    //가속도      몇스탭만에 갈거냐?      원하는길이는?     가중치는?0이면 같다.
    public static double[] getAcceleration(int step,double charlength, double weight){
        double [] exp = new double[step]; 
        double sum=0;
//      double weight=0.2;
        for(int i=0;i<exp.length;i++){
            double expval = Math.exp(-(weight*(i+1)));
            exp[i]=expval;
            sum+=expval;
        }
        for(int i=0;i<exp.length;i++){
            exp[i]=exp[i]/sum;
        }
        
        for(int i=0;i<exp.length;i++){
            exp[i]=charlength*exp[i];
        }
        
        return exp;
    }
    
    
    //비례식..?
    public static double getProportional (int pixPoint ,int maxPoint, int maxValue, int thisPoint){
//      maxPoint / maxValue     =  thisPoint / F   
//      maxPoint * F    =   maxValue * thisPoint
//      F =  maxValue*thisPoint /maxPoint
//      F =  (thisPoint /maxPoint) *maxValue
          double dy = (double)pixPoint+(double)maxPoint-(double)thisPoint;
          double r2 = 2*(double)maxPoint;
          double s  = (double)(1-(dy)/(r2));
          double alpha = (double)((s<0?0:s)*maxValue);
        return alpha;
    }
    
    
    //퍼센트
    public static double getPersent(double min,double max,double wantOffsetPersent){
        double g = min - max;
      return Math.abs(g*wantOffsetPersent/100);
        
    }
    
    
    
    ///////////////////태양광  고도각 .방위각.
//  public static Direction getDeirection_Elevation_Direction(double altitude, double latitude, Date date){
//  //6월21일
//  //13시 최고 태양광받는 시기다.
//      long betweenDay=0;
//      Direction di = new Direction();
//      
//      int y = date.getYear()+1900;
//      int m = date.getMonth()+1;
//      int d = date.getDate();
//      betweenDay = Utilities.getDateBetweenDay( y, 1, 1,y, m, d);
//      
////        date.get
//      
////        Utilities.getDate_toInt()
//      
//      //적위
//      /*
//        (1) 태양의 적위(δ) 계산
//          태양의 적위(δ)는 지구의 중심과 태양의 중심을 잇는 선이 지구의 적도와 이루
//          는 각도이며, 이에 대한 계산식은 아래와 같다.
//          단, n은 1월 1일을 기준으로 한 계산 대상일의 일수(예:1월 1일은 1, 12월 21일은 355)
//          δ = 23.45 sin(360 * (284 + n) / 365)
//       */
//      
//      
//      double declination = 0;
//      declination = 23.45*  Math.sin(360 * (284 + betweenDay) / 365);
//      
//      
//      /*(2) 태양의 고도각()
//      태양의 고도각은 다음 식에 의해 구할 수 있다.
//        sin  sinsin coscoscos
//      단,  = 대지의 위도[°], δ = 태양의 적위[°],
//      ω = 시각(時角) (예: 1시간은 15°, 24시간은 360°)
//      */
//      
//      double elevation = 0 ;
//      elevation = (Math.sin(declination)*Math.sin(altitude)+Math.cos(declination)*Math.cos(altitude))
//      
//      d.set
//      return null;
//  }

    
    
    
    
    
    
    
    
}
