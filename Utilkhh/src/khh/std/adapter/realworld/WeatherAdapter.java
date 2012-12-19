package khh.std.adapter.realworld;
import java.util.Date;

import khh.math.util.MathUtil;
import khh.std.adapter.AdapterMapBase;
import khh.std.realworld.Direction;
import khh.std.realworld.Realworld;
import khh.std.realworld.Weather;
import khh.util.Util;

public class WeatherAdapter extends AdapterMapBase<String,Realworld> {
	String info;
	
	
	double temperature_max; // ���
	double  rainday_cnt;//	�����ϼ�
	double  raincontinuous_time;//	������ӽð�
	double  fogday_cnt; //	�Ȱ��ϼ�
	double  sandyday_cnt;//	Ȳ���ϼ�
	double  dustday_cnt;//	�����ϼ�  ����(����)�� ��� ���� ������ ���� ������ ������ ����� ���� ���Ѵ�
	double  mistday_cnt;//	�ڹ��ϼ� �ڹ�(����, mist)�� ����� �������� ���ῡ ���Ͽ� ��� ������ �Ȱ����� ������ ���� ���¸� ����Ų��.
	double  sunshine_time;//	�����ð�
	String	coment;	//�ڸ�Ʈ
	
	
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public double getTemperature_max() {
		return temperature_max;
	}
	public void setTemperature_max(double temperatureMax) {
		temperature_max = temperatureMax;
	}
	public double getRainday_cnt() {
		return rainday_cnt;
	}
	public void setRainday_cnt(double raindayCnt) {
		rainday_cnt = raindayCnt;
	}
	public double getRaincontinuous_time() {
		return raincontinuous_time;
	}
	public void setRaincontinuous_time(double raincontinuousTime) {
		raincontinuous_time = raincontinuousTime;
	}
	public double getFogday_cnt() {
		return fogday_cnt;
	}
	public void setFogday_cnt(double fogdayCnt) {
		fogday_cnt = fogdayCnt;
	}
	public double getSandyday_cnt() {
		return sandyday_cnt;
	}
	public void setSandyday_cnt(double sandydayCnt) {
		sandyday_cnt = sandydayCnt;
	}
	public double getDustday_cnt() {
		return dustday_cnt;
	}
	public void setDustday_cnt(double dustdayCnt) {
		dustday_cnt = dustdayCnt;
	}
	public double getMistday_cnt() {
		return mistday_cnt;
	}
	public void setMistday_cnt(double mistdayCnt) {
		mistday_cnt = mistdayCnt;
	}
	
	public double getSunshine_time() {
		return sunshine_time;
	}
	public void setSunshine_time(double sunshineTime) {
		sunshine_time = sunshineTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
	public Realworld getAlgorithm() {
		// TODO Auto-generated method stub
		return null;
	}
	public Realworld getAvg() {
		Realworld weather=new Realworld();
		weather  =  getSum();
		weather.getWeather().setTemperature		(weather.getWeather().getTemperature	() / this.size	());
		weather.getWeather().setRainfall		(weather.getWeather().getRainfall		() / this.size	());
		weather.getWeather().setInsolation		(weather.getWeather().getInsolation		() / this.size	());
		weather.getWeather().setHumidity		(weather.getWeather().getHumidity		() / this.size	());
		weather.getWeather().setDust			(weather.getWeather().getDust			() / this.size	());
		weather.getWeather().setCloud			(weather.getWeather().getCloud			() / this.size	());
		weather.getWeather().setSnow			(weather.getWeather().getSnow			() / this.size	());
		weather.getWeather().setWind			(weather.getWeather().getWind			() / this.size	());
		return weather;
	}
	
	public Realworld getMax() {
		Realworld realworld=new Realworld();
		Weather weather=new Weather();
		
		double [] Temperature		 = new double[this.size()];
		double [] Rainfall			 = new double[this.size()];
		double [] Insolation		 = new double[this.size()];
		double [] Humidity			 = new double[this.size()];
		double [] Dust				 = new double[this.size()];
		double [] Cloud				 = new double[this.size()];
		double [] Snow				 = new double[this.size()];
		double [] Wind				 = new double[this.size()];
		
		try{
			for(int i = 0 ; i < this.size() ; i ++){
				Temperature	[i]    =	(this.get(i).getWeather().getTemperature	()  );
				Rainfall	[i]    =	(this.get(i).getWeather().getRainfall		()  );
				Insolation	[i]    =	(this.get(i).getWeather().getInsolation		()  );
				Humidity	[i]    =	(this.get(i).getWeather().getHumidity		()  );
				Dust		[i]    =	(this.get(i).getWeather().getDust			()  );
				Cloud		[i]    =	(this.get(i).getWeather().getCloud			()  );
				Snow		[i]    =	(this.get(i).getWeather().getSnow			()  );
				Wind		[i]    =	(this.get(i).getWeather().getWind			()  );
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		weather.setTemperature		(    MathUtil.getMax(Temperature	)    );
		weather.setRainfall			(    MathUtil.getMax(Rainfall		)    );
		weather.setInsolation		(    MathUtil.getMax(Insolation	)    );
		weather.setHumidity			(    MathUtil.getMax(Humidity		)    );
		weather.setDust				(    MathUtil.getMax(Dust			)    );
		weather.setCloud			(    MathUtil.getMax(Cloud			)    );
		weather.setSnow				(    MathUtil.getMax(Snow			)    );
		weather.setWind				(    MathUtil.getMax(Wind			)    );
		
		realworld.setWeather(weather);
		realworld.setDirection(new Direction());
		realworld.setDate(new Date());
		
		return realworld;
	}
	public Realworld getMin() {
		Realworld realworld=new Realworld();
		Weather weather=new Weather();
		double [] Temperature		 = new double[this.size()];
		double [] Rainfall			 = new double[this.size()];
		double [] Insolation		 = new double[this.size()];
		double [] Humidity			 = new double[this.size()];
		double [] Dust				 = new double[this.size()];
		double [] Cloud				 = new double[this.size()];
		double [] Snow				 = new double[this.size()];
		double [] Wind				 = new double[this.size()];
		
		try{
			for(int i = 0 ; i < this.size() ; i ++){
				Temperature	[i]    =	(this.get(i).getWeather().getTemperature	()  );
				Rainfall	[i]    =	(this.get(i).getWeather().getRainfall		()  );
				Insolation	[i]    =	(this.get(i).getWeather().getInsolation		()  );
				Humidity	[i]    =	(this.get(i).getWeather().getHumidity		()  );
				Dust		[i]    =	(this.get(i).getWeather().getDust			()  );
				Cloud		[i]    =	(this.get(i).getWeather().getCloud			()  );
				Snow		[i]    =	(this.get(i).getWeather().getSnow			()  );
				Wind		[i]    =	(this.get(i).getWeather().getWind			()  );
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		weather.setTemperature		(    MathUtil.getMin(Temperature	)    );
		weather.setRainfall			(    MathUtil.getMin(Rainfall		)    );
		weather.setInsolation		(    MathUtil.getMin(Insolation	)    );
		weather.setHumidity			(    MathUtil.getMin(Humidity		)    );
		weather.setDust				(    MathUtil.getMin(Dust			)    );
		weather.setCloud			(    MathUtil.getMin(Cloud			)    );
		weather.setSnow				(    MathUtil.getMin(Snow			)    );
		weather.setWind				(    MathUtil.getMin(Wind			)    );
		
		realworld.setWeather(weather);
		realworld.setDirection(new Direction());
		realworld.setDate(new Date());
		
		return realworld;
	}
	public Realworld getSum() {
		Realworld realworld=new Realworld();
		realworld.setWeather(new Weather());
		realworld.setDirection(new Direction());
		realworld.setDate(new Date());
		try{
			for(int i = 0 ; i < this.size() ; i ++){
				realworld.getWeather().setTemperature		(realworld.getWeather().getTemperature		() + this.get(i).getWeather().getTemperature		());
				realworld.getWeather().setRainfall			(realworld.getWeather().getRainfall		    () + this.get(i).getWeather().getRainfall		());
				realworld.getWeather().setInsolation		(realworld.getWeather().getInsolation		() + this.get(i).getWeather().getInsolation		());
				realworld.getWeather().setHumidity			(realworld.getWeather().getHumidity		    () + this.get(i).getWeather().getHumidity		());
				realworld.getWeather().setDust				(realworld.getWeather().getDust			    () + this.get(i).getWeather().getDust			());
				realworld.getWeather().setCloud				(realworld.getWeather().getCloud			() + this.get(i).getWeather().getCloud			());
				realworld.getWeather().setSnow				(realworld.getWeather().getSnow			    () + this.get(i).getWeather().getSnow			());
				realworld.getWeather().setWind				(realworld.getWeather().getWind			    () + this.get(i).getWeather().getWind			());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return realworld;
	}
	

	
	
	
	


}
