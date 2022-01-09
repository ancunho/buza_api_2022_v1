package buza.group.api.util;

import org.apache.commons.lang3.time.FastDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");

    public static String md5(String val) throws Exception {
        StringBuffer sb = new StringBuffer();

        MessageDigest mDigest = MessageDigest.getInstance("MD5");
        mDigest.update(val.getBytes());

        byte[] msgStr = mDigest.digest() ;

        for(int i=0; i < msgStr.length; i++){
            sb.append(Integer.toString((msgStr[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String decrypt(String data)
    {
        char[] tmpByte = data.toCharArray();

        for (int i = 0; i < tmpByte.length ; i++)
        {
            tmpByte[i] = (char)(~tmpByte[i]);
        }

        return new String(tmpByte);
    }

    public static boolean isLogin(HttpServletRequest req) {
        HttpSession ses = req.getSession(true);
        boolean bLogin = false;
        if (ses == null || ses.getAttribute("LOGIN_ID") == null || ses.getAttribute("LOGIN_ID").equals("")) {
            bLogin = false;
        } else {
            bLogin = true;
        }
        return bLogin;
    }

    public static String nullnbsp(String strData) {
        String rtnData = null;
        rtnData = strData;
        if (strData == null || strData.equals("")) {
            rtnData = "&nbsp;";
        }
        return rtnData;
    }

    public static String nbspnull(String strData) {
        if ("&nbsp;".equals(strData)) {
            return "";
        }
        return strData;
    }

    public static String nullempty(String strData) {
        String rtnData = null;
        rtnData = strData;
        if (strData == null || strData.equals("")) {
            rtnData = "";
        }
        return rtnData;
    }

    public static String nullempty(Object objData) {
        String rtnData = null;
        if (objData == null) return "";
        rtnData = String.valueOf(objData);
        if ("".equals(String.valueOf(objData))) {
            rtnData = "";
        }
        return rtnData;
    }

    public static String nullreplace(String strData, String strReplace) {
        String rtnData = null;
        rtnData = strData;
        if (strData == null || strData.equals("")) {
            rtnData = strReplace;
        }
        return rtnData;
    }

    public static String nullreplace(Object objData, String strReplace) {
        String rtnData = null;
        rtnData = String.valueOf(objData);
        if (objData == null || objData.equals("")) {
            rtnData = strReplace;
        }
        return rtnData;
    }

    public static String date2split(String strData) {
        String rtnData = null;
        if (strData.length() == 8) {
            rtnData = strData.substring(0, 4) + "-" + strData.substring(4, 6) + "-" + strData.substring(6, 8);
        } else if (strData.length() == 6) {
            rtnData = strData.substring(0, 4) + "-" + strData.substring(4, 6);
        } else {
            rtnData = strData;
        }
        return rtnData;
    }

    public static String date2split2(String strData) {
        String rtnData = null;
        rtnData = strData.replaceAll("-", "");
        if (rtnData.length() == 8) {
            rtnData = rtnData.substring(0, 4) + "年 " + rtnData.substring(4, 6) + "月 " + rtnData.substring(6, 8) + "日";
        }
        return rtnData;
    }

    public static String time2split(String strTime) {
        String rtnTime = null;
        if (strTime.length() == 4) {
            rtnTime = strTime.substring(0, 2) + ":" + strTime.substring(2, 4);
        } else {
            rtnTime = strTime;
        }
        return rtnTime;
    }

    public static String time2split2(String strTime) {
        String rtnTime = null;
        rtnTime = strTime.replaceAll(":", "");
        if (rtnTime.length() == 4) {
            rtnTime = rtnTime.substring(0, 2) + "时 " + rtnTime.substring(2, 4) + "分";
        } else if (rtnTime.length() == 6) {
            rtnTime = rtnTime.substring(0, 2) + "时 " + rtnTime.substring(2, 4) + "分 " + rtnTime.substring(4, 6) + "秒";
        }
        return rtnTime;
    }

    public static String loadingHTML() {
        String rtnHTML = "";
        rtnHTML += "<div id=";
        return rtnHTML;
    }

    public static String getCurrentDate(String strPattern) {
        Date date = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST")).getTime();
        FastDateFormat formatter = FastDateFormat.getInstance(strPattern, Locale.getDefault());
        return formatter.format(date);
    }

    public static String getCurrentAddDate(String strPattern, int addDay) {
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST"));
        cal.add(Calendar.DATE, addDay);
        Date date = cal.getTime();

        FastDateFormat formatter = FastDateFormat.getInstance(strPattern, Locale.getDefault());
        return formatter.format(date);
    }

    public static String getCurrentAddMonth(String strPattern, int addMonth) {
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST"));
        cal.add(Calendar.MONTH, addMonth);
        Date date = cal.getTime();

        FastDateFormat formatter = FastDateFormat.getInstance(strPattern, Locale.getDefault());
        return formatter.format(date);
    }

    public static String removeComma(String str) {
        String oldstr = ",";
        String newstr = "";
        return str.replaceAll(oldstr, newstr);
    }

    public static String setComma(String str) {
        if (str == null || str.equals("")) str = "0";
        String rtnData = "";
        try {
            DecimalFormat decFormat = new DecimalFormat("###,###,###,###");
            rtnData = decFormat.format(Double.parseDouble(str));
        } catch (Exception e) {
            e.printStackTrace();
            rtnData = str;
        }
        return rtnData;
    }

    public static String setComma(String str, String format) {
        if (str == null || str.equals("")) str = "0";
        String rtnData = "";
        try {
            DecimalFormat decFormat = new DecimalFormat(format);
            rtnData = decFormat.format(Double.parseDouble(str));
        } catch (Exception e) {
            e.printStackTrace();
            rtnData = str;
        }
        return rtnData;
    }

    public static String replace(String str, String oldstr, String newstr) {
        StringBuffer buf = new StringBuffer();

        if( str == null )
            return null;

        int savedpos = 0;
        while(true)
        {
            int pos = str.indexOf(oldstr, savedpos);
            if(pos != -1)
            {
                buf.append(str.substring(savedpos, pos));
                buf.append(newstr);

                savedpos = pos + oldstr.length();
                if(savedpos >= str.length())
                    break;
            }
            else
                break;
        }

        buf.append(str.substring(savedpos, str.length()));
        return buf.toString();
    }

    public static String replaceAllErr(String str){
//        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("\"", "&quot;");
        str = str.replaceAll("\'", "&#39;");
        str = str.replaceAll("\r\n", " ").replaceAll("\n", " ");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        return str;
    }

    public static String replaceAllErr2(String str){
        str = str.replaceAll("\\(", "&#40;");
        str = str.replaceAll("\\)", "&#41;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        return str;
    }

    public static String reverseAllErr(String str) {
        if (str == null || "".equals(str)) {
            str = "";
        } else {
            str = str.replaceAll("&amp;", "&");
            str = str.replaceAll("&quot;", "\"");
            str = str.replaceAll("&#34;", "\"");
            str = str.replaceAll("&#39;", "\'");
            str = str.replaceAll("&nbsp;", " ");
            str = str.replaceAll("&lt;", "<");
            str = str.replaceAll("&#40;", "\\(");
            str = str.replaceAll("&#41;", "\\)");
            str = str.replaceAll("&gt;", ">");
            str = str.replaceAll("&#x2F;", "\\/");

            str = str.replaceAll("\r\n", " ").replaceAll("\n", " ");
        }
        return str;
    }

    public static String replaceDirectCharset(String str) {
        if (str == null || "".equals(str)) {
            str = "";
        } else {
            str = str.replaceAll("<%@ page contentType=\"text/html; charset=utf-8\" %>", "");
        }
        return str;
    }

    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null) return true;

        if (o instanceof String) {
            if (((String)o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection)o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            return true;
        } else {
            return false;
        }

        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (checkIP(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (checkIP(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean checkIP(String ip) {
        return isEmpty(ip) || "unknown".equalsIgnoreCase(ip);
    }

    public static String setReformIP(String ip) {   //127.0.0.1 -> 127.000.000.001
        String[] arrIP = Split(ip, ".");
        String rtnIP = "";
        for (int i = 0 ; i < arrIP.length ; i++) {
            rtnIP += ("000" + arrIP[i]).substring(("000" + arrIP[i]).length() - 3) + ".";
        }
        return rtnIP.substring(0, 15);
    }

    public static String setReformReverseIP(String ip) {   //127.000.000.001 -> 127.0.0.1
        String[] arrIP = Split(ip, ".");
        String rtnIP = "";
        for (int i = 0 ; i < arrIP.length ; i++) {
            rtnIP += String.valueOf(Integer.parseInt(arrIP[i])) + ".";
        }
        return rtnIP.substring(0, rtnIP.length() - 1);
    }

    public static String[] Split(String text, String parser) {
        int count = 0, index = 0, i = 0, endIdx = 0;

        if (text == null || "".equals(text)) return null;

        do {
            count++;
            index++;
            index = text.indexOf(parser, index);
        } while (index != -1);

        String[] data = new String[count];

        index = 0;

        while (i < count) {
            endIdx = text.indexOf(parser, index);
            if (endIdx == -1) {
                data[i] = text.substring(index).trim();
            } else {
                data[i] = text.substring(index, endIdx).trim();
            }
            index = endIdx + 1;
            i++;
        }

        return data;
    }

    public static boolean isNumber(String str) {
        boolean check = true;
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isDigit(str.charAt(i))){
                check = false;
                break;
            }
        }
        return check;
    }

    public static boolean isMobile(String phoneNo) {
        Pattern pattern = Pattern.compile("^1[3456789]\\d{9}$");
        Matcher m = pattern.matcher(phoneNo);
        return m.matches();
    }

    public static String setDate(String d) {
        String rtnDate = "";
        if (d.length() != 8) {
            rtnDate = d;
        }
        if (! isNumber(d)) {
            rtnDate = d;
        }
        if ("".equals(rtnDate)) {
            rtnDate = d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);

            try {
                FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd");
                Date dt = df.parse(rtnDate);
            } catch (Exception e) {
                rtnDate = d;
            }
        }
        return rtnDate;
    }

    public static String file2Icon(String fileName) {
        String rtnIconFile = "";

        if (fileName.toLowerCase().endsWith("bmp")) rtnIconFile = "/img/file_icon/bmp.gif";
        else if (fileName.toLowerCase().endsWith("doc")) rtnIconFile = "/img/file_icon/doc.gif";
        else if (fileName.toLowerCase().endsWith("docx")) rtnIconFile = "/img/file_icon/doc.gif";
        else if (fileName.toLowerCase().endsWith("gif")) rtnIconFile = "/img/file_icon/gif.gif";
        else if (fileName.toLowerCase().endsWith("gul")) rtnIconFile = "/img/file_icon/gul.gif";
        else if (fileName.toLowerCase().endsWith("htm")) rtnIconFile = "/img/file_icon/html.gif";
        else if (fileName.toLowerCase().endsWith("html")) rtnIconFile = "/img/file_icon/html.gif";
        else if (fileName.toLowerCase().endsWith("hwp")) rtnIconFile = "/img/file_icon/hwp.gif";
        else if (fileName.toLowerCase().endsWith("jpg")) rtnIconFile = "/img/file_icon/jpg.gif";
        else if (fileName.toLowerCase().endsWith("jpeg")) rtnIconFile = "/img/file_icon/jpg.gif";
        else if (fileName.toLowerCase().endsWith("ppt")) rtnIconFile = "/img/file_icon/ppt.gif";
        else if (fileName.toLowerCase().endsWith("pptx")) rtnIconFile = "/img/file_icon/ppt.gif";
        else if (fileName.toLowerCase().endsWith("txt")) rtnIconFile = "/img/file_icon/txt.gif";
        else if (fileName.toLowerCase().endsWith("log")) rtnIconFile = "/img/file_icon/txt.gif";
        else if (fileName.toLowerCase().endsWith("xls")) rtnIconFile = "/img/file_icon/xls.gif";
        else if (fileName.toLowerCase().endsWith("xlsx")) rtnIconFile = "/img/file_icon/xls.gif";
        else if (fileName.toLowerCase().endsWith("zip")) rtnIconFile = "/img/file_icon/zip.gif";
        else if (fileName.toLowerCase().endsWith("rar")) rtnIconFile = "/img/file_icon/zip.gif";
        else if (fileName.toLowerCase().endsWith("wav")) rtnIconFile = "/img/file_icon/wav.gif";
        else if (fileName.toLowerCase().endsWith("mp3")) rtnIconFile = "/img/file_icon/wav.gif";
        else if (fileName.toLowerCase().endsWith("pdf")) rtnIconFile = "/img/file_icon/pdf.gif";
        else rtnIconFile = "/img/file_icon/any.gif";

        return rtnIconFile;
    }

    public static String getExtention(String fileName) {
        String ext;
        int dot = fileName.lastIndexOf(".");
        if (dot != -1) {
            ext = fileName.substring(dot + 1);
        } else {
            ext = "";
        }
        return ext;
    }

    public static String numberNegative(String val) {
        val = nullempty(val);
        try {
            if (new BigDecimal(val).compareTo(new BigDecimal("0")) < 0) {
                val = "<span style='color: #f00'>" + val + "</span>";
            }
        } catch (Exception e) {}
        return val;
    }

    public static String getErrMsg(Exception e) {
        StringBuffer sbErrMsg = new StringBuffer();
        try {
            StackTraceElement element[] = e.getStackTrace();
            for (StackTraceElement anElement : element) {
                sbErrMsg.append(anElement.toString());
                sbErrMsg.append("<br />");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbErrMsg.toString();
    }

    public static StringBuilder mapToXML(Map data , StringBuilder buffer , int inCnt) {

        Set keys = data.keySet();

        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            Object key = (Object) iterator.next();
            Object value = data.get(key);

            if(value instanceof Map) {

                fillTabKey(buffer , inCnt);
                buffer.append("<").append(key).append(">");
                mapToXML((Map)value , buffer , inCnt+1);
                fillTabKey(buffer , inCnt);
                buffer.append("</").append(key).append(">");
            }
            else {
                fillTabKey(buffer , inCnt);
                buffer.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
            }

        }

        return buffer;
    }

    public static String maskString(String strData, int iExcept, String maskChar) {
        String rtnData = null;
        rtnData = strData;
        if (strData == null || strData.equals("")) {
            rtnData = "";
        } else {
            if (rtnData.length() > iExcept) {
                rtnData = rtnData.substring(0, iExcept) + "****";
            }
        }
        return rtnData;
    }

    private static StringBuilder fillTabKey(StringBuilder buffer, int inCnt) {
        for(int ct = 1 ; ct <= inCnt ; ct++) {
//            buffer.append("\t");
            buffer.append("");
        }
        return buffer;
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差年数
     */
    public static int diffYear(Date one, Date two) {

        int diffMonth = diffMonth(one, two);
        return (int) Math.floor(diffMonth / 12);
    }

    public static int diffMonth(Date one, Date two) {
        Calendar calendar = Calendar.getInstance();

        //得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONTH);

        //得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONTH);

        return ((yearTwo - yearOne) * 12) + (monthTwo - monthOne);
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        return (long) Math.ceil(Double.parseDouble(Long.toString(one.getTime() - two.getTime())) / (24 * 60 * 60 * 1000));
    }

}
