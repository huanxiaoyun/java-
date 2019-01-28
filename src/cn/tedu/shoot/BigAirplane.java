package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class BigAirplane extends Flyingobject implements Enemy{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
			images[i] = readImage("bigplane"+i+".png");
		}
	}
	
	private int speed;//移动速度
	public BigAirplane(){
		super(69,99);
		speed = 2;
	} 
	
	/**大敌机移动*/
	public void step() {
		y+=speed;//y+(向下)
	}
	
	int index = 1;
	/**重写大敌机getImage()*/
	 public BufferedImage getImage() {
		 if(isLife()) {
			 return images[0];
		 }else if(isDead()) {
			 BufferedImage img = images[index++];
			 if(index == images.length) {
				 statc = REMOVE;
			 }
			 return img;
		 }
		 return null;
	 }
	 
	 /**重写getScore()得分*/
	 public int getScore() {
		 return 3;//
	 }
}
