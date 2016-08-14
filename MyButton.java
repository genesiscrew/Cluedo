package cluedo;

import javax.swing.JButton;
@SuppressWarnings("serial")
public class MyButton extends JButton{
	int x;
	int y;

	MyButton(int x, int y){
		this.x = x;
		this.y = y;

	}

	public int getX(){
		return this.x;

	}
	public int getY(){
		return this.y;

	}


}
