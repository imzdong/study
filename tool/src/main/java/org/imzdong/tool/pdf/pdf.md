#### JAVA操作PDF开源项目

##### 一、操作PDF的JAR主要分成两类，分别是iText系列和Apache PDFBox系列
1. iText PDF ：iText是著名的开放项目，是用于生成PDF文档的一个java类库。通过iText不仅可以生成PDF或rtf的文档，
  而且可以将XML、Html文件转化为 PDF 文件
> [iText 5官网](https://kb.itextpdf.com/home/it5kb)，已停止维护。[iText5仓库地址](https://github.com/itext/itextpdf)
    [iText 7官网](https://kb.itextpdf.com/home/it7kb) 。[iText7仓库地址](https://github.com/itext/itext7)
  iText 7 与iText 5是两个不同的体系。 iText 5已经暂停维护， iText 5与iText 7都分为商业版和社区版
> [itxt7和itext5对比](https://itextpdf.com/en/products/features)
> [开发文档](https://itextpdf.com/en/resources/api-documentation)

2. [Apache PDFBox](https://pdfbox.apache.org/),[Github](https://github.com/apache/pdfbox)。
> Apache PDFBox库是用于处理PDF文档的开源Java工具。该项目允许创建新的PDF文档，操纵现有文档以及从文档中提取内容的功能。
> Apache PDFBox还提供了几个命令行实用程序。可以说Apache出品必是精品。

3. [OpenPDF](https://github.com/LibrePDF/OpenPDF)
> OpenPDF是一个Java库，使用LGPL和MPL开源许可证。可以拥有创建和编辑PDF文件。
> OpenPDF是iText的LGPL/MPL开源后继产品，它基于iText 4 svn标签的一个分支。

4. [x-easypdf](https://gitee.com/xsxgit/x-easypdf)
> x-easypdf基于pdfbox构建而来，极大降低使用门槛，以组件化的形式进行pdf的构建。
> 简单易用，仅需一行代码，便可完成pdf的操作。是国人开源的一个基于Apache PDFBox的JAR包。

##### 二、操作PDF相关工具
1. Jasper Report ：是一个强大、灵活的报表生成工具，能够展示丰富的页面内容，并将之转换成 PDF
> JasperReport是一个强大、灵活的报表生成工具，能够展示丰富的页面内容，并将之转换成 PDF，HTML，或者 XML格式。该库完全由Java写成，可以用于在各种Java应用程序，包括 J2EE，Web应用程序中生成动态内容。
> 只需要将JasperReport引入工程中即可完成PDF报表的编译、显示、输出等工作。数据源支持更多，
> 常用JDBC SQL查询、XML文件、CSV文件 、HQL（Hibernate 查询），HBase，JAVA集合等。还允许你义自己的数据源，通过JASPER文件及数据源，JASPER 就能生成最终用户想要的文档格式。   
2. Openoffice ：openoffice是开源软件且能在windows和linux平台下运行，可以灵活的将word或者Excel转化为PDF文档。

##### 三、生成可替换pdf
1. [freemarker](http://freemarker.foofun.cn/)
> FreeMarker是一款模板引擎：即一种基于模板和要改变的数据，并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。 它不是面向最终用户的，
> 而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件。

