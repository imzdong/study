package org.imzdong.tool.pdf;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.imzdong.tool.util.Itext7HtmlPdfUtil;
import org.imzdong.tool.util.TemplateUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TemplateTest {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    private String template = "article.ftlh";
    private String rootPath = "D:\\work\\pdf\\itext7\\";
    private String htmlPath = rootPath + "20210305_01.html";
    private String destPdf = rootPath + "20210305_01.pdf";
    private Configuration init;

    @Before
    public void before(){
        init = TemplateUtil.init("src/test/resources/template");
    }

    @Test
    public void runAll(){
        article2Html();
        try {
            html2Pdf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void article2Html() {
        /* Create a data-model */
        Map root = new HashMap();
        String html = "{\"error\":[],\"extra\":[],\"data\":{\"video_id\":\"\",\"sku\":\"100006601\",\"video_cover\":\"\",\"author_name\":\"李运华\",\"text_read_version\":3,\"audio_size\":4480941,\"article_cover\":\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/1a\\/32\\/1a1ef458cc81d86a5a233d3e379cfa32.jpg\",\"subtitles\":[],\"product_type\":\"c1\",\"audio_url\":\"https:\\/\\/res001.geekbang.org\\/media\\/audio\\/0d\\/dc\\/0de96702b98219cd28de5ae2096a5bdc\\/ld\\/ld.m3u8\",\"chapter_id\":\"360\",\"column_had_sub\":true,\"audio_dubber\":\"黄洲君\",\"is_finished\":true,\"share\":{\"content\":\"前阿里P9技术专家的实战架构心法\",\"title\":\"李运华 · 从0开始学架构\",\"poster\":\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/ce\\/db\\/ceb37edc8efefc8b2cde54f6592340db.jpg\",\"cover\":\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/a9\\/ed\\/a96b163e2d9bf19cdfc0f5d0d69e13ed.jpg\"},\"like\":{\"had_done\":false,\"count\":54},\"had_liked\":false,\"product_id\":100006601,\"audio_time\":\"00:09:19\",\"video_height\":0,\"rate_percent\":100,\"article_content\":\"<p>对于技术人员来说，“架构”是一个再常见不过的词了。我们会对新员工培训整个系统的架构，参加架构设计评审，学习业界开源系统（例如，MySQL、Hadoop）的架构，研究大公司的架构实现（例如，微信架构、淘宝架构）……虽然“架构”这个词常见，但如果深究一下<span class=\\\"orange\\\">“架构”到底指什么<\\/span>，大部分人也许并不一定能够准确地回答。例如：<\\/p>\\n<ul>\\n<li>\\n<p>架构和框架是什么关系？有什么区别？<\\/p>\\n<\\/li>\\n<li>\\n<p>Linux有架构，MySQL有架构，JVM也有架构，使用Java开发、MySQL存储、跑在Linux上的业务系统也有架构，应该关注哪个架构呢？<\\/p>\\n<\\/li>\\n<li>\\n<p>微信有架构，微信的登录系统也有架构，微信的支付系统也有架构，当我们谈微信架构时，到底是在谈什么架构？<\\/p>\\n<\\/li>\\n<\\/ul>\\n<p>要想准确地回答这几个问题，关键在于梳理几个有关系而又相似的概念，包括：系统与子系统、模块与组件、框架与架构。<\\/p>\\n<!-- [[[read_end]]] -->\\n<h2>系统与子系统<\\/h2>\\n<p>我们先来看维基百科定义的“系统”。<\\/p>\\n<blockquote>\\n<p>系统泛指由一群有关联的个体组成，根据某种规则运作，能完成个别元件不能单独完成的工作的群体。它的意思是“总体”“整体”或“联盟”。<\\/p>\\n<\\/blockquote>\\n<p>我来提炼一下里面的关键内容：<\\/p>\\n<ol>\\n<li>\\n<p><strong>关联<\\/strong>：系统是由一群有关联的个体组成的，没有关联的个体堆在一起不能成为一个系统。例如，把一个发动机和一台PC放在一起不能称之为一个系统，把发动机、底盘、轮胎、车架组合起来才能成为一台汽车。<\\/p>\\n<\\/li>\\n<li>\\n<p><strong>规则<\\/strong>：系统内的个体需要按照指定的规则运作，而不是单个个体各自为政。规则规定了系统内个体分工和协作的方式。例如，汽车发动机负责产生动力，然后通过变速器和传动轴，将动力输出到车轮上，从而驱动汽车前进。<\\/p>\\n<\\/li>\\n<li>\\n<p><strong>能力<\\/strong>：系统能力与个体能力有本质的差别，系统能力不是个体能力之和，而是产生了新的能力。例如，汽车能够载重前进，而发动机、变速器、传动轴、车轮本身都不具备这样的能力。<\\/p>\\n<\\/li>\\n<\\/ol>\\n<p>我们再来看子系统的定义。<\\/p>\\n<blockquote>\\n<p>子系统也是由一群有关联的个体所组成的系统，多半会是更大系统中的一部分。<\\/p>\\n<\\/blockquote>\\n<p>其实子系统的定义和系统定义是一样的，只是观察的角度有差异，一个系统可能是另外一个更大系统的子系统。<\\/p>\\n<p>按照这个定义，系统和子系统比较容易理解。我们以微信为例来做一个分析。<\\/p>\\n<ol>\\n<li>\\n<p>微信本身是一个系统，包含聊天、登录、支付、朋友圈等子系统。<\\/p>\\n<\\/li>\\n<li>\\n<p>朋友圈这个系统又包括动态、评论、点赞等子系统。<\\/p>\\n<\\/li>\\n<li>\\n<p>评论这个系统可能又包括防刷子系统、审核子系统、发布子系统、存储子系统。<\\/p>\\n<\\/li>\\n<li>\\n<p>评论审核子系统不再包含业务意义上的子系统，而是包括各个模块或者组件，这些模块或者组件本身也是另外一个维度上的系统。例如，MySQL、Redis等是存储系统，但不是业务子系统。<\\/p>\\n<\\/li>\\n<\\/ol>\\n<h2>模块与组件<\\/h2>\\n<p>模块和组件两个概念在实际工作中很容易混淆，我们经常能够听到类似这样的说法：<\\/p>\\n<ul>\\n<li>\\n<p>MySQL模块主要负责存储数据，而ElasticSearch模块主要负责数据搜索。<\\/p>\\n<\\/li>\\n<li>\\n<p>我们有安全加密组件、有审核组件。<\\/p>\\n<\\/li>\\n<li>\\n<p>App的下载模块使用了第三方的组件。<\\/p>\\n<\\/li>\\n<\\/ul>\\n<p>造成这种现象的主要原因是，模块与组件的定义并不好理解，也不能很好地进行区分。我们来看看这两者在维基百科上的定义。<\\/p>\\n<blockquote>\\n<p>软件模块（Module）是一套一致而互相有紧密关连的软件组织。它分别包含了程序和数据结构两部分。现代软件开发往往利用模块作为合成的单位。模块的接口表达了由该模块提供的功能和调用它时所需的元素。模块是可能分开被编写的单位。这使它们可再用和允许人员同时协作、编写及研究不同的模块。<\\/p>\\n<\\/blockquote>\\n<blockquote>\\n<p>软件组件定义为自包含的、可编程的、可重用的、与语言无关的软件单元，软件组件可以很容易被用于组装应用程序中。<\\/p>\\n<\\/blockquote>\\n<p>可能你看完这两个定义后一头雾水，还是不知道这两者有什么区别。造成这种现象的根本原因是，<strong>模块和组件都是系统的组成部分，只是从不同的角度拆分系统而已<\\/strong>。<\\/p>\\n<p>从逻辑的角度来拆分系统后，得到的单元就是“模块”；从物理的角度来拆分系统后，得到的单元就是“组件”。划分模块的主要目的是职责分离；划分组件的主要目的是单元复用。其实，“组件”的英文component也可翻译成中文的“零件”一词，“零件”更容易理解一些，“零件”是一个物理的概念，并且具备“独立且可替换”的特点。<\\/p>\\n<p>我以一个最简单的网站系统来为例。假设我们要做一个学生信息管理系统，这个系统从逻辑的角度来拆分，可以分为“登录注册模块”“个人信息模块”“个人成绩模块”；从物理的角度来拆分，可以拆分为Nginx、Web服务器、MySQL。<\\/p>\\n<h2>框架与架构<\\/h2>\\n<p>框架是和架构比较相似的概念，且两者有较强的关联关系，所以在实际工作中，这两个概念有时我们容易分不清楚。参考维基百科上框架与架构的定义，我来解释两者的区别。<\\/p>\\n<blockquote>\\n<p>软件框架（Software framework）通常指的是为了实现某个业界标准或完成特定基本任务的软件组件规范，也指为了实现某个软件组件规范时，提供规范所要求之基础功能的软件产品。<\\/p>\\n<\\/blockquote>\\n<p>我来提炼一下其中关键部分：<\\/p>\\n<ol>\\n<li>\\n<p>框架是组件规范：例如，MVC就是一种最常见的开发规范，类似的还有MVP、MVVM、J2EE等框架。<\\/p>\\n<\\/li>\\n<li>\\n<p>框架提供基础功能的产品：例如，Spring MVC是MVC的开发框架，除了满足MVC的规范，Spring提供了很多基础功能来帮助我们实现功能，包括注解（@Controller等）、Spring Security、Spring JPA等很多基础功能。<\\/p>\\n<\\/li>\\n<\\/ol>\\n<blockquote>\\n<p>软件架构指软件系统的“基础结构”，创造这些基础结构的准则，以及对这些结构的描述。<\\/p>\\n<\\/blockquote>\\n<p>单纯从定义的角度来看，框架和架构的区别还是比较明显的，<strong>框架关注的是“规范”，架构关注的是“结构”<\\/strong>。框架的英文是Framework，架构的英文是Architecture。Spring MVC的英文文档标题就是“Web MVC framework”。<\\/p>\\n<p>虽然如此，在实际工作中我们却经常碰到一些似是而非的说法。例如，“我们的系统是MVC架构”“我们需要将android app重构为MVP架构”“我们的系统基于SSH框架开发”“我们是SSH的架构”“XX系统是基于Spring MVC框架开发，标准的MVC架构”……<\\/p>\\n<p>究竟什么说法是对的，什么说法是错的呢？<\\/p>\\n<p>其实这些说法都是对的，造成这种现象的根本原因隐藏于架构的定义中，关键就是“基础结构”这个概念并没有明确说是从什么角度来分解的。采用不同的角度或者维度，可以将系统划分为不同的结构，其实我在“模块与组件”中的“学生管理系统”示例已经包含了这点。<\\/p>\\n<p>从业务逻辑的角度分解，“学生管理系统”的架构是：<\\/p>\\n<p><img src=\\\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/74\\/0c\\/746f547767d94a5a7b8a9a130fcefc0c.jpg\\\" alt=\\\"\\\" \\/><\\/p>\\n<p>从物理部署的角度分解，“学生管理系统”的架构是：<\\/p>\\n<p><img src=\\\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/06\\/ed\\/0682867076f29d8f48c4021dabfd98ed.jpg\\\" alt=\\\"\\\" \\/><\\/p>\\n<p>从开发规范的角度分解，“学生管理系统”可以采用标准的MVC框架来开发，因此架构又变成了MVC架构：<\\/p>\\n<p><img src=\\\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/e1\\/1d\\/e1b415fd316dc3f487a75f228c5fcf1d.jpg\\\" alt=\\\"\\\" \\/><\\/p>\\n<p>这些“架构”，都是“学生管理系统”正确的架构，只是从不同的角度来分解而已，这也是IBM的RUP将软件架构视图分为著名的“<strong>4+1视图<\\/strong>”的原因。<\\/p>\\n<h2>重新定义架构<\\/h2>\\n<p>参考维基百科的定义，我将架构重新定义为：<strong>软件架构指软件系统的顶层结构<\\/strong>。<\\/p>\\n<p>这个定义看似很简单，但包含的信息很丰富，基本上把系统、子系统、模块、组件、架构等概念都串起来了，我来详细解释一下。<\\/p>\\n<p>首先，“系统是一群关联个体组成”，这些“个体”可以是“子系统”“模块”“组件”等；架构需要明确系统包含哪些“个体”。<\\/p>\\n<p>其次，系统中的个体需要“根据某种规则”运作，架构需要明确个体运作和协作的规则。<\\/p>\\n<p>第三，维基百科定义的架构用到了“基础结构”这个说法，我改为“顶层结构”，可以更好地区分系统和子系统，避免将系统架构和子系统架构混淆在一起导致架构层次混乱。<\\/p>\\n<h2>小结<\\/h2>\\n<p>今天我为你梳理了与架构有关的几个容易混淆的概念，包括系统与子系统、模块与组件、框架与架构，解释了架构的定义，希望对你有所帮助。<\\/p>\\n<p>这就是今天的全部内容，留一道思考题给你吧。你原来理解的架构是如何定义的？对比我今天讲的架构定义，你觉得差异在哪里？<\\/p>\\n<p>欢迎你把答案写到留言区，和我一起讨论。相信经过深度思考的回答，也会让你对知识的理解更加深刻。（编辑乱入：精彩的留言有机会获得丰厚福利哦！）<\\/p>\\n\",\"footer_cover_data\":{\"img_url\":\"\",\"mp_url\":\"\",\"link_url\":\"\"},\"column_is_experience\":false,\"audio_md5\":\"0de96702b98219cd28de5ae2096a5bdc\",\"article_cover_hidden\":false,\"is_required\":true,\"rate\":{\"2\":{\"cur_version\":0,\"max_rate\":0,\"cur_rate\":0,\"is_finished\":false,\"total_rate\":0,\"learned_seconds\":0},\"1\":{\"cur_version\":1614759298,\"max_rate\":50,\"cur_rate\":1,\"is_finished\":true,\"total_rate\":50,\"learned_seconds\":2057},\"3\":{\"cur_version\":0,\"max_rate\":0,\"cur_rate\":0,\"is_finished\":false,\"total_rate\":0,\"learned_seconds\":0}},\"score\":\"21524870000\",\"like_count\":1027,\"article_subtitle\":\"无\",\"audio_download_url\":\"https:\\/\\/static001.geekbang.org\\/resource\\/audio\\/0d\\/dc\\/0de96702b98219cd28de5ae2096a5bdc.mp3\",\"id\":6458,\"had_viewed\":true,\"article_title\":\"01 | 架构到底是指什么？\",\"column_bgcolor\":\"#a2cde8\",\"article_features\":0,\"video_time\":\"\",\"is_video_preview\":false,\"article_summary\":\"对于技术人员来说，“架构”是一个再常见不过的词了，但“架构”到底是指什么？\",\"column_cover\":\"https:\\/\\/static001.geekbang.org\\/resource\\/image\\/a9\\/ed\\/a96b163e2d9bf19cdfc0f5d0d69e13ed.jpg\",\"column_sale_type\":0,\"audio_time_arr\":{\"m\":\"09\",\"s\":\"19\",\"h\":\"00\"},\"column_is_onboard\":true,\"column_id\":81,\"video_size\":0,\"audio_title\":\"01 | 架构到底是指什么-新\",\"article_poster_wxlite\":\"https:\\/\\/static001.geekbang.org\\/render\\/screen\\/cf\\/b8\\/cf471ee4f6e7455f0f3efd9decbd08b8.jpeg\",\"text_read_percent\":105010501000,\"comment_count\":387,\"cid\":81,\"article_could_preview\":false,\"article_cshort\":\"<p>对于技术人员来说，“架构”是一个再常见不过的词了。我们会对新员工培训整个系统的架构，参加架构设计评审，学习业界开源系统（例如，MySQL、Hadoop）的架构，研究大公司的架构实现（例如，微信架构、淘宝架构）……虽然“架构”这个词常见，但如果深究一下<span class=\\\"orange\\\">“架构”到底指什么<\\/span>，大部分人也许并不一定能够准确地回答。例如：<\\/p>\\n<ul>\\n<li>\\n<p>架构和框架是什么关系？有什么区别？<\\/p>\\n<\\/li>\\n<li>\\n<p>Linux有架构，MySQL有架构，JVM也有架构，使用Java开发、MySQL存储、跑在Linux上的业务系统也有架构，应该关注哪个架构呢？<\\/p>\\n<\\/li>\\n<li>\\n<p>微信有架构，微信的登录系统也有架构，微信的支付系统也有架构，当我们谈微信架构时，到底是在谈什么架构？<\\/p>\\n<\\/li>\\n<\\/ul>\\n<p>要想准确地回答这几个问题，关键在于梳理几个有关系而又相似的概念，包括：系统与子系统、模块与组件、框架与架构。<\\/p>\\n\",\"video_width\":0,\"column_could_sub\":true,\"article_ctime\":1524870000,\"article_sharetitle\":\"架构到底是指什么？\"},\"code\":0}\n";
        JSONObject parse = JSONObject.parseObject(html);
        JSONObject data = parse.getJSONObject("data");
        String content = data.getString("article_content");
        JSONObject article = new JSONObject();
        article.put("articleContent", content);
        article.put("articleCover", data.getString("article_cover"));
        article.put("articleTitle", data.getString("article_title"));
        article.put("authorName", data.getString("author_name"));
        long articleCtime = data.getLongValue("article_ctime");
        article.put("articleCtime", simpleDateFormat.format(new Date(articleCtime)));
        root.put("article", article);
        try {
            TemplateUtil.template2Html(init, template, htmlPath, root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void html2Pdf() throws IOException {
        Itext7HtmlPdfUtil.html2Pdf(htmlPath, destPdf);
    }
}
