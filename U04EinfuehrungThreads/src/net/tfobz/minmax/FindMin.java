package net.tfobz.minmax;

public class FindMin extends Thread{
	private int [] arr;
	/**
	 * FindMin
	 * @param arry
	 */
	public FindMin(int [] arry) {
		arr = arry;
	}
	
	/**
	 * run
	 */
	@Override
	public void run() {
		int ret = arr[0];
		//Ermittelt die kleinste Zahl und speichert diese in ret
		for (int index = 0; index < arr.length; index++) {
			FindGUI.progressBars[0].setValue((index/(arr.length/100))+1);
			if (arr[index] < ret) {
				ret = arr[index];
			}
		}
		FindGUI.texts[0].setText(String.valueOf(ret));
	}
}
