package com.kangren.practice.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebView;

/**
 * 网络工具类
 * Created by kangren on 2017/12/26.
 */
public class NetworkUtils
{
    public static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";

    /**
     * 网络类型：-1-无网络
     */
    public static final String STATE_NETWORK_NONE = "-1";

    /**
     * 网络类型：0-WiFi
     */
    public static final String STATE_NETWORK_WIFI = "0";

    /**
     * 网络类型：1-3G
     */
    public static final String STATE_NETWORK_3G = "1";
    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final int APN_TYPE_DISABLED = -1;// 网络不可用
    public static final int APN_TYPE_OTHER = 0;
    public static final int APN_TYPE_CM_CU_WAP = 1;// 移动联通wap10.0.0.172
    public static final int APN_TYPE_CT_WAP = 2;// 电信wap 10.0.0.200
    public static final int APN_TYPE_NET = 3;// 电信,移动,联通,wifi 等net网络
    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    public static String USER_AGENT = "Mozilla/5.0 (Linux; U; Android " + Build.VERSION.RELEASE + "; Build/" + Build.ID
            + ")";

    /**
     * 获取网络状态：-1-无网络，0-WiFi，1-3G
     *
     * @param context
     * @return
     */
    public static String getNetworkState(Context context)
    {
        if (!isNetworkAvailable(context))
        {
            return STATE_NETWORK_NONE;
        }
        if (isMobileNetwork(context))
        {
            return STATE_NETWORK_3G;
        }
        // 网络可用且不是3G，都算是wifi
        return STATE_NETWORK_WIFI;
    }

    /**
     * Returns whether the network is available
     *
     * @param context Context
     * @return 网络是否可用
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNetworkAvailable(Context context)
    {
        return getConnectedNetworkInfo(context) != null;
    }

    /**
     * 获取网络类型
     *
     * @param context Context
     * @return 网络类型
     * @see [类、类#方法、类#成员]
     */
    public static int getNetworkType(Context context)
    {
        NetworkInfo networkInfo = getConnectedNetworkInfo(context);
        if (networkInfo != null)
        {
            return networkInfo.getType();
        }

        return -1;
    }

    public static NetworkInfo getConnectedNetworkInfo(Context context)
    {
        try
        {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            if (connectivity == null)
            {
                LogUtils.error("couldn't get connectivity manager");
            }
            else
            {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null)
                {
                    /*
                     * for (int i = 0; i < info.length; i++) { if
                     * (info[i].getState() == NetworkInfo.State.CONNECTED) {
                     * return info[i]; } }
                     */
                    return info;
                }
            }

        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }
        return null;
    }

    /**
     * 判断网络是否是电信网络
     */
    public static boolean isTelecomNet(Context context)
    {
        NetworkInfo info = getConnectedNetworkInfo(context);
        if (info != null && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE)
        {
            String extraInfo = info.getExtraInfo();
            if (TextUtils.isEmpty(extraInfo))
            {
                return false;
            }
            extraInfo = info.getExtraInfo().toLowerCase();
            if ("cmwap".equals(extraInfo) || "cmnet".equals(extraInfo) || "uniwap".equals(extraInfo)
                    || "uninet".equals(extraInfo) || "3gwap".equals(extraInfo) || "3gnet".equals(extraInfo))
            {
                return false;
            }
            else
            {

                return true;
            }
        }
        return false;
    }

    /**
     * 判断网络是否是中国移动网络
     */
    public static boolean isChinaMobileNet(Context context)
    {
        NetworkInfo info = getConnectedNetworkInfo(context);
        if (info != null && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_MOBILE)
        {
            String extraInfo = info.getExtraInfo();
            if (TextUtils.isEmpty(extraInfo))
            {
                return false;
            }
            extraInfo = extraInfo.toLowerCase();
            return extraInfo.equals("cmnet") || extraInfo.equals("cmwap");
        }
        return false;
    }

    /**
     * 判断网络是不是手机网络，非wifi
     *
     * @param context Context
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isMobileNetwork(Context context)
    {
        return (ConnectivityManager.TYPE_MOBILE == getNetworkType(context));
    }

    /**
     * 判断网络是不是wifi
     *
     * @param context Context
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isWifiNetwork(Context context)
    {
        return (ConnectivityManager.TYPE_WIFI == getNetworkType(context));
    }

    public static int getConnectionType(Context context)
    {

        if (isWifiNetwork(context))
        {
            return ConnectionType.Wifi;
        }
        else
        {
            NetworkInfo networkInfo = getConnectedNetworkInfo(context);
            if (networkInfo == null)
            {
                return ConnectionType.Unknown;
            }
            int nType = networkInfo.getType();
            if (nType != ConnectivityManager.TYPE_MOBILE)
            {
                return ConnectionType.Unknown;
            }
            try
            {
                final Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
                if (c != null)
                {
                    c.moveToFirst();
                    final String user = c.getString(c.getColumnIndex("user"));
                    if (!TextUtils.isEmpty(user))
                    {
                        LogUtils.debug("===>代理：" + c.getString(c.getColumnIndex("proxy")));
                        if (user.startsWith(CTWAP))
                        {
                            LogUtils.debug("===>电信wap网络");
                            return ConnectionType.G2;
                        }
                    }
                }
                c.close();
            }
            catch (Exception e)
            {
                // ignore
            }
            String extraInfo = networkInfo.getExtraInfo();
            LogUtils.debug("extraInfo:" + extraInfo);
            if (extraInfo != null)
            {
                // 通过apn名称判断是否是联通和移动wap
                extraInfo = extraInfo.toLowerCase();
                if (extraInfo.equals(CMWAP) || extraInfo.equals(WAP_3G) || extraInfo.equals(UNIWAP))
                {
                    LogUtils.debug(" ======>移动联通wap网络");
                    return ConnectionType.G2;
                }
            }

            LogUtils.debug(" ======>net网络");

            return ConnectionType.G3;
        }

    }

    /**
     * 获取接入点类型</br>
     * Net网络：运营商（移动联通电信）net网络，wifi，usb网络共享<br/>
     * Wap网络：移动联通wap（代理相同：10.0.0.172：80），电信wap（代理：10.0.0.200：80）<br/>
     */
    public static int getAPNType(Context context)
    {

        NetworkInfo networkInfo = getConnectedNetworkInfo(context);
        if (networkInfo == null)
        {
            return APN_TYPE_DISABLED;
        }

        int nType = networkInfo.getType();
        if (nType != ConnectivityManager.TYPE_MOBILE)
        {
            return APN_TYPE_OTHER;
        }

        // 判断是否电信wap:
        // 不要通过getExtraInfo获取接入点名称来判断类型，
        // 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
        // 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码,
        // 所以可以通过这个进行判断！
        try
        {
            final Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
            if (c != null)
            {
                c.moveToFirst();
                final String user = c.getString(c.getColumnIndex("user"));
                if (!TextUtils.isEmpty(user))
                {
                    LogUtils.debug("===>代理：" + c.getString(c.getColumnIndex("proxy")));
                    if (user.startsWith(CTWAP))
                    {
                        LogUtils.debug("===>电信wap网络");
                        return APN_TYPE_CT_WAP;
                    }
                }
            }
            c.close();
        }
        catch (Exception e)
        {
            LogUtils.error(e + "");
        }

        // 判断是移动联通wap:
        // 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
        // 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
        // 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
        // 所以采用getExtraInfo获取接入点名字进行判断
        String extraInfo = networkInfo.getExtraInfo();
        LogUtils.debug("extraInfo:" + extraInfo);
        if (extraInfo != null)
        {
            // 通过apn名称判断是否是联通和移动wap
            extraInfo = extraInfo.toLowerCase();
            if (extraInfo.equals(CMWAP) || extraInfo.equals(WAP_3G) || extraInfo.equals(UNIWAP))
            {
                LogUtils.debug(" ======>移动联通wap网络");
                return APN_TYPE_CM_CU_WAP;
            }
        }

        LogUtils.debug(" ======>net网络");

        return APN_TYPE_NET;

    }

    /**
     * 3GWAP,uniwap 下不取号
     */
    public static boolean is3GWAP(Context context)
    {
        try
        {
            NetworkInfo networkInfo = getConnectedNetworkInfo(context);
            if (networkInfo != null)
            {
                String extraInfo = networkInfo.getExtraInfo();
                if (extraInfo != null && (WAP_3G.equalsIgnoreCase(extraInfo) || UNIWAP.equalsIgnoreCase(extraInfo)))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.error("CHECK 3GWAP ERROR");
        }
        return false;
    }

    /** 获取wifi名称 */
    public static String getWifiSSID(Context context)
    {
        if (isWifiNetwork(context))
        {
            try
            {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                return info.getSSID();
            }
            catch (Exception e)
            {
                LogUtils.error(e.toString(), e);
            }
        }
        return null;
    }

    /**
     * Returns whether the network is roaming
     *
     * @param context Context
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNetworkRoaming(Context context)
    {
        try
        {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            if (connectivity == null)
            {
                LogUtils.error("couldn't get connectivity manager");
            }
            else
            {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                        Context.TELEPHONY_SERVICE);
                if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    if (/* TelephonyManager.getDefault() */telephonyManager.isNetworkRoaming())
                    {

                        return true;
                    }
                    else
                    {

                    }
                }
                else
                {

                }
            }

        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }

        return false;
    }

    /** 获取本机ip。 */
    public static String getLocalIpAddress()
    {
        // String ipaddress = "";
        try
        {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements())
            {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements())
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    // loopback地址就是代表本机的IP地址，只要第一个字节是127，就是lookback地址
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                        // ipaddress = ipaddress + ";" +
                        // inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }
        return null;
        // return ipaddress;
    }

    /** 手机号码 */
    public static String getLine1Number(Context context)
    {
        try
        {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }
        return "";
    }

    // 获取手机的的IMEI
    public static String getPhoneIMEI(Context context)
    {
        try
        {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }
        return "";

    }

    /** 判断端口是否被占用 */
    public static boolean isPortUsed(int port)
    {
        String[] cmds = {"netstat", "-an"};
        java.lang.Process process = null;
        InputStream is = null;
        DataInputStream dis = null;
        try
        {

            String line = "";
            Runtime runtime = Runtime.getRuntime();

            process = runtime.exec(cmds);
            is = process.getInputStream();
            dis = new DataInputStream(is);
            while ((line = dis.readLine()) != null)
            {
                // LogUtils.error(line);
                if (line.contains(":" + port))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }
        finally
        {
            try
            {
                if (dis != null)
                {
                    dis.close();
                }
                if (is != null)
                {
                    is.close();
                }
                if (process != null)
                {
                    process.destroy();
                }
            }
            catch (Exception e)
            {
                LogUtils.error(e.toString(), e);
            }
        }
        return false;
    }

    public static String getMacAddressPure(Context context)
    {
        String addr = getMacAddress(context);
        if (addr == null)
        {
            return "";
        }
        String addrNoColon = addr.replaceAll(":", "");
        String addrNoLine = addrNoColon.replaceAll("-", "");

        return addrNoLine.toUpperCase();

    }

    /**
     * 更新获取mac地址的方法，解决Android6.0设备通过WifiInfo.getMacAddress()方法获取到的mac地址全是02:00:00:00:00:00的问题
     * @param context
     * @return
     */
    private static String getWifiMacAddress(Context context)
    {
        String mac = getOldWifiMacAddress(context);
        if (!DEFAULT_MAC_ADDRESS.equals(mac) && !TextUtils.isEmpty(mac))
        {
            // 如果mac非空且不等于02:00:00:00:00:00，直接返回
            return mac;
        }
        String address = "";
        try
        {
            // 把当前机器上的访问网络接口的存入 Enumeration集合中
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements())
            {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0 || !"wlan0".equalsIgnoreCase(netWork.getName()))
                {
                    continue;
                }
                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                StringBuilder builder = new StringBuilder();
                for (byte b : by)
                {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0)
                {
                    builder.deleteCharAt(builder.length() - 1);
                }
                // wifi name为wlan0的mac地址即是设备的mac
                address = builder.toString();
                if (!TextUtils.isEmpty(address))
                {
                    // 转为小写
                    address = address.toLowerCase();
                }
            }
        }
        catch (Exception e)
        {
            LogUtils.error(e + "");
        }
        return address;
    }

    private static String getOldWifiMacAddress(Context context)
    {
        try
        {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        }
        catch (Exception e)
        {
            LogUtils.error(e.toString(), e);
        }
        return "";
    }

    public static String getMacAddress(Context context)
    {
        String addr = getMacAddrInFile("/sys/class/net/eth0/address");
        if (TextUtils.isEmpty(addr))
        {
            addr = getMacAddrInFile("/sys/class/net/wlan0/address");
        }

        if (TextUtils.isEmpty(addr))
        {
            return getWifiMacAddress(context);
        }

        return addr;
    }

    private static String getMacAddrInFile(String filepath)
    {
        File f = new File(filepath);
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(f);
            BufferedReader rd = new BufferedReader(new InputStreamReader(fis));
            String str = rd.readLine();

            // 去除空格
            str = str.replaceAll(" ", "");

            // 查看是否是全0的无效MAC地址 如 00:00:00:00:00:00
            String p = str.replaceAll("-", "");
            p = p.replaceAll(":", "");
            if (p.matches("0*"))
            {
                return null;
            }
            return str;
        }
        catch (Exception e)
        {
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        return null;
    }

    /**
     * Convert byte array to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes)
    {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++)
        {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10)
                sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     *
     * @param str
     * @return array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str)
    {
        try
        {
            return str.getBytes("UTF-8");
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static String loadFileAsString(String filename) throws java.io.IOException
    {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1)
            {
                if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF)
                {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                }
                else
                {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (Exception ex)
            {
            }
        }
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName)
    {
        try
        {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces)
            {
                if (interfaceName != null)
                {
                    if (!intf.getName().equalsIgnoreCase(interfaceName))
                        continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null)
                    return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0)
                    buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        }
        catch (Exception ex)
        {
        } // for now eat exceptions
        return "";
        /*
         * try { // this is so Linux hack return
         * loadFileAsString("/sys/class/net/" +interfaceName +
         * "/address").toUpperCase().trim(); } catch (IOException ex) { return
         * null; }
         */
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4)
    {
        try
        {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces)
            {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs)
                {
                    if (!addr.isLoopbackAddress())
                    {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4)
                        {
                            if (isIPv4)
                                return sAddr;
                        }
                        else
                        {
                            if (!isIPv4)
                            {
                                int delim = sAddr.indexOf('%'); // drop ip6
                                // port suffix
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
        } // for now eat exceptions
        return "";
    }

    public static void initUserAgent(Context c)
    {
        String ua = null;
        try
        {
            WebView mes = new WebView(c);
            ua = mes.getSettings().getUserAgentString();
        }
        catch (Throwable e)
        {
            LogUtils.error("init agent error:" + e);
        }
        if (!TextUtils.isEmpty(ua))
        {
            USER_AGENT = ua;
        }
        else
        {

            try
            {
                ua = System.getProperty("http.agent");
            }
            catch (Throwable e)
            {
                LogUtils.error("init agent error2:" + e);
            }

            if (!TextUtils.isEmpty(ua))
            {
                USER_AGENT = ua;
            }
        }
        LogUtils.error("UA:" + USER_AGENT);
    }

    /**
     * 获取从开机到现在当前应用消耗流量总和
     *
     * @return 字节数
     */
    public static long getDataFlow()
    {
        try
        {
            // 下载的流量 byte
            return TrafficStats.getUidRxBytes(Process.myUid());
        }
        catch (Throwable t)
        {
            LogUtils.error("wentaoli getDataFlow error:" + t, t);
        }
        return 0;
    }

    public static class ConnectionType
    {
        public static int Unknown = 0;

        public static int Ethernet = 1;

        public static int Wifi = 2;

        public static int Unknown_Generation = 3;

        public static int G2 = 4;

        public static int G3 = 5;

        public static int G4 = 6;

    }

    /**
     * @author youxueliu
     */
    public static class NetworkReceiverUtil
    {
        private static final String TAG = "NetworkReceiver";

        public static void registNetworkReceive(Context context, BroadcastReceiver networkReceiver)
        {
            if (context == null || networkReceiver == null)
                return;
            try
            {
                IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                context.registerReceiver(networkReceiver, intentFilter);
            }
            catch (IllegalArgumentException e)
            {
                LogUtils.error(TAG + " e.getMessage:" + e.getMessage());
            }
        }

        public static void unregisterNetworkReceive(Context context, BroadcastReceiver networkReceiver)
        {
            if (context == null || networkReceiver == null)
                return;
            try
            {
                if (networkReceiver != null)
                    context.unregisterReceiver(networkReceiver);
            }
            catch (Exception e)
            {
                LogUtils.error(TAG + "unregister error: " + e);
            }
        }

    }

}
