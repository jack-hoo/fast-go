//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddressUtils {
    protected static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public AddressUtils() {
    }

    public static String getAddresses(String ip) {
        try {
            String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
            String returnStr = getResult(urlStr, ip);
            if (returnStr != null) {
                String[] temp = returnStr.split(",");
                if (temp.length < 3) {
                    return "0";
                } else {
                    String region = temp[5].split(":")[1].replaceAll("\"", "");
                    region = decodeUnicode(region);
                    String country = "";
                    String area = "";
                    String city = "";
                    String county = "";
                    String isp = "";

                    for(int i = 0; i < temp.length; ++i) {
                        switch(i) {
                            case 1:
                                country = temp[i].split(":")[2].replaceAll("\"", "");
                                country = decodeUnicode(country);
                            case 2:
                            case 4:
                            case 6:
                            case 8:
                            case 10:
                            default:
                                break;
                            case 3:
                                area = temp[i].split(":")[1].replaceAll("\"", "");
                                area = decodeUnicode(area);
                                break;
                            case 5:
                                region = temp[i].split(":")[1].replaceAll("\"", "");
                                region = decodeUnicode(region);
                                break;
                            case 7:
                                city = temp[i].split(":")[1].replaceAll("\"", "");
                                city = decodeUnicode(city);
                                break;
                            case 9:
                                county = temp[i].split(":")[1].replaceAll("\"", "");
                                county = decodeUnicode(county);
                                break;
                            case 11:
                                isp = temp[i].split(":")[1].replaceAll("\"", "");
                                isp = decodeUnicode(isp);
                        }
                    }

                    String address = region + city;
                    if (StringUtils.isBlank(address)) {
                        address = "地球村";
                    }

                    return address;
                }
            } else {
                return null;
            }
        } catch (Exception var11) {
            log.error("获取ip错误" + var11);
            return null;
        }
    }

    private static String getResult(String urlStr, String ip) {
        URL url = null;
        HttpURLConnection connection = null;

        BufferedReader reader;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes("ip=" + ip);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            reader.close();
            String var8 = buffer.toString();
            return var8;
        } catch (Exception var12) {
            log.error("根据ip获取区域地址错误，api异常{}", var12);
            reader = null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

        }

        return reader.toString();
    }

    public static String decodeUnicode(String theString) {
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        int x = 0;

        while(true) {
            while(true) {
                while(x < len) {
                    char aChar = theString.charAt(x++);
                    if (aChar == '\\') {
                        aChar = theString.charAt(x++);
                        if (aChar == 'u') {
                            int value = 0;

                            for(int i = 0; i < 4; ++i) {
                                aChar = theString.charAt(x++);
                                switch(aChar) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                        value = (value << 4) + aChar - 48;
                                        break;
                                    case ':':
                                    case ';':
                                    case '<':
                                    case '=':
                                    case '>':
                                    case '?':
                                    case '@':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case '[':
                                    case '\\':
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                    default:
                                        throw new IllegalArgumentException("Malformed      encoding.");
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                        value = (value << 4) + 10 + aChar - 65;
                                        break;
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                        value = (value << 4) + 10 + aChar - 97;
                                }
                            }

                            outBuffer.append((char)value);
                        } else {
                            if (aChar == 't') {
                                aChar = '\t';
                            } else if (aChar == 'r') {
                                aChar = '\r';
                            } else if (aChar == 'n') {
                                aChar = '\n';
                            } else if (aChar == 'f') {
                                aChar = '\f';
                            }

                            outBuffer.append(aChar);
                        }
                    } else {
                        outBuffer.append(aChar);
                    }
                }

                return outBuffer.toString();
            }
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            ip = request.getHeader("X-Forwarded-For");
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(44);
                return index != -1 ? ip.substring(0, index) : ip;
            } else {
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }

                return ip;
            }
        }
    }
}
