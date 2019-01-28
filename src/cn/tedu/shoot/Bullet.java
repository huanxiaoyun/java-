package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Bullet extends Flyingobject{
	private static BufferedImage image;
	static {
		image = readImage("bullet.png");
	}
	
	private int speed;//移动速度
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 4; 
	}
	/**子弹移动*/
	public void step() {
		y-=speed;//y+(向下)
	}
	 /**重写getImage()获取图片*/
	 public BufferedImage getImage() {
		 if(isLife()) {//活着的
			 return image;//返回image
		 }
		 if(isDead()) {//死了的
			 statc = REMOVE;//状态修改为REMOVE
		 }
		 return null;//DEAD和Remove时,返回null
	 }
	 
	 /**重写outOfBounds()越界检测*/
	 public boolean outOfBounds() {
		 return this.y<=-this.height;//子弹的y大于等于-的子弹的高
	 }
}
