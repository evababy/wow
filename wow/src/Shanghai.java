/**
 * @Comments ：
 * @Author ：陈彦吉
 * @Group : A组
 * @Worker: 1416
 * @Date ：2012-10-22 下午01:38:35
 * @Project ：wow
 * @Company ：Vstsoft
 */
public class Shanghai {

	/**
	 * @Comments ：寶庫5號第二次P1消BUFF最佳時間：12（8-15）
	 * @param args
	 * @Author ：陈彦吉
	 * @Group : A组
	 * @Worker: 1416
	 * @Date ：2012-10-22 下午01:38:35
	 */
	public static void main(String[] args) {
		double z = 0;
		double start = 18;
		int b = 0;
		for (int j = 1; j < 100; j++) {
			for (int i = 1; i < 100; i++) {

				if (b % j == 0) {
					b = 0;
					z += 50;
				} else {
					start = 18 * (1 + (b * 0.05));
				}
				z += start;
				b++;
			}
			System.out.println(j + ":" + z);
			z = 0;
		}

	}

}
