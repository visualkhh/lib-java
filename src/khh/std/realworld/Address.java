package khh.std.realworld;

public class Address {

	String postcode		;	//�����ȣ
	String si			;			//��
	String gu			;			//��
	String dong			;		//��
	String li			;			//��
	String bunji		;		//����
	String apt			;			//����Ʈ
	

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(
			String postcode	,
			String si		,
			String gu		,
			String dong		,
			String li		,
			String bunji	,
			String apt		
			) {
		setPostcode	 ( postcode	);
		setSi		 ( si		);    
		setGu		 ( gu		);    
		setDong		 ( dong		);
		setLi		 ( li		);    
		setBunji	 ( bunji	);    
		setApt		 ( apt		);
	}
	
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getSi() {
		return si;
	}
	public void setSi(String si) {
		this.si = si;
	}
	public String getGu() {
		return gu;
	}
	public void setGu(String gu) {
		this.gu = gu;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getLi() {
		return li;
	}
	public void setLi(String li) {
		this.li = li;
	}
	public String getBunji() {
		return bunji;
	}
	public void setBunji(String bunji) {
		this.bunji = bunji;
	}
	public String getApt() {
		return apt;
	}
	public void setApt(String apt) {
		this.apt = apt;
	}
	
	
	public String getFullAddr(){
		String addr = ""+
		(si		 ==null?"":si		)+" "+
		(gu		 ==null?"":gu		)+" "+
		(dong	 ==null?"":dong	    )+" "+
		(li		 ==null?"":li		)+" "+
		(bunji	 ==null?"":bunji	)+" "+
		(apt	 ==null?"":apt	 	)+" "+
		(postcode==null?"":postcode);	
		
		return addr; 
	}
	
	
	
	
}
