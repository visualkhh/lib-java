public class MorseRecordDTO {

	String msg = "";
	int length = 0;

	public MorseRecordDTO() {
	}
	public MorseRecordDTO(String msg, int length) {
		this.msg = msg;
		this.length = length;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
