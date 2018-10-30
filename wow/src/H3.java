/**
 * @Comments ：
 * @Author ：Chen Yanji
 * @Date ：2012-1-16 下午04:57:22
 * @Project ：test
 * @Company ：Vstsoft
 */
public class H3 {

	static String s[] = { "紫", "绿", "黄", "蓝", "红", "黑" };
	static String ss[] = { "4次治疗", "4M分散", "50%速度", "吸蓝",
			"AOE", "软泥" };

	/**
	 * @Comments ：
	 * @param args
	 * @Author ：Chen Yanji
	 * @Date ：2012-1-16 下午04:57:22
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		aa();
	}

	public static void aa() {
		for (int i = 1111; i < 9999; i++) {
			String str = String.valueOf(i);
			int i1 = Integer.parseInt(str.substring(0, 1));
			int i2 = Integer.parseInt(str.substring(1, 2));
			int i3 = Integer.parseInt(str.substring(2, 3));
			int i4 = Integer.parseInt(str.substring(3, 4));
			if (i1 < i2 && i2 < i3 && i3 < i4) {
				if (in(i1) && in(i2) && in(i3) && in(i4)) {
					System.out.print(s[i1 - 1] + "" + s[i2 - 1] + ""
							+ s[i3 - 1] + "" + s[i4 - 1] + "\t");
					System.out.print("(" + ss[i1 - 1] + "+" + ss[i2 - 1] + "+"
							+ ss[i3 - 1] + "+" + ss[i4 - 1] + ")"); 
					System.out.println("\t杀：");
				}
			}
		}
	}

	public static boolean in(int i) {
		if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
			return true;
		} else {
			return false;
		}
	}

}

