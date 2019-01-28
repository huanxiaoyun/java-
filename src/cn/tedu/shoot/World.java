package cn.tedu.shoot;
import javax.swing.JFrame;//窗口
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;//定时器
import java.util.TimerTask;//定时任务，定时干的事情
import java.util.Random;//引用Random
import java.util.Arrays;
import java.awt.event.MouseAdapter;//侦听器的类
import java.awt.event.MouseEvent;///侦听器的一些参数
import java.awt.image.BufferedImage;//引图片
//主窗口
public class World extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	
	public static final int START = 0;//启动状态
	public static final int RUNNING = 1;//运行状态
	public static final int PAUSE = 2;//
	public static final int GAME_OVER = 3;
	private int state = START;//当前状态
	
	private static BufferedImage start;	//启动图
	private static BufferedImage pause; //暂停图
	private static BufferedImage gameover; //游戏结束图
	static {
		start = Flyingobject.readImage("start.png");
		pause = Flyingobject.readImage("pause.png");
		gameover = Flyingobject.readImage("gameover.png");
	}
	
	private Sky sky = new Sky();					   //一个背景1窗口哦
	private Hero hero = new Hero();				  //一个英雄机
	Flyingobject[] enemies = {}; //敌人:小敌机+大敌机+小蜜蜂
	Bullet[] bullets = {}; //子弹数组
	
	/**生成敌人*/
	public Flyingobject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type<5) {
			return new Bee();
		}else if(type<12) {
			return new Airplane();
		}else {
			return new BigAirplane();
		}
	}
	
	int enterIndex =0;//敌人入场计数
	/**敌人入场*/
	public void enterAction() {//每10毫米走一次
		enterIndex++;//每10毫秒增1
		if(enterIndex%40==0) {//每400毫秒走一次
			Flyingobject obj = nextOne();//获取敌人对象
			enemies = Arrays.copyOf(enemies, enemies.length+1);//敌人扩容
			enemies[enemies.length-1] = obj;//将敌人放最后一位
		}
	}
	
	int shootIndex = 0;
	/**子弹入场*/
	public void shootAction() {//每10毫秒走一次
		shootIndex++;
		if(shootIndex%30==0) {////每300毫秒走一次
			Bullet[] bs = hero.shoot(); //获取子弹对象
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);//扩容(bs)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
		}
	}
	
	/**飞行物移动*/
	public void stepAction() {
		sky.step();
		
		for(int i=0;i<enemies.length;i++) {
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step();
		}
	}
	
	/**越界的处理*/
	public void outOfBoundsAction() {
		int index =0;//1)下标  2）不越界的敌人个数
		Flyingobject[] enemyLives = new Flyingobject[enemies.length];
		for(int i=0;i<enemies.length;i++) {
			Flyingobject f = enemies[i];
			if(!f.outOfBounds()&& !f.isRemove()) {//不越界的个数
				enemyLives[index] = f;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);//将不越界敌人数组赋给enemies画出来
		
		index = 0;//1)下标  2）不越界的子弹个数
		Bullet[] buls = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++) {
			Bullet b = bullets[i];
			if(!b.outOfBounds() && !b.isRemove()) {
				buls[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(buls,index);
	}
	
	int score = 0;//玩家的得分
	/**子弹与敌人的碰撞*/
	public void bulletBangAction() {
		for(int i=0;i<bullets.length;i++) {
			Bullet b= bullets[i];//每个子弹
			for(int j=0;j<enemies.length;j++) {
				Flyingobject f= enemies[j];//每个敌人
				if(b.isLife() && f.isLife() && f.hit(b)) {//活着并撞上了
					b.goDead();//子弹去死
					f.goDead();//敌人去死
					
					if(f instanceof Enemy) {//所有的得分
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					
					//Bee(
					//BigyelloWBee
					if(f instanceof Award) {//若被撞对象为奖励
						Award a = (Award)f;	 //将被撞对象强转为奖励
						int type = a.getAwardType();//获取奖励类型
						switch(type) {//
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						}
					}
					
				}
			}
		}
	}
	
	/**英雄机与敌人碰撞*/
	public void heroBangAction() {
		for(int i=0;i<enemies.length;i++) {
				Flyingobject f= enemies[i];//每个敌人
				if(hero.isLife() && f.isLife() && f.hit(hero)) {//活着并撞上了
					f.goDead();//敌人去死
					hero.subtractLife();//英雄机减命
					hero.clearDoubleFire();//清空英雄机火力值
				}
		}
	}
	/**检测游戏结束*/
	public void checkGameoverAction() {
		if(hero.getLife()<=0) {//游戏结束了
			state = GAME_OVER;
		}
	}
		
	
	
	
	/**启动程序的执行*/
	public void action() {
		/**侦听器*/
		MouseAdapter l = new MouseAdapter() {
			/**重写鼠标移动事件*/
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
				int x = e.getX();
				int y = e.getY();
				hero.moveTo(x,y);
				}
			}
			/**重写鼠标点击事件*/
			public void mouseClicked(MouseEvent e) {
				switch(state) {//根据当前状态做不同的处理
				case START:
					state=RUNNING;
					break;
				case GAME_OVER://游戏结束时
					score = 0;	//清理现场
					sky = new Sky();
					hero = new Hero();
					enemies = new Flyingobject[0];
					bullets = new Bullet[0];
					state=START;//修该为启动状态
					break;
				}
			}
			
			/**重写鼠标移出事件*/
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) {//运行状态时
					state=PAUSE;//修改为暂停状态
				}
			}
			/**重写鼠标移入事件*/
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {
					state=RUNNING;
				}
			}
			
		};
		this.addMouseListener(l);//处理鼠标点击操作事件
		this.addMouseMotionListener(l);//处理鼠标移动
		
		/**定时器*/
		Timer timer = new Timer();//定时器对象
		int interval = 10;//执行时间
		timer.schedule(new TimerTask() {
			public void run() {//定时干的事
				if(state==RUNNING) {
				enterAction();//敌人（小敌机...
				shootAction();//子弹入场
				 stepAction();//飞行物
				 outOfBoundsAction();//删除越界敌人和子弹
				 bulletBangAction();//子弹与敌人碰撞
				 heroBangAction();//英雄机与敌人碰撞
				 checkGameoverAction();//检测游戏结束
				}
				repaint();//重画（重新调用）
			}
		},interval,interval);//定时计划表
	}
	
	/**重写paint（）画对象,h:画笔*/
	
	public void paint(Graphics h) {
		sky .paintObject(h);//画天空
		hero.paintObject(h);//画英雄机
		for(int i=0;i<enemies.length;i++) {//。。
			enemies[i].paintObject(h);
		}
		for(int i=0;i<bullets.length;i++) {//。。
			bullets[i].paintObject(h);
		}
		
		h.drawString("Score："+score, 10, 25);
		h.drawString("Life："+hero.getLife(), 10, 45);
		
		switch(state) {
		case START:
			h.drawImage(start,0,0,null);
			break;
		case PAUSE:
			h.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			h.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	public static void main(String[] args) {
			JFrame frame = new JFrame();
			World word = new World();
			frame.add(word);//
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(WIDTH,HEIGHT);
			frame.setLocationRelativeTo(null); 
			frame.setVisible(true); //1.设置可见 2.
			
			word.action();
			
			
	}
}



/**
 * 作业：
 * 1.今天的所有项目功能做3遍
 * 
 *014 000000022
 * 
 * 
 * */
