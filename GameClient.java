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
* @Description: ��Ϸ�ͻ���
* @author IG-Jack five
* @date 2019��8��17�� ����11:24:37
* @version 0.1
*/
public class GameClient extends Frame {
	//����mouse
	//Mouse mouse = new Mouse(100, 200, "piane/feiji.png",this,true);
	//�����ҷ���ɫ�ļ���
	public ArrayList<Mouse> mouses = new ArrayList<>();
	
	//�������߼���
	public ArrayList<Prop> props = new ArrayList<>();
	
	
	//�����ӵ��ļ���
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	//����һ������ͼ
	BackGround backImg = new BackGround(0, 0, "6.png");
	
	//����һ����ը����
	public ArrayList<Boom> booms = new ArrayList<>();
	
	
	//�����з�����
	public ArrayList<Mouse> enemys = new ArrayList<>();
	
	//����boss����
	public ArrayList<Mouse> bosss = new ArrayList<>();
	
	
	//���ͼƬ��˸������
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
	//���ɿͻ��˵ķ���
	public void launchFrame() {
		
		SoundPlayer soundPlayer = new SoundPlayer("sound/beijing.mp3");
		soundPlayer.start();
		//���
		this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		//����
		this.setTitle("������");
		//�����Ƿ��ܹ���ʾ
		this.setVisible(true);
		//��ֹ���
		this.setResizable(false);
		//���ھ���
		this.setLocationRelativeTo(null);
		//�رմ��� ��ӹرռ�����
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		mouse = new Mouse(100, 200, "piane/feiji.png",this,true);
		//���ҷ�������ӽ�ɫ
		mouses.add(mouse);
		
		//���������
		//this.addMouseListener(new MouseAdapter() {
		//	@Override
		//	public void mouseClicked(MouseEvent e) {
		//		System.out.println("���");
	     //	}
		//	});
		//���̼���
		this.addKeyListener(new KeyAdapter() {
			//���̰��µ����
			@Override
			public void keyPressed(KeyEvent e) {
			    mouse.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				mouse.keyReleased(e);
			}
		});
		
		//�����߳�
		new panintThread().start();
		
		
		//���ط�������ӵ���
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
		
		//���boss
		bosss.add(new Boss(800, 300, this,false));
		
	}
	//��дpaint����
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
         
         //��ǿѭ������ÿ���ӵ�
         //��ǿforѭ���������κβ���
         for(int i=0; i<bullets.size();i++)
         {    
        	 Bullet bullet = bullets.get(i);
        	 bullet.draw(g);
        	 bullet.hitMonsters(enemys);
        	 bullet.hitMonsters(mouses);
        	 bullet.hitMonsters(bosss);
         }
       
         g.drawString("��ǰ�ӵ���������"+bullets.size(),10 ,50 );
         g.drawString("��ǰ�л�������"+enemys.size(), 10, 70);
         g.drawString("��ǰ��ը������"+booms.size(), 10, 90);
         //ѭ�����л�
         for(int i=0;i<enemys.size();i++) {
        	 enemys.get(i).draw(g);
         }
         //ѭ����ը
         for(int i=0;i<booms.size();i++) {
        	 if(booms.get(i).isLive()==true) {
        	 booms.get(i).draw(g);
        	 }
          }
       //ѭ��������
         for(int i=0;i<props.size();i++) {
        	 props.get(i).draw(g);
        	 }
         
         //Boss����ʱ��
         if(enemys.size()==0) {
        	//ѭ����boss
             for(int i=0;i<bosss.size();i++) {
            	 bosss.get(i).draw(g);
            	 }
        	 
         }
         
        
       }
    
	//�ڲ���
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
