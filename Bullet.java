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
* @Description: �ӵ���
* @author IG-Jack five
* @date 2019��8��19�� ����7:11:15
*
*/
public class Bullet extends GameObj implements ActionAble{
	//���β������ֵ���
	SinglePlay singlePlay = new SinglePlay();	
	
	//�����ٶ�
	private Integer speed;
	
	//�õ��ͻ����ӵ�
	public GameClient gc;
	
	//�ӵ�����
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
	//���ӵ�
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		outOfBounds();
	}
  
	//�ӵ�Խ������
	public void outOfBounds() {
		if(x>Constant.GAME_WIDTH||x<0) {
			gc.bullets.remove(this);
		}
		
	}
	//������ɵ���
	Random random = new Random();
	
	//�ӵ���һ��С�ɻ�
	public boolean hitMonster(Mouse mouse) {
		
		Boom boom = new Boom(mouse.x,mouse.y,gc);
		
		if(this.getRec().intersects(mouse.getRec())&&this.isGood!=mouse.isGood) {
			if(mouse.isGood) {
				//�е�����Ѫ��
				mouse.blood -=10;
				if(mouse.blood ==0) {
					//ս��
					gc.mouses.remove(mouse);
				
					
				    }
                    
				
				//�Ƴ��ӵ�
				gc.bullets.remove(this);
				}else {
					 singlePlay.play("sound/djsw.mp3");
					if(mouse instanceof Boss) {
						
						
						mouse.blood -=100;
						if(mouse.blood <= 0) 
						{
							gc.bosss.remove(mouse);
							//�Ƴ��ӵ�
							gc.bullets.remove(this);
						}
						
					}
					else {

						//�Ƴ��л�
						gc.enemys.remove(mouse);
						//�Ƴ��ӵ�
						gc.bullets.remove(this);
						
						//������ɵ���
						if(random.nextInt(500)>400) {
							if(mouse instanceof EnemyMouse) {
								EnemyMouse enemyMouse = (EnemyMouse)mouse;
								Prop prop = new Prop(mouse.x+enemyMouse.imgs1[0].getWidth(null), mouse.y+enemyMouse.imgs1[0].getHeight(null), "prop/daoju1.png");
								gc.props.add(prop);
							}
			            }
					}
				}
			
			//��ӱ�ը
            gc.booms.add(boom);
		       return true;
       
		}
		return false;
	}
	//�ӵ������л�
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

	
	//��ȡ�ӵ��ľ���
	public Rectangle getRec() {
		return new Rectangle(x, y, this.img.getWidth(null), this.img.getHeight(null));
	}

}
