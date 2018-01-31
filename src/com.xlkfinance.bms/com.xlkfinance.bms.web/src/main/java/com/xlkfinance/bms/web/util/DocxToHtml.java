package com.xlkfinance.bms.web.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.Constants;

/**
 * 
 * @ClassName: DocxToHtml
 * @Description: TODO
 * @author: Chong.Zeng
 * @date: 2015年1月21日 上午9:42:06
 */
public class DocxToHtml {
	/**
	 * 回车符ASCII码
	 */
	private static final short ENTER_ASCII = 13;

	/**
	 * 空格符ASCII码
	 */
	private static final short SPACE_ASCII = 32;

	/**
	 * 水平制表符ASCII码
	 */
	private static final short TABULATION_ASCII = 9;

	public static String htmlText = "";
	public static String htmlTextTbl = "";
	public static int counter = 0;
	public static int beginPosi = 0;
	public static int endPosi = 0;
	public static int beginArray[];
	public static int endArray[];
	public static String htmlTextArray[];
	public static boolean tblExist = false;

	/**
	 * 
	 * @Description: 解析word2003转换成html
	 * @param srcFile
	 *            需要解析的word文档
	 * @param type
	 *            模块参数
	 * @return 转换后的Html名字
	 * @throws Exception
	 * @author: Chong.Zeng
	 * @date: 2015年1月21日 上午9:07:30
	 */
	public static String reviewWord(String reviewRoot, String srcFile) throws Exception {
		try {
			String[] p = srcFile.split("\\.");
			String realPath = new StringBuffer().append("/nfs/review/").append(DateUtil.format(new Date(), "yyyyMMdd")).append(Constants.SEPARATOR).toString();
			String htmlPath = reviewRoot + Constants.SEPARATOR + realPath;
			File f = new File(htmlPath);
			if (!f.exists()) {
				f.mkdirs();
			}

			// 获取前一天的日期文件
			String prevDay = new StringBuffer().append(reviewRoot).append("/nfs/review/").append(getNextDay(new Date())).append(Constants.SEPARATOR).toString();
			File prevFile = new File(prevDay);
			if (prevFile.exists()) {
				prevFile.delete();
			}
			// 转换的后Html地址
			String htmlName = UUID.randomUUID().toString() + ".html";
			String fileName = htmlPath + Constants.SEPARATOR + htmlName;
			File tmpFile = new File(fileName);
			if (!tmpFile.exists()) {
				tmpFile.createNewFile();
			}

			// 比较文件扩展名
			if (p[p.length - 1].equalsIgnoreCase("doc")) {
				getWordAndStyle(srcFile, fileName);
			} else if (p[p.length - 1].equalsIgnoreCase("docx")) {
				canExtractImage(srcFile, fileName);
			}
			return realPath + htmlName;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 读取每个文字样式
	 * 
	 * @param fileName
	 * @throws Exception
	 */

	public static void getWordAndStyle(String srcFile, String fileName) throws Exception {
		FileInputStream fis = null;
		try {
			File file = new File(srcFile);
			fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument doc = new HWPFDocument(fis);

			Range rangetbl = doc.getRange();// 得到文档的读取范围
			TableIterator it = new TableIterator(rangetbl);
			int num = 100;
			int fos = fileName.lastIndexOf("/");
			String title = fileName.substring(fos + 1);
			title = title.substring(0, title.lastIndexOf("."));

			beginArray = new int[num];
			endArray = new int[num];
			htmlTextArray = new String[num];
			// 取得文档中字符的总数
			int length = doc.characterLength();
			// 创建图片容器
			htmlText = "<html><head><title>" + title + "预览</title></head><body>";
			// 创建临时字符串,好加以判断一串字符是否存在相同格式
			if (it.hasNext()) {
				readTable(it, rangetbl);
			}

			int cur = 0;

			String tempString = "";
			for (int i = 0; i < length - 1; i++) {
				// 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
				Range range = new Range(i, i + 1, doc);

				CharacterRun cr = range.getCharacterRun(0);
				if (tblExist) {
					if (i == beginArray[cur]) {
						htmlText += tempString + htmlTextArray[cur];
						tempString = "";
						i = endArray[cur] - 1;
						cur++;
						continue;
					}
				} else {
					Range range2 = new Range(i + 1, i + 2, doc);
					// 第二个字符
					CharacterRun cr2 = range2.getCharacterRun(0);
					char c = cr.text().charAt(0);

					// 判断是否为回车符
					if (c == ENTER_ASCII) {
						tempString += "<br/>";

					}
					// 判断是否为空格符
					else if (c == SPACE_ASCII) {
						tempString += " ";
					}
					// 判断是否为水平制表符
					else if (c == TABULATION_ASCII) {
						tempString += "    ";
					}
					// 比较前后2个字符是否具有相同的格式
					boolean flag = compareCharStyle(cr, cr2);
					if (flag) {
						tempString += cr.text();
					} else {
						String fontStyle = "<span style=\"font-family:" + cr.getFontName() + ";font-size:" + cr.getFontSize() / 2 + "pt;";
						if (cr.isBold()) {
							fontStyle += "font-weight:bold;";
						}
						if (cr.isItalic()) {
							fontStyle += "font-style:italic;";
						}

						htmlText += fontStyle + "\" mce_style=\"font-family:" + cr.getFontName() + ";font-size:" + cr.getFontSize() / 2 + "pt;";

						if (cr.isBold()) {
							fontStyle += "font-weight:bold;";
						}
						if (cr.isItalic()) {
							fontStyle += "font-style:italic;";
						}
						htmlText += fontStyle + "\">" + tempString + cr.text() + "</span>";
						tempString = "";
					}
				}
			}

			htmlText += tempString + "</body></html>";
			writeFile(htmlText, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * 读写文档中的表格
	 * 
	 */
	public static void readTable(TableIterator it, Range rangetbl) throws Exception {

		htmlTextTbl = "";
		// 迭代文档中的表格

		counter = -1;
		while (it.hasNext()) {
			tblExist = true;
			htmlTextTbl = "";
			Table tb = it.next();
			beginPosi = tb.getStartOffset();
			endPosi = tb.getEndOffset();

			counter = counter + 1;
			// 迭代行，默认从0开始
			beginArray[counter] = beginPosi;
			endArray[counter] = endPosi;

			htmlTextTbl += "<table border>";
			for (int i = 0; i < tb.numRows(); i++) {
				TableRow tr = tb.getRow(i);

				htmlTextTbl += "<tr>";
				// 迭代列，默认从0开始
				for (int j = 0; j < tr.numCells(); j++) {
					TableCell td = tr.getCell(j);// 取得单元格
					int cellWidth = td.getWidth();

					// 取得单元格的内容
					for (int k = 0; k < td.numParagraphs(); k++) {
						Paragraph para = td.getParagraph(k);
						String s = para.text().toString().trim();
						if (s == "") {
							s = " ";
						}
						htmlTextTbl += "<td width=" + cellWidth + ">" + s + "</td>";
					} // end for
				} // end for
			} // end for
			htmlTextTbl += "</table>";
			htmlTextArray[counter] = htmlTextTbl;

		} // end while
	}

	public static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) {
		boolean flag = false;
		if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic() && cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 写文件
	 * 
	 * @param s
	 */
	public static void writeFile(String docContent, String fileName) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(fileName);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "gb2312"));
			bw.write(docContent);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

	/**
	 * 
	 * @Description: 解析word2007转换成html
	 * @param srcFile
	 * @param fileName
	 * @throws IOException
	 * @author: Chong.Zeng
	 * @date: 2015年1月21日 上午9:06:15
	 */
	public static void canExtractImage(String srcFile, String fileName) {
		File f = new File(srcFile);
		XWPFDocument document = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			if (f.exists()) {
				if (f.getName().toUpperCase().endsWith(".DOCX")) {

					in = new FileInputStream(f);
					document = new XWPFDocument(in);

					File imageFolderFile = new File("");
					XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
					options.setExtractor(new FileImageExtractor(imageFolderFile));
					out = new FileOutputStream(new File(fileName));
					XHTMLConverter.getInstance().convert(document, out, options);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XWPFConverterException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 获取当前日期前一天
	 * @param date
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月28日 上午9:46:35
	 */
	public static String getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		return sf.format(date).toString();
	}

}
