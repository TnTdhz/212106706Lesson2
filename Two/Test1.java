package Main;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class Test1 {
    public static void main(String[] args) {
        String url = "https://www.zhihu.com/people/tnt-30-58/collections";
        //获取我的知乎收藏夹的Html页面源代码
        Document doc = Jsoup.parse(getHtml(url));
        //根据a标签的类名来提取我们的收藏夹名字和网址
        Elements elements = doc.getElementsByClass("SelfCollectionItem-title");
        //调用show方法，对获取的Html文件进行提取和展示在控制台
        showHtml(elements);
        //循环遍历提取的Html的元素的链接，并调用方法访问链接对获取的Json内容进行提取展示在控制台
        for(int num=0;num<elements.size();num++) {
            //将获从元素中提取的内容与固定格式拼接成链接进行访问
            String url1="https://www.zhihu.com/api/v4/collections/"+elements.get(num).attr("href").substring(12)+"/items?offset=0&limit=20";
            //调用getJson方法获取Json数据
            String value = getJson(url1);
            //调用方法对Json数据进行提取并展示在控制台
            showJson(elements,value,num);
        }
    }

    /**
     * 功能：获取访问的收藏夹的Html数据
     *
     * @param url 链接网址
     * @return String类型，访问链接后获取的Html数据
     */
    public static String getHtml(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            // 建立实际的连接
            httpURLConnection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //将获取的Html数据保存在本地上
        try (FileOutputStream fos = new FileOutputStream("E:\\Html.txt")) {
            fos.write(result.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 功能：获取访问每个收藏夹获取的JSON
     * @param url 要获取Json数据需要访问的链接
     * @return String类型，访问连接后返回的Json数据
     */
    public static String getJson(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //将获取的Json数据保存在本地上
        try (FileOutputStream fos = new FileOutputStream("E:\\Json.txt")) {
            fos.write(result.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * 功能：将获取的JSON数据经过提取展示在控制台
     * @param elements Html提取后的元素文件
     * @param value 访问连接后获取的Json数据
     * @param n 当前获取的是第几个收藏夹
     */
    public static void showJson(Elements elements,String value,int n){
        //分析Json的格式，得到一个Json对象
        JSONObject json = (JSONObject) JSONSerializer.toJSON(value);
        //若Json数据包含数组，可以解析来获取一个Json数组
        JSONArray array = json.getJSONArray("data");
        System.out.println("其中，"+elements.get(n).text()+"类有"+array.size()+"个文章内容，分给为：");
        for(int num=0;num<array.size();num++) {
            //再对JSON数组进行解析得到新的JSON对象
            JSONObject jsonValue = array.getJSONObject(num);
            JSONObject jsonContent = jsonValue.getJSONObject("content");
            if(jsonContent.getString("title").length()>1){
                System.out.println(jsonContent.getString("title")+jsonContent.getString("url"));
            }else{
                JSONObject jsonQuestion = jsonContent.getJSONObject("question");
                System.out.println(jsonQuestion.getString("title")+jsonQuestion.getString("url"));
            }
        }
    }

    /**
     * 功能：将获取的Html数据经过提取展示在控制台
     * @param elements Html提取后的元素文件
     */
    public static void showHtml(Elements elements){
        System.out.print("我的知乎收藏夹里有"+elements.size()+"大类，分别为：");
        int i=0;
        //遍历存放的元素，提取出标签的内容并展示在控制台
        for(Element element:elements){
            if(i==0) {
                //输出的是标签的文本
                System.out.print( element.text());
                i=1;
            }else {
                System.out.print("," + element.text());
            }
        }
        System.out.println();
    }
}
