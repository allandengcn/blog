package cn.allandeng.blog.util.markdownUtil;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
* @ClassName MarkdownUtils
* @Date: 2020/2/7 17:29
* @Description: Markdown工具类
* @Author: Allan Deng
* @Version: 1.0 
**/
public class MarkdownUtils {

    /**
     * @Title markdownToHtml
     * @Author Allan Deng
     * @Description  转换markdown到HTML
     * @Date 12:53 2020/2/10
     * @Param [markdown]
     * @return java.lang.String
     **/
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * @Title markdownToHtmlExtensions
     * @Author Allan Deng
     * @Description  增加扩展[标题锚点，表格生成]
     * @Date 12:54 2020/2/10
     * @Param [markdown]
     * @return java.lang.String
     **/
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
    * @ClassName MarkdownUtils
    * @Date: 2020/2/10 12:54
    * @Description: 内部静态类，给html中添加属性
    * @Author: Allan Deng
    * @Version: 1.0
    **/
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
            if (node instanceof Heading ) {
                attributes.put("class", "ui header");
            }
            if (node instanceof Image) {
                attributes.put("class", "ui rounded image m-shadow-small");
            }
        }
    }


    /**
     * @Title main
     * @Author Allan Deng
     * @Description  Markdown的测试类
     * @Date 12:55 2020/2/10
     * @Param [args]
     * @return void
     **/
    public static void main(String[] args) {
        String table = "| hello | hi   | 哈哈哈   |\n" +
                "| ----- | ---- | ----- |\n" +
                "| 斯维尔多  | 士大夫  | f啊    |\n" +
                "| 阿什顿发  | 非固定杆 | 撒阿什顿发 |\n" +
                "\n";
        String a = "[Allan的个人博客](http://allandeng.cn)";
        System.out.println(markdownToHtmlExtensions(a));
    }
}
