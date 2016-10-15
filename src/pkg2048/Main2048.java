/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

/**
 *
 * @author Rear82
 */
import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main2048 {

	/**
	 * @param args the command line arguments
	 */
	static int caozuoshu;
	static int tu[][] = new int[4][4];
	static int tuback[][][] = new int[10000][4][4];
	static int fang, score;
	static int scoreback[] = new int[10000];
	static boolean shifou, suv;

	public static void cundang() throws Exception {
		File file = new File("存档.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter br = new BufferedWriter(new FileWriter(file));

		br.write(Integer.toString(score));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				br.newLine();
				br.write(Integer.toString(tu[i][j]));
			}
		}
		br.close();
	}

	public static void duqu() throws Exception {
		File file = new File("存档.txt");
		if (!file.exists()) {
			System.out.println("没有发现存档文件！");
			qingkong();
			shengcheng();
			shengcheng();
		} else {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String s = null;
			int[] shuju = new int[17];
			int i = 0;
			while ((s = br.readLine()) != null) {
				shuju[i] = Integer.parseInt(s);
				i++;
			}
			score = shuju[0];
			for (i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					tu[i][j] = shuju[i * 4 + j + 1];
				}
			}
		}
	}

	public static void jilu() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tuback[caozuoshu][i][j] = tu[i][j];
			}
		}
		scoreback[caozuoshu] = score;
	}

	public static void huifu() {
		caozuoshu--;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tu[i][j] = tuback[caozuoshu][i][j];
			}
		}
		score = scoreback[caozuoshu];
	}

	public static void myprint(String c, int a) {
		for (int i = 0; i < a; i++) {
			System.out.print(c);
		}
	}

	public static void myprint(String c, int a, String b) {
		for (int i = 0; i < a; i++) {
			System.out.print(c);
		}
		System.out.print(b);
	}

	public static int ram(int a, int b) {
		int c = (int) (Math.random() * (b - a + 1)) + a;
		return c;
	}

	public static int len(int a) {
		int s, i;
		s = 0;
		for (i = 10; i > 0; i--) {
			if ((int) (a / (Math.pow(10, i))) == 0) {
				s = i;
			}
		}
		return s;
	}

	public static String zhuanhuan(int b) {
		String s = "";
		String y = s;
		int t = (int) (4 - len(b)) / 2;
		for (int i = 0; i < t; i++) {
			s = s + " ";
		}

		for (int i = 0; i < 4 - t - len(b); i++) {
			y = y + " ";
		}
		if (b == 0) {
			s = s + " " + y;
		} else {
			s = s + Integer.toString(b) + y;
		}
		return s;
	}

	public static void xmlprint(int arr[][]) {
		System.out.println("┌──┬──┬──┬──┐");
		String[][] b = new String[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				b[i][j] = zhuanhuan(arr[i][j]);

				b[i][j] = b[i][j] + "│";

				if (j == 0) {
					b[i][j] = "│" + b[i][j];
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(b[i][j]);
				if (j == 3 & i != 3) {
					System.out.println("\n├──┼──┼──┼──┤");
				}
			}
		}
		System.out.println("\n└──┴──┴──┴──┘\n");

	}

	public static void shengcheng() {
		int x, y;
		x = ram(0, 3);
		y = ram(0, 3);

		while (tu[x][y] != 0) {
			x = ram(0, 3);
			y = ram(0, 3);
		}

		int a = ram(1, 2);
		tu[x][y] = a * 2;

	}

	public static void yidong(int arr[][]) {
		int panduan;
		for (int i = 0; i < 4; i++) {
			panduan = 0;
			for (int j = 2; j > -1; j--) {
				if (arr[i][j] != 0) {

					for (int k = 1 + j; k <= 3; k++) {
						if (arr[i][k] != 0) {
							panduan = 1;
							if (arr[i][j] == arr[i][k]) {
								arr[i][j] = 0;
								score = score + arr[i][k] * 2;
								arr[i][k] = arr[i][k] * 2;
								shifou = true;
							} else {
								if ((k - 1) != j) {
									arr[i][k - 1] = arr[i][j];
									arr[i][j] = 0;
									shifou = true;
								}
							}
							break;
						}
					}
					if (panduan == 0) {
						arr[i][3] = arr[i][j];
						arr[i][j] = 0;
						shifou = true;
					}
				}
			}
		}
	}

	public static void gameover() {
		suv = false;
		myprint("*", 20);
		System.out.println("\n");
		myprint(" ", 6, "game over!");
		System.out.println("\n");
		myprint(" ", 3, "Your score is " + score);
		System.out.println("\n");
		System.out.println("\n是否重新开始游戏？(输入y并按下回车来重新开始)\n");

		myprint("*", 20);
		Scanner input = new Scanner(System.in);
		String m = input.next();
		switch (m) {
			case "y":
			case "Y":
				System.out.println("已经重新开始游戏！");
				suv = true;
				qingkong();
				shengcheng();
				shengcheng();
				jilu();
				xmlprint(tu);
				while (suv == true) {
					game();
				}
				break;
		}
	}

	public static void game() {
		if (suv = true) {
			shifou = false;
			Scanner input = new Scanner(System.in);
			String m = input.next();
			String n = m;
			switch (n) {
				case "b":
					if (caozuoshu > 0) {
						huifu();
						xmlprint(tu);
					}
					break;
				case "a":
				case "w":
				case "d":
				case "s":

					switch (m) {
						case "d":
							fang = 1;
							break;
						case "s":
							fang = 2;
							break;
						case "a":
							fang = 3;
							break;
						case "w":
							fang = 4;
							break;
					}

					int[][] linshi = new int[4][4];
					switch (fang) {
						case 1:
							yidong(tu);
							break;

						case 4:
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									linshi[i][j] = tu[3 - j][i];
								}
							}
							yidong(linshi);
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									tu[i][j] = linshi[j][3 - i];
								}
							}
							break;

						case 3:
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									linshi[i][j] = tu[i][3 - j];
								}
							}
							yidong(linshi);
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									tu[i][j] = linshi[i][3 - j];
								}
							}
							break;

						case 2:
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									linshi[i][j] = tu[j][3 - i];
								}
							}
							yidong(linshi);
							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									tu[i][j] = linshi[3 - j][i];
								}
							}
							break;
					}
					if (shifou == true) {
						caozuoshu++;
						jilu();

						Timer timer = new Timer();

						System.out.println("移动完成：\n");
						xmlprint(tu);
						System.out.println("正在随机生成新的数字：\n\n");
						timer.schedule(new mytask(), 250);

					} else {
						System.out.println("这是一次无效的移动！");
						int kl = 0;
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 4; j++) {
								if (tu[i][j] == 0) {
									kl = 1;
								}
							}
						}

						if (kl == 0) {
							gameover();
						}
					}
					break;
			}
		}
	}

	static class mytask extends TimerTask {

		public void run() {

			shengcheng();
			xmlprint(tu);
			System.out.println("已经随机生成新的数字：\n您现在的分数为" + score + "分\n");
			try {
				cundang();
			} catch (Exception ex) {
				Logger.getLogger(Main2048.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void qingkong() {
		shifou = false;
		caozuoshu = 0;
		score = 0;
		suv = true;
		score = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				tu[i][j] = 0;
			}
		}
	}

	public static void main(String[] args) {
		// TODO code application logic here
		System.out.println("欢迎来到2048小游戏！\n开发者:Rear82\n操作说明:使用w键向上，s键向下，d键向右，a键向左。\n您也可以输入b键来撤销上一步操作。\n输入1来载入上一次游戏，输入2来开始新游戏！");
		Scanner input = new Scanner(System.in);
		String m = input.next();
		int u = 0;
		switch (m) {
			case "1": {
				suv = true;
				try {
					duqu();
				} catch (Exception ex) {
					Logger.getLogger(Main2048.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			u = 1;

			break;
		}
		if (u == 0) {
			qingkong();
			shengcheng();
			shengcheng();
		}
		jilu();

		xmlprint(tu);
		while (suv == true) {
			game();

		}
		//System.out.println(len(0));
	}

}
