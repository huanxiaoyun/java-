package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends Flyingobject implements Award{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
		images[i] = readImage("bee"+i+".png");
		}
	}
	private int xspeed;//x坐标移动速度
	private int yspeed;//y坐标移动速度
	private int awardType;//奖励类型
	public Bee(){
		super(60,50);
		xspeed = 1;
		yspeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2);//0到1之间的随机数
	}
	/**小蜜蜂移动*/
	public void step() {
		x+=xspeed;//x+(向左或向右)
		y+=yspeed;//y+(向下)
		if(x<=0 || x>=World.WIDTH-this.width) {//若到两边了
			xspeed*=-1;//
		}
	}
	
	int index = 1;
	/**重写小敌机getImage()*/
	 public BufferedImage getImage() {
		 if(isLife()) {//活
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
	 
	 /**重写getAwardType()获取奖励*/
	 public int getAwardType() {
		 return awardType;//返回奖励
	 }
	 
	 /* index =1
	  * 10M img = images[1] index=2 返回images[1]
	  * 20M img = images[2] index=3 返回images[2]
	  * 30M img = images[3] index=4 返回images[3]
	  * 40M img = images[4] index=5(REMOVE) 返回images[4]
	  * */
}
