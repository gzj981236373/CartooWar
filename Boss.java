package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: Boss
* @Description: boss类
* @author IG-Jack five
* @date 2019年8月22日 下午2:55:06
*
*/
public class Boss extends Mouse implements ActionAble {
	private boolean up = true;
	
	
	private int speed = 1;
	
	public Boss() {
		// TODO Auto-generated constructor stub
	}
	
	public Boss(int x, int y,GameClient gc,boolean isGood) {
		this.x = x;
		this.y = y;
		this.gc = gc;
		this.isGood = isGood;
		this.blood = 100000;
	}
	
	
	
	
	//动态一个图片的数组
	private static Image[] imgs = new Image[4];
	static {
		for(int i =0; i<imgs.length;i++) {
			imgs[i] = GetImageUtil.getImg("boss/boss"+(i+1)+".png");
		} 
	}
    int count = 0;
	@Override
	public void draw(Graphics g) {
		if(count > 3) {
			count = 0;
		}
		
		g.drawImage(imgs[count++], x, y, null);
		move();
		g.drawString("当前血量："+blood, x, y);
	}
	
	@Override
	public void move() {
		x -=speed;

		if(x < 750) {
			x = 750;
		
		
		if(up) {
			y -= speed;
		}
		if(!up){
			y += speed;
		}
		
		
			if(y>Constant.GAME_HEIGHT-imgs[0].getHeight(null)) {
				up = true;
			}
			if(y<30) {
				up = false;
			}
		}
		if(random.nextInt(500)>450) {
		    fire();
		}
	}
	
	//生成随机数
	Random random = new Random();
	//获取boss的矩形
	public Rectangle getRec() {
		return new Rectangle(x, y,imgs[0].getWidth(null), imgs[0].getHeight(null));
	}
	
    @Override
    public void fire() {
    	
    	Bullet b = new Bullet(x+imgs[0].getWidth(null)/2-10, y+imgs[0].getHeight(null)/2-20, "Bullet/feidan.png",gc,false);
		gc.bullets.add(b);
    }
	
}
