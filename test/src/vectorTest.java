import java.util.Vector;

public class vectorTest
{
	public static void main(String[] args)
	{
		Vector<String> vc =new Vector<String>();

		System.out.println(vc.size()+"  Start size = "+vc.capacity());
		vc.add("주광복");
		vc.add("허세준");
		vc.add("권선주");
		vc.add("김영효");
		vc.add("김현하");
		vc.add("주광복");
		vc.add("허세준");
		vc.add("권선주");
		vc.add("김영효");
		vc.add("김현하");
		vc.add("김현하");
		vc.add("권선주");
		vc.add("김영효");
		vc.add("김현하");
		vc.add("주광복");
		vc.add("허세준");
		vc.add("권선주");
		vc.add("김영효");
		vc.add("김현하");
		vc.add("김현하");
		vc.add("권선주");
		vc.add("김영효");
		vc.add("김현하");
		vc.add("주광복");
		vc.add("허세준");
		vc.add("권선주");
		vc.add("김영효");
		vc.add("김현하");
		vc.add("김현하");

		//존재 여부
		boolean boo = vc.contains("김현하");
		System.out.println(boo);
		
		
		//지정한 인덱스의 요소를 반환한다. 다만 Object형 상태기 때문에 형변환을 해주야 한다.
		String temp = (String)vc.elementAt(2);	
		System.out.println("요소 index 2: " + temp);
		
		
		vc.remove(0);			//public Object  remove(int index)		  return 삭제엘리먼트	 
		vc.removeElementAt(1);	//public void removeElementAt(int index)
		//지정한 인덱스의 엘리먼트 삭제. 빈 자리는 자동적으로 다음 엘리먼트가 이동하여 채워진다.
		System.out.println(vc.size()+"  Remove2 size = "+vc.capacity());
		vc.clear();			//해당 벡터의 모든 element(요소) 삭제
		System.out.println(vc.size()+" Clear  size = "+vc.capacity());
		
		System.out.println();
		
		////////Restart//////////////
		
		vc.addElement("벡터 요소 1");
		vc.addElement("벡터 요소 2");
		
		vc.insertElementAt("벡터 요소 3", 1);	//지정한 인덱스에 요소 삽입
		
		System.out.println("요소 index 0: " + (String)vc.elementAt(0));
		System.out.println("요소 index 1: " + (String)vc.elementAt(1));
		System.out.println("요소 index 2: " + (String)vc.elementAt(2));
		
		vc.clear();
		
		
		vc.addElement("Vector 요소 0");
		vc.addElement("Vector 요소 1");
		vc.addElement("Vector 요소 2");
		vc.addElement("Vector 요소 3");
		vc.addElement("Vector 요소 4");
		vc.addElement("Vector 요소 5");
		
		for (int x = 0; x < 10; x++)
		{
			for (int i = 0; i < 6; i++)
			{
				System.out.println(vc.remove(0));
				vc.addElement("VectorPPP 요소 "+i);  
			}
				
		}
		
		
		System.out.println(vc.size()+"   size = "+vc.capacity());
		
		
		Vector<String> tt =new Vector<String>();
		tt.add("zz");
		System.out.println(tt.remove(0));
		System.out.println(tt.remove(0));
		
		
		
	}
}
