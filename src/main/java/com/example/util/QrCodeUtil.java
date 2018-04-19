package com.example.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtil {
	/**
	 * 画制定logo和制定描述的二维码
	 * 
	 * @author songyz
	 *
	 */
	private static final int QRCOLOR = 0xFF000000; // 默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色
	
	private static final int WIDTH = 200; // 二维码宽
	private static final int HEIGHT = 200; // 二维码高
	
	// 用于设置QR二维码参数
	private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
	    private static final long serialVersionUID = 1L;
	    {
	        put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
	    put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
	        put(EncodeHintType.MARGIN, 0);
	    }
	};
	// 生成不带logo的二维码图片，存放到本地，locatio位存放地址
	public static void encode(String location, String qrUrl) {
		drawLogoQRCode(null, location, qrUrl, null);
	}
	
	// 生成带logo的，存放到本地
	public static void encode(File logoFile, String location, String qrUrl) {
		drawLogoQRCode(logoFile, location, qrUrl, null);
	}
	
	// 生成不带logo的，存放进流
	public static void encode(String qrUrl, HttpServletResponse response) {
		drawLogoQRCode(null, null, qrUrl, response);
	}
	
	// 生成带logo的，存放进流
	public static void encode(File logoFile, String qrUrl, HttpServletResponse response) {
		drawLogoQRCode(logoFile, null, qrUrl, response);
	}
	
	// 生成带logo的二维码图片
	private static void drawLogoQRCode(File logoFile, String location, String qrUrl, HttpServletResponse response) {
	    try {
	        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	        // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
	        BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
	        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
		    // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
		    for (int x = 0; x < WIDTH; x++) {
		        for (int y = 0; y < HEIGHT; y++) {
		            image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
		        }
		    }
	
		    int width = image.getWidth();
		    int height = image.getHeight();
		    if (Objects.nonNull(logoFile) && logoFile.exists()) {
		        // 构建绘图对象
		        Graphics2D g = image.createGraphics();
		        // 读取Logo图片
		        BufferedImage logo = ImageIO.read(logoFile);
		        // 开始绘制logo图片
		        g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
		        g.dispose();
		        logo.flush();
		    }
		    image.flush();
		    if(StringUtils.isNotEmpty(location)) {
			    ImageIO.write(image, "png", new File(location));
		    	
		    } else if(response != null) {
	            ImageIO.write(image, "png", response.getOutputStream());
		    }  
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws WriterException {
	    String local = "D://logo3.png";
	    String url = "https://www.baidu.com/";
	    encode(local, url);
	}
}
