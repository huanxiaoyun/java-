package cn.tedu.shoot;
import java.awt.image.BufferedImage;//图片
import java.util.Random;
import javax.imageio.ImageIO;//？图片
import java.awt.Graphics;//画图片

public abstract class Flyingobject {
	public static final int LIFE = 0;//活着的
	public static final int DEAD = 1;//死了的
	public static final int REMOVE = 2;//删除的
	protected int statc = LIFE;//当前状态(默认的当前状态) 
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	//小敌机，大敌机，小米峰
	public Flyingobject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();
		this.x = rand.nextInt(World.WIDTH-this.width);//0到（窗口宽）
		this.y = -this.height;//
	}
	//天空，英雄，子弹
	public Flyingobject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x =		x;
		this.y = 	y; 
	}
	
	/**飞行物移动*/
	public abstract void step();
	
	/**同包下读图片*/
	public static BufferedImage readImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(Flyingobject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**获取图片*/
	public abstract BufferedImage getImage();
	
	/**判断活着的*/
	public boolean isLife() {
		return statc == LIFE;
	}
	/**判断死了的*/
	public boolean isDead() {
		return statc == DEAD;
	}
	/**判断删除*/
	public boolean isRemove() {
		return statc == REMOVE;
	}
	
	/**画图片*/
	public void paintObject(Graphics g) {
		g.drawImage(this.getImage(),this.x,this.y,null);
	}
	
	/**敌人越界检测*/
	public boolean outOfBounds() {
		return this.y>=World.HEIGHT;//敌人的y>=窗口的高
	}
	
	/**碰撞检测*/
	public boolean hit(Flyingobject other) {
		int x1 = this.x-other.width;
		int x2 = this.x+this.width;
		int y1 = this.y-other.height;
		int y2 = this.y+this.height;
		int x = other.x;
		int y = other.y;
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;//
	}
	
	/***/
	public void goDead() {
		statc = DEAD;
	}
	
	
	
}
