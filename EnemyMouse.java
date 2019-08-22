package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: EnemyMouse
* @Description: 敌机类
* @author IG-Jack five
* @date 2019年8月21日 上午10:36:46
*
*/
public class EnemyMouse extends Mouse implements ActionAble{

	private Integer enemyType;
    
	
	private Integer speed;
	
	private GameClient gc;
	
	
	
	public static Image[] imgs1 = {
			GetImageUtil.getImg("enemy/fjj001.png"),
			GetImageUtil.getImg("enemy/fjjj001.png"),
			
			
	};
	
    public EnemyMouse(){
    	
    }
    public EnemyMouse(int x,int y,int enemyType,GameClient gc,boolean isGood){
    	this.x = x;
    	this.y = y;
    	this.enemyType = enemyType;
    	this.speed =1;
    	this.gc = gc;
    	this.isGood = isGood;
    }
    @Override
    public void move() {
    	x -=speed;
    }
    int count = 0;
    //画敌方飞机
	@Override
	public void draw(Graphics g) {
		if(count > 1 ) {
			count = 0;
		}
	   
	g.drawImage(imgs1[count++], x, y, null);
	move();
	
	
	if(random.nextInt(500)>490) {
		fire();
	}
	
}
	//随机数
	Random random = new Random();
	
	//敌军开火
	public void fire() {
		
		Bullet bullet = new Bullet(x, y+imgs1[0].getHeight(null)/2,"Bullet/001.png", gc,false);
		gc.bullets.add(bullet);
	}
	

	//获取敌机的矩形
	public Rectangle getRec() {
		return new Rectangle(x, y, this.imgs1[0].getWidth(null), this.imgs1[0].getHeight(null));
	}

}
