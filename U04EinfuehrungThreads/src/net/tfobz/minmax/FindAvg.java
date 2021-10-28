package net.tfobz.minmax;

public class FindAvg extends Thread{
	private int [] arr;
	/**
	 * FindMax
	 * @param arry
	 */
	public FindAvg(int [] arry) {
		arr = arry;
	}
	
	/**
	 * run
	 */
	@Override
	public void run() {
		double ret = 0;
		//Ermittelt die groesste Zahl und speichert diese in ret
		for (int index = 0; index < arr.length; index++) {
			FindGUI.progressBars[2].setValue((index/(arr.length/100))+1);
			ret = ret + arr[index];
		}
		ret = (ret/(double)arr.length);
		FindGUI.texts[2].setText(String.valueOf((int)ret));
	}
}
