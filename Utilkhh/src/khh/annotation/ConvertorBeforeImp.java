package khh.annotation;

public class ConvertorBeforeImp implements Convertor<String>{


	@Override
	public String converting(String injection) {
		System.out.println("before");
		return injection+"b_";
	}

}
