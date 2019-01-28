package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**天空*/
public class Sky extends Flyingobject{
	private static BufferedImage image;
	static {
		image = readImage("background.png");
	}
	
	private int speed;//移动速度
	private int y1;//第二张图片的y坐标
	/**构造方法**/ 
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		speed = 1;
		y1 = -World.HEIGHT;
	}
	
/**重写天空移动*/
 public void step() {
	y+=speed;//y+(向下)
	y1+=speed;//y1+(向下)
	if(y>=World.HEIGHT) {//若y>=窗口的高,0意味着
		y=-World.HEIGHT;
	}
	if(y1>World.HEIGHT) {
		y1=-World.HEIGHT;
	}
}
 
 /**重写getImage()*/
 public BufferedImage getImage() {
	 return image;
 }
 
	/**画对象*/
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}

}
