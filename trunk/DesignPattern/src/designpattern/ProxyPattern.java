package designpattern;

interface Image_I{
	public void draw();
}

class Image implements Image_I{
	@Override
	public void draw() {
		System.out.println("그림을 그렸습니다.");
	}
}
class ImageView implements Image_I{
	@Override
	public void draw() {
		if(ProxyPattern.viewCnt%2==0)
		System.out.println("인터페이스를 구현하여 프록시객체를만들었습니다. 상황에 맞게 그림을 그렸습니다.");
	}
}

class ImageViewObj extends Image{
	@Override
	public void draw() {
		if(ProxyPattern.viewCnt%2==0)
		System.out.println("상속하여 프록시객체를만들었습니다   상황에 맞게 그림을 그렸습니다.");
	}
}



public class ProxyPattern {
	public static int viewCnt=0;
	public static void main(String[] args) {
		Image_I img=null;
		for(int i = 0 ; i < 10 ; i++){
			img = new Image();
			img.draw();
			viewCnt++;
		}
		
		for(int i = 0 ; i < 10 ; i++){
			img = new ImageView();
			img.draw();
			viewCnt++;
		}
		
		for(int i = 0 ; i < 10 ; i++){
			img = new ImageViewObj();
			img.draw();
			viewCnt++;
		}
	}
	
}
