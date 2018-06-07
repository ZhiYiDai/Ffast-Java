package cn.ffast.core.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码工具类
 */
public class PasswordUtil {
	/**
	 * 取得随机字符串
	 * @param from
	 * @param distance
	 * @return
	 */
	public static String getRandom(int from,int distance){
		int no =(int) (distance* Math.random() +from );
		return RandomStringUtils.randomAlphanumeric(no).toLowerCase();
	}

	/**
	 * 取得用户密码
	 * @param prefix
	 * @param pwd
	 * @param suffix
	 * @return
	 */
	public static String getPwd(String prefix,String pwd,String suffix){
		try {
			String md5PWD = Hex.encodeHexString(CodeUtil.encryptMD5(pwd.getBytes()));
			String str = prefix+md5PWD+suffix;
			return Hex.encodeHexString(CodeUtil.encryptSHA(str.getBytes()));
		} catch (Exception e) {
			new RuntimeException(e.getMessage());
		}
		return null;
	}

	/**
	 * @description: 取得用户密码
	 * @createTime: 2017年6月2日 下午6:38:03
	 * @author: lys
	 * @param prefix
	 * @param pwd
	 * @param suffix
	 * @return
	 */
	public static String getPwd(String prefix,String pwd){
		return getPwd(prefix,pwd,"");
	}


	/**
	 * 密码验证:不能全是数字或全是字母，长度>6
	 */
	public static Boolean valdateUsepwd(String usepwd) {
		String pwdPattern = "(?!^\\d+$)(?!^[a-zA-Z]+$).{6,}";
		Pattern pattern = Pattern.compile(pwdPattern);
		Matcher matcher = pattern.matcher(usepwd);
		return matcher.matches();
	}
	
	
	public static void main(String[] args) {

		System.out.println(PasswordUtil.getPwd("sfff","123456"));
	}
	
}
