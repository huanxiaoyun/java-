package cn.tedu.shoot;
import java.awt.image.BufferedImage;

public class Airplane extends Flyingobject implements Enemy{
	private static BufferedImage[] images;
	static {
			images = new BufferedImage[5];
			for(int i=0;i<images.length;i++) {
				images[i] = readImage("airplane"+i+".png");
			}
	}
	
	private int speed;//移动速度
	//
	public Airplane(){ 
		super(49,36);
		speed = 2;
	}
	
	/**小敌机移动*/
	public void step() {
		y+=speed;//y+(向下)
	}
	
	int index = 1;
	/**重写小敌机getImage()*/
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
		 return 1;//
	 }
}
				