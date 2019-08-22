package com.neuedu.entity;

import java.awt.Graphics;

import com.neuedu.action.ActionAble;
import com.neuedu.util.GetImageUtil;

/**
* @ClassName: BackGround
* @Description: 背景类
* @author IG-Jack five
* @date 2019年8月20日 上午10:01:26
*
*/
public class BackGround extends GameObj implements ActionAble{

    private Integer speed;

	public BackGround() {
		// TODO Auto-generated constructor stub
	}	
	
    public BackGround(int x,int y,String imgName) {
    	this.x = x;
    	this.y = y;
    	this.img = GetImageUtil.getImg(imgName);
    	this.speed = 2;
    }
    
	
	
	
	
	
	
	@Override
	public void move() {
		x -= speed;
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
		move();
		
	}

}
