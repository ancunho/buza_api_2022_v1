package buza.group.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hello {

    public static void main(String[] args) throws Exception {
        String format;
        int begin = 202201;
        System.out.println(">>>>begin/100::" + begin % 100);
        int cursor = -12;
//        if( begin < 100000 ){//用于全年表
//            if( cursor == -12 ){ // 12 相当于取去年
//                System.out.println(">>>>>111");
//                System.out.println(begin -1);
//            }
//            begin = begin * 100 +12 ;
//        }else{
//            if (begin % 100 == 12 && cursor == -12 ) {
//                System.out.println(">>>>>2222");
//                System.out.println(begin / 100 -1);
//            }
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = sdf.parse(begin + "");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, cursor);
        format = format(c.getTime(), "yyyyMM");
        System.out.println(format);
    }

    public static String format(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


}
