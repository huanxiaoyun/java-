package cn.tedu.shoot;
/**奖励接口*/
public interface Award {
	public int DOUBLE_FIRE = 0;//火力值
	public int LIFE = 1;				//生命
	/**获取奖励类型(0或1或2)*/
	public int getAwardType();
}
