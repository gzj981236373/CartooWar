package com.neuedu.entity;

import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.neuedu.action.ActionAble;
import com.neuedu.client.GameClient;
import com.neuedu.constant.Constant;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SinglePlay;

/**
* @ClassName: Bullet
* @Description: 子弹类
* @author IG-Jack five
* @date 2019年8月19日 下午7:11:15
*
*/
public class Bullet extends GameObj implements ActionAble{
	//单次播放音乐的类
	SinglePlay singlePlay = new SinglePlay();	
	
	//创建速度
	private Integer speed;
	
	//拿到客户端子弹
	public GameClient gc;
	
	//子弹类型
	public boolean isGood;
	
	public Bullet() {
		 
	 }
    public Bullet(int x,int y,String imgName,GameClient gc,boolean isGood) {
    	
    	this.x = x;
    	this.y = y;
    	this.img = GetImageUtil.getImg(imgName);
    	this.speed = 20;
    	this.gc = gc;
    	this.isGood = isGood;
    }
	@Override
	public void move() {
		if(isGood) {
			x += speed;
		}
		else {
			x -=speed;
		}
		
	}
	//画子弹
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		outOfBounds();
	}
  
	//子弹越界销毁
	public void outOfBounds() {
		if(x>Constant.GAME_WIDTH||x<0) {
			gc.bullets.remove(this);
		}
		
	}
	//随机生成道具
	Random random = new Random();
	
	//子弹打一个小飞机
	public boolean hitMonster(Mouse mouse) {
		
		Boom boom = new Boom(mouse.x,mouse.y,gc);
		
		if(this.getRec().intersects(mouse.getRec())&&this.isGood!=mouse.isGood) {
			if(mouse.isGood) {
				//中弹减少血量
				mouse.blood -=10;
				if(mouse.blood ==0) {
					//战死
					gc.mouses.remove(mouse);
				
					
				    }
                    
				
				//移除子弹
				gc.bullets.remove(this);
				}else {
					 singlePlay.play("sound/djsw.mp3");
					if(mouse instanceof Boss) {
						
						
						mouse.blood -=100;
						if(mouse.blood <= 0) 
						{
							gc.bosss.remove(mouse);
							//移除子弹
							gc.bullets.remove(this);
						}
						
					}
					else {

						//移除敌机
						gc.enemys.remove(mouse);
						//移除子弹
						gc.bullets.remove(this);
						
						//随机生成道具
						if(random.nextInt(500)>400) {
							if(mouse instanceof EnemyMouse) {
								EnemyMouse enemyMouse = (EnemyMouse)mouse;
								Prop prop = new Prop(mouse.x+enemyMouse.imgs1[0].getWidth(null), mouse.y+enemyMouse.imgs1[0].getHeight(null), "prop/daoju1.png");
								gc.props.add(prop);
							}
			            }
					}
				}
			
			//添加爆炸
            gc.booms.add(boom);
		       return true;
       
		}
		return false;
	}
	//子弹打多个敌机
	public boolean hitMonsters(ArrayList<Mouse> monsters) {
		if(monsters==null) {
			return false;
		}
		
		for(int i=0;i<monsters.size();i++) 
		{
			if (hitMonster(monsters.get(i)))
			{
				return true;
			}
		}
		return false;
		}

	
	//获取子弹的矩形
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}

}
