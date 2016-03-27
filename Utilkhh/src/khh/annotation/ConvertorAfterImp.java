package khh.annotation;

public class ConvertorAfterImp implements Convertor<String>{

	@Override
	public String converting(String injection) {
		System.out.println("after");
		return injection+"A";
	}

}
