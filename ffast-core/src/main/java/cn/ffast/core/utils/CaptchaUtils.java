/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年1月13日 上午10:32:35
 */
package cn.ffast.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtils {
    private static Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);
    private final static int LINE = 6;
    private final static int SIZE = 4;

    private static Font BASE_FONT;

    static {
        try {
            InputStream is = CaptchaUtils.class.getResourceAsStream("/font/WellrockSlabBold.ttf");
            if(is==null){
                logger.error("font file is null");
            }
            BASE_FONT = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 随机中常用中文字
    // String words =
    // "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
    private final static String WORDS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789abcdefghjkmnpqrstuvwxyz";

    public static class Captcha {
        private String code;
        private BufferedImage image;

        Captcha(String code, BufferedImage image) {
            this.code = code;
            this.image = image;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }
    }

    public static Captcha createCaptcha(int WITDH, int HEIGHT) {
        // 生成一张图片
        BufferedImage image = new BufferedImage(WITDH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        // 为图片构建一只画笔
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 设置背景色
        setBackColor(g, WITDH, HEIGHT);
        // 设置边框
        setBorder(g, WITDH, HEIGHT);
        // 添加干扰线
        addRandomLine(g, WITDH, HEIGHT);
        // 添加随机常用中文字
        String checkcode = addRandomWord(g, WITDH, HEIGHT);

        return new Captcha(checkcode, image);
    }

    /*
     * 设置图片背景色
     */
    private static void setBackColor(Graphics2D g, int WITDH, int HEIGHT) {
        // 设置画笔颜色
        g.setColor(new Color(255, 255, 255));
        // 填充指定大小的矩形
        g.fillRect(0, 0, WITDH, HEIGHT);
    }

    /*
     * 设置边框颜色
     */
    private static void setBorder(Graphics2D g, int WITDH, int HEIGHT) {
        g.setColor(new Color(0, 0, 80));
        // 绘制指定矩形的边框
        //	g.drawRect(0, 0, WITDH - 1, HEIGHT - 1);
    }

    private static void addRandomLine(Graphics2D g, int WITDH, int HEIGHT) {
        // 创建随机对象
        Random random = new Random();
        g.setColor(new Color(random.nextInt(255), random.nextInt(255), 0));
        // 随机两个坐标,添加干扰线
        for (int i = 1; i <= LINE; i++) {
            int x1 = random.nextInt(WITDH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WITDH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1 + 10, y1 + 2, x2 - 10, y2 - 2);
        }
    }

    private static String addRandomWord(Graphics2D g, int WITDH, int HEIGHT) {
        StringBuilder sb = new StringBuilder();
        g.setColor(new Color(30, 30, 30));
        // 设置字体
        g.setFont(BASE_FONT.deriveFont(Font.BOLD, HEIGHT - 3));

        // 创建随机对象
        Random random = new Random();

        // 定义一个下标
        int index;
        // 定义X轴变量
        int x = WITDH / 10;
        for (int i = 1; i <= SIZE; i++) {
            // 随机的下标小于words的长度
            index = random.nextInt(WORDS.length());
            // 获取index索引处的字符
            char c = WORDS.charAt(index);
            // 将每个字添加到sb中
            // String code =c+"";
            sb.append(c);
            // 添加旋转角度 随机角度到-10 ---10度
            int angle = random.nextInt(3) - 3;
            // 转成弧度
            double theta = Math.PI * angle / 180;
            // 旋转
            g.rotate(theta, WITDH / 2, HEIGHT / 2);
            // 画上字符
            g.drawString(String.valueOf(c), x, HEIGHT / 2 + 10);
            x += WITDH / 5;
        }
        return sb.toString();
    }


}
