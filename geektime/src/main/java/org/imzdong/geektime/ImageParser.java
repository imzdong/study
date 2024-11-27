package org.imzdong.geektime;

import static org.imzdong.geektime.GeekTimeEbookUtil.CUSTOM_COOKIE;
import static org.imzdong.geektime.GeekTimeEbookUtil.article2Html;

import java.awt.Graphics2D;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.time.Instant;

public class ImageParser {

    private final String outputFolder;

    public ImageParser(String outputFolder) {
        this.outputFolder = outputFolder+"\\images";
    }

    public String parseImage(String content) {
        // 去除 img 标签中的 style 属性
        String pattern = "img (.{1,15}=\".*?\") src=\".*?\"";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String style = m.group(1);
            content = content.replace(style, "");
        }

        // 去除空的 img 标签
        pattern = "</?img>";
        p = Pattern.compile(pattern);
        m = p.matcher(content);
        content = m.replaceAll("");

        // 提取 img 标签中的 src 属性
        pattern = "img\\s+src=\"(.*?)\"";
        p = Pattern.compile(pattern);
        m = p.matcher(content);
        List<String> imgUrls = new ArrayList<>();
        while (m.find()) {
            imgUrls.add(m.group(1));
        }

        for (String url : imgUrls) {
            String imageName = getImagesNameFromUrl(url);
            try {
                URL imageUrl = new URL(url);
                InputStream inputStream = imageUrl.openStream();
                byte[] imageData = inputStream.readAllBytes();
                new File(outputFolder, imageName).mkdirs();
                String imgFn = Paths.get(outputFolder, imageName).toString();
                saveImage(imageData, imgFn, 500, 500, 0.5f);
                content = content.replace(url, "images/"+imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return content;
    }

    private void saveImage(byte[] content, String filename, Integer minWidth, Integer minHeight, Float ratio) {
        if (minWidth == null) {
            minWidth = 500;
        }
        if (minHeight == null) {
            minHeight = 500;
        }
        if (ratio == null) {
            ratio = 0.5f;
        }

        try (InputStream is = new ByteArrayInputStream(content)) {
            BufferedImage img = ImageIO.read(is);
            int w = img.getWidth();
            int h = img.getHeight();

            if (w <= minWidth || h <= minHeight) {
                ImageIO.write(img, getFormatNameFromFilename(filename), new File(filename));
                return;
            }

            int rw = (int) (w * ratio);
            int rh = (int) (h * ratio);
            if (rw < minWidth) {
                rw = minWidth;
                rh = (int) (rh * ((float) minWidth / rw));
            }
            if (rh < minHeight) {
                rh = minHeight;
                rw = (int) (rw * ((float) minHeight / rh));
            }

            img = resizeImage(img, rw, rh);
            ImageIO.write(img, getFormatNameFromFilename(filename), new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    private String getFormatNameFromFilename(String filename) {
        String[] parts = filename.split("\\.");
        if (parts.length > 1) {
            return parts[parts.length - 1].toLowerCase();
        }
        return "png"; // 默认格式
    }

    private String getImagesNameFromUrl(String url) {
        try {
            URL o = new URL(url);
            String path = o.getPath();
            Path u = Paths.get(path);
            String stem = u.getName(u.getNameCount() - 1).toString();
            int lastIndexOf = stem.lastIndexOf(".");
            //String suffix = u.getName(u.getNameCount()).toString();
            return String.format("%s-%d%s", stem.substring(0, lastIndexOf),
                    Instant.now().getEpochSecond(), stem.substring(lastIndexOf));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return url;
        }
    }

    public static void main(String[] args) throws Exception{
        GeekTimeConstant.headers.put("Cookie", CUSTOM_COOKIE);
        String courseHtmlDirName = "D:\\Download\\geektime\\out";
        //GeekTimeArticle geekTimeArticle = new GeekTimeArticle("780923");
        ImageParser parser = new ImageParser(courseHtmlDirName);
        String parsedContent = parser.parseImage(fillArticle());
        System.out.println(parsedContent);
        article2Html("test", parsedContent, courseHtmlDirName);
    }

    private static String fillArticle(){
        return "<p>你好，我是独行。</p><p>2022年11月底，OpenAI发布了ChatGPT，2023年1月注册用户超过一亿，成为历史上增长最快的应用，上一个纪录保持者是TikTok，注册用户超过一亿用时9个月。2023年3月开始，ChatGPT燃爆中国互联网界。</p><p>实际上国内外同一时期搞大模型的团队很多，为什么ChatGPT会突然火起来？还有在ChatGPT发布后，为什么各个大厂在短时间内相继发布大模型产品？比如3月百度发布文心一言，4月阿里云发布通义千问，5月科大讯飞发布星火认知大模型等等。</p><p><img src=\"https://static001.geekbang.org/resource/image/67/f6/67b5bdf53481e6ba693f042yy76a80f6.png?wh=2558x558\" alt=\"\"></p><p>我们最容易想到的原因是，OpenAI在自然语言处理（NLP）方面取得了突破性的进展，这是技术层面看到的。实际上，ChatGPT背后包含了一系列的资源整合，包括技术、资金、大厂背书等等，以及多个国际巨头的通力合作，比如OpenAI、微软、NVIDIA、GitHub等。所以说，<strong>ChatGPT不仅仅是技术上的突破，更是工程和产品的伟大胜利！</strong></p><p>那么ChatGPT具体是如何赢得这场胜利的呢？我们一一来看。</p><h2>NLP技术突破：强势整合技术资源</h2><p>基于Transformer架构的语言模型大体上分为两类，一类是以BERT为代表的<strong>掩码语言模型</strong>（Masked Language Model，MLM），一类以GPT为代表的<strong>自回归语言模型</strong>（Autoregressive Language Model，ALM）。OpenAI的创建宗旨是：创建造福全人类的安全通用人工智能（AGI），所以创立之初就摒弃了传统AI模型标注式的训练方式，因为可用来标注的数据总是有限的，很难做得非常通用。那么为了实现AGI，OpenAI在技术上到底做对了什么呢？</p><!-- [[[read_end]]] --><h3>基于自回归的无监督训练</h3><p>GPT系列的模型一直走的是和BERT不一样的线路，早些年压力巨大，毕竟BERT是Google发布的，非常权威。但是OpenAI一直认为自回归模型训练潜力更大，尤其在GPT-2引入zero-shot后，更加有信心了。</p><p>按照人类语言的习惯，语言本身就有先后顺序，而且我们日常说话也是下文依赖上文。所以有人猜测，自回归语言模型代表了标准的语言模型，利用上文信息预测下文，这比传统AI预测更加复杂，但是上限更高，更有望通向AGI，这正是OpenAI的愿景。尽管在GPT-1和GPT-2的探索中没有取得压倒性的效果，但确实验证了标准语言模型在zero-shot等方面的潜在能力。</p><p>无监督自回归的训练方式，使GPT模型可以接受大量文本数据，所以后面有了GPT-3，1750亿的参数规模，使GPT-3直接问鼎当时最大的模型，GPT-3使用了大约45TB的文本数据，一次训练费用近460万美元，在当时，相比上一代模型GPT-2，效果已经非常好了，这也是人们所讲的大力出奇迹。</p><p>但是，我们现在来看这个问题，GPT-3发布时间大概是2020年3月，当时的GPT-3还不具备直接和人类对话的能力。而ChatGPT所使用的模型是GPT-3.5，爆火时间在2022年年底到2023年3月，期间将近2年的时间，OpenAI在做什么呢？答案是他们在想办法让GPT模型可以优雅地和人类进行对话。</p><h3>与人类意识对齐（Alignment）</h3><p>我们知道，人类是有感情的，很多事物是有极限的，比如人再怎么能吃也不可能一顿饭吃100个馒头，万一大模型输出的内容有这类意识，那么肯定是不合理的，所以要进行微调对齐。</p><p>GPT-3和GPT-3.5其实是两个不同的系列，使用过OpenAI API的人应该知道，还有几个细化的模型，比如code-davinci、text-davince系列。顾名思义，code-davince就是OpenAI另一个产品codex使用的模型，在text-davince-001的基础上使用源代码进行训练，产生了code-davinci-002模型（codex）。再后来在code-davinci-002模型基础上，基于有监督的指令微调，产生了text-davinci-002，最后在text-davinci-002模型基础上，使用RLHF，产生了text-davinci-003和ChatGPT模型。演进过程如图所示：</p><p><img src=\"https://static001.geekbang.org/resource/image/66/b2/66d1a52f16bd1ab4d736e257f63cebb2.png?wh=2428x1394\" alt=\"图片\"></p><p>GPT-3经过充分训练，但是依然不是一个适合与人类进行对话的模型，所以从GPT-3到GPT-3.5再到InstructGPT和ChatGPT，参数规模并没有太大变化，主要是经过了各种技术的微调，说白了就是去<strong>适配人类情景</strong>。其中，最突出的就是RLHF。RLHF就是Reinforcement Learning From Human Feedback（人类反馈强化学习）的简称。关于RLHF的详细介绍，我们会放在后面的章节中。</p><h3>突现能力（Emergent Ability）</h3><p>突现能力是指大语言模型展现出来的特有的强大能力，比如复杂推理、思维链等。这些是NLP领域一直追求的能力，在大模型出现后，这些能力也随之浮现出来。我们举一个简单的例子。</p><pre><code class=\"language-plain\">问题：小明每天早饭吃2个馒头，他一个月会吃掉多少包馒头？\n" +
                "\n" +
                "一个月按30天说，共吃掉60个馒头，每包5个馒头的话，总共12包。\n" +
                "\n" +
                "答案：12\n" +
                "</code></pre><p>这个推理看着简单，实际上对于AI来说有一定的难度，因为语言和数学混在一起了。在早期GPT-3模型上进行类似的推理，准确率并不高，低于40%，后来在code-davinci-002上进行推理，准确率能达到80%以上。为什么性能会有这么大的提升？</p><p>很明显原因不是模型规模，因为code-davinci-002在规模上并没有扩大，唯一能解释的就是 <strong>code-davinci-002是基于代码进行训练的</strong>，这些突现能力是大模型经过大量代码训练后展现出来的能力。也有人说，面向过程的编程跟人类逐步解决任务的过程很类似，面向对象编程跟人类将复杂任务分解为多个简单任务的过程很类似。所以有人认为，代码训练和思维链及复杂推理有很强的相关性，不过到目前为止没有非常确定的证据可以证明这一点。</p><p>我们可以总结一下。</p><ol>\n" +
                "<li>模型不是越大越好。论参数，GPT-3的1750亿不是参数最大的模型，比如，微软和英伟达联合开发的Megatron-Turing模型拥有超过5000亿个参数，但是在性能方面并不是最好的，因为模型未经充分地训练。</li>\n" +
                "<li>RLHF也不是最早用在GPT上的，却在恰当的时机用到了ChatGPT身上。</li>\n" +
                "<li>在语言模型上使用大量代码进行训练，只有codex这么做了。</li>\n" +
                "</ol><p>所以，ChatGPT在技术上的突破可以理解为：</p><p>$$自回归语言模型+充分无监督训练+大量代码训练+有监督指令微调+RLHF$$</p><p>放眼望去，全球仅此一家！那这么多技术叠加在一起，怎么才能“大力出奇迹”呢？答案就是进行超大规模预训练。</p><h2>超大规模数据集：超过40T的文本数据</h2><p>大模型训练首先需要搞定高质量数据集，我们分两个点去考虑。</p><p>首先，我向你介绍下基础模型GPT-3的训练数据集。GPT-3模型具有1750亿个参数，训练数据集大约500B个token（1B=1 billion，也就是10亿）。下面是训练数据大概的组成结构：</p><p><img src=\"https://static001.geekbang.org/resource/image/1e/f4/1e8378d209127e75b0eba87f1f0f49f4.png?wh=1040x300\" alt=\"图片\" title=\"数据源于官方的 GPT-3 论文《Language Models are Few-Shot Learners》\"></p><p>原始大约45T的纯文本数据，经历过滤后，大概是750G的高质量文本数据。</p><p>我们再来看下ChatGPT的训练数据。ChatGPT属于GPT-3.5系列，官方并没有明确说明这个模型的参数规模，所以网上看到的大部分的数据都是猜测，有人说15亿，也有人说20亿甚至1500亿。但是大概率，ChatGPT的参数规模是小于GPT-3的，其训练数据基于大量<strong>对话型数据</strong>进行指令微调，典型训练数据如下：</p><ul>\n" +
                "<li><strong>Persona-Chat 的数据集</strong>：专门用于训练ChatGPT等会话式AI模型。由两个人类参与者之间的超过160,000条对话组成，每个参与者都被分配了一个独特的角色来描述他们的背景、兴趣和个性。这使得ChatGPT能够学习如何生成个性化且与对话的特定上下文相关的响应。</li>\n" +
                "<li><strong>康奈尔电影对话语料库</strong>：包含电影脚本中角色之间对话的数据集。包括10,000多个电影角色对之间的200,000多次对话，涵盖各种主题和类型。</li>\n" +
                "<li><strong>Ubuntu 对话语料库</strong>：寻求技术支持的用户与Ubuntu社区支持团队之间多轮对话的集合。它包含超过100万个对话，使其成为用于对话系统研究的最大的公开数据集之一。</li>\n" +
                "<li><strong>DailyDialog</strong>：各种主题的人与人对话的集合，从日常生活对话到有关社会问题的讨论。数据集中的每个对话都由几个回合组成，并标有一组情感、情绪和主题信息。</li>\n" +
                "</ul><p>除了这些数据集之外，ChatGPT 还接受了互联网上大量非结构化数据的训练，包括网站、书籍和其他文本源。这使得 ChatGPT 能够从更一般的意义上了解语言的结构和模式，然后可以针对对话管理或情感分析等特定应用进行微调。</p><p>有这么多数据了，接着就要进行预训练了。下面这一步卡住了大部分大模型厂商，那就是<strong>计算资源</strong>。对于OpenAI这种创业公司而言，无疑是很大的困难，那他们是怎么解决的呢？没错，找金主爸爸。</p><h2>找对了金主爸爸</h2><p>OpenAI做对了一件非常重要的事儿，那就是找钱，而且还是找大钱，动辄几亿美元的投入。GPT-3的单次训练成本高达460万美元。在前景未知的情况下，这么大的投入是非常难的。找钱成了OpenAI非常重要的事情，为了找钱，OpenAI从开源转为闭源。</p><p>实际上，早期OpenAI是开源的，创办宗旨就是创建通用人工智能，造福人类，但是大模型训练需要大量的计算资源和数据，这是实打实需要资金投入的，所以OpenAI由开源转为闭源，设计了一种商业模式来吸引投资人，并吸引大量资金，其中最主要的就是微软。下面我向你介绍下这种商业模式。</p><p>OpenAI母公司是OpenAI Inc，属于非营利性质组织，这种情况下资本无法进入，所以后来成立了一家子公司，叫OpenAI LP，现在我们常说的OpenAI，其实就是OpenAI LP，这是一家纯粹的商业化公司，这家公司设置了最高100倍的回报上限，以此来权衡盈利和非盈利属性，也可以叫做“有限盈利”，既迎合资本家利益，也看似符合母公司非盈利组织的创建初衷。</p><p><img src=\"https://static001.geekbang.org/resource/image/af/19/af81436b5171cc0f4f1cda8acb9a6219.png?wh=2560x680\" alt=\"\"></p><p>通过这种股权结构，成功获得微软累计超过100亿美金的投资，这里不得不说，微软为OpenAI带来的不仅仅是钱，更是强大的影响力，毕竟全世界人民是认微软的，包括我们中国人。</p><p>当模型完成大规模训练后，接下来就是面世了，OpenAI的玩法很直接，直接产品化，让大家随便玩。</p><h2>产品化开放：让大家随便玩</h2><p>ChatGPT成功之处在于，愿意公开免费给普通用户使用，虽然各大厂商都在宣称自己在搞大模型，且有多么厉害，但真正产品化后开放给用户的，还要数ChatGPT。</p><h3>便捷使用</h3><p>大部分的AI厂家只发布模型，感兴趣的技术人员去Huggingface下载，自己部署把玩，这样就把模型限制在了非常小的一个范围内。</p><p>而ChatGPT不一样，发布的是普通大众用户都可以使用的产品。通过邮箱注册就可以使用，全天候不限时，直接通过网页对话，<strong>使用门槛非常低</strong>。这要放在国内，还不得让你下载个App，甚至拉几个朋友才可以用。毕竟这背后是实打实的GPU算力成本，据说2022年ChatGPT的算力费用（包含训练和推理）＋人力成本接近5亿美金，试问一下哪个小厂具备这样的实力？而哪个大厂又会在看不到业务前景的背景下，每年投这么多钱去玩？但OpenAI做到了。</p><p>从2024年4月份开始，ChatGPT开启无需注册即可使用的模式，彻底成为互联网基础设施。</p><h3>适用场景多</h3><p>OpenAI官方公开的ChatGPT使用场景有30类（参考<a href=\"https://platform.openai.com/examples\">官方链接</a>），可以用于代码编写、代码翻译、智能问答、语音识别、模拟面试、情感分析、机器翻译、智能客服等多个领域。这使得更多的人能够感受到ChatGPT带来的便利和价值，容易获得大众的支持和信任。</p><h3>使用效果好</h3><p>在ChatGPT之前，市面上已经有很多AI问答机器人产品，比如微软小冰、小度，用过的人都知道，它们都有很多局限性。小冰是由小模型组成的，只能同时处理特定类型任务，无法相互关联，小度也一样，这类产品无法做通用性的问答，无法像ChatGPT一样，像是真人在回答，甚至还有记忆、有感情。</p><h2>工程化应用</h2><p>工程化即系统化、模块化、规范化的一个过程。我们做工程技术的开发人员很清楚，一个优秀的产品背后一定是有着惊人的技术参数，就拿支付宝来说，双十一高峰期，50万笔/秒的支付吞吐量，背后是大量的服务堆出来的，比如数百万台的服务器、大量的分布式技术的应用，如缓存、消息、文件存储等等。</p><p>ChatGPT也一样，OpenAI不是仅仅提供一个模型，让我们自己部署自己玩，而是直接将以大模型为内核的整套技术完成了产品化。对于两个月注册用户过亿的世界级产品来讲，这里面涉及到的技术可想而知，除了AI本身涉及的技术外，常见的工程化技术一样少不了。Web相关的我们就不讲了，主要分享下和模型训练相关的技术。</p><p>为了方便进行大模型训练。2020年，微软为OpenAI在Azure上搭建了计算集群，包含285000个AMD infiniBand 连接的CPU内核，另外还有10000个NVIDIA V100 Tensor Core GPU，是当时世界上第五大超级计算机，也是有史以来在公共云中（Azure）建立的最大的超级计算机。</p><p>我们知道，大模型训练动不动就是几周、几个月的训练，而如果服务器出现故障或者网络连接不稳定，怎么办？肯定不希望从头开始。那么如何实现容错呢？</p><p>微软开发了Project Forge，即Azure容器化和全局调度服务，可以保证AI计算负载保持高水平的利用率；通过建立透明检查点，定期增量保存模型的状态和代码，出现故障，恢复到最近检查点；同时硬件厂家NVIDIA也做了调整，他们的GPU实现了CRIU（用户模式下的检查点恢复），可以恢复GPU的内存使用，最终和CPU的检查点保持一致。软件和硬件协同工作，提高了效率。</p><p>所以光是模型训练这一步，为了实现可靠性，就集微软工程部门、微软研究院、OpenAI团队、NVIDIA团队共同努力于一体。同时，还涉及了隐私计算、私密GPU、TE空间等等。除此之外，OpenAI还雇佣了大量工人（约7.7万人）进行人工标注，总体硬件成本接近10亿美金，单日运营成本高达70万美金，真的是很烧钱。</p><h2>小结</h2><p>这节课我从宏观方面向你介绍了ChatGPT崛起的原因，相信你也对ChatGPT有了更深入的理解。下面我们再简单总结一下。所谓<strong>工程化主要就是指集技术、数据、产品、资源于一身，是一个系统性、规范性的工程项目</strong>，很多人想到ChatGPT，就想到了大模型本身，其实这是片面的，具体你可以参考下面思维导图再回顾一下细节。</p><p><img src=\"https://static001.geekbang.org/resource/image/27/b7/274a7c16a8a139a8fa01865c701d9db7.png?wh=1718x1382\" alt=\"图片\"></p><p>AI大模型还处于发展非常迅速的阶段，实际上天天都在发生变化，所以很多事情都还没有定论，我们需要保持开放的心态，不断接纳，不断学习。下一节课，我会从如何用好大模型这个角度，给你讲解下<strong>提示词工程</strong>，这也是刚接触AI大模型的用户非常容易忽略的问题，当然这也是能否用好AI大模型的核心。</p><h2>思考题</h2><p>学完了这节课的内容，请你来思考2个问题，ChatGPT已经这么强大了，那它是否已经具备了人类大脑的思维？还有AI到底能否代替我们目前的工作？欢迎你把你的观点分享到评论区，我们一起讨论，如果你觉得这节课的内容对你有帮助的话，也欢迎你分享给其他朋友，我们下节课再见！</p><p><a href=\"https://jinshuju.net/f/D8y8pw\">戳此加入课程交流群</a></p>";
    }
}