package com.neuedu.client;

import java.awt.Frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicListUI.PropertyChangeHandler;

import com.neuedu.constant.Constant;
import com.neuedu.entity.BackGround;
import com.neuedu.entity.Boom;
import com.neuedu.entity.Boss;
import com.neuedu.entity.Bullet;
import com.neuedu.entity.EnemyMouse;
import com.neuedu.entity.Mouse;
import com.neuedu.entity.Prop;
import com.neuedu.util.GetImageUtil;
import com.neuedu.util.SoundPlayer;


/**
* @ClassName: GameClient
* @Description: 游戏客户端
* @author IG-Jack five
* @date 2019年8月17日 上午11:24:37
* @version 0.1
*/
public class GameClient extends Frame {
	//创建mouse
	//Mouse mouse = new Mouse(100, 200, "piane/feiji.png",this,true);
	//创建我方角色的集合
	public ArrayList<Mouse> mouses = new ArrayList<>();
	
	//创建道具集合
	public ArrayList<Prop> props = new ArrayList<>();
	
	
	//创建子弹的集合
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	//创建一个背景图
	BackGround backImg = new BackGround(0, 0, "6.png");
	
	//创建一个爆炸集合
	public ArrayList<Boom> booms = new ArrayList<>();
	
	
	//创建敌方集合
	public ArrayList<Mouse> enemys = new ArrayList<>();
	
	//创建boss集合
	public ArrayList<Mouse> bosss = new ArrayList<>();
	
	
	//解决图片闪烁的问题
	public void update(Graphics g) {
		Image backImg = createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		Graphics backg = backImg.getGraphics();
		Color color = backg.getColor();
		backg.setColor(Color.WHITE);
		backg.fillRect(0,0,Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		backg.setColor(color);
		paint(backg);
		g.drawImage(backImg, 0, 0, null);
	}
	Mouse mouse = null;
	//生成客户端的方法
	public void launchFrame() {
		
		SoundPlayer soundPlayer = new SoundPlayer("sound/beijing.mp3");
		soundPlayer.start();
		//宽高
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		//标题
		this.setTitle("宁配吗？");
		//设置是否能够显示
		this.setVisible(true);
		//禁止最大化
		this.setResizable(false);
		//窗口居中
		this.setLocationRelativeTo(null);
		//关闭窗口 添加关闭监听器
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		mouse = new Mouse(100, 200, "piane/feiji.png",this,true);
		//往我放容器添加角色
		mouses.add(mouse);
		
		//添加鼠标监听
		//this.addMouseListener(new MouseAdapter() {
		//	@Override
		//	public void mouseClicked(MouseEvent e) {
		//		System.out.println("鼠标");
	     //	}
		//	});
		//键盘监听
		this.addKeyListener(new KeyAdapter() {
			//键盘按下的情况
			@Override
			public void keyPressed(KeyEvent e) {
			    mouse.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				mouse.keyReleased(e);
			}
		});
		
		//启动线程
		new panintThread().start();
		
		
		//往地方容器添加敌人
		EnemyMouse enemy1 = new EnemyMouse(800,100,1,this,false);
		EnemyMouse enemy2 = new EnemyMouse(800,150,1,this,false);
		EnemyMouse enemy3 = new EnemyMouse(700,100,1,this,false);
		EnemyMouse enemy4 = new EnemyMouse(700,150,1,this,false);
		EnemyMouse enemy5 = new EnemyMouse(800,200,1,this,false);
		EnemyMouse enemy6 = new EnemyMouse(700,200,1,this,false);
		EnemyMouse enemy7 = new EnemyMouse(800,250,1,this,false);
		enemys.add(enemy1);
		enemys.add(enemy2);
		enemys.add(enemy3);
		enemys.add(enemy4);
		enemys.add(enemy5);
		enemys.add(enemy6);
		enemys.add(enemy7);
		
		//添加boss
		bosss.add(new Boss(800, 300, this,false));
		
	}
	//重写paint方法
    @Override
    public void paint(Graphics g) 
    {
    	 backImg.draw(g);
    	 g.drawLine(800, 0,800, 600);
    	 for(int i=0;i<mouses.size();i++) {
    		 Mouse mouse2 = mouses.get(i);
    		 mouse2.draw(g);
    		 mouse2.containItems(props);
    	 }
         
         //增强循环画出每个子弹
         //增强for循环不能做任何操作
         for(int i=0; i<bullets.size();i++)
         {    
        	 Bullet bullet = bullets.get(i);
        	 bullet.draw(g);
        	 bullet.hitMonsters(enemys);
        	 bullet.hitMonsters(mouses);
        	 bullet.hitMonsters(bosss);
         }
       
         g.drawString("当前子弹的数量："+bullets.size(),10 ,50 );
         g.drawString("当前敌机的数量"+enemys.size(), 10, 70);
         g.drawString("当前爆炸的数量"+booms.size(), 10, 90);
         //循环画敌机
         for(int i=0;i<enemys.size();i++) {
        	 enemys.get(i).draw(g);
         }
         //循环爆炸
         for(int i=0;i<booms.size();i++) {
        	 if(booms.get(i).isLive()==true) {
        	 booms.get(i).draw(g);
        	 }
          }
       //循环画道具
         for(int i=0;i<props.size();i++) {
        	 props.get(i).draw(g);
        	 }
         
         //Boss出场时间
         if(enemys.size()==0) {
        	//循环画boss
             for(int i=0;i<bosss.size();i++) {
            	 bosss.get(i).draw(g);
            	 }
        	 
         }
         
        
       }
    
	//内部类
    class panintThread extends Thread
    {
    	
    	@Override
    	public void run() 
    	{
    		while(true)
    		{
    		    repaint();	
    		    try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }
}
