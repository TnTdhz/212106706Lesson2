package Main;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


public class Test {
    private static String url="https://j1.pupuapi.com/client/search/search_box/products?store_id=deef1dd8-65ee-46bc-9e18-8cf1478a67e9&search_term=%E7%8E%89%E7%B1%B3&sort=0&search_term_from=10&place_id=41823320-5111-429a-b22b-a3d32e60100e&place_zip=350102&page=1&size=20 ";

    public static void main(String[] args) {
        String value = sendGet(url, "");
    }
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Host", "j1.pupuapi.com");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143");
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("open-id", "oMwzt0MJ-O3C7fdFmdz8ibF-yk8I");
            connection.setRequestProperty("pp-os", "0");
            connection.setRequestProperty("pp-placeid", "41823320-5111-429a-b22b-a3d32e60100e");
            connection.setRequestProperty("pp-version", "2021061900");
            connection.setRequestProperty("pp_storeid", "deef1dd8-65ee-46bc-9e18-8cf1478a67e9");
            connection.setRequestProperty("sign", "21f92edd76c33a5b1f871f85e6f02332");
            connection.setRequestProperty("timestamp", "1647177837615");
            connection.setRequestProperty("Referer", "https://servicewechat.com/wx122ef876a7132eb4/155/page-frame.html");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
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
        return result;
    }
}
