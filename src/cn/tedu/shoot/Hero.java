package cn.tedu.shoot;
import java.awt.image.BufferedImage;

/**英雄机*/
public class Hero extends Flyingobject{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		images[0] = readImage("hero0.png");
		images[1] = readImage("hero1.png");
	}
	
	private int life;//生命 
	private int doubleFire;//火力值
	//
	public Hero(){ 
		super(97,124,150,400);
		life = 3;
		doubleFire = 0;
	}
	
	public void moveTo(int x,int y) {//英雄机随鼠标移动x/y
		this.x = x-this.width/2;//英雄机的x = 鼠标的x-1/2英雄机的宽
		this.y = y-this.height/2;//英雄机的x = 鼠标的x-1/2英雄机的高
	}
	
	/**英雄机移动*/
	public void step() {
		
	}
	
	int index =0;//下标
	 /**重写英雄机getImage()*/
	 public BufferedImage getImage() {
		 if(isLife()) {
			 return images[index++%images.length];
		 }
		 return null;
	 }
	 
	 /**开火*/
	 public Bullet[] shoot() {
		 int xStep = this.width/4;//4分之1英雄机的宽
		 int yStep = 20;						//固定的24
		 if(doubleFire>0) {//双
			 Bullet[] bs = new Bullet[2];
			 bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);//
			 bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);//
			 doubleFire-=2;//发射一次双倍火力，则火力值减
			 return bs;
		 }else {
			 Bullet[] bs = new Bullet[1];
			 bs[0] = new Bullet(this.x+2*xStep,this.y-yStep);//
			 return bs;
		 }
	 }
	 /**生命值奖励*/
	 public void addLife() {
		 	life++;
	 }
	 
	 /**生命值获取*/
	 public int getLife() {
		 	return life;//返回命数
	 }
	 /**英雄机减命*/
	 public void subtractLife() {
		 	life--;
	 }
	 /**清空英雄机火力值*/
	 public void clearDoubleFire() {
		 doubleFire=0;
	 }
	 
	 /**火力值奖励*/
	 public void addDoubleFire() {
		 doubleFire+=40;
	 }

	 
}
