package net.tfobz.minmax;

public class FindMax extends Thread{
	private int [] arr;
	
	/**
	 * FindMax
	 * @param arry
	 */
	public FindMax(int [] arry) {
		arr = arry;
	}
	
	/**
	 * run
	 */
	@Override
	public void run() {
		int ret = arr[0];
		//Ermittelt die groesste Zahl und speichert diese in ret
		for (int index = 0; index < arr.length; index++) {
			FindGUI.progressBars[1].setValue((index/(arr.length/100))+1);
			if (arr[index] > ret) {
				ret = arr[index];
			}
		}
		FindGUI.texts[1].setText(String.valueOf(ret));
	}
}
