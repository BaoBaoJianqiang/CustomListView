package com.example.jianqiang.testlistview;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.jianqiang.testlistview.helpers.ZanContentHelper;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<News> generateData() {
        List<News> newsList = new ArrayList<News>();

        newsList.add(genNew1(0));
        newsList.add(genNew2(0));
        newsList.add(genNew3(0));
        newsList.add(genNew4(0));
        newsList.add(genNew5(0));
        newsList.add(genNew6(0));
        newsList.add(genNew7(0));
        newsList.add(genNew8(0));
        newsList.add(genNew9(0));

        return newsList;
    }


    public static List<News> generateLoadData(int hours) {
        List<News> newsList = new ArrayList<News>();

        newsList.add(genNew1(hours));
        newsList.add(genNew2(hours));
        newsList.add(genNew3(hours));
        newsList.add(genNew4(hours));
        newsList.add(genNew5(hours));
        newsList.add(genNew6(hours));
        newsList.add(genNew7(hours));
        newsList.add(genNew8(hours));
        newsList.add(genNew9(hours));

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

    static News genNew1(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("希区柯克", "有你真好"));
        commentList1.add(new Comment("小鱼儿 回复 希区柯克", "我是天才"));
        commentList1.add(new Comment("包建强", "咸鱼翻身！"));
        hours += 1;
        News news1 = new News();
        news1.author = "小鱼儿";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "毕业蓝翔技校，技能搬运工！专门搜集生活中的实用小技能、小智慧，为您的生活提供更多帮助与便利。";
        news1.showtime = hours + "小时前";
        news1.commentList = commentList1;

        return news1;
    }

    static News genNew2(int hours) {
        List<Comment> commentList1 = new ArrayList<>();
        commentList1.add(new Comment("阿轲", "背后攻击加倍伤害"));
        commentList1.add(new Comment("孙尚香 回复 阿轲", "来一个肉"));
        commentList1.add(new Comment("太乙真人", "我游走！"));
        hours += 2;
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));

        Article article = new Article("写给Android App开发人员看的Android底层知识（6）",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png",
                "https://mp.weixin.qq.com/s?srcid=05288cTv2IBYRVUxz1j6EhnG&scene=23&mid=2247483762&sn=58816eaeb1ecb8e42ef7cb35b52ab03a&idx=1&__biz=MzI2NzM1Njk4MQ%3D%3D&chksm=ea815f28ddf6d63e0eff29dd5c185552d69bfe9e3008e68115b89c4a403f20fdce0a67c22e9f&mpshare=1&key=7dad7409be596df695e7c725d2e2166b4eb608c034b768212dce373c28bb0060ddea1bd2f94f081df45b3f09f8ada015a50590e645a119c4f05b76cd86b989d00560d723b308db869325e7e08e048b4d&ascene=0&uin=MTIxMjgxNjU0MQ%3D%3D&devicetype=iMac+MacBookAir5%2C2+OSX+OSX+10.11.6+build(15G31)&version=12020510&nettype=WIFI&fontScale=100&pass_ticket=AHHXeHr0CIiOW2vWsgK6YJMUNx4YMbnnZmtgEKgjwhd6wkko7fPA5gloJZMtoT8l#rd&appinstall=0");

        News news1 = new News();
        news1.author = "太乙真人";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "王者荣耀走起";
        news1.showtime = "2小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"高渐离", "张飞", "不知火舞", "有些人就是喜欢把ID起得很长很长你说讨厌不讨厌"});
        news1.commentList = commentList1;
        news1.imageList = imageList;
        news1.article = article;

        return news1;
    }

    static News genNew3(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("李四", "80分走起"));
        commentList1.add(new Comment("石头回复李四", "避风塘吧"));
        commentList1.add(new Comment("王二麻子", "谁跟我对家？"));
        hours += 3;
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));

        News news1 = new News();
        news1.author = "张三";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "晚上团建";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"老爸", "老妈", "三姨"});
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew4(int hours) {
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        hours += 4;
        News news1 = new News();
        news1.author = "肉胖";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "TCP/IP协议栈是一系列网络协议的总和，是构成网络通信的核心骨架";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"叶曲波", "起为", "郭刚"});
        news1.imageList = imageList;

        return news1;
    }

    static News genNew5(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("巴尔扎克", "写得不错"));
        commentList1.add(new Comment("雨果 回复 巴尔扎克", "送一套人间喜剧"));
        commentList1.add(new Comment("契诃夫", "来俄国，我请客！"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        hours += 5;
        News news1 = new News();
        news1.author = "萨克雷";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "名利场终于写完了";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"高尔斯华绥", "阿婆", "乔叟"});
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew6(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("小红帽", "蛋糕做得不错"));
        commentList1.add(new Comment("包建强 回复 小红帽", "谢谢夸奖"));
        commentList1.add(new Comment("大灰狼", "提拉米苏有吗？"));
        hours += 6;
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        News news1 = new News();
        news1.author = "包建强";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "晒一下我的烘焙蛋糕";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"蛋糕达人", "白雪公主", "蓝精灵"});
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }


    static News genNew7(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("杨逍", "忍着"));
        commentList1.add(new Comment("张无忌 回复 杨逍", "。。。"));
        commentList1.add(new Comment("朱元璋", "有痔疮贴肛泰！"));
        hours += 7;
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));

        News news1 = new News();
        news1.author = "张无忌";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "有痔疮怎么办？";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"周芷若", "赵敏", "小昭"});
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew8(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("李逵", "我想娶老婆！"));
        commentList1.add(new Comment("宋江 回复 李逵", "忍着！"));
        commentList1.add(new Comment("萧让", "我来写！"));
        hours += 8;
        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));


        News news1 = new News();
        news1.author = "宋江";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "105个男人和3个女人的故事";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"李逵", "卢俊义", "孙二娘"});
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew9(int hours) {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("孙猴", "吃俺老孙一棒！"));
        commentList1.add(new Comment("老猪", "高老庄一日游"));
        commentList1.add(new Comment("老沙 回复 孙猴", "大师兄，师父被妖怪抓走了～～"));

        List<ImageEntity> imageList = new ArrayList<>();
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
        imageList.add(new ImageEntity(
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));

        hours += 9;
        News news1 = new News();
        news1.author = "唐僧";
        news1.avator = "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png";
        news1.content = "骑白马的不一定是我～～";
        news1.showtime = hours + "小时前";
        news1.preferList = ZanContentHelper.initPreferListFromArray(new String[]{"杨戬", "太上老君的那头牛", "玉皇大帝"});
        news1.commentList = commentList1;
        news1.imageList = imageList;

        return news1;
    }

}