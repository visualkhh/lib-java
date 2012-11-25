import java.text.NumberFormat;

public class calc
{
	
	
	//소스점자르기.
	public static String longDouble2String(int size, double value) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(size);
        nf.setGroupingUsed(false);
        return nf.format(value);
    }
	
	
	public static void main(String[] args)
	{
		
		
		double ttt= 123123.444;
		int t =-1;

		if(t<0){
				int z =0 ;
				for (int i = 0; i > t; i--)
				{
					z++;
				}
		
				System.out.println(z);
				int zzz = (int)Math.pow(10, z);
				
				ttt= ttt/zzz;
				System.out.println(ttt);
		}else if(t>=0){
			int tt  = (int)Math.pow(10, t);
			ttt = ttt*tt;
			System.out.println(ttt);
		}
		
		
//		String tz = calc.longDouble2String(5, ttt);
//		System.out.println(tz);
	}
}
