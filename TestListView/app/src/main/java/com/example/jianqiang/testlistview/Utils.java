package com.example.jianqiang.testlistview;

import com.example.jianqiang.testlistview.entity.ImageListImpl;

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


        return newsList;
    }

    static News genNew1() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("希区柯克", "有你真好"));
        commentList1.add(new Comment("小鱼儿 回复 希区柯克", "我是天才"));
        commentList1.add(new Comment("包建强", "咸鱼翻身！"));
//        List<ImageList> imageList = new ArrayList<>();
//        imageList.add(new ImageListImpl("http://img4.imgtn.bdimg.com/it/u=988504629,3426016391&fm=26&gp=0.jpg"));
//        imageList.add(new ImageListImpl("http://img0.imgtn.bdimg.com/it/u=4048252325,2901172895&fm=26&gp=0.jpg"));
        News news1 = new News();
        news1.author = "小鱼儿";
        news1.avator = "https://pic.cnblogs.com/face/313471/20141107134952.png";
        news1.content = "毕业蓝翔技校，技能搬运工！专门搜集生活中的实用小技能、小智慧，为您的生活提供更多帮助与便利。";
        news1.showtime = "1小时前";
        news1.commentList = commentList1;
//        news1.imageList = imageList;

        return news1;
    }

    static News genNew2() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("阿轲", "背后攻击加倍伤害"));
        commentList1.add(new Comment("孙尚香 回复 阿轲", "来一个肉"));
        commentList1.add(new Comment("太乙真人", "我游走！"));
        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232804284-650168411.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170755206-8482257.jpg"));
//        imageList.add(new ImageEntity(
//                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png",
//                "http://orbm62bsw.bkt.clouddn.com/iv_size_150.png"));
//
//        Article article = new Article("写给Android App开发人员看的Android底层知识（6）",
//                "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png",
//                "https://mp.weixin.qq.com/s?srcid=05288cTv2IBYRVUxz1j6EhnG&scene=23&mid=2247483762&sn=58816eaeb1ecb8e42ef7cb35b52ab03a&idx=1&__biz=MzI2NzM1Njk4MQ%3D%3D&chksm=ea815f28ddf6d63e0eff29dd5c185552d69bfe9e3008e68115b89c4a403f20fdce0a67c22e9f&mpshare=1&key=7dad7409be596df695e7c725d2e2166b4eb608c034b768212dce373c28bb0060ddea1bd2f94f081df45b3f09f8ada015a50590e645a119c4f05b76cd86b989d00560d723b308db869325e7e08e048b4d&ascene=0&uin=MTIxMjgxNjU0MQ%3D%3D&devicetype=iMac+MacBookAir5%2C2+OSX+OSX+10.11.6+build(15G31)&version=12020510&nettype=WIFI&fontScale=100&pass_ticket=AHHXeHr0CIiOW2vWsgK6YJMUNx4YMbnnZmtgEKgjwhd6wkko7fPA5gloJZMtoT8l#rd&appinstall=0");


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
        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232807659-1613196240.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170803378-885659992.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232808737-526194272.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170805206-407657334.jpg"));
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("李四", "80分走起"));
        commentList1.add(new Comment("石头回复李四", "避风塘吧"));
        commentList1.add(new Comment("王二麻子", "谁跟我对家？"));

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
        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232805315-1694449167.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170756987-1678527272.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232806003-1403354337.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170758690-1011087584.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232806878-567816518.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170800331-273216434.jpg"));


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

        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232809472-1436915874.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170657065-625030711.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232810190-1071664920.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170659737-1337992150.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232811097-666636887.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170703612-1713689374.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232813409-411073305.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170710144-81620241.jpg"));

        Article article = new Article("写给Android App开发人员看的Android底层知识（6）",
                "http://orbm62bsw.bkt.clouddn.com/iv_size_60.png",
                "https://mp.weixin.qq.com/s?srcid=05288cTv2IBYRVUxz1j6EhnG&scene=23&mid=2247483762&sn=58816eaeb1ecb8e42ef7cb35b52ab03a&idx=1&__biz=MzI2NzM1Njk4MQ%3D%3D&chksm=ea815f28ddf6d63e0eff29dd5c185552d69bfe9e3008e68115b89c4a403f20fdce0a67c22e9f&mpshare=1&key=7dad7409be596df695e7c725d2e2166b4eb608c034b768212dce373c28bb0060ddea1bd2f94f081df45b3f09f8ada015a50590e645a119c4f05b76cd86b989d00560d723b308db869325e7e08e048b4d&ascene=0&uin=MTIxMjgxNjU0MQ%3D%3D&devicetype=iMac+MacBookAir5%2C2+OSX+OSX+10.11.6+build(15G31)&version=12020510&nettype=WIFI&fontScale=100&pass_ticket=AHHXeHr0CIiOW2vWsgK6YJMUNx4YMbnnZmtgEKgjwhd6wkko7fPA5gloJZMtoT8l#rd&appinstall=0");

        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("巴尔扎克", "写得不错"));
        commentList1.add(new Comment("雨果 回复 巴尔扎克", "送一套人间喜剧"));
        commentList1.add(new Comment("契诃夫", "来俄国，我请客！"));

        News news1 = new News();
        news1.author = "萨克雷";
        news1.avator = "https://pic.cnblogs.com/face/834468/20151115214952.png";
        news1.content = "名利场终于写完了";
        news1.showtime = "5小时前";
        news1.preferList = new String[]{"高尔斯华绥", "阿婆", "乔叟"};
        news1.commentList = commentList1;
        news1.article = article;
        news1.imageList = imageList;

        return news1;
    }

    static News genNew6() {

        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232811815-408456443.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170706253-874995511.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232812675-1529086713.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170708675-518528607.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232814112-1202599613.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170711706-2009580983.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232814878-849343918.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195629243-1895016019.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232815690-652544177.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195630430-1558004714.jpg"));

        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("小红帽", "蛋糕做得不错"));
        commentList1.add(new Comment("包建强 回复 小红帽", "谢谢夸奖"));
        commentList1.add(new Comment("大灰狼", "提拉米苏有吗？"));

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
        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232816456-260574807.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195632086-2075291200.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232817112-324732726.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195633758-442953618.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232818440-650324261.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195635383-338500418.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232819128-903937804.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195636274-587297471.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232819862-770734536.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195637164-1130728236.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232820690-933806903.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195638274-1413996443.jpg"));

        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("杨逍", "忍着"));
        commentList1.add(new Comment("张无忌 回复 杨逍", "。。。"));
        commentList1.add(new Comment("朱元璋", "有痔疮贴肛泰！"));

        News news1 = new News();
        news1.author = "张无忌";
        news1.avator = "https://pic.cnblogs.com/face/250417/20160816172634.png";
        news1.content = "有痔疮怎么办？";
        news1.showtime = "7小时前";
        news1.preferList = new String[]{"周芷若", "赵敏", "小昭"};
        news1.commentList = commentList1;
        news1.imageList =imageList;

        return news1;
    }

    static News genNew8() {
        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("李逵", "我想娶老婆！"));
        commentList1.add(new Comment("宋江 回复 李逵", "忍着！"));
        commentList1.add(new Comment("萧让", "我来写！"));

        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232821722-194979215.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195640227-1267313938.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232822581-343289627.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195642149-516368770.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232823456-323836689.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195643368-1447404094.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232824206-778364773.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195644399-1475169374.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232825237-110554084.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170702195645789-1725998039.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232826050-109533249.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170713206-1922495404.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232826722-558691486.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170715034-1396079706.jpg"));

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
        List<ImageList> imageList = new ArrayList<>();
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232834909-206530286.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170737769-954773306.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232835675-1911287744.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170740753-1721425970.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232836597-236471481.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170741847-94358040.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232837378-2017547884.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170744050-255958834.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232838019-1551578929.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170745815-694708215.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232838769-1567307723.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170747237-1890139313.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232839597-1469292784.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170749065-1714000663.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232840706-198155695.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170750753-312170637.jpg"));
        imageList.add(new ImageListImpl(
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170706232841753-2063022404.jpg",
                "http://images2015.cnblogs.com/blog/13430/201707/13430-20170704170751800-1544610049.jpg"));

        List<Comment> commentList1 = new ArrayList<Comment>();
        commentList1.add(new Comment("孙猴", "吃俺老孙一棒！"));
        commentList1.add(new Comment("老猪", "高老庄一日游"));
        commentList1.add(new Comment("老沙 回复", "大师兄，师父被妖怪抓走了～～"));

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
}