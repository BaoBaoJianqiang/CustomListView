package com.example.jianqiang.testlistview;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<News> generateData() {
        List<News> newsList = new ArrayList<News>();

        newsList.add(genNew1());
        newsList.add(genNew2());
        newsList.add(genNew3());
        newsList.add(genNew4());
        newsList.add(genNew5());
        newsList.add(genNew6());
        newsList.add(genNew7());
        newsList.add(genNew8());
        newsList.add(genNew9());
        newsList.add(genNew10());

        return newsList;
    }

    /**
     * @param content      The text strings that need to be drawn
     * @param contentWidth The width of the text area
     * @param left         The distance between the text area's left side to its parent's left side
     * @param top          The distance between the text area's top side to its parent's top side
     * @return An instance of the StaticLayout class, which created by the input params.
     * The finally generated line count can be fetched by invoking staticLayout.getLineCount();
     * e.g.
     * StaticLayout staticLayout = innerDrawText(canvas, textPaint, news.content, 1000, imgDefault.getWidth() + 10, titleSize + 10);
     * int lineCount = staticLayout.getLineCount();
     */
    public static StaticLayout smartDrawText(@NonNull Canvas canvas, @NonNull TextPaint paint, String content, int contentWidth, float left, float top) {
        StaticLayout staticLayout = new StaticLayout(content, paint, contentWidth, Alignment.ALIGN_NORMAL, 1f, 0f, true);

        canvas.save();

        canvas.translate(left, top);

        staticLayout.draw(canvas);

        canvas.restore();

        return staticLayout;
    }

    static News genNew1() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("希区柯克", "有你真好"));
        commentList1.add(new Comment("小鱼儿 回复 希区柯克", "我是天才"));
        commentList1.add(new Comment("包建强", "咸鱼翻身！"));

        News news1 = new News();
        news1.author = "小鱼儿";
        news1.avator = "https://pic.cnblogs.com/face/313471/20141107134952.png";
        news1.content = "毕业蓝翔技校，技能搬运工！专门搜集生活中的实用小技能、小智慧，为您的生活提供更多帮助与便利。";
        news1.showtime = "1小时前";
        news1.commentList = commentList1;

        return news1;
    }

    static News genNew2() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("阿轲", "背后攻击加倍伤害"));
        commentList1.add(new Comment("孙尚香 回复 阿轲", "来一个肉"));
        commentList1.add(new Comment("太乙真人", "我游走！"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195629789-2082196185.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195629243-1895016019.jpg"));

        News news1 = new News();
        news1.author = "太乙真人";
        news1.avator = "https://pic.cnblogs.com/face/605230/20170228090302.png";
        news1.content = "王者荣耀走起";
        news1.showtime = "2小时前";
        news1.preferList = new String[]{"高渐离", "张飞", "不知火舞"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew3() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("李四", "80分走起"));
        commentList1.add(new Comment("石头回复李四", "避风塘吧"));
        commentList1.add(new Comment("王二麻子", "谁跟我对家？"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195630930-225242378.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195630430-1558004714.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195632493-1258559002.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195632086-2075291200.jpg"));

        News news1 = new News();
        news1.author = "张三";
        news1.avator = "https://pic.cnblogs.com/face/u383503.jpg?id=26222424";
        news1.content = "晚上团建";
        news1.showtime = "3小时前";
        news1.preferList = new String[]{"老爸", "老妈", "三姨"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew4() {
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195634383-1070913148.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195633758-442953618.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195635852-639599381.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195635383-338500418.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195636602-372270222.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195636274-587297471.jpg"));

        News news1 = new News();
        news1.author = "肉胖";
        news1.avator = "https://pic.cnblogs.com/face/433855/20130219085148.png";
        news1.content = "TCP/IP协议栈是一系列网络协议的总和，是构成网络通信的核心骨架";
        news1.showtime = "4小时前";
        news1.preferList = new String[]{"叶曲波", "起为", "郭刚"};
        news1.imageList = imageList;

        return news1;
    }

    static News genNew5() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("巴尔扎克", "写得不错"));
        commentList1.add(new Comment("雨果 回复 巴尔扎克", "送一套人间喜剧"));
        commentList1.add(new Comment("契诃夫", "来俄国，我请客！"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195637618-562701226.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195637164-1130728236.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195638868-1262864250.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195638274-1413996443.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195640930-1569605375.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195640227-1267313938.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195642618-1809860382.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195642149-516368770.jpg"));

        News news1 = new News();
        news1.author = "萨克雷";
        news1.avator = "https://pic.cnblogs.com/face/834468/20151115214952.png";
        news1.content = "名利场终于写完了";
        news1.showtime = "5小时前";
        news1.preferList = new String[]{"高尔斯华绥", "阿婆", "乔叟"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew6() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("小红帽", "蛋糕做得不错"));
        commentList1.add(new Comment("包建强 回复 小红帽", "谢谢夸奖"));
        commentList1.add(new Comment("大灰狼", "提拉米苏有吗？"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195643711-1422992067.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195643368-1447404094.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195644836-632791374.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195644399-1475169374.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195646227-518934377.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195645789-1725998039.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170701769-1284927467.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170657065-625030711.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195642618-1809860382.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170659737-1337992150.jpg"));

        News news1 = new News();
        news1.author = "包建强";
        news1.avator = "https://pic.cnblogs.com/face/1118933/20170616222517.png";
        news1.content = "晒一下我的烘焙蛋糕";
        news1.showtime = "6小时前";
        news1.preferList = new String[]{"蛋糕达人", "白雪公主", "蓝精灵"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }


    static News genNew7() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("杨逍", "忍着"));
        commentList1.add(new Comment("张无忌 回复 杨逍", "。。。"));
        commentList1.add(new Comment("朱元璋", "有痔疮贴肛泰！"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170704847-1486041241.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170703612-1713689374.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170707347-150104447.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170706253-874995511.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170709503-596963397.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170708675-518528607.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170710862-1044663143.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170710144-81620241.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170712456-1770778830.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170711706-2009580983.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170714081-1828307293.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170713206-1922495404.jpg"));

        News news1 = new News();
        news1.author = "张无忌";
        news1.avator = "https://pic.cnblogs.com/face/250417/20160816172634.png";
        news1.content = "有痔疮怎么办？";
        news1.showtime = "7小时前";
        news1.preferList = new String[]{"周芷若", "赵敏", "小昭"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew8() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("李逵", "我想娶老婆！"));
        commentList1.add(new Comment("宋江 回复 李逵", "忍着！"));
        commentList1.add(new Comment("萧让", "我来写！"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170715925-685466831.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170715034-1396079706.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170719378-348356629.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170717550-823287507.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170721112-1623829640.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170720175-655523140.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170724659-2084304094.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170722862-1148269424.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170728472-1325824878.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170726128-678495864.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170730237-1268688520.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170729503-485189251.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170731940-1266814910.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170731144-1213815575.jpg"));

        News news1 = new News();
        news1.author = "宋江";
        news1.avator = "https://pic.cnblogs.com/face/1181291/20170629150910.png";
        news1.content = "105个男人和3个女人的故事";
        news1.showtime = "8小时前";
        news1.preferList = new String[]{"李逵", "卢俊义", "孙二娘"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew9() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("孙猴", "吃俺老孙一棒！"));
        commentList1.add(new Comment("老猪", "高老庄一日游"));
        commentList1.add(new Comment("老沙 回复 孙猴", "大师兄，师父被妖怪抓走了～～"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170734800-2123583979.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170733815-1273124079.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170736722-394182217.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170736144-1715670465.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170738769-577325520.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170737769-954773306.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170741237-121489258.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170740753-1721425970.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170743472-418003073.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170741847-94358040.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170745206-258254917.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170744050-255958834.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170746315-3145675.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170745815-694708215.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170748565-628161064.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170747237-1890139313.jpg"));

        News news1 = new News();
        news1.author = "唐僧";
        news1.avator = "https://pic.cnblogs.com/face/601535/20140621193541.png";
        news1.content = "骑白马的不一定是我～～";
        news1.showtime = "9小时前";
        news1.preferList = new String[]{"杨戬", "太上老君的那头牛", "玉皇大帝"};
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew10() {
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170749815-195041392.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170749065-1714000663.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170751253-1036292416.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170750753-312170637.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170752894-1793121221.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170751800-1544610049.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170754347-1960163847.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170753644-926049392.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170756206-1653337497.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170755206-8482257.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170757675-2002363657.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170756987-1678527272.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170759565-45566636.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170758690-1011087584.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170801097-1556014869.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170800331-273216434.jpg"));
        imageList.add(new ImageEntity(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170804284-1909970115.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170803378-885659992.jpg"));

        News news1 = new News();
        news1.author = "包建强";
        news1.avator = "https://pic.cnblogs.com/face/1118933/20170616222517.png";
        news1.content = "Android培训哪家强？";
        news1.showtime = "10小时前";
        news1.preferList = new String[]{"老邓", "老张", "玉刚"};
        news1.imageList = imageList;

        return news1;
    }}