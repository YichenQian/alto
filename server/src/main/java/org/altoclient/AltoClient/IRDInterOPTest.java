/**
 * @author chenguohai:chenguohai67@outlook.com, chenguohai@huawei.com
 *
 */

package org.altoclient.AltoClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.ipv6.IPv6Network;

@SuppressWarnings("unused")
public final class IRDInterOPTest {
    public static boolean generateDefautNetworkMap(List<NetworkMap> networkMapList) {
        networkMapList.clear();

        List<String> iPV4List1 = new ArrayList<String>();
        iPV4List1.add("100.0.0.0/8");
        List<String> iPV6List1 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("mine", iPV4List1, iPV6List1));

        List<String> iPV4List2 = new ArrayList<String>();
        iPV4List2.add("100.0.0.0/10");
        List<String> iPV6List2 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("mine1", iPV4List2, iPV6List2));

        List<String> iPV4List3 = new ArrayList<String>();
        iPV4List3.add("100.0.1.0/24");
        iPV4List3.add("100.0.64.0/24");
        iPV4List3.add("100.0.192.0/24");
        List<String> iPV6List3 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("mine1a", iPV4List3, iPV6List3));

        List<String> iPV4List4 = new ArrayList<String>();
        iPV4List4.add("100.64.0.0/10");
        List<String> iPV6List4 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("mine2", iPV4List4, iPV6List4));

        List<String> iPV4List5 = new ArrayList<String>();
        iPV4List5.add("100.128.0.0/10");
        List<String> iPV6List5 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("mine3", iPV4List5, iPV6List5));

        List<String> iPV4List6 = new ArrayList<String>();
        iPV4List6.add("128.0.0.0/16");
        iPV4List6.add("130.0.0.0/16");
        List<String> iPV6List6 = new ArrayList<String>();
        addNetworkToStringList(iPV6List6, "2001:DB8:0000::/33");
        networkMapList.add(new NetworkMap("peer1", iPV4List6, iPV6List6));

        List<String> iPV4List7 = new ArrayList<String>();
        iPV4List7.add("129.0.0.0/16");
        iPV4List7.add("131.0.0.0/16");
        List<String> iPV6List7 = new ArrayList<String>();
        addNetworkToStringList(iPV6List7, "2001:DB8:8000::/33");
        networkMapList.add(new NetworkMap("peer2", iPV4List7, iPV6List7));

        List<String> iPV4List8 = new ArrayList<String>();
        iPV4List8.add("132.0.0.0/16");
        List<String> iPV6List8 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("tran1", iPV4List8, iPV6List8));

        List<String> iPV4List9 = new ArrayList<String>();
        iPV4List9.add("135.0.0.0/16");
        List<String> iPV6List9 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("tran2", iPV4List9, iPV6List9));

        List<String> iPV4ListA = new ArrayList<String>();
        iPV4ListA.add("0.0.0.0/0");
        List<String> iPV6ListA = new ArrayList<String>();
        addNetworkToStringList(iPV6ListA, "::0/0");
        networkMapList.add(new NetworkMap("default", iPV4ListA, iPV6ListA));

        List<String> iPV4ListB = new ArrayList<String>();
        iPV4ListB.add("127.0.0.0/8");
        List<String> iPV6ListB = new ArrayList<String>();
        addNetworkToStringList(iPV6ListB, "::1/128");
        networkMapList.add(new NetworkMap("loopback", iPV4ListB, iPV6ListB));

        List<String> iPV4ListC = new ArrayList<String>();
        iPV4ListC.add("169.254.0.0/16");
        List<String> iPV6ListC = new ArrayList<String>();
        addNetworkToStringList(iPV6ListC, "FF80::/10");
        networkMapList.add(new NetworkMap("linklocal", iPV4ListC, iPV6ListC));

        List<String> iPV4ListD = new ArrayList<String>();
        iPV4ListD.add("10.0.0.0/8");
        iPV4ListD.add("172.16.0.0/12");
        iPV4ListD.add("192.168.0.0/16");
        List<String> iPV6ListD = new ArrayList<String>();
        addNetworkToStringList(iPV6ListD, "FC00::/7");
        networkMapList.add(new NetworkMap("private", iPV4ListD, iPV6ListD));

        return true;
    }

    public static boolean generateDefaultRoutingcostMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("default", "default", 1.0));
        costMapList.add(new CostMap("default", "mine", 75.0));
        costMapList.add(new CostMap("default", "mine1", 75.0));
        costMapList.add(new CostMap("default", "mine1a", 75.0));
        costMapList.add(new CostMap("default", "mine2", 75.0));
        costMapList.add(new CostMap("default", "mine3", 75.0));
        costMapList.add(new CostMap("default", "private", 75.0));

        costMapList.add(new CostMap("linklocal", "linklocal", 1.0));

        costMapList.add(new CostMap("loopback", "loopback", 0.0));

        costMapList.add(new CostMap("mine", "default", 75.0));
        costMapList.add(new CostMap("mine", "mine", 1.0));
        costMapList.add(new CostMap("mine", "mine1", 15.0));
        costMapList.add(new CostMap("mine", "mine1a", 15.0));
        costMapList.add(new CostMap("mine", "mine2", 15.0));
        costMapList.add(new CostMap("mine", "mine3", 15.0));
        costMapList.add(new CostMap("mine", "peer1", 30.0));
        costMapList.add(new CostMap("mine", "peer2", 30.0));
        costMapList.add(new CostMap("mine", "tran1", 50.0));
        costMapList.add(new CostMap("mine", "tran2", 50.0));

        costMapList.add(new CostMap("mine1", "default", 75.0));
        costMapList.add(new CostMap("mine1", "mine", 15.0));
        costMapList.add(new CostMap("mine1", "mine1", 1.0));
        costMapList.add(new CostMap("mine1", "mine1a", 2.5));
        costMapList.add(new CostMap("mine1", "mine2", 5.0));
        costMapList.add(new CostMap("mine1", "mine3", 7.0));
        costMapList.add(new CostMap("mine1", "peer1", 20.0));
        costMapList.add(new CostMap("mine1", "peer2", 25.0));
        costMapList.add(new CostMap("mine1", "tran1", 40.0));
        costMapList.add(new CostMap("mine1", "tran2", 45.0));

        costMapList.add(new CostMap("mine1a", "default", 75.0));
        costMapList.add(new CostMap("mine1a", "mine", 15.0));
        costMapList.add(new CostMap("mine1a", "mine1", 2.0));
        costMapList.add(new CostMap("mine1a", "mine1a", 1.0));
        costMapList.add(new CostMap("mine1a", "mine2", 7.0));
        costMapList.add(new CostMap("mine1a", "mine3", 9.0));
        costMapList.add(new CostMap("mine1a", "peer1", 22.0));
        costMapList.add(new CostMap("mine1a", "peer2", 24.0));
        costMapList.add(new CostMap("mine1a", "tran1", 42.0));
        costMapList.add(new CostMap("mine1a", "tran2", 48.0));

        costMapList.add(new CostMap("mine2", "default", 75.0));
        costMapList.add(new CostMap("mine2", "mine", 15.0));
        costMapList.add(new CostMap("mine2", "mine1", 5.5));
        costMapList.add(new CostMap("mine2", "mine1a", 7.0));
        costMapList.add(new CostMap("mine2", "mine2", 1.0));
        costMapList.add(new CostMap("mine2", "mine3", 6.0));
        costMapList.add(new CostMap("mine2", "peer1", 23.0));
        costMapList.add(new CostMap("mine2", "peer2", 25.0));
        costMapList.add(new CostMap("mine2", "tran1", 43.0));
        costMapList.add(new CostMap("mine2", "tran2", 46.0));

        costMapList.add(new CostMap("mine3", "default", 75.0));
        costMapList.add(new CostMap("mine3", "mine", 15.0));
        costMapList.add(new CostMap("mine3", "mine1", 7.0));
        costMapList.add(new CostMap("mine3", "mine1a", 9.0));
        costMapList.add(new CostMap("mine3", "mine2", 6.0));
        costMapList.add(new CostMap("mine3", "mine3", 1.0));
        costMapList.add(new CostMap("mine3", "peer1", 25.0));
        costMapList.add(new CostMap("mine3", "peer2", 28.0));
        costMapList.add(new CostMap("mine3", "tran1", 45.0));
        costMapList.add(new CostMap("mine3", "tran2", 49.0));

        costMapList.add(new CostMap("peer1", "mine", 30.0));
        costMapList.add(new CostMap("peer1", "mine1", 20.0));
        costMapList.add(new CostMap("peer1", "mine1a", 22.0));
        costMapList.add(new CostMap("peer1", "mine2", 23.0));
        costMapList.add(new CostMap("peer1", "mine3", 25.0));
        costMapList.add(new CostMap("peer1", "peer1", 1.0));

        costMapList.add(new CostMap("peer2", "mine", 30.0));
        costMapList.add(new CostMap("peer2", "mine1", 25.0));
        costMapList.add(new CostMap("peer2", "mine1a", 24.0));
        costMapList.add(new CostMap("peer2", "mine2", 25.0));
        costMapList.add(new CostMap("peer2", "mine3", 28.0));
        costMapList.add(new CostMap("peer2", "peer2", 1.0));

        costMapList.add(new CostMap("private", "default", 75.0));
        costMapList.add(new CostMap("private", "private", 1.0));

        costMapList.add(new CostMap("tran1", "mine", 50.0));
        costMapList.add(new CostMap("tran1", "mine1", 40.0));
        costMapList.add(new CostMap("tran1", "mine1a", 42.0));
        costMapList.add(new CostMap("tran1", "mine2", 43.0));
        costMapList.add(new CostMap("tran1", "mine3", 45.0));
        costMapList.add(new CostMap("tran1", "tran1", 1.0));

        costMapList.add(new CostMap("tran2", "mine", 50.0));
        costMapList.add(new CostMap("tran2", "mine1", 45.0));
        costMapList.add(new CostMap("tran2", "mine1a", 48.0));
        costMapList.add(new CostMap("tran2", "mine2", 46.0));
        costMapList.add(new CostMap("tran2", "mine3", 49.0));
        costMapList.add(new CostMap("tran2", "tran2", 1.0));

        return true;
    }

    public static boolean generateDefaultRoutingcostTestSrcMap(List<CostMap> costMapList)// generateDefaultRoutingcostMap
    {
        costMapList.clear();

        costMapList.add(new CostMap("default", "default", 1.0));
        costMapList.add(new CostMap("default", "mine", 75.0));
        costMapList.add(new CostMap("default", "mine1", 75.0));
        costMapList.add(new CostMap("default", "mine1a", 75.0));
        costMapList.add(new CostMap("default", "mine2", 75.0));
        costMapList.add(new CostMap("default", "mine3", 75.0));
        costMapList.add(new CostMap("default", "private", 75.0));
        return true;
    }

    public static boolean generateDefaultRoutingCostTestDstMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("default", "default", 1.0));
        costMapList.add(new CostMap("mine", "default", 75.0));
        costMapList.add(new CostMap("mine1", "default", 75.0));
        costMapList.add(new CostMap("mine1a", "default", 75.0));
        costMapList.add(new CostMap("mine2", "default", 75.0));
        costMapList.add(new CostMap("mine3", "default", 75.0));
        costMapList.add(new CostMap("private", "default", 75.0));

        return true;
    }

    public static boolean generateDefaultRoutingcostConstraintsMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("mine", "peer1", 30.0));
        costMapList.add(new CostMap("mine", "peer2", 30.0));

        costMapList.add(new CostMap("mine1", "peer1", 20.0));
        costMapList.add(new CostMap("mine1", "peer2", 25.0));

        costMapList.add(new CostMap("mine1a", "peer1", 22.0));
        costMapList.add(new CostMap("mine1a", "peer2", 24.0));

        costMapList.add(new CostMap("mine2", "peer1", 23.0));
        costMapList.add(new CostMap("mine2", "peer2", 25.0));

        costMapList.add(new CostMap("mine3", "peer1", 25.0));
        costMapList.add(new CostMap("mine3", "peer2", 28.0));

        costMapList.add(new CostMap("peer1", "mine", 30.0));
        costMapList.add(new CostMap("peer1", "mine1", 20.0));
        costMapList.add(new CostMap("peer1", "mine1a", 22.0));
        costMapList.add(new CostMap("peer1", "mine2", 23.0));
        costMapList.add(new CostMap("peer1", "mine3", 25.0));

        costMapList.add(new CostMap("peer2", "mine", 30.0));
        costMapList.add(new CostMap("peer2", "mine1", 25.0));
        costMapList.add(new CostMap("peer2", "mine1a", 24.0));
        costMapList.add(new CostMap("peer2", "mine2", 25.0));
        costMapList.add(new CostMap("peer2", "mine3", 28.0));

        return true;
    }

    public static boolean generateDefaultHopcountMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("default", "default", 1.0));
        costMapList.add(new CostMap("default", "mine", 10.0));
        costMapList.add(new CostMap("default", "mine1", 10.0));
        costMapList.add(new CostMap("default", "mine1a", 10.0));
        costMapList.add(new CostMap("default", "mine2", 10.0));
        costMapList.add(new CostMap("default", "mine3", 10.0));
        costMapList.add(new CostMap("default", "private", 10.0));

        costMapList.add(new CostMap("linklocal", "linklocal", 1.0));

        costMapList.add(new CostMap("loopback", "loopback", 0.0));

        costMapList.add(new CostMap("mine", "default", 10.0));
        costMapList.add(new CostMap("mine", "mine", 1.0));
        costMapList.add(new CostMap("mine", "mine1", 3.0));
        costMapList.add(new CostMap("mine", "mine1a", 3.0));
        costMapList.add(new CostMap("mine", "mine2", 3.0));
        costMapList.add(new CostMap("mine", "mine3", 3.0));
        costMapList.add(new CostMap("mine", "peer1", 5.0));
        costMapList.add(new CostMap("mine", "peer2", 6.0));
        costMapList.add(new CostMap("mine", "tran1", 8.0));
        costMapList.add(new CostMap("mine", "tran2", 8.0));

        costMapList.add(new CostMap("mine1", "default", 10.0));
        costMapList.add(new CostMap("mine1", "mine", 3.0));
        costMapList.add(new CostMap("mine1", "mine1", 1.0));
        costMapList.add(new CostMap("mine1", "mine1a", 2));
        costMapList.add(new CostMap("mine1", "mine2", 2.0));
        costMapList.add(new CostMap("mine1", "mine3", 2.0));
        costMapList.add(new CostMap("mine1", "peer1", 4.0));
        costMapList.add(new CostMap("mine1", "peer2", 5.0));
        costMapList.add(new CostMap("mine1", "tran1", 6.0));
        costMapList.add(new CostMap("mine1", "tran2", 7.0));

        costMapList.add(new CostMap("mine1a", "default", 10.0));
        costMapList.add(new CostMap("mine1a", "mine", 3.0));
        costMapList.add(new CostMap("mine1a", "mine1", 2.0));
        costMapList.add(new CostMap("mine1a", "mine1a", 1.0));
        costMapList.add(new CostMap("mine1a", "mine2", 2.0));
        costMapList.add(new CostMap("mine1a", "mine3", 3.0));
        costMapList.add(new CostMap("mine1a", "peer1", 5.0));
        costMapList.add(new CostMap("mine1a", "peer2", 6.0));
        costMapList.add(new CostMap("mine1a", "tran1", 7.0));
        costMapList.add(new CostMap("mine1a", "tran2", 8.0));

        costMapList.add(new CostMap("mine2", "default", 10.0));
        costMapList.add(new CostMap("mine2", "mine", 3.0));
        costMapList.add(new CostMap("mine2", "mine1", 2));
        costMapList.add(new CostMap("mine2", "mine1a", 2.0));
        costMapList.add(new CostMap("mine2", "mine2", 1.0));
        costMapList.add(new CostMap("mine2", "mine3", 2.0));
        costMapList.add(new CostMap("mine2", "peer1", 4.0));
        costMapList.add(new CostMap("mine2", "peer2", 5.0));
        costMapList.add(new CostMap("mine2", "tran1", 6.0));
        costMapList.add(new CostMap("mine2", "tran2", 7.0));

        costMapList.add(new CostMap("mine3", "default", 10.0));
        costMapList.add(new CostMap("mine3", "mine", 3.0));
        costMapList.add(new CostMap("mine3", "mine1", 2.0));
        costMapList.add(new CostMap("mine3", "mine1a", 3.0));
        costMapList.add(new CostMap("mine3", "mine2", 2.0));
        costMapList.add(new CostMap("mine3", "mine3", 1.0));
        costMapList.add(new CostMap("mine3", "peer1", 4.0));
        costMapList.add(new CostMap("mine3", "peer2", 5.0));
        costMapList.add(new CostMap("mine3", "tran1", 6.0));
        costMapList.add(new CostMap("mine3", "tran2", 7.0));

        costMapList.add(new CostMap("peer1", "mine", 5.0));
        costMapList.add(new CostMap("peer1", "mine1", 4.0));
        costMapList.add(new CostMap("peer1", "mine1a", 5.0));
        costMapList.add(new CostMap("peer1", "mine2", 4.0));
        costMapList.add(new CostMap("peer1", "mine3", 4.0));
        costMapList.add(new CostMap("peer1", "peer1", 1.0));

        costMapList.add(new CostMap("peer2", "mine", 6.0));
        costMapList.add(new CostMap("peer2", "mine1", 5.0));
        costMapList.add(new CostMap("peer2", "mine1a", 6.0));
        costMapList.add(new CostMap("peer2", "mine2", 5.0));
        costMapList.add(new CostMap("peer2", "mine3", 5.0));
        costMapList.add(new CostMap("peer2", "peer2", 1.0));

        costMapList.add(new CostMap("private", "default", 10.0));
        costMapList.add(new CostMap("private", "private", 1.0));

        costMapList.add(new CostMap("tran1", "mine", 8.0));
        costMapList.add(new CostMap("tran1", "mine1", 6.0));
        costMapList.add(new CostMap("tran1", "mine1a", 7.0));
        costMapList.add(new CostMap("tran1", "mine2", 6.0));
        costMapList.add(new CostMap("tran1", "mine3", 6.0));
        costMapList.add(new CostMap("tran1", "tran1", 1.0));

        costMapList.add(new CostMap("tran2", "mine", 8.0));
        costMapList.add(new CostMap("tran2", "mine1", 7.0));
        costMapList.add(new CostMap("tran2", "mine1a", 8.0));
        costMapList.add(new CostMap("tran2", "mine2", 7.0));
        costMapList.add(new CostMap("tran2", "mine3", 7.0));
        costMapList.add(new CostMap("tran2", "tran2", 1.0));

        return true;
    }

    public static boolean generateDefaultHopcountTestSrcMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("default", "default", 1.0));
        costMapList.add(new CostMap("default", "mine", 10.0));
        costMapList.add(new CostMap("default", "mine1", 10.0));
        costMapList.add(new CostMap("default", "mine1a", 10.0));
        costMapList.add(new CostMap("default", "mine2", 10.0));
        costMapList.add(new CostMap("default", "mine3", 10.0));
        costMapList.add(new CostMap("default", "private", 10.0));
        return true;
    }

    public static boolean generateDefaultHopcountTestDstMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("default", "default", 1.0));
        costMapList.add(new CostMap("mine", "default", 10.0));
        costMapList.add(new CostMap("mine1", "default", 10.0));
        costMapList.add(new CostMap("mine1a", "default", 10.0));
        costMapList.add(new CostMap("mine2", "default", 10.0));
        costMapList.add(new CostMap("mine3", "default", 10.0));
        costMapList.add(new CostMap("private", "default", 10.0));

        return true;
    }

    public static boolean generateDefaultHopcountConstraintsMap(List<CostMap> costMapList) {
        costMapList.clear();

        // costMapList.add(new CostMap("default","default",1.0));
        costMapList.add(new CostMap("default", "mine", 10.0));
        costMapList.add(new CostMap("default", "mine1", 10.0));
        costMapList.add(new CostMap("default", "mine1a", 10.0));
        costMapList.add(new CostMap("default", "mine2", 10.0));
        costMapList.add(new CostMap("default", "mine3", 10.0));
        costMapList.add(new CostMap("default", "private", 10.0));

        // costMapList.add(new CostMap("default","default",1.0));
        costMapList.add(new CostMap("mine", "default", 10.0));
        costMapList.add(new CostMap("mine1", "default", 10.0));
        costMapList.add(new CostMap("mine1a", "default", 10.0));
        costMapList.add(new CostMap("mine2", "default", 10.0));
        costMapList.add(new CostMap("mine3", "default", 10.0));
        costMapList.add(new CostMap("private", "default", 10.0));

        return true;
    }

    public static boolean generateDefautTestNetworkMap(List<NetworkMap> networkMapList) {
        networkMapList.clear();

        List<String> iPV4ListB = new ArrayList<String>();
        iPV4ListB.add("127.0.0.0/8");
        List<String> iPV6ListB = new ArrayList<String>();
        iPV6ListB.add("::1/128");
        networkMapList.add(new NetworkMap("loopback", iPV4ListB, iPV6ListB));

        return true;
    }

    public static boolean generateAlternateNetworkMap(List<NetworkMap> networkMapList) {
        networkMapList.clear();

        List<String> iPV4List1 = new ArrayList<String>();
        iPV4List1.add("101.0.0.0/16");
        List<String> iPV6List1 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("dc1", iPV4List1, iPV6List1));

        List<String> iPV4List2 = new ArrayList<String>();
        iPV4List2.add("102.0.0.0/16");
        List<String> iPV6List2 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("dc2", iPV4List2, iPV6List2));

        List<String> iPV4List3 = new ArrayList<String>();
        iPV4List3.add("103.0.0.0/16");
        List<String> iPV6List3 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("dc3", iPV4List3, iPV6List3));

        List<String> iPV4List4 = new ArrayList<String>();
        iPV4List4.add("104.0.0.0/16");
        List<String> iPV6List4 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("dc4", iPV4List4, iPV6List4));

        List<String> iPV4List5 = new ArrayList<String>();
        iPV4List5.add("201.0.0.0/16");
        List<String> iPV6List5 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("user1", iPV4List5, iPV6List5));

        List<String> iPV4List6 = new ArrayList<String>();
        iPV4List6.add("202.0.0.0/16");
        List<String> iPV6List6 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("user2", iPV4List6, iPV6List6));

        List<String> iPV4List7 = new ArrayList<String>();
        iPV4List7.add("203.0.0.0/16");
        List<String> iPV6List7 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("user3", iPV4List7, iPV6List7));

        List<String> iPV4List8 = new ArrayList<String>();
        iPV4List8.add("204.0.0.0/16");
        List<String> iPV6List8 = new ArrayList<String>();
        networkMapList.add(new NetworkMap("user4", iPV4List8, iPV6List8));

        List<String> iPV4ListA = new ArrayList<String>();
        iPV4ListA.add("0.0.0.0/0");
        List<String> iPV6ListA = new ArrayList<String>();
        addNetworkToStringList(iPV6ListA, "::0/0");
        networkMapList.add(new NetworkMap("default", iPV4ListA, iPV6ListA));

        List<String> iPV4ListB = new ArrayList<String>();
        iPV4ListB.add("127.0.0.0/8");
        List<String> iPV6ListB = new ArrayList<String>();
        addNetworkToStringList(iPV6ListB, "::1/128");
        networkMapList.add(new NetworkMap("loopback", iPV4ListB, iPV6ListB));

        List<String> iPV4ListC = new ArrayList<String>();
        iPV4ListC.add("169.254.0.0/16");
        List<String> iPV6ListC = new ArrayList<String>();
        addNetworkToStringList(iPV6ListC, "FF80::/10");
        networkMapList.add(new NetworkMap("linklocal", iPV4ListC, iPV6ListC));

        List<String> iPV4ListD = new ArrayList<String>();
        iPV4ListD.add("10.0.0.0/8");
        iPV4ListD.add("172.16.0.0/12");
        iPV4ListD.add("192.168.0.0/16");
        List<String> iPV6ListD = new ArrayList<String>();
        addNetworkToStringList(iPV6ListD, "FC00::/7");
        networkMapList.add(new NetworkMap("private", iPV4ListD, iPV6ListD));

        return true;
    }

    public static boolean generateAlternateTestNetworkMap(List<NetworkMap> networkMapList) {
        networkMapList.clear();

        List<String> iPV4ListB = new ArrayList<String>();
        iPV4ListB.add("127.0.0.0/8");
        List<String> iPV6ListB = new ArrayList<String>();
        iPV6ListB.add("::1/128");
        networkMapList.add(new NetworkMap("loopback", iPV4ListB, iPV6ListB));

        return true;
    }

    public static boolean generateAlternateRoutingcostMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("dc1", "dc1", 0.0));
        costMapList.add(new CostMap("dc1", "dc2", 5.0));
        costMapList.add(new CostMap("dc1", "dc3", 5.0));
        costMapList.add(new CostMap("dc1", "dc4", 5.0));
        costMapList.add(new CostMap("dc1", "default", 50.0));
        costMapList.add(new CostMap("dc1", "user1", 10.0));
        costMapList.add(new CostMap("dc1", "user2", 20.0));
        costMapList.add(new CostMap("dc1", "user3", 30.0));
        costMapList.add(new CostMap("dc1", "user4", 40.0));

        costMapList.add(new CostMap("dc2", "dc1", 5.0));
        costMapList.add(new CostMap("dc2", "dc2", 0.0));
        costMapList.add(new CostMap("dc2", "dc3", 5.0));
        costMapList.add(new CostMap("dc2", "dc4", 5.0));
        costMapList.add(new CostMap("dc2", "default", 50.0));
        costMapList.add(new CostMap("dc2", "user1", 20.0));
        costMapList.add(new CostMap("dc2", "user2", 10.0));
        costMapList.add(new CostMap("dc2", "user3", 20.0));
        costMapList.add(new CostMap("dc2", "user4", 30.0));

        costMapList.add(new CostMap("dc3", "dc1", 5.0));
        costMapList.add(new CostMap("dc3", "dc2", 5.0));
        costMapList.add(new CostMap("dc3", "dc3", 0.0));
        costMapList.add(new CostMap("dc3", "dc4", 5.0));
        costMapList.add(new CostMap("dc3", "default", 50.0));
        costMapList.add(new CostMap("dc3", "user1", 30.0));
        costMapList.add(new CostMap("dc3", "user2", 20.0));
        costMapList.add(new CostMap("dc3", "user3", 10.0));
        costMapList.add(new CostMap("dc3", "user4", 20.0));

        costMapList.add(new CostMap("dc4", "dc1", 5.0));
        costMapList.add(new CostMap("dc4", "dc2", 5.0));
        costMapList.add(new CostMap("dc4", "dc3", 5.0));
        costMapList.add(new CostMap("dc4", "dc4", 0.0));
        costMapList.add(new CostMap("dc4", "default", 50.0));
        costMapList.add(new CostMap("dc4", "user1", 40.0));
        costMapList.add(new CostMap("dc4", "user2", 30.0));
        costMapList.add(new CostMap("dc4", "user3", 20.0));
        costMapList.add(new CostMap("dc4", "user4", 10.0));

        costMapList.add(new CostMap("default", "dc1", 50.0));
        costMapList.add(new CostMap("default", "dc2", 50.0));
        costMapList.add(new CostMap("default", "dc3", 50.0));
        costMapList.add(new CostMap("default", "dc4", 50.0));
        costMapList.add(new CostMap("default", "default", 0.0));

        costMapList.add(new CostMap("user1", "dc1", 10.0));
        costMapList.add(new CostMap("user1", "dc2", 20.0));
        costMapList.add(new CostMap("user1", "dc3", 30.0));
        costMapList.add(new CostMap("user1", "dc4", 40.0));
        costMapList.add(new CostMap("user1", "user1", 0.0));

        costMapList.add(new CostMap("user2", "dc1", 20.0));
        costMapList.add(new CostMap("user2", "dc2", 10.0));
        costMapList.add(new CostMap("user2", "dc3", 20.0));
        costMapList.add(new CostMap("user2", "dc4", 30.0));
        costMapList.add(new CostMap("user2", "user2", 0.0));

        costMapList.add(new CostMap("user3", "dc1", 30.0));
        costMapList.add(new CostMap("user3", "dc2", 20.0));
        costMapList.add(new CostMap("user3", "dc3", 10.0));
        costMapList.add(new CostMap("user3", "dc4", 20.0));
        costMapList.add(new CostMap("user3", "user3", 0.0));

        costMapList.add(new CostMap("user4", "dc1", 40.0));
        costMapList.add(new CostMap("user4", "dc2", 30.0));
        costMapList.add(new CostMap("user4", "dc3", 20.0));
        costMapList.add(new CostMap("user4", "dc4", 10.0));
        costMapList.add(new CostMap("user4", "user4", 0.0));

        return true;
    }

    public static boolean generateAlternateRoutingcostTestSrcMap(List<CostMap> costMapList)// generateDefaultRoutingcostMap
    {
        costMapList.clear();
        List<CostMap> costMapListTmp = new ArrayList<CostMap>();
        generateAlternateRoutingcostMap(costMapListTmp);
        for (CostMap loop : costMapListTmp) {
            if (loop.getSrcPID().intern() == "default".intern()) {
                try {
                    costMapList.add((CostMap) (loop.clone()));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean generateAlternateRoutingCostTestDstMap(List<CostMap> costMapList) {
        costMapList.clear();
        List<CostMap> costMapListTmp = new ArrayList<CostMap>();
        generateAlternateRoutingcostMap(costMapListTmp);
        for (CostMap loop : costMapListTmp) {
            if (loop.getDstPID().intern() == "default".intern()) {
                try {
                    costMapList.add((CostMap) (loop.clone()));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public static boolean generateAlternateRoutingcostConstraintsMap(List<CostMap> costMapList) {
        costMapList.clear();
        List<CostMap> costMapListTmp = new ArrayList<CostMap>();
        generateAlternateRoutingcostMap(costMapListTmp);
        for (CostMap loop : costMapListTmp) {
            double cost = loop.getCost();
            if ((cost >= 20.0) && (cost <= 30.0)) {
                try {
                    costMapList.add((CostMap) (loop.clone()));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public static boolean generateAlternateHopcountMap(List<CostMap> costMapList) {
        costMapList.clear();

        costMapList.add(new CostMap("dc1", "dc1", 0.0));
        costMapList.add(new CostMap("dc1", "dc2", 1.0));
        costMapList.add(new CostMap("dc1", "dc3", 1.0));
        costMapList.add(new CostMap("dc1", "dc4", 1.0));
        costMapList.add(new CostMap("dc1", "default", 8.0));
        costMapList.add(new CostMap("dc1", "user1", 3.0));
        costMapList.add(new CostMap("dc1", "user2", 4.0));
        costMapList.add(new CostMap("dc1", "user3", 5.0));
        costMapList.add(new CostMap("dc1", "user4", 6.0));

        costMapList.add(new CostMap("dc2", "dc1", 1.0));
        costMapList.add(new CostMap("dc2", "dc2", 0.0));
        costMapList.add(new CostMap("dc2", "dc3", 1.0));
        costMapList.add(new CostMap("dc2", "dc4", 1.0));
        costMapList.add(new CostMap("dc2", "default", 8.0));
        costMapList.add(new CostMap("dc2", "user1", 4.0));
        costMapList.add(new CostMap("dc2", "user2", 3.0));
        costMapList.add(new CostMap("dc2", "user3", 4.0));
        costMapList.add(new CostMap("dc2", "user4", 5.0));

        costMapList.add(new CostMap("dc3", "dc1", 1.0));
        costMapList.add(new CostMap("dc3", "dc2", 1.0));
        costMapList.add(new CostMap("dc3", "dc3", 0.0));
        costMapList.add(new CostMap("dc3", "dc4", 1.0));
        costMapList.add(new CostMap("dc3", "default", 8.0));
        costMapList.add(new CostMap("dc3", "user1", 5.0));
        costMapList.add(new CostMap("dc3", "user2", 4.0));
        costMapList.add(new CostMap("dc3", "user3", 3.0));
        costMapList.add(new CostMap("dc3", "user4", 4.0));

        costMapList.add(new CostMap("dc4", "dc1", 1.0));
        costMapList.add(new CostMap("dc4", "dc2", 1.0));
        costMapList.add(new CostMap("dc4", "dc3", 1.0));
        costMapList.add(new CostMap("dc4", "dc4", 0.0));
        costMapList.add(new CostMap("dc4", "default", 8.0));
        costMapList.add(new CostMap("dc4", "user1", 6.0));
        costMapList.add(new CostMap("dc4", "user2", 5.0));
        costMapList.add(new CostMap("dc4", "user3", 4.0));
        costMapList.add(new CostMap("dc4", "user4", 3.0));

        costMapList.add(new CostMap("default", "default", 0.0));
        costMapList.add(new CostMap("default", "dc1", 8.0));
        costMapList.add(new CostMap("default", "dc2", 8.0));
        costMapList.add(new CostMap("default", "dc3", 8.0));
        costMapList.add(new CostMap("default", "dc4", 8.0));

        costMapList.add(new CostMap("user1", "user1", 0.0));
        costMapList.add(new CostMap("user1", "dc1", 3.0));
        costMapList.add(new CostMap("user1", "dc2", 4.0));
        costMapList.add(new CostMap("user1", "dc3", 5.0));
        costMapList.add(new CostMap("user1", "dc4", 6.0));

        costMapList.add(new CostMap("user2", "user2", 0.0));
        costMapList.add(new CostMap("user2", "dc1", 4.0));
        costMapList.add(new CostMap("user2", "dc2", 3.0));
        costMapList.add(new CostMap("user2", "dc3", 4.0));
        costMapList.add(new CostMap("user2", "dc4", 5.0));

        costMapList.add(new CostMap("user3", "user3", 0.0));
        costMapList.add(new CostMap("user3", "dc1", 5.0));
        costMapList.add(new CostMap("user3", "dc2", 4.0));
        costMapList.add(new CostMap("user3", "dc3", 3.0));
        costMapList.add(new CostMap("user3", "dc4", 4.0));

        costMapList.add(new CostMap("user4", "user4", 0.0));
        costMapList.add(new CostMap("user4", "dc1", 6.0));
        costMapList.add(new CostMap("user4", "dc2", 5.0));
        costMapList.add(new CostMap("user4", "dc3", 4.0));
        costMapList.add(new CostMap("user4", "dc4", 3.0));

        return true;
    }

    public static boolean generateAlternateHopcountTestSrcMap(List<CostMap> costMapList)// generateDefaultRoutingcostMap
    {
        costMapList.clear();
        List<CostMap> costMapListTmp = new ArrayList<CostMap>();
        generateAlternateHopcountMap(costMapListTmp);
        for (CostMap loop : costMapListTmp) {
            if (loop.getSrcPID().intern() == "default".intern()) {
                try {
                    costMapList.add((CostMap) (loop.clone()));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean generateAlternateHopcountTestDstMap(List<CostMap> costMapList) {
        costMapList.clear();
        List<CostMap> costMapListTmp = new ArrayList<CostMap>();
        generateAlternateHopcountMap(costMapListTmp);
        for (CostMap loop : costMapListTmp) {
            if (loop.getDstPID().intern() == "default".intern()) {
                try {
                    costMapList.add((CostMap) (loop.clone()));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public static boolean generateAlternateHopcountConstraintsMap(List<CostMap> costMapList) {
        costMapList.clear();
        List<CostMap> costMapListTmp = new ArrayList<CostMap>();
        generateAlternateHopcountMap(costMapListTmp);
        for (CostMap loop : costMapListTmp) {
            double cost = loop.getCost();
            if ((cost >= 4.0) && (cost <= 11.0)) {
                try {
                    costMapList.add((CostMap) (loop.clone()));
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    private static void addNetworkToStringList(List<String> list, String network) {
        if (network.contains(":")) {
            list.add(IPv6Network.fromString(network).toString());
        } else {
            list.add(network);
        }
    }

    public static boolean checkDefaultNetworkMap(List<String> argsList, List<String> reasonList) {

        JSONObject jsonDefaultNetworkMap = new JSONObject();
        List<String> pids = new ArrayList<String>();
        List<String> pidNameList = new ArrayList<String>();
        List<String> pidResponseNameList = new ArrayList<String>();

        try {
            addNetworkToStringList(pids, "100.0.0.0/8");
            jsonDefaultNetworkMap.put("mine", pids);
            pids.clear();
            pidNameList.add("mine");
            addNetworkToStringList(pids, "100.0.0.0/10");
            jsonDefaultNetworkMap.put("mine1", pids);
            pids.clear();
            pidNameList.add("mine1");
            addNetworkToStringList(pids, "100.0.1.0/24");
            addNetworkToStringList(pids, "100.0.64.0/24");
            addNetworkToStringList(pids, "100.0.192.0/24");
            jsonDefaultNetworkMap.put("mine1a", pids);
            pids.clear();
            pidNameList.add("mine1a");
            addNetworkToStringList(pids, "100.64.0.0/10");
            jsonDefaultNetworkMap.put("mine2", pids);
            pids.clear();
            pidNameList.add("mine2");
            addNetworkToStringList(pids, "100.128.0.0/10");
            jsonDefaultNetworkMap.put("mine3", pids);
            pids.clear();
            pidNameList.add("mine3");
            addNetworkToStringList(pids, "128.0.0.0/16");
            addNetworkToStringList(pids, "130.0.0.0/16");
            pids.add(IPv6Network.fromString("2001:DB8:0000::/33").toString());
            jsonDefaultNetworkMap.put("peer1", pids);
            pids.clear();
            pidNameList.add("peer1");
            addNetworkToStringList(pids, "129.0.0.0/16");
            addNetworkToStringList(pids, "131.0.0.0/16");
            addNetworkToStringList(pids, "2001:DB8:8000::/33");
            jsonDefaultNetworkMap.put("peer2", pids);
            pids.clear();
            pidNameList.add("peer2");
            addNetworkToStringList(pids, "132.0.0.0/16");
            jsonDefaultNetworkMap.put("tran1", pids);
            pids.clear();
            pidNameList.add("tran1");
            addNetworkToStringList(pids, "135.0.0.0/16");
            jsonDefaultNetworkMap.put("tran2", pids);
            pids.clear();
            pidNameList.add("tran2");
            addNetworkToStringList(pids, "0.0.0.0/0");
            addNetworkToStringList(pids, "::0/0");
            jsonDefaultNetworkMap.put("default", pids);
            pids.clear();
            pidNameList.add("default");
            addNetworkToStringList(pids, "127.0.0.0/8");
            addNetworkToStringList(pids, "::1/128");
            jsonDefaultNetworkMap.put("loopback", pids);
            pids.clear();
            pidNameList.add("loopback");
            addNetworkToStringList(pids, "169.254.0.0/16");
            addNetworkToStringList(pids, "FF80::/10");
            jsonDefaultNetworkMap.put("linklocal", pids);
            pids.clear();
            pidNameList.add("linklocal");
            addNetworkToStringList(pids, "10.0.0.0/8");
            addNetworkToStringList(pids, "172.16.0.0/12");
            addNetworkToStringList(pids, "192.168.0.0/16");
            addNetworkToStringList(pids, "FC00::/7");
            jsonDefaultNetworkMap.put("private", pids);
            pids.clear();
            pidNameList.add("private");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VTag vTag;
        List<NetworkMap> networkMapList;
        String operationStatus;
        AltoClientNetworkMapSwapInfo networkMapInfo = new AltoClientNetworkMapSwapInfo();
        AltoClientNetworkMap altoClientNetworkMap = null;

        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1));
        altoClientNetworkMap.getInfo(networkMapInfo);

        vTag = networkMapInfo.getVTag();
        networkMapList = networkMapInfo.getNetworkMapList();
        operationStatus = networkMapInfo.getOperationStatus();
        for (NetworkMap loop : networkMapList) {
            // System.out.println(loop);
        }
        reasonList.clear();

        for (NetworkMap loop : networkMapList) {
            List<String> pid4List = loop.getIPV4List();
            List<String> pid6List = loop.getIPV6List();
            String pidName = loop.getPidID();
            pidResponseNameList.add(pidName);
            if (jsonDefaultNetworkMap.has(pidName)) {
                try {
                    JSONArray pidArray = jsonDefaultNetworkMap.getJSONArray(pidName);
                    List<String> pidList = new ArrayList<String>();
                    List<String> pid_response = new ArrayList<String>();
                    for (int i = 0; i < pidArray.length(); i++) {
                        pidList.add(pidArray.getString(i));
                    }
                    for (String loopa : pid4List) {
                        pid_response.add(loopa);
                    }
                    for (String loopa : pid6List) {
                        pid_response.add(loopa);
                    }
                    for (String ipAddress : pid_response) {
                        if (!pidList.contains(ipAddress)) {
                            reasonList.add("Error: There is an additional IP address block in PID[" + pidName
                                    + "], additional IP address block is [" + ipAddress + "].");
                        }
                    }
                    for (String ipAddress : pidList) {
                        if (!pid_response.contains(ipAddress)) {
                            reasonList.add("Error: There is an absent IP address block in PID[" + pidName
                                    + "], absent IP address block is [" + ipAddress + "].");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                reasonList.add("Error: There is an additional PID:" + "\tpid name:" + pidName + ".");
            }
        }

        for (String loop : pidNameList) {
            if (!pidResponseNameList.contains(loop))
                reasonList.add("Error: There is an absent PID in response:" + "\tpid name:" + loop + ".");
        }

        return true;
    }

    public static boolean checkDefaultRoutingcostMap(List<String> argsList, List<String> reasonList) {
        List<CostMap> DefaultcostMapList = new ArrayList<CostMap>();
        generateDefaultRoutingcostMap(DefaultcostMapList);

        List<VTag> vTagList;
        CostType costType;
        List<CostMap> ReponseCostMapList;
        String operationStatus;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;

        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1));
        altoClientCostMap.getInfo(costMapInfo);
        ReponseCostMapList = costMapInfo.getCostMapList();
        compareEqualCostMap(ReponseCostMapList, DefaultcostMapList, reasonList);

        return true;
    }
    /////////

    public static boolean checkAlternatetRoutingcostMap(List<String> argsList, List<String> reasonList) {
        List<CostMap> DefaultcostMapList = new ArrayList<CostMap>();
        generateAlternateRoutingcostMap(DefaultcostMapList);

        List<VTag> vTagList;
        CostType costType;
        List<CostMap> ReponseCostMapList;
        String operationStatus;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;

        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1));
        altoClientCostMap.getInfo(costMapInfo);
        ReponseCostMapList = costMapInfo.getCostMapList();
        compareEqualCostMap(ReponseCostMapList, DefaultcostMapList, reasonList);

        return true;
    }

    public static boolean checkAlternateNetworkMap(List<String> argsList, List<String> reasonList) {

        JSONObject jsonDefaultNetworkMap = new JSONObject();
        List<String> pids = new ArrayList<String>();
        List<String> pidNameList = new ArrayList<String>();
        List<String> pidResponseNameList = new ArrayList<String>();

        try {
            pids.add("101.0.0.0/16");
            jsonDefaultNetworkMap.put("dc1", pids);
            pids.clear();
            pidNameList.add("dc1");
            pids.add("102.0.0.0/16");
            jsonDefaultNetworkMap.put("dc2", pids);
            pids.clear();
            pidNameList.add("dc2");
            pids.add("103.0.0.0/16");
            ;
            jsonDefaultNetworkMap.put("dc3", pids);
            pids.clear();
            pidNameList.add("dc3");
            pids.add("104.0.0.0/16");
            jsonDefaultNetworkMap.put("dc4", pids);
            pids.clear();
            pidNameList.add("dc4");

            pids.add("201.0.0.0/16");
            jsonDefaultNetworkMap.put("user1", pids);
            pids.clear();
            pidNameList.add("user1");
            pids.add("202.0.0.0/16");
            jsonDefaultNetworkMap.put("user2", pids);
            pids.clear();
            pidNameList.add("user2");
            pids.add("203.0.0.0/16");
            jsonDefaultNetworkMap.put("user3", pids);
            pids.clear();
            pidNameList.add("user3");
            pids.add("204.0.0.0/16");
            jsonDefaultNetworkMap.put("user4", pids);
            pids.clear();
            pidNameList.add("user4");

            pids.add("0.0.0.0/0");
            addNetworkToStringList(pids, "::0/0");
            jsonDefaultNetworkMap.put("default", pids);
            pids.clear();
            pidNameList.add("default");
            pids.add("127.0.0.0/8");
            addNetworkToStringList(pids, "::1/128");
            jsonDefaultNetworkMap.put("loopback", pids);
            pids.clear();
            pidNameList.add("loopback");
            pids.add("169.254.0.0/16");
            addNetworkToStringList(pids, "FF80::/10");
            jsonDefaultNetworkMap.put("linklocal", pids);
            pids.clear();
            pidNameList.add("linklocal");
            pids.add("10.0.0.0/8");
            pids.add("172.16.0.0/12");
            pids.add("192.168.0.0/16");
            addNetworkToStringList(pids, "FC00::/7");
            jsonDefaultNetworkMap.put("private", pids);
            pids.clear();
            pidNameList.add("private");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VTag vTag;
        List<NetworkMap> networkMapList;
        String operationStatus;
        AltoClientNetworkMapSwapInfo networkMapInfo = new AltoClientNetworkMapSwapInfo();
        AltoClientNetworkMap altoClientNetworkMap = null;

        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1));
        altoClientNetworkMap.getInfo(networkMapInfo);

        vTag = networkMapInfo.getVTag();
        networkMapList = networkMapInfo.getNetworkMapList();
        operationStatus = networkMapInfo.getOperationStatus();
        for (NetworkMap loop : networkMapList) {
            // System.out.println(loop);
        }
        reasonList.clear();

        for (NetworkMap loop : networkMapList) {
            List<String> pid4List = loop.getIPV4List();
            List<String> pid6List = loop.getIPV6List();
            String pidName = loop.getPidID();
            pidResponseNameList.add(pidName);
            if (jsonDefaultNetworkMap.has(pidName)) {
                try {
                    JSONArray pidArray = jsonDefaultNetworkMap.getJSONArray(pidName);
                    List<String> pidList = new ArrayList<String>();
                    List<String> pid_response = new ArrayList<String>();
                    for (int i = 0; i < pidArray.length(); i++) {
                        pidList.add(pidArray.getString(i));
                    }
                    for (String loopa : pid4List) {
                        pid_response.add(loopa);
                    }
                    for (String loopa : pid6List) {
                        pid_response.add(loopa);
                    }
                    for (String ipAddress : pid_response) {
                        if (!pidList.contains(ipAddress)) {
                            reasonList.add("Error: There is an additional IP address block in PID[" + pidName
                                    + "], additional IP address block is [" + ipAddress + "].");
                        }
                    }
                    for (String ipAddress : pidList) {
                        if (!pid_response.contains(ipAddress)) {
                            reasonList.add("Error: There is an absent IP address block in PID[" + pidName
                                    + "], absent IP address block is [" + ipAddress + "].");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                reasonList.add("Error: There is an additional PID:" + "\tpid name:" + pidName + ".");
            }
        }

        return true;
    }

    public static boolean checkDefaultHopcountMap(List<String> argsList, List<String> reasonList) {
        List<CostMap> defaultOrdCostMap = new ArrayList<CostMap>();
        generateDefaultHopcountMap(defaultOrdCostMap);
        List<VTag> vTagList;
        CostType costType;
        List<CostMap> costMapList;
        String operationStatus;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
        CostType postCostType;

        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1));
        altoClientCostMap.getInfo(costMapInfo);

        costMapList = costMapInfo.getCostMapList();
        reasonList.clear();

        List<String> srcs = new ArrayList<String>();
        compareEqualCostMap(costMapList, defaultOrdCostMap, reasonList);

        return true;
    }

    public static boolean checkAlternateHopcountMap(List<String> argsList, List<String> reasonList) {
        List<CostMap> defaultOrdCostMap = new ArrayList<CostMap>();
        generateAlternateHopcountMap(defaultOrdCostMap);
        List<VTag> vTagList;
        CostType costType;
        List<CostMap> costMapList;
        String operationStatus;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
        CostType postCostType;

        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1));
        altoClientCostMap.getInfo(costMapInfo);

        costMapList = costMapInfo.getCostMapList();
        reasonList.clear();

        List<String> srcs = new ArrayList<String>();
        compareEqualCostMap(costMapList, defaultOrdCostMap, reasonList);

        return true;
    }

    public static boolean checkEPPMap(List<String> argsList, List<String> reasonList) {

        class EPP {
            public String ip;
            public String val;

            EPP(String ip, String val) {
                this.ip = ip;
                this.val = val;
            }

        }
        ;
        List<VTag> vTagList;
        List<EPProp> endPointPropList;

        AltoClientEPPropSwapInfo EPPInfo = new AltoClientEPPropSwapInfo();
        AltoClientEndPointProp altoClientEndPointProp;

        List<String> properties = new ArrayList<String>();
        List<String> endpoints = new ArrayList<String>();
        properties.add("priv:ietf-type");
        // properties.add("alt-network.pid");
        String ip1 = "ipv4:100.0.1.25";
        String ip2 = "ipv4:128.0.1.25";
        String ip3 = "ipv6:2001:DB8:2::";
        String ip4 = "ipv4:132.0.1.25";
        String ip5 = "ipv4:135.0.1.25";
        String val1 = "mine";
        String val2 = "peer";
        String val3 = "peer";
        String val4 = "transit";
        String val5 = "transit";
        JSONObject jsonDefaultEPP = new JSONObject();
        endpoints.add(ip1);
        endpoints.add(ip2);
        endpoints.add(ip3);
        endpoints.add(ip4);
        endpoints.add(ip5);

        altoClientEndPointProp = new AltoClientEndPointProp(argsList.get(0), argsList.get(1), properties, endpoints);
        altoClientEndPointProp.getInfo(EPPInfo);
        vTagList = EPPInfo.getVTagList();
        endPointPropList = EPPInfo.getEndPointPropList();

        List<EPP> responseEPP = new ArrayList<EPP>();
        List<EPP> defaultEPP = new ArrayList<EPP>();
        defaultEPP.add(new EPP(ip1, val1));
        defaultEPP.add(new EPP(ip2, val2));
        defaultEPP.add(new EPP(ip3, val3));
        defaultEPP.add(new EPP(ip4, val4));
        defaultEPP.add(new EPP(ip5, val5));

        for (EPProp loop : endPointPropList) {
            responseEPP.add(new EPP(loop.getEndPointID(), loop.getPropertiesValue()));
        }
        reasonList.add("The Endpint properties value check is here.");
        boolean bAllFlag = false;

        List<String> responseID = new ArrayList<String>();
        List<String> defaultID = new ArrayList<String>();
        for (EPP loopResponse : responseEPP) {
            responseID.add(loopResponse.ip);
        }
        for (EPP loopResponse : defaultEPP) {
            defaultID.add(loopResponse.ip);
        }
        for (String loopResponse : responseID) {
            if (!(defaultID.contains(loopResponse))) {
                reasonList.add("Error:There is an additonal Endpoint Properties value for " + loopResponse);
                bAllFlag = true;
            }
        }
        for (String loopResponse : defaultID) {
            if (!(responseID.contains(loopResponse))) {
                reasonList.add("Error:There is an absent Endpoint Properties value for " + loopResponse);
                bAllFlag = true;
            }
        }
        for (EPP loopResponse : responseEPP) {
            for (EPP loopDefault : defaultEPP) {
                if (loopResponse.ip.intern() == loopDefault.ip.intern()) {
                    if (loopResponse.val.intern() != loopDefault.val.intern()) {
                        reasonList
                                .add("Error:There is an Endpoint Properties value not consistent with Figure 7. The IP address is "
                                        + loopResponse.ip + " value is " + loopResponse.val + ". ");
                        bAllFlag = true;
                    }
                    break;
                }
            }
        }

        if (bAllFlag == false) {
            reasonList.add("The Tested server provides Values for \"priv:ietf-type\" consistent with Figure 7.");
        }

        return true;
    }

    public static boolean compareNetworkMapItem(NetworkMap aNetworkMap, NetworkMap bNetworkMap) {
        List<String> aPid4List = aNetworkMap.getIPV4List();
        List<String> aPid6List = aNetworkMap.getIPV6List();
        String aPidName = aNetworkMap.getPidID();
        List<String> bPid4List = bNetworkMap.getIPV4List();
        List<String> bPid6List = bNetworkMap.getIPV6List();
        String bPidName = bNetworkMap.getPidID();
        boolean fourEqual = true;
        boolean sixEqual = true;
        for (String loop : aPid4List) {
            if (!bPid4List.contains(loop)) {
                fourEqual = false;
                break;
            }
        }

        for (String loop : bPid4List) {
            if (!aPid4List.contains(loop)) {
                fourEqual = false;
                break;
            }
        }
        for (String loop : aPid6List) {
            if (!bPid6List.contains(loop)) {
                sixEqual = false;
                break;
            }
        }

        for (String loop : bPid6List) {
            if (!aPid6List.contains(loop)) {
                sixEqual = false;
                break;
            }
        }

        if ((fourEqual == true) && (sixEqual == true) && (aPidName.intern() == bPidName.intern())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareEqualNetworkMap(List<NetworkMap> aNetworkMapList, List<NetworkMap> bNetworkMapList,
            List<String> reasonList) {
        boolean bFlag = false;
        boolean bcompare = false;

        for (NetworkMap aLoop : aNetworkMapList) {
            bFlag = false;
            for (NetworkMap bLoop : bNetworkMapList) {
                bcompare = compareNetworkMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an additional network map item.\t" + aLoop + ".");
            }
        }

        for (NetworkMap bLoop : bNetworkMapList) {
            bFlag = false;
            for (NetworkMap aLoop : aNetworkMapList) {
                bcompare = compareNetworkMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an absent network map item.\t" + bLoop + ".");
            }
        }

        return true;
    }

    public static boolean defaultFilteredNetworkMapTest(List<String> argsList, List<String> reasonList) {
        // including five test
        reasonList.clear();
        boolean bFlag = false;

        List<NetworkMap> NetworkMapList = new ArrayList<NetworkMap>();
        generateDefautNetworkMap(NetworkMapList);

        List<NetworkMap> networkMapList;
        AltoClientNetworkMapSwapInfo networkMapInfo = new AltoClientNetworkMapSwapInfo();
        AltoClientNetworkMap altoClientNetworkMap = null;
        List<String> pids = new ArrayList<String>();
        List<String> address_types = new ArrayList<String>();

        reasonList.add("");
        // first empty pids
        pids.clear();
        // altoClientNetworkMap=new
        // AltoClientNetworkMap("alto.alcatel-lucent.com:8000","def-network/network/filtered",pids,null);
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, null);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.4 A-----4.1.A Filtered Network Map Tests."
                + "Empty PIDs and Omitted \"address-types\" array.");
        reasonList.add("\tcheck manually");
        compareEqualNetworkMap(networkMapList, NetworkMapList, reasonList);

        pids.clear();
        address_types.clear();
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, address_types);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.4 A----- 4.1.B Filtered Network Map Tests."
                + "Empty PIDs and Empty \"address-types\" array.");
        reasonList.add("\tcheck manually");
        compareEqualNetworkMap(networkMapList, NetworkMapList, reasonList);

        reasonList.add("");
        pids.clear();
        address_types.clear();
        address_types.add("ipv6");
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, address_types);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("\tresult 1.1.5 ------ 4.1.C Filtered Network Map Tests." + "Empty PIDs and IPV6 address only.");
        bFlag = false;
        for (NetworkMap loop : networkMapList) {
            if (loop.getIPV6List().isEmpty()) {
                bFlag = true;
                reasonList.add("\t Error:there are PIDs with empty IPv6List " + loop.getPidID() + ". ");
            }
        }

        bFlag = false;
        for (NetworkMap loop : networkMapList) {
            if (!loop.getIPV4List().isEmpty()) {
                bFlag = true;
                reasonList.add("\tError: there are PIDs PIDs with ipv4." + loop.getPidID() + "\t" + loop.getIPV4List());
            }
        }
        if (bFlag == false) {
            reasonList.add("\tresult 1.1.5 ------ONLY return PIDs with ipv6.");
        }

        reasonList.add("");
        pids.clear();
        pids.add("invalidPIDAAA1");
        pids.add("invalidPIDAAA2");
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, null);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.6 ------4.1.D Filtered Network Map Tests."
                + "PIDs array with one or more non-existent PID names array.");
        if (networkMapList.size() > 0) {
            for (NetworkMap loop : networkMapList)
                reasonList.add("\tError: return PIDs." + loop.toStringLinked());
        } else {
            reasonList.add("\tresult 1.1.6 ------The test server does not return any PIDs.");
        }
        reasonList.add("");
        pids.clear();
        pids.add("invalidPIDAAA1");
        pids.add("loopback");
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, null);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        NetworkMapList.clear();
        generateDefautTestNetworkMap(NetworkMapList);
        reasonList.add("result 1.1.7 ------4.1.E Filtered Network Map Tests."
                + "PIDs array with one or more non-existent PID names array.");
        compareEqualNetworkMap(networkMapList, NetworkMapList, reasonList);
        return true;
    }

    public static boolean alternateFilteredNetworkMapTest(List<String> argsList, List<String> reasonList) {
        // including five test
        reasonList.clear();
        boolean bFlag = false;

        List<NetworkMap> NetworkMapList = new ArrayList<NetworkMap>();
        generateAlternateNetworkMap(NetworkMapList);

        List<NetworkMap> networkMapList;
        AltoClientNetworkMapSwapInfo networkMapInfo = new AltoClientNetworkMapSwapInfo();
        AltoClientNetworkMap altoClientNetworkMap = null;
        List<String> pids = new ArrayList<String>();
        List<String> address_types = new ArrayList<String>();

        reasonList.add("");
        // first empty pids
        pids.clear();
        // altoClientNetworkMap=new
        // AltoClientNetworkMap("alto.alcatel-lucent.com:8000","def-network/network/filtered",pids,null);
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, null);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.8 A-----4.1.A Filtered Network Map Tests."
                + "Empty PIDs and Omitted \"address-types\" array.");
        reasonList.add("\tcheck manually");
        compareEqualNetworkMap(networkMapList, NetworkMapList, reasonList);

        reasonList.add("");
        pids.clear();
        address_types.clear();
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, address_types);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.8 A----- 4.1.B Filtered Network Map Tests."
                + "Empty PIDs and Empty \"address-types\" array.");
        reasonList.add("\tcheck manually");
        compareEqualNetworkMap(networkMapList, NetworkMapList, reasonList);

        reasonList.add("");
        pids.clear();
        address_types.clear();
        address_types.add("ipv6");
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, address_types);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.9 ------ 4.1.C Filtered Network Map Tests." + "Empty PIDs and IPV6 address only.");
        bFlag = false;
        for (NetworkMap loop : networkMapList) {
            if (loop.getIPV6List().isEmpty()) {
                bFlag = true;
                reasonList.add("\t Error:there are PIDs with empty IPv6List " + loop.getPidID() + ". ");
            }
        }
        bFlag = false;
        for (NetworkMap loop : networkMapList) {
            if (!loop.getIPV4List().isEmpty()) {
                bFlag = true;
                reasonList.add("\tError: return PIDs with ipv4." + loop.getIPV4List());
            }
        }
        if (bFlag == false) {
            reasonList.add("result 1.1.9 ------ONLY return PIDs with ipv6.");
        }

        reasonList.add("");
        pids.clear();
        pids.add("invalidPIDAAA1");
        pids.add("invalidPIDAAA2");
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, null);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        reasonList.add("result 1.1.10 ------4.1.D Filtered Network Map Tests."
                + "PIDs array with one or more non-existent PID names array.");
        if (networkMapList.size() > 0) {
            for (NetworkMap loop : networkMapList)
                reasonList.add("\tError: return PIDs." + loop.toStringLinked());
        } else {
            reasonList.add("\tresult 1.1.10 ------The test server does not return any PIDs.");
        }

        reasonList.add("");
        pids.clear();
        pids.add("invalidPIDAAA1");
        pids.add("loopback");
        altoClientNetworkMap = new AltoClientNetworkMap(argsList.get(0), argsList.get(1), pids, null);
        altoClientNetworkMap.getInfo(networkMapInfo);
        networkMapList = networkMapInfo.getNetworkMapList();
        NetworkMapList.clear();
        generateAlternateTestNetworkMap(NetworkMapList);
        reasonList.add("result 1.1.11 ------4.1.E Filtered Network Map Tests."
                + "PIDs array with one or more non-existent PID names array.");
        compareEqualNetworkMap(networkMapList, NetworkMapList, reasonList);
        return true;
    }

    public static boolean compareCostMapItem(CostMap aCostMap, CostMap bCostMap) {
        String aSrc = aCostMap.getSrcPID();
        String aDst = aCostMap.getDstPID();
        double aCost = aCostMap.getCost();

        String bSrc = bCostMap.getSrcPID();
        String bDst = bCostMap.getDstPID();
        double bCost = bCostMap.getCost();

        if ((aSrc.intern() == bSrc.intern()) && (aDst.intern() == bDst.intern()) && (aCost == bCost)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareEqualCostMap(List<CostMap> aCostMapList, List<CostMap> bCostMapList,
            List<String> reasonList) {
        boolean bFlag = false;
        boolean bcompare = false;

        for (CostMap aLoop : aCostMapList) {
            bFlag = false;
            for (CostMap bLoop : bCostMapList) {
                bcompare = compareCostMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an additional cost map item.\t" + aLoop + ".");
            }
        }

        for (CostMap bLoop : bCostMapList) {
            bFlag = false;
            for (CostMap aLoop : aCostMapList) {
                bcompare = compareCostMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an absent cost map item.\t" + bLoop + ".");
            }
        }
        return true;
    }

    public static boolean filteredCostMapTestDefaultRoutingcost(List<String> argsList, List<String> reasonList) {
        // including five test
        reasonList.clear();
        boolean bFlag = false;

        List<CostMap> CostMapList = new ArrayList<CostMap>();
        List<String> srcPids = new ArrayList<String>();
        List<String> dstPids = new ArrayList<String>();
        List<String> constraints = new ArrayList<String>();
        List<CostMap> costMapList;

        CostType costType;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
        CostType postCostType = new CostType();

        postCostType.setName("rtg-num");
        postCostType.setMode("numerical");
        postCostType.setMetric("routingcost");

        generateDefaultRoutingcostMap(CostMapList);

        reasonList.add("4.2. Filtered Cost Map Tests");
        reasonList.add("result 1.1.20----4.2.A Filtered Cost Map Tests.Empty \"srcs\" and \"dsts\" arrays");

        srcPids.clear();
        dstPids.clear();
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.21----4.2.B Filtered Cost Map Tests.Empty \"srcs\" \"dsts\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateDefaultRoutingCostTestDstMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.22----4.2.C Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateDefaultRoutingcostTestSrcMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList
                .add("result 1.1.23----4.2.D Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        dstPids.add("invaliddefault");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 0) {
            reasonList.add("The tested server returns an empty cost map");
        } else {
            reasonList.add("Error:The tested server returns an non-empty cost map");
        }

        reasonList.add(
                "result 1.1.24----4.2.E Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid  and valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        srcPids.add("default");
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 1) {
            if ((costMapList.get(0).getSrcPID().intern() == "default")
                    && ((costMapList.get(0).getDstPID().intern() == "default"))) {
                reasonList.add("The tested server returns cost values only for valid PIDs");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }

        } else {
            if (costMapList.size() == 0) {
                reasonList.add("Error:The tested server returns no cost value. But there should be one. ");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }
        }

        reasonList.add(
                "result 1.1.25----4.2.F Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays and  constraints of [\"ge 20\",\"le 30\"]");
        srcPids.clear();
        dstPids.clear();
        constraints.clear();
        constraints.add("ge 20");
        constraints.add("le 30");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateDefaultRoutingcostConstraintsMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        return true;
    }

    public static boolean filteredCostMapTestDefaultHopcount(List<String> argsList, List<String> reasonList) {
        // including five test
        reasonList.clear();
        boolean bFlag = false;

        List<CostMap> CostMapList = new ArrayList<CostMap>();
        List<String> srcPids = new ArrayList<String>();
        List<String> dstPids = new ArrayList<String>();
        List<String> constraints = new ArrayList<String>();
        List<CostMap> costMapList;

        CostType costType;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
        CostType postCostType = new CostType();

        postCostType.setName("hop-num");
        postCostType.setMode("numerical");
        postCostType.setMetric("hopcount");

        generateDefaultHopcountMap(CostMapList);

        reasonList.add("4.2. Filtered Cost Map Tests hopcount");
        reasonList.add("result 1.1.26----4.2.A Filtered Cost Map Tests.Empty \"srcs\" and \"dsts\" arrays");

        srcPids.clear();
        dstPids.clear();
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.27----4.2.B Filtered Cost Map Tests.Empty \"srcs\" \"dsts\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateDefaultHopcountTestDstMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.28----4.2.C Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateDefaultHopcountTestSrcMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList
                .add("result 1.1.29----4.2.D Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        dstPids.add("invaliddefault");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 0) {
            reasonList.add("The tested server returns an empty cost map");
        } else {
            reasonList.add("Error:The tested server returns an non-empty cost map");
        }

        reasonList.add(
                "result 1.1.30----4.2.E Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid  and valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        srcPids.add("default");
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 1) {
            if ((costMapList.get(0).getSrcPID().intern() == "default")
                    && ((costMapList.get(0).getDstPID().intern() == "default"))) {
                reasonList.add("The tested server returns cost values only for valid PIDs");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }

        } else {
            if (costMapList.size() == 0) {
                reasonList.add("Error:The tested server returns no cost value. But there should be one. ");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }
        }

        reasonList.add(
                "result 1.1.31----4.2.F Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays and  constraints of [\"ge 9\",\"le 30\"]");
        srcPids.clear();
        dstPids.clear();
        constraints.clear();
        constraints.add("ge 9");
        constraints.add("le 30");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateDefaultHopcountConstraintsMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        return true;
    }

    public static boolean filteredCostMapTestAlternateRoutingcost(List<String> argsList, List<String> reasonList) {
        // including five test
        reasonList.clear();
        boolean bFlag = false;

        List<CostMap> CostMapList = new ArrayList<CostMap>();
        List<String> srcPids = new ArrayList<String>();
        List<String> dstPids = new ArrayList<String>();
        List<String> constraints = new ArrayList<String>();
        List<CostMap> costMapList;

        CostType costType;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
        CostType postCostType = new CostType();

        postCostType.setName("rtg-num");
        postCostType.setMode("numerical");
        postCostType.setMetric("routingcost");

        generateAlternateRoutingcostMap(CostMapList);

        reasonList.add("4.2. Filtered Cost Map Tests");
        reasonList.add("result 1.1.32----4.2.A Filtered Cost Map Tests.Empty \"srcs\" and \"dsts\" arrays");

        srcPids.clear();
        dstPids.clear();
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.33----4.2.B Filtered Cost Map Tests.Empty \"srcs\" \"dsts\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateAlternateRoutingCostTestDstMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.34----4.2.C Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateAlternateRoutingcostTestSrcMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList
                .add("result 1.1.35----4.2.D Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        dstPids.add("invaliddefault");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 0) {
            reasonList.add("The tested server returns an empty cost map");
        } else {
            reasonList.add("Error:The tested server returns an non-empty cost map");
        }

        reasonList.add(
                "result 1.1.36----4.2.E Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid  and valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        srcPids.add("default");
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 1) {
            if ((costMapList.get(0).getSrcPID().intern() == "default")
                    && ((costMapList.get(0).getDstPID().intern() == "default"))) {
                reasonList.add("The tested server returns cost values only for valid PIDs");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }

        } else {
            if (costMapList.size() == 0) {
                reasonList.add("Error:The tested server returns no cost value. But there should be one. ");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }
        }

        reasonList.add(
                "result 1.1.37----4.2.F Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays and  constraints of [\"ge 20\",\"le 30\"]");
        srcPids.clear();
        dstPids.clear();
        constraints.clear();
        constraints.add("ge 20");
        constraints.add("le 30");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateAlternateRoutingcostConstraintsMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        return true;
    }

    public static boolean filteredCostMapTestAlternateHopcount(List<String> argsList, List<String> reasonList) {
        // including five test
        reasonList.clear();
        boolean bFlag = false;

        List<CostMap> CostMapList = new ArrayList<CostMap>();
        List<String> srcPids = new ArrayList<String>();
        List<String> dstPids = new ArrayList<String>();
        List<String> constraints = new ArrayList<String>();
        List<CostMap> costMapList;

        CostType costType;
        AltoClientCostMapSwapInfo costMapInfo = new AltoClientCostMapSwapInfo();
        AltoClientCostMap altoClientCostMap;
        CostType postCostType = new CostType();

        postCostType.setName("hop-num");
        postCostType.setMode("numerical");
        postCostType.setMetric("hopcount");

        generateAlternateHopcountMap(CostMapList);

        reasonList.add("4.2. Filtered Cost Map Tests hopcount");
        reasonList.add("result 1.1.38----4.2.A Filtered Cost Map Tests.Empty \"srcs\" and \"dsts\" arrays");

        srcPids.clear();
        dstPids.clear();
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.39----4.2.B Filtered Cost Map Tests.Empty \"srcs\" \"dsts\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateAlternateHopcountTestDstMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList.add("result 1.1.40----4.2.C Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays with valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateAlternateHopcountTestSrcMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        reasonList
                .add("result 1.1.41----4.2.D Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        dstPids.add("invaliddefault");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 0) {
            reasonList.add("The tested server returns an empty cost map");
        } else {
            reasonList.add("Error:The tested server returns an non-empty cost map");
        }

        reasonList.add(
                "result 1.1.42----4.2.E Filtered Cost Map Tests.Both \"dsts\" \"srcs\" arrays with invalid  and valid PIDs");
        srcPids.clear();
        dstPids.clear();
        srcPids.add("invaliddefault");
        srcPids.add("default");
        dstPids.add("default");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        if (costMapList.size() == 1) {
            if ((costMapList.get(0).getSrcPID().intern() == "default")
                    && ((costMapList.get(0).getDstPID().intern() == "default"))) {
                reasonList.add("The tested server returns cost values only for valid PIDs");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }

        } else {
            if (costMapList.size() == 0) {
                reasonList.add("Error:The tested server returns no cost value. But there should be one. ");
            } else {
                for (CostMap loop : costMapList)
                    reasonList.add("Error:The tested server returns a cost value. " + loop.toStringLinked());
            }
        }

        reasonList.add(
                "result 1.1.43----4.2.F Filtered Cost Map Tests.Empty \"dsts\" \"srcs\" arrays and  constraints of [\"ge 4\",\"le 11\"]");
        srcPids.clear();
        dstPids.clear();
        constraints.clear();
        constraints.add("ge 4");
        constraints.add("le 11");
        altoClientCostMap = new AltoClientCostMap(argsList.get(0), argsList.get(1), postCostType, srcPids, dstPids,
                constraints);
        altoClientCostMap.getInfo(costMapInfo);
        costMapList = costMapInfo.getCostMapList();
        CostMapList.clear();
        generateAlternateHopcountConstraintsMap(CostMapList);
        compareEqualCostMap(costMapList, CostMapList, reasonList);

        return true;
    }

    public static boolean generateDefaultEPPMap(List<EPProp> EPPMapList, String type) {
        EPPMapList.clear();
        if (type.contains("default")) {

            EPPMapList.add(new EPProp("ipv4:0.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:10.1.2.3", "default", "private"));
            EPPMapList.add(new EPProp("ipv4:100.0.0.1", "default", "mine1"));
            EPPMapList.add(new EPProp("ipv4:100.0.1.1", "default", "mine1a"));
            EPPMapList.add(new EPProp("ipv4:100.0.192.1", "default", "mine1a"));
            EPPMapList.add(new EPProp("ipv4:100.0.64.1", "default", "mine1a"));
            EPPMapList.add(new EPProp("ipv4:100.130.0.1", "default", "mine3"));
            EPPMapList.add(new EPProp("ipv4:100.200.0.1", "default", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.75.0.1", "default", "mine2"));
            EPPMapList.add(new EPProp("ipv4:101.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:101.1.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:102.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:103.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:104.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:127.0.0.1", "default", "loopback"));
            EPPMapList.add(new EPProp("ipv4:127.255.255.255", "default", "loopback"));
            EPPMapList.add(new EPProp("ipv4:128.0.0.1", "default", "peer1"));
            EPPMapList.add(new EPProp("ipv4:129.0.0.1", "default", "peer2"));
            EPPMapList.add(new EPProp("ipv4:130.0.0.1", "default", "peer1"));
            EPPMapList.add(new EPProp("ipv4:131.0.0.1", "default", "peer2"));
            EPPMapList.add(new EPProp("ipv4:132.0.0.1", "default", "tran1"));
            EPPMapList.add(new EPProp("ipv4:135.0.0.1", "default", "tran2"));
            EPPMapList.add(new EPProp("ipv4:169.254.1.2", "default", "linklocal"));
            EPPMapList.add(new EPProp("ipv4:201.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:201.1.2.3", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:202.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:203.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:204.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv4:99.0.0.1", "default", "default"));
            EPPMapList.add(new EPProp("ipv6:::1", "default", "loopback"));
            EPPMapList.add(new EPProp("ipv6:::2", "default", "default"));
            EPPMapList.add(new EPProp("ipv6:2001:DB8::", "default", "peer1"));
            EPPMapList.add(new EPProp("ipv6:2001:DB8:8000::1", "default", "peer2"));
            EPPMapList.add(new EPProp("ipv6:FC00:1::", "default", "private"));
            EPPMapList.add(new EPProp("ipv6:FF80:1:2::", "default", "linklocal"));
        }
        if (type.contains("alternate")) {
            EPPMapList.add(new EPProp("ipv4:0.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:10.1.2.3", "alternate", "private"));
            EPPMapList.add(new EPProp("ipv4:100.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:100.0.1.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:100.0.192.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:100.0.64.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:100.130.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:100.200.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:100.75.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:101.0.0.1", "alternate", "dc1"));
            EPPMapList.add(new EPProp("ipv4:101.1.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:102.0.0.1", "alternate", "dc2"));
            EPPMapList.add(new EPProp("ipv4:103.0.0.1", "alternate", "dc3"));
            EPPMapList.add(new EPProp("ipv4:104.0.0.1", "alternate", "dc4"));
            EPPMapList.add(new EPProp("ipv4:127.0.0.1", "alternate", "loopback"));
            EPPMapList.add(new EPProp("ipv4:127.255.255.255", "alternate", "loopback"));
            EPPMapList.add(new EPProp("ipv4:128.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:129.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:130.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:131.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:132.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:135.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:169.254.1.2", "alternate", "linklocal"));
            EPPMapList.add(new EPProp("ipv4:201.0.0.1", "alternate", "user1"));
            EPPMapList.add(new EPProp("ipv4:201.1.2.3", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv4:202.0.0.1", "alternate", "user2"));
            EPPMapList.add(new EPProp("ipv4:203.0.0.1", "alternate", "user3"));
            EPPMapList.add(new EPProp("ipv4:204.0.0.1", "alternate", "user4"));
            EPPMapList.add(new EPProp("ipv4:99.0.0.1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv6:::1", "alternate", "loopback"));
            EPPMapList.add(new EPProp("ipv6:::2", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv6:2001:DB8::", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv6:2001:DB8:8000::1", "alternate", "default"));
            EPPMapList.add(new EPProp("ipv6:FC00:1::", "alternate", "private"));
            EPPMapList.add(new EPProp("ipv6:FF80:1:2::", "alternate", "linklocal"));
        }
        if (type.contains("ietf")) {

            // EPPMapList.add(new
            // EPProp("ipv4:0.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:10.1.2.3","priv:ietf-type","invalid"));
            EPPMapList.add(new EPProp("ipv4:100.0.0.1", "priv:ietf-type", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.0.1.1", "priv:ietf-type", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.0.192.1", "priv:ietf-type", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.0.64.1", "priv:ietf-type", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.130.0.1", "priv:ietf-type", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.200.0.1", "priv:ietf-type", "mine"));
            EPPMapList.add(new EPProp("ipv4:100.75.0.1", "priv:ietf-type", "mine"));
            // EPPMapList.add(new
            // EPProp("ipv4:101.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:101.1.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:102.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:103.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:104.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:127.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:127.255.255.255","priv:ietf-type","invalid"));
            EPPMapList.add(new EPProp("ipv4:128.0.0.1", "priv:ietf-type", "peer"));
            EPPMapList.add(new EPProp("ipv4:129.0.0.1", "priv:ietf-type", "peer"));
            EPPMapList.add(new EPProp("ipv4:130.0.0.1", "priv:ietf-type", "peer"));
            EPPMapList.add(new EPProp("ipv4:131.0.0.1", "priv:ietf-type", "peer"));
            EPPMapList.add(new EPProp("ipv4:132.0.0.1", "priv:ietf-type", "transit"));
            EPPMapList.add(new EPProp("ipv4:135.0.0.1", "priv:ietf-type", "transit"));
            // EPPMapList.add(new
            // EPProp("ipv4:169.254.1.2","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:201.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:201.1.2.3","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:202.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:203.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:204.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv4:99.0.0.1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv6:::1","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv6:::2","priv:ietf-type","invalid"));
            EPPMapList.add(new EPProp("ipv6:2001:DB8::", "priv:ietf-type", "peer"));
            EPPMapList.add(new EPProp("ipv6:2001:DB8:8000::1", "priv:ietf-type", "peer"));
            // EPPMapList.add(new
            // EPProp("ipv6:fc00:1::","priv:ietf-type","invalid"));
            // EPPMapList.add(new
            // EPProp("ipv6:ff80:1:2::","priv:ietf-type","invalid"));
        }
        return true;
    }

    public static boolean generateDefaulEPPMapIP(List<String> IPList) {
        IPList.clear();

        IPList.add("ipv4:0.0.0.1");
        IPList.add("ipv4:10.1.2.3");
        IPList.add("ipv4:100.0.0.1");
        IPList.add("ipv4:100.0.1.1");
        IPList.add("ipv4:100.0.192.1");
        IPList.add("ipv4:100.0.64.1");
        IPList.add("ipv4:100.130.0.1");
        IPList.add("ipv4:100.200.0.1");
        IPList.add("ipv4:100.75.0.1");
        IPList.add("ipv4:101.0.0.1");
        IPList.add("ipv4:101.1.0.1");
        IPList.add("ipv4:102.0.0.1");
        IPList.add("ipv4:103.0.0.1");
        IPList.add("ipv4:104.0.0.1");
        IPList.add("ipv4:127.0.0.1");
        IPList.add("ipv4:127.255.255.255");
        IPList.add("ipv4:128.0.0.1");
        IPList.add("ipv4:129.0.0.1");
        IPList.add("ipv4:130.0.0.1");
        IPList.add("ipv4:131.0.0.1");
        IPList.add("ipv4:132.0.0.1");
        IPList.add("ipv4:135.0.0.1");
        IPList.add("ipv4:169.254.1.2");
        IPList.add("ipv4:201.0.0.1");
        IPList.add("ipv4:201.1.2.3");
        IPList.add("ipv4:202.0.0.1");
        IPList.add("ipv4:203.0.0.1");
        IPList.add("ipv4:204.0.0.1");
        IPList.add("ipv4:99.0.0.1");
        IPList.add("ipv6:::1");
        IPList.add("ipv6:::2");
        IPList.add("ipv6:2001:DB8::");
        IPList.add("ipv6:2001:DB8:8000::1");
        IPList.add("ipv6:FC00:1::");
        IPList.add("ipv6:FF80:1:2::");

        return true;
    }

    public static boolean compareEPPMapItem(EPProp aEPProp, EPProp bEPProp) {
        String aPid = aEPProp.getEndPointID();
        String aProperties = aEPProp.getProperties();
        String aValue = aEPProp.getPropertiesValue();

        String bPid = bEPProp.getEndPointID();
        String bProperties = bEPProp.getProperties();
        String bValue = bEPProp.getPropertiesValue();

        if ((aPid.intern() == bPid.intern()) && (aProperties.intern() == bProperties.intern())
                && (aValue.intern() == bValue.intern())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareEPPropMap(List<EPProp> aEPPropList, List<EPProp> bEPPropList,
            List<String> reasonList) {
        boolean bFlag = false;
        boolean bcompare = false;
        // System.out.println(aEPPropList); System.out.println(bEPPropList);
        for (EPProp aLoop : aEPPropList) {
            bFlag = false;
            for (EPProp bLoop : bEPPropList) {
                bcompare = compareEPPMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an additional Endpoint Properties map item.\t" + aLoop + ".");
            }
        }

        for (EPProp bLoop : bEPPropList) {
            bFlag = false;
            for (EPProp aLoop : aEPPropList) {
                bcompare = compareEPPMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an absent Endpoint Properties item.\t" + bLoop + ".");
            }
        }
        return true;
    }

    public static boolean EndPropertiesMapTest(List<String> argsList, List<String> reasonList) {
        boolean bFlag = false;
        reasonList.clear();
        // first get what properties.
        List<String> propertiesTested = new ArrayList<String>();
        for (int i = 2; i < argsList.size(); i++) {

            propertiesTested.add(argsList.get(i));
        }
        for (String loop : propertiesTested) {
            reasonList.add("The tested ALTO server does provide Endpoint Properties service with " + loop + ".");
            List<EPProp> endPointPropList;
            String operationStatus;
            AltoClientEPPropSwapInfo EPPInfo = new AltoClientEPPropSwapInfo();
            AltoClientEndPointProp altoClientEndPointProp;
            List<String> properties = new ArrayList<String>();
            List<String> endpoints = new ArrayList<String>();

            generateDefaulEPPMapIP(endpoints);
            properties.add(loop);
            altoClientEndPointProp = new AltoClientEndPointProp(argsList.get(0), argsList.get(1), properties,
                    endpoints);
            altoClientEndPointProp.getInfo(EPPInfo);
            endPointPropList = EPPInfo.getEndPointPropList();
            operationStatus = EPPInfo.getOperationStatus();
            String propType = "invalid";
            // ipv4:128.0.0.1 peer1 default peer
            if (endPointPropList.size() > 0) {
                bFlag = false;
                for (EPProp aLoop : endPointPropList) {
                    if ((aLoop.getEndPointID().intern() == "ipv4:128.0.0.1".intern())
                            && (aLoop.getPropertiesValue().intern() == "peer1".intern())) {
                        propType = "default";
                        bFlag = true;
                        break;
                    }
                    if ((aLoop.getEndPointID().intern() == "ipv4:128.0.0.1".intern())
                            && (aLoop.getPropertiesValue().intern() == "default".intern())) {
                        propType = "alternate";
                        bFlag = true;
                        break;
                    }
                    if ((aLoop.getEndPointID().intern() == "ipv4:128.0.0.1".intern())
                            && (aLoop.getPropertiesValue().intern() == "peer".intern())) {
                        propType = "priv:ietf-type";
                        bFlag = true;
                        break;
                    }
                }

                for (EPProp aLoop : endPointPropList) {
                    aLoop.setProperties(propType);
                }

                List<EPProp> EndPointPropList = new ArrayList<EPProp>();
                generateDefaultEPPMap(EndPointPropList, propType);
                compareEPPropMap(endPointPropList, EndPointPropList, reasonList);
            }
        }
        return true;
    }

    public static boolean generateECS441(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.0.0.100", 1.0));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.8.1.100", 1.0));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.0.1.100", 2.5));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.64.0.100", 5.0));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.128.4.100", 7.0));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:130.0.1.100", 20.0));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:132.0.8.100", 40.0));

            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.0.0.100", 7.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.8.1.100", 7.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.0.1.100", 9.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.64.0.100", 6.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.128.4.100", 1.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:130.0.1.100", 25.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:132.0.8.100", 45.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.0.0.100", 1));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.8.1.100", 1));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.0.1.100", 2));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.64.0.100", 2));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:100.128.4.100", 2));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:130.0.1.100", 4));
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "ipv4:132.0.8.100", 6));

            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.0.0.100", 2));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.8.1.100", 2));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.0.1.100", 3));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.64.0.100", 2));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:100.128.4.100", 1));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:130.0.1.100", 4));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "ipv4:132.0.8.100", 6));
        }

        return true;
    }

    public static boolean generateECS441IP(List<String> srcList, List<String> dstList) {
        srcList.clear();
        dstList.clear();

        srcList.add("ipv4:100.0.0.128");
        srcList.add("ipv4:100.131.39.11");

        dstList.add("ipv4:100.0.0.100");
        dstList.add("ipv4:100.8.1.100");
        dstList.add("ipv4:100.0.1.100");
        dstList.add("ipv4:100.64.0.100");
        dstList.add("ipv4:100.128.4.100");
        dstList.add("ipv4:130.0.1.100");
        dstList.add("ipv4:132.0.8.100");

        return true;
    }

    public static boolean generateECS442(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("ipv4:10.0.1.0", "ipv4:10.0.1.1", 1.0));
            costMapList.add(new EndPointCost("ipv4:10.0.1.0", "ipv6:::1:2", 75.0));
            costMapList.add(new EndPointCost("ipv6:::2", "ipv4:10.0.1.1", 75.0));
            costMapList.add(new EndPointCost("ipv6:::2", "ipv6:::1:2", 1.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("ipv4:10.0.1.0", "ipv4:10.0.1.1", 1));
            costMapList.add(new EndPointCost("ipv4:10.0.1.0", "ipv6:::1:2", 10));
            costMapList.add(new EndPointCost("ipv6:::2", "ipv4:10.0.1.1", 10));
            costMapList.add(new EndPointCost("ipv6:::2", "ipv6:::1:2", 1));
        }

        return true;
    }

    public static boolean generateECS442IP(List<String> srcList, List<String> dstList) {
        srcList.clear();
        dstList.clear();

        srcList.add("ipv4:10.0.1.0");
        srcList.add("ipv6:::2");

        dstList.add("ipv4:10.0.1.1");
        dstList.add("ipv6:::1:2");

        return true;
    }

    public static boolean generateECS443(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("ipv4:127.0.0.1", "ipv4:127.0.1.0", 0.0));
            costMapList.add(new EndPointCost("ipv6:::1", "ipv4:127.0.1.0", 0.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("ipv4:127.0.0.1", "ipv4:127.0.1.0", 0));
            costMapList.add(new EndPointCost("ipv6:::1", "ipv4:127.0.1.0", 0));
        }

        return true;
    }

    public static boolean generateECS443IP(List<String> srcList, List<String> dstList) {
        srcList.clear();
        dstList.clear();

        srcList.add("ipv4:127.0.0.1");
        srcList.add("ipv6:::1");

        dstList.add("ipv4:127.0.1.0");

        return true;
    }

    public static boolean generateECS444D(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "default", 75.0));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "default", 75.0));
            costMapList.add(new EndPointCost("ipv4:0.0.0.1", "default", 1.0));
            costMapList.add(new EndPointCost("ipv4:10.0.0.1", "default", 75.0));
            costMapList.add(new EndPointCost("ipv6:::2", "default", 1.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("ipv4:100.0.0.128", "default", 10));
            costMapList.add(new EndPointCost("ipv4:100.131.39.11", "default", 10));
            costMapList.add(new EndPointCost("ipv4:0.0.0.1", "default", 1));
            costMapList.add(new EndPointCost("ipv4:10.0.0.1", "default", 10));
            costMapList.add(new EndPointCost("ipv6:::2", "default", 1));
        }

        return true;
    }

    public static boolean generateECS444P(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("ipv4:0.0.0.1", "private", 75.0));
            costMapList.add(new EndPointCost("ipv4:10.0.0.1", "private", 1.0));
            costMapList.add(new EndPointCost("ipv6:::2", "private", 75.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("ipv4:0.0.0.1", "private", 10));
            costMapList.add(new EndPointCost("ipv4:10.0.0.1", "private", 1));
            costMapList.add(new EndPointCost("ipv6:::2", "private", 10));
        }

        return true;
    }

    public static boolean generateECS444IP(List<String> srcList, List<String> dstList) {
        srcList.clear();
        dstList.clear();

        srcList.add("ipv4:100.0.0.128");
        srcList.add("ipv4:100.131.39.11");
        srcList.add("ipv4:0.0.0.1");
        srcList.add("ipv4:10.0.0.1");
        srcList.add("ipv6:::2");

        return true;
    }

    public static boolean generateECS445D(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("default", "ipv4:100.0.0.128", 75.0));
            costMapList.add(new EndPointCost("default", "ipv4:100.131.39.11", 75.0));
            costMapList.add(new EndPointCost("default", "ipv4:0.0.0.1", 1.0));
            costMapList.add(new EndPointCost("default", "ipv4:10.0.0.1", 75.0));
            costMapList.add(new EndPointCost("default", "ipv6:::2", 1.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("default", "ipv4:100.0.0.128", 10));
            costMapList.add(new EndPointCost("default", "ipv4:100.131.39.11", 10));
            costMapList.add(new EndPointCost("default", "ipv4:0.0.0.1", 1));
            costMapList.add(new EndPointCost("default", "ipv4:10.0.0.1", 10));
            costMapList.add(new EndPointCost("default", "ipv6:::2", 1));
        }

        return true;
    }

    public static boolean generateECS445P(List<EndPointCost> costMapList, String type) {
        costMapList.clear();

        if (type.contains("routingcost")) {
            costMapList.add(new EndPointCost("private", "ipv4:0.0.0.1", 75.0));
            costMapList.add(new EndPointCost("private", "ipv4:10.0.0.1", 1.0));
            costMapList.add(new EndPointCost("private", "ipv6:::2", 75.0));
        }
        if (type.contains("hopcount")) {
            costMapList.add(new EndPointCost("private", "ipv4:0.0.0.1", 10));
            costMapList.add(new EndPointCost("private", "ipv4:10.0.0.1", 1));
            costMapList.add(new EndPointCost("private", "ipv6:::2", 10));
        }

        return true;
    }

    public static boolean generateECS445IP(List<String> srcList, List<String> dstList) {
        srcList.clear();
        dstList.clear();

        dstList.add("ipv4:100.0.0.128");
        dstList.add("ipv4:100.131.39.11");
        dstList.add("ipv4:0.0.0.1");
        dstList.add("ipv4:10.0.0.1");
        dstList.add("ipv6:::2");

        return true;
    }

    public static boolean compareECSMapItem(EndPointCost aEndPointCost, EndPointCost bEndPointCost) {
        String aSrcEP = aEndPointCost.getSrcEP();
        String aDstEP = aEndPointCost.getDstEP();
        double aCostValue = aEndPointCost.getCost();

        String bSrcEP = bEndPointCost.getSrcEP();
        String bDstEP = bEndPointCost.getDstEP();
        double bCostValue = bEndPointCost.getCost();

        if ((aSrcEP.intern() == bSrcEP.intern()) && (aDstEP.intern() == bDstEP.intern())
                && (aCostValue == bCostValue)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean compareECSMap(List<EndPointCost> aEndPointCostList, List<EndPointCost> bEndPointCostList,
            List<String> reasonList) {
        boolean bFlag = false;
        boolean bcompare = false;


        for (EndPointCost aLoop : aEndPointCostList) {
            bFlag = false;
            for (EndPointCost bLoop : bEndPointCostList) {
                bcompare = compareECSMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an additional ECS item.\t" + aLoop + ".");
            }
        }

        for (EndPointCost bLoop : bEndPointCostList) {
            bFlag = false;
            for (EndPointCost aLoop : aEndPointCostList) {
                bcompare = compareECSMapItem(aLoop, bLoop);
                if (bcompare == true) {
                    bFlag = true;
                    break;
                }
            }
            if (bFlag == false) {
                reasonList.add("Error:There is an absent ECS item.\t" + bLoop + ".");
            }
        }
        return true;
    }

    public static boolean ECSMapTest(List<String> argsList, List<String> reasonList) {
        String routingcost = "routingcost";
        String hopcount = "hopcount";
        List<String> metricTypeList = new ArrayList<String>();
        for (int i = 2; i < argsList.size(); i++) {
            metricTypeList.add(argsList.get(i));
        }
        boolean bFlag = false;
        reasonList.clear();

        List<String> srcEPs = new ArrayList<String>();
        List<String> dstEPs = new ArrayList<String>();
        CostType postCostType = new CostType();
        postCostType.setName("invalid");
        postCostType.setMode("numerical");
        postCostType.setMetric("invalid");

        AltoClientEndPointCostSwapInfo endPointCostInfo = new AltoClientEndPointCostSwapInfo();
        AltoClientEndPointCost altoClientEndPointCost = null;
        List<EndPointCost> endPointCostList = null;
        List<EndPointCost> EndPointCostList = new ArrayList<EndPointCost>();

        for (String loop : metricTypeList) {
            reasonList.add("result 1.1.45 1.1.50----" + "4.4.1. ECS Test 1" + " .");
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS441(EndPointCostList, loop);
            generateECS441IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            compareECSMap(endPointCostList, EndPointCostList, reasonList);
        }
        reasonList.add("result 1.1.46 1.1.51----" + "4.4.2. ECS Test 2" + " .");
        for (String loop : metricTypeList) {
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS442(EndPointCostList, loop);
            generateECS442IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            compareECSMap(endPointCostList, EndPointCostList, reasonList);
        }
        reasonList.add("result 1.1.47 1.1.52----" + "4.4.3. ECS Test 3" + " .");
        for (String loop : metricTypeList) {
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS443(EndPointCostList, loop);
            generateECS443IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            compareECSMap(endPointCostList, EndPointCostList, reasonList);
        }
        reasonList.add("result 1.1.48 1.1.53----" + "4.4.4. ECS Test 4." + " Client in Default .");

        for (String loop : metricTypeList) {
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS444D(EndPointCostList, loop);
            generateECS444IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            boolean tmp = true;
            for (EndPointCost aLoop : endPointCostList) {
                if (tmp == true)
                    reasonList.add("result 1.1.48 1.1.53----" + " Client address is " + aLoop.getDstEP() + " .");
                tmp = false;
            }
            for (EndPointCost aLoop : endPointCostList) {
                aLoop.setDstEP("default");
            }
            compareECSMap(endPointCostList, EndPointCostList, reasonList);

        }
        reasonList.add("\n\n");
        reasonList.add("result 1.1.48 1.1.53----" + "4.4.4. ECS Test 4." + " Client in Private .");
        for (String loop : metricTypeList) {
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS444D(EndPointCostList, loop);
            generateECS444IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            boolean tmp = true;
            for (EndPointCost aLoop : endPointCostList) {
                if (tmp == true)
                    reasonList.add("result 1.1.48 1.1.53----" + "Client address is " + aLoop.getDstEP() + " .");
                tmp = false;
            }
            for (EndPointCost aLoop : endPointCostList) {
                aLoop.setDstEP("private");
            }
            compareECSMap(endPointCostList, EndPointCostList, reasonList);
        }
        reasonList.add("\n\n\n\n\n.");
        reasonList.add("result 1.1.49 1.1.54----" + "\n4.4.5. ECS Test 5" + "Client in Default .");
        for (String loop : metricTypeList) {
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS445D(EndPointCostList, loop);
            generateECS445IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            boolean tmp = true;
            for (EndPointCost aLoop : endPointCostList) {
                if (tmp == true)
                    reasonList.add("result 1.1.49 1.1.54----" + "Client address is " + aLoop.getDstEP() + " .");
                tmp = false;
            }
            for (EndPointCost aLoop : endPointCostList) {
                aLoop.setSrcEP("default");
            }
            compareECSMap(endPointCostList, EndPointCostList, reasonList);
        }
        reasonList.add("\n\n\n\n\n.");
        reasonList.add("result 1.1.49 1.1.54----" + "4.4.5. ECS Test 5" + "Client in Private .");
        for (String loop : metricTypeList) {
            postCostType.setMetric(loop);
            reasonList.add("The tested server supports numerical " + loop + " test.");
            EndPointCostList.clear();
            generateECS445D(EndPointCostList, loop);
            generateECS445IP(srcEPs, dstEPs);
            altoClientEndPointCost = new AltoClientEndPointCost(argsList.get(0), argsList.get(1), postCostType, srcEPs,
                    dstEPs);
            altoClientEndPointCost.getInfo(endPointCostInfo);
            endPointCostList = endPointCostInfo.getEndPointCostList();
            boolean tmp = true;
            for (EndPointCost aLoop : endPointCostList) {
                if (tmp == true)
                    reasonList.add("result 1.1.49 1.1.54----" + "Client address is " + aLoop.getDstEP() + " .");
                tmp = false;
            }
            for (EndPointCost aLoop : endPointCostList) {
                aLoop.setSrcEP("private");
            }
            compareECSMap(endPointCostList, EndPointCostList, reasonList);
        }
        return true;
    }

    public static List<String> Error5Test(String[] args) {
        List<String> retStringList = new ArrayList<String>();
        List<String> getInfoList = null;
        ErrorInterOpTest errorInterOpTest = null;
        /*
         * System.out.println("begin to start test"); for(String loop:args) {
         * System.out.println(loop); }
         */
        String testOperation = args[0];

        if (testOperation.contains("59IAH")) {
            errorInterOpTest = new ErrorInterOpTest(args[1], args[2], args[3]);
            getInfoList = new ArrayList<String>();
            errorInterOpTest.getInfo(getInfoList);
            System.out.println(getInfoList);

        } else if (testOperation.contains("51IFT") || testOperation.contains("52MPF") || testOperation.contains("53IPN")
                || testOperation.contains("54IEA") || testOperation.contains("58JSE")
                || testOperation.contains("510IAH") || testOperation.contains("511ICT")) {
            int propertiesCnt = Integer.parseInt(args[3]);
            int EPCnt = Integer.parseInt(args[propertiesCnt + 4]);
            int EPPos = propertiesCnt + 4;

            List<String> EPs = new ArrayList<String>();
            List<String> properties = new ArrayList<String>();
            while (propertiesCnt > 0) {
                properties.add(args[3 + propertiesCnt]);
                propertiesCnt = propertiesCnt - 1;
            }
            while (EPCnt > 0) {
                EPs.add(args[EPCnt + EPPos]);
                EPCnt = EPCnt - 1;
            }

            System.out.println("EPs " + EPs.toString());
            System.out.println("properties " + properties.toString());
            errorInterOpTest = new ErrorInterOpTest(args[0], args[1], args[2], properties, EPs);
            getInfoList = new ArrayList<String>();
            errorInterOpTest.getInfo(getInfoList);

        } else {
            CostType postCostType = new CostType();
            postCostType.setMetric(args[3]);
            postCostType.setMode(args[4]);
            List<String> srcPids = new ArrayList<String>();
            List<String> dstPids = new ArrayList<String>();
            List<String> constraints = new ArrayList<String>();
            constraints.add(args[5]);

            System.out.println("CostType" + postCostType.toString());
            System.out.println("constraints " + constraints.toString());

            if (args[0].contains("57ICC")) {
                errorInterOpTest = new ErrorInterOpTest(args[0], args[1], args[2], postCostType, srcPids, dstPids,
                        constraints);
            } else {
                errorInterOpTest = new ErrorInterOpTest(args[0], args[1], args[2], postCostType, srcPids, dstPids,
                        null);
            }
            getInfoList = new ArrayList<String>();
            errorInterOpTest.getInfo(getInfoList);

        }
        // System.out.println("done");
        return getInfoList;

    }

    //
    /**
     * @param args
     */
    public static void main(String[] args) {
        List<String> hostNameRequestLine = new ArrayList<String>();
        AltoClientConstInternalMethod altoClientConstInternalMethod = new AltoClientConstInternalMethod();
        hostNameRequestLine.clear();
        altoClientConstInternalMethod.methodName(args[0], hostNameRequestLine);
        AltoClientIRD altoClientIRD = new AltoClientIRD(hostNameRequestLine.get(0), hostNameRequestLine.get(1));
        AltoClientIRDSwapInfo IRDInfo = new AltoClientIRDSwapInfo();
        altoClientIRD.getInfo(IRDInfo);

        // print data
        List<CostType> costTypeList = IRDInfo.getCostTypeList();
        String defaultNetworkMapName = IRDInfo.getDefaultAltoNetworkMap();
        String defaultNetworkMapUrl = null;
        String alternateNetworkMapName = null;
        String alternateNetworkMapUrl = null;
        List<Resource> resourceList = IRDInfo.getResourceList();
        String operationStatus = IRDInfo.getOperationStatus();

        String numericalRoutingcost = null;
        String numericalHopcount = null;
        String ordinalRoutingcost = null;
        String ordinalHopcount = null;

        String routingcost = "routingcost";
        String hopcount = "hopcount";
        String numerical = "numerical";
        String ordinal = "ordinal";
        boolean bFlag = false;

        String def_num_rtg_name = null;
        String def_ord_rtg_name = null;
        String def_num_hop_name = null;
        String def_ord_hop_name = null;
        String def_num_rtg_url = null;
        String def_ord_rtg_url = null;
        String def_num_hop_url = null;
        String def_ord_hop_url = null;

        String alt_num_rtg_name = null;
        String alt_ord_rtg_name = null;
        String alt_num_hop_name = null;
        String alt_ord_hop_name = null;
        String alt_num_rtg_url = null;
        String alt_ord_rtg_url = null;
        String alt_num_hop_url = null;
        String alt_ord_hop_url = null;

        String defNetworkMapFilteredName = null;
        String defNetworkMapFilteredNameUrl = null;
        String altNetworkMapFilteredName = null;
        String altNetworkMapFilteredNameUrl = null;

        String defCostMapFilteredName = null;
        String defCostMapFilteredNameUrl = null;
        List<String> defCostMapFilteredSupportType = new ArrayList<String>();
        String altCostMapFilteredName = null;
        String altCostMapFilteredNameUrl = null;
        List<String> altCostMapFilteredSupportType = new ArrayList<String>();

        String EndpointCostMapName = null;
        String EndpointCostMapUrl = null;
        List<String> ECSSupportType = new ArrayList<String>();

        String endPropsName = null;
        String endPropsUrl = null;
        List<String> EPPPSupportTypesList = new ArrayList<String>();

        List<String> reasonList = new ArrayList<String>();

        for (CostType loop : costTypeList) {
            if ((loop.getMetric().intern() == routingcost.intern())
                    && (loop.getMode().intern() == numerical.intern())) {
                numericalRoutingcost = loop.getName();
            }
            if ((loop.getMetric().intern() == routingcost.intern()) && (loop.getMode().intern() == ordinal.intern())) {
                ordinalRoutingcost = loop.getName();
            }
            if ((loop.getMetric().intern() == hopcount.intern()) && (loop.getMode().intern() == numerical.intern())) {
                numericalHopcount = loop.getName();
            }
            if ((loop.getMetric().intern() == hopcount.intern()) && (loop.getMode().intern() == ordinal.intern())) {
                ordinalHopcount = loop.getName();
            }
        }

        if (numericalRoutingcost == null) {
            // System.out.println("The tested server dose not support numerical
            // routingcost");
            numericalRoutingcost = "invalid";
        }
        if (ordinalRoutingcost == null) {
            // System.out.println("The tested server dose not support ordinal
            // routingcost");
            ordinalRoutingcost = "invalid";
        }
        if (numericalHopcount == null) {
            // System.out.println("The tested server dose not support numerical
            // hopcount");
            numericalHopcount = "invalid";
        }
        if (ordinalHopcount == null) {
            // System.out.println("The tested server dose not support ordinal
            // hopcount");
            ordinalHopcount = "invalid";
        }

        System.out.println("result 1.1.1 -----  The URL is[" + args[0] + "].");
        System.out.println();
        System.out.println("2.1. Default Network Map And Cost Maps Check.");

        for (Resource loop : resourceList) {
            if (loop.getResourceName().intern() == defaultNetworkMapName.intern()) {
                System.out.println("result 1.1.2 ----2.1 A The tested ALTO server provides default network map:"
                        + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                defaultNetworkMapUrl = loop.getUri();
                bFlag = true;
                break;
            }
        }
        if (bFlag == false) {
            System.out.println(
                    "result 1.1.2 ----2.1 A Error:The tested ALTO server does not provide default network map");
        }
        bFlag = false;

        if (defaultNetworkMapUrl != null) {
            reasonList.clear();
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(defaultNetworkMapUrl, hostNameRequestLine);
            checkDefaultNetworkMap(hostNameRequestLine, reasonList);
            if (reasonList.size() > 0) {
                System.out.println(
                        "2.1.B/ 3.2 Error:The tested ALTO server provides default network map with some Errors as followings.");
                System.out.println("pls  check manually because of JAVA string comparing ........");
                for (String loopa : reasonList)
                    System.out.println("\t\t" + loopa);
                bFlag = true;
            }
        }

        if (bFlag == false) {
            System.out
                    .println("2.1.B/3.2 The tested ALTO server provides default network map consistent with Figure 1 ");
        }
        bFlag = false;

        System.out.println("\n2.1.C The tested ALTO server provides following cost map");
        for (Resource loop : resourceList) {

            String mediaType = loop.getMediaType();
            String accepts = loop.getAccepts();
            if ((mediaType.intern() == AltoClientResponseType.CostMapResponseType.intern())
                    && (accepts.intern() == AltoClientConstString.Invalid.intern())
                    && (loop.getUsesList().contains(defaultNetworkMapName))) {

                Capabilities capabilities = loop.getCapabilities();
                List<String> costTypeNamesList = capabilities.getCostTypeNamesList();

                if (costTypeNamesList.contains(numericalRoutingcost)) {
                    def_num_rtg_name = loop.getResourceName();
                    def_num_rtg_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides numerical routingcost map.\t"
                            + def_num_rtg_name + "  " + def_num_rtg_name);
                }
                if (costTypeNamesList.contains(ordinalRoutingcost)) {
                    def_ord_rtg_name = loop.getResourceName();
                    def_ord_rtg_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides ordinal routingcost map.\t"
                            + def_ord_rtg_name + "  " + def_ord_rtg_url);
                }
                if (costTypeNamesList.contains(numericalHopcount)) {
                    def_num_hop_name = loop.getResourceName();
                    def_num_hop_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides numerical hopcount map.\t"
                            + def_num_hop_name + "  " + def_num_hop_url);
                }
                if (costTypeNamesList.contains(ordinalHopcount)) {
                    def_ord_hop_name = loop.getResourceName();
                    def_ord_hop_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides ordinal hopcount map.\t"
                            + def_ord_hop_name + "  " + def_ord_hop_url);
                }
            }
        }
        if (def_num_rtg_url == null) {
            def_num_rtg_url = "invalid";
            System.out.println(
                    "\tError  1.1.12-----2.1.C The tested server does NOT provide a numerical ��routingcost�� cost map resource for default network map");
        } else {
            System.out.println("\tresult 1.1.12-----2.1.C The tested ALTO server provides numerical routingcost map.\t"
                    + def_num_rtg_name + "  " + def_num_rtg_name);
        }
        if (def_ord_rtg_url == null) {
            def_ord_rtg_url = "invalid";
            System.out.println(
                    "\tError  1.1.13-----2.1.C The tested server does NOT provide a ordinal ��routingcost�� cost map resource for default network map");
        } else {
            System.out.println("\tresult 1.1.13-----2.1.C The tested ALTO server provides ordinal routingcost map.\t"
                    + def_ord_rtg_name + "  " + def_ord_rtg_url);
        }
        if (def_num_hop_url == null) {
            def_num_hop_url = "invalid";
            System.out.println(
                    "\tError  1.1.14-----2.1.C The tested server does NOT provide a numerical ��hopcount�� cost map resource for default network map");
        } else {
            System.out.println("\tresult 1.1.14-----2.1.C The tested ALTO server provides numerical hopcount map.\t"
                    + def_num_hop_name + "  " + def_num_hop_url);
        }
        if (def_ord_hop_url == null) {
            def_ord_hop_url = "invalid";
            System.out.println(
                    "\tError  1.1.14-----2.1.C The tested server does NOT provide a ordinal ��hopcount�� cost map resource for default network map");
        } else {
            System.out.println("\tresult 1.1.15-----2.1.C The tested ALTO server provides ordinal hopcount map.\t"
                    + def_ord_hop_name + "  " + def_ord_hop_url);
        }

        System.out.println();
        bFlag = false;
        if (!def_num_rtg_url.contains("invalid")) {
            System.out.println(
                    "\tresult1.1.12-----2.1.C /3.3 The tested ALTO server does provide a numerical routingcost map for default network map.");
            System.out.println("\tInfo:" + "\tname:" + def_num_rtg_name + "\turi:" + def_num_rtg_url);

            reasonList.clear();
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(def_num_rtg_url, hostNameRequestLine);
            checkDefaultRoutingcostMap(hostNameRequestLine, reasonList);
            if (reasonList.size() > 0) {

                System.out.println(
                        "\n\tresult1.1.12-----Error 2.1.C/3.3 The tested ALTO server provides numerical routingcost map with some Errors as followings.");
                for (String loopa : reasonList)
                    System.out.println("\t\t" + loopa);
                bFlag = true;
            } else {
                System.out.println(
                        "\tresult1.1.12-----2.1.C/3.3 The tested ALTO server does provides numerical routingcost map consistent with Figure 2");
            }
        } else {
            System.out.println(
                    "\tresult1.1.12-----2.1.C /3.3 Error:The tested ALTO server does not provide numerical routingcost map for default network map");
        }

        System.out.println();
        if (!def_num_hop_url.contains("invalid")) {
            System.out.println(
                    "result1.1.14-----2.1.C /3.3 The tested ALTO server does provide a numerical hopcount map for default network map.");
            System.out.println("\tInfo:" + "\tname:" + def_num_hop_name + "\turi:" + def_num_hop_url);
            System.out.println("\tCAUTION:check order value manually.");

            reasonList.clear();
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(def_num_hop_url, hostNameRequestLine);
            checkDefaultHopcountMap(hostNameRequestLine, reasonList);
            System.out.println(
                    "\tresult1.1.14-----2.1.C /3.3The tested ALTO server provides numerical hopcount map as followings.");
            if (reasonList.size() > 0) {
                System.out.println("Check Manually!");
            }
            for (String loopa : reasonList)
                System.out.println("\t\t" + loopa);
        } else {
            System.out.println(
                    "\tresult1.1.14-----2.1.C /3.3 Error:The tested ALTO server does not provide numerical hopcount map for default network map");
        }

        // ----------------------------------

        System.out.println();
        System.out.println("2.2. Alternate Network Map And Cost Maps Check.");

        bFlag = false;
        for (Resource loop : resourceList) {
            if ((loop.getMediaType().intern() == AltoClientResponseType.NetworkMapResponseType.intern()
                    && (loop.getResourceName().intern() != defaultNetworkMapName.intern()))
                    && (loop.getAccepts().intern() == AltoClientConstString.Invalid.intern())) {
                alternateNetworkMapName = loop.getResourceName();
                alternateNetworkMapUrl = loop.getUri();
                bFlag = true;
                System.out
                        .println("\tresult 1.1.3 ----2.2.A The tested ALTO server does provide an alternate ntwork map:"
                                + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                break;
            }
        }

        if (bFlag == false) {
            System.out.println(
                    "\tresult 1.1.3 ----Error:2.2.A The tested ALTO server does not provide an alternate network map");
            alternateNetworkMapUrl = "invalid";
        }
        bFlag = false;

        if (!alternateNetworkMapUrl.contains("invalid")) {

            reasonList.clear();
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(alternateNetworkMapUrl, hostNameRequestLine);
            checkAlternateNetworkMap(hostNameRequestLine, reasonList);
            if (reasonList.size() > 0) {
                bFlag = true;
                System.out.println(
                        "\t2.2.B The tested ALTO server provides alternate network map with some Errors as followings.");
                if (reasonList.size() > 0) {
                    System.out.println("Check Manually!");
                }
                for (String loopa : reasonList)
                    System.out.println("\t\t" + loopa);
            }

        }
        if (bFlag == false) {
            System.out
                    .println("\t2.2.B The tested ALTO server provides alternate network map consistent with Figure4. ");
        }
        bFlag = false;
        // ----------------------------------
        // ----------------------------------
        System.out.println();
        System.out.println("2.2.C The tested ALTO server provides following alternate cost map");
        for (Resource loop : resourceList) {
            String accepts = loop.getAccepts();
            if ((loop.getMediaType().intern() == AltoClientResponseType.CostMapResponseType.intern())
                    && (accepts.intern() == AltoClientConstString.Invalid.intern())
                    && (loop.getUsesList().contains(alternateNetworkMapName))) {
                Capabilities capabilities = loop.getCapabilities();
                List<String> costTypeNamesList = capabilities.getCostTypeNamesList();

                if (costTypeNamesList.contains(numericalRoutingcost)) {
                    alt_num_rtg_name = loop.getResourceName();
                    alt_num_rtg_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides numerical routingcost map.\t"
                            + alt_num_rtg_name + "  " + alt_num_rtg_url);
                }
                if (costTypeNamesList.contains(ordinalRoutingcost)) {
                    alt_ord_rtg_name = loop.getResourceName();
                    alt_ord_rtg_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides ordinal routingcost map.\t"
                            + alt_ord_rtg_name + "  " + alt_ord_rtg_url);
                }
                if (costTypeNamesList.contains(numericalHopcount)) {
                    alt_num_hop_name = loop.getResourceName();
                    alt_num_hop_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides numerical hopcount map.\t"
                            + alt_num_hop_name + "  " + alt_num_hop_url);
                }
                if (costTypeNamesList.contains(ordinalHopcount)) {
                    alt_ord_hop_name = loop.getResourceName();
                    alt_ord_hop_url = loop.getUri();
                    System.out.println("\t2.1.C The tested ALTO server provides ordinal hopcount map.\t"
                            + alt_ord_hop_name + "  " + alt_ord_hop_url);
                }
            }
        }
        if (alt_num_rtg_url == null) {
            alt_num_rtg_url = "invalid";
        } else {
            System.out.println(
                    "\tresult1.1.16------------2.1.C The tested ALTO server provides numerical routingcost map.\t"
                            + alt_num_rtg_name + "  " + alt_num_rtg_url);
        }
        if (alt_ord_rtg_url == null) {
            alt_ord_rtg_url = "invalid";
        } else {
            System.out.println(
                    "\tresult1.1.17------------2.1.C The tested ALTO server provides ordinal routingcost map.\t"
                            + alt_ord_rtg_name + "  " + alt_ord_rtg_url);
        }
        if (alt_num_hop_url == null) {
            alt_num_hop_url = "invalid";
        } else {
            System.out
                    .println("\tresult1.1.18------------2.1.C The tested ALTO server provides numerical hopcount map.\t"
                            + alt_num_hop_name + "  " + alt_num_hop_url);
        }
        if (alt_ord_hop_url == null) {
            alt_ord_hop_url = "invalid";
        } else {
            System.out.println("\tresult1.1.19------------2.1.C The tested ALTO server provides ordinal hopcount map.\t"
                    + alt_ord_hop_name + "  " + alt_ord_hop_url);
        }

        System.out.println();
        bFlag = false;
        if (!alt_num_rtg_url.contains("invalid")) {
            System.out.println(
                    "result 1.1.16------2.2.C/3.5 The tested ALTO server does provide a numerical routingcost map for alternate network map.");
            System.out.println("\tInfo:" + "\tname:" + def_num_rtg_name + "\turi:" + def_num_rtg_url);

            reasonList.clear();
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(alt_num_rtg_url, hostNameRequestLine);
            checkAlternatetRoutingcostMap(hostNameRequestLine, reasonList);
            if (reasonList.size() > 0) {

                System.out.println(
                        "\tresult 1.1.16------2.2.C/3.5 The tested ALTO server provides numerical routingcost map with some Errors as followings.");
                for (String loopa : reasonList)
                    System.out.println("\t\t" + loopa);
                bFlag = true;
            } else {
                System.out.println(
                        "\tresult 1.1.16------2.2.C/3.5 The tested ALTO server does provides numerical routingcost map consistent with Figure 5");
            }
        } else {
            System.out.println(
                    "\tresult 1.1.16------2.2.C/3.5 Error:The tested ALTO server does not provide numerical routingcost map for alternte network map");
        }

        if (!alt_num_hop_url.contains("invalid")) {
            System.out.println("result 1.1.18------CAUTION:check order value manually.");
            System.out.println(
                    "\n2.2.D The tested ALTO server does provide a numerical hopcount map for alternate network map.");
            System.out.println("\tInfo:" + "\tname:" + alt_num_hop_url + "\turi:" + alt_num_hop_url);

            reasonList.clear();
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(alt_num_hop_url, hostNameRequestLine);
            checkAlternateHopcountMap(hostNameRequestLine, reasonList);
            System.out.println("\tThe tested ALTO server provides order routingcost map as followings.Check Manually!");
            if (reasonList.size() > 0) {
                System.out.println("\tCheck Manually!");
            }
            for (String loopa : reasonList)
                System.out.println("\t\t" + loopa);
        } else {
            System.out.println(
                    "\tresult 1.1.18------2.2.D  Error:The tested ALTO server does not provide numerical hopcount map for alternate network map");
        }

        // ------------------------------------------------------------------------------------------------------
        System.out.println();
        System.out.println("2.3./3.4/3.10 Endpoint Properties");
        for (Resource loop : resourceList) {
            if (loop.getMediaType().intern() == AltoClientResponseType.EndPointPropResponseType.intern()) {
                Capabilities capabilities = loop.getCapabilities();
                for (String aLoop : capabilities.getPropTypesList()) {
                    EPPPSupportTypesList.add(aLoop);
                }

                if (EPPPSupportTypesList.contains("priv:ietf-type")) {
                    System.out.println(
                            "\t2.3/3.4 The tested ALTO server does provide Endpoint Properties service with 'priv:ietf-type'.");
                    System.out
                            .println("\t2.3/3.4 Info:" + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                    endPropsName = loop.getResourceName();
                    endPropsUrl = loop.getUri();
                    bFlag = true;
                }
                if (EPPPSupportTypesList.size() >= 2) {
                    System.out.println("\t2.3/3.4 The tested ALTO server does provide Endpoint Properties service with "
                            + EPPPSupportTypesList + ".");
                }
                break;

            }
        }
        if (bFlag == false) {
            System.out.println(
                    "\t2.3/3.4 Error:The tested ALTO server does not provide Endpoint Properties service with 'priv:ietf-type'");

        }
        bFlag = false;

        if (endPropsUrl != null) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
            reasonList.clear();
            checkEPPMap(hostNameRequestLine, reasonList);
            if (reasonList.size() > 0) {
                for (String loop : reasonList)
                    System.out.println(loop);
            }
        }
        bFlag = false;
        // ------------------------------------------------------------------------------------------------------//
        // check for filtered network map.
        System.out.println();
        System.out.println("3. Server Resources and Configuration");
        System.out.println("3.7 Filtered Network Map resources for either or both network maps");
        System.out.println("3.7.1 Filtered Network Map resources for default network map");
        for (Resource loop : resourceList) {

            String mediaType = loop.getMediaType();
            String acceptsType = loop.getAccepts();
            if ((mediaType.intern() == AltoClientResponseType.NetworkMapResponseType.intern())
                    && (acceptsType.intern() == AltoClientFilteredContentTypeString.NetworkMap)
                    && (loop.getUsesList().contains(defaultNetworkMapName))) {
                System.out.println(
                        "\tThe tested ALTO server does provide a Filtered Network Map for default network map.");
                System.out.println("\tInfo:" + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                defNetworkMapFilteredName = loop.getResourceName();
                defNetworkMapFilteredNameUrl = loop.getUri();
                bFlag = true;
            }
        }

        if (bFlag == false) {
            System.out.println(
                    "\tError:The tested ALTO server does not provide a Filtered Network Map for default network map");
        }
        bFlag = false;
        System.out.println("3.7.2 Filtered Network Map resources for alternate network map");
        for (Resource loop : resourceList) {

            String mediaType = loop.getMediaType();
            String acceptsType = loop.getAccepts();
            if ((mediaType.intern() == AltoClientResponseType.NetworkMapResponseType.intern())
                    && (acceptsType.intern() == AltoClientFilteredContentTypeString.NetworkMap)
                    && (loop.getUsesList().contains(alternateNetworkMapName))) {
                System.out.println(
                        "\tThe tested ALTO server does provide a Filtered Network Map for alternate network map.");
                System.out.println("\tInfo:" + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                altNetworkMapFilteredName = loop.getResourceName();
                altNetworkMapFilteredNameUrl = loop.getUri();
                bFlag = true;
            }
        }

        if (bFlag == false) {
            System.out.println(
                    "\t Error:The tested ALTO server does not provide a Filtered Network Map for alternate network map");
        }
        bFlag = false;

        // ------------------------------------------------------------------------------------------------------//
        // check for filtered cost map.
        System.out.println("3.8 Filtered Cost Map resources");
        System.out.println("3.8 Check Filtered Cost Maps for default network map.");
        for (Resource loop : resourceList) {
            String acceptsType = loop.getAccepts();
            if ((loop.getMediaType().intern() == AltoClientResponseType.CostMapResponseType.intern())
                    && (acceptsType.intern() == AltoClientFilteredContentTypeString.CostMap)
                    && (loop.getUsesList().contains(defaultNetworkMapName))) {
                System.out
                        .println("\tThe tested ALTO server does provide  Filtered Cost Maps for default network map.");
                System.out.println("\tInfo:" + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                defCostMapFilteredName = loop.getResourceName();
                defCostMapFilteredNameUrl = loop.getUri();
                bFlag = true;
                Capabilities capabilities = loop.getCapabilities();
                List<String> costTypeNamesList = capabilities.getCostTypeNamesList();
                Boolean costConstraints = capabilities.getCostConstraints();
                if (costTypeNamesList.contains(numericalRoutingcost)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a numerical routingcost default network map.");
                    defCostMapFilteredSupportType.add(numericalRoutingcost);
                }
                if (costTypeNamesList.contains(numericalHopcount)) {
                    System.out
                            .println("\tThe tested ALTO server does provide a numerical hocount default network map.");
                    defCostMapFilteredSupportType.add(numericalHopcount);
                }
                if (costTypeNamesList.contains(ordinalRoutingcost)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a ordinal routingcost default network map.");
                    defCostMapFilteredSupportType.add(ordinalRoutingcost);
                }
                if (costTypeNamesList.contains(ordinalHopcount)) {
                    System.out.println("\tThe tested ALTO server does provide a ordinal hocount default network map.");
                    defCostMapFilteredSupportType.add(ordinalHopcount);
                }
                System.out.println();
                if (costConstraints.booleanValue() == true) {
                    System.out.println("\tThe Filtered Cost Map accepts constraints.");
                } else {
                    System.out.println("\tThe Filtered Cost Map does not accept constraints.");
                }
            }
        }

        if (bFlag == false) {
            System.out.println(
                    "\tError:The tested ALTO server does not provide a Filtered Cost Map for default network map");
        }
        bFlag = false;
        System.out.println();
        System.out.println("3.8 Check Filtered Cost Maps for alternate network map.");
        for (Resource loop : resourceList) {

            String mediaType = loop.getMediaType();
            String acceptsType = loop.getAccepts();
            if ((mediaType.intern() == AltoClientResponseType.CostMapResponseType.intern())
                    && (acceptsType.intern() == AltoClientFilteredContentTypeString.CostMap)
                    && (loop.getUsesList().contains(alternateNetworkMapName))) {
                System.out.println(
                        "\tThe tested ALTO server does provide a Filtered Cost Map for alternate network map.");
                System.out.println("\tInfo:" + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                altCostMapFilteredName = loop.getResourceName();
                altCostMapFilteredNameUrl = loop.getUri();
                bFlag = true;
                Capabilities capabilities = loop.getCapabilities();
                List<String> costTypeNamesList = capabilities.getCostTypeNamesList();
                Boolean costConstraints = capabilities.getCostConstraints();
                if (costTypeNamesList.contains(numericalRoutingcost)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a numerical routingcost alternate network map.");
                    altCostMapFilteredSupportType.add(numericalRoutingcost);
                }
                if (costTypeNamesList.contains(numericalHopcount)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a numerical hocount alternate network map.");
                    altCostMapFilteredSupportType.add(numericalHopcount);
                }
                if (costTypeNamesList.contains(ordinalRoutingcost)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a ordinal routingcost alternate network map.");
                    altCostMapFilteredSupportType.add(ordinalRoutingcost);
                }
                if (costTypeNamesList.contains(ordinalHopcount)) {
                    System.out
                            .println("\tThe tested ALTO server does provide a ordinal hocount alternate network map.");
                    altCostMapFilteredSupportType.add(ordinalHopcount);
                }
                System.out.println();
                if (costConstraints.booleanValue() == true) {
                    System.out.println("\tThe Filtered Cost Map accepts constraints.");
                } else {
                    System.out.println("\tThe Filtered Cost Map does not accept constraints.");
                }
            }
        }

        if (bFlag == false) {
            System.out.println(
                    "\tError:The tested ALTO server does not provide a Filtered Cost Map for alternate network map");
        }
        bFlag = false;

        // ------------------------------------------------------------------------------------------------------//
        // Endpoint Cost Service
        System.out.println();
        System.out.println("3.9 Check Endpoint Cost Service.");
        for (Resource loop : resourceList) {

            String mediaType = loop.getMediaType();
            String acceptsType = loop.getAccepts();
            if ((mediaType.intern() == AltoClientResponseType.EndPointCostResponseType.intern())
                    && (acceptsType.intern() == AltoClientFilteredContentTypeString.EndPointCost)) {
                System.out.println("\tThe tested ALTO server does provide a Endpoint Cost Service.");
                System.out.println("\tInfo:" + "\tname:" + loop.getResourceName() + "\turi:" + loop.getUri());
                EndpointCostMapName = loop.getResourceName();
                EndpointCostMapUrl = loop.getUri();
                bFlag = true;
                Capabilities capabilities = loop.getCapabilities();
                List<String> costTypeNamesList = capabilities.getCostTypeNamesList();
                Boolean costConstraints = capabilities.getCostConstraints();
                if (costTypeNamesList.contains(numericalRoutingcost)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a numerical routingcost in Endpoint Cost Service.");
                    ECSSupportType.add(numericalRoutingcost);
                }
                if (costTypeNamesList.contains(numericalHopcount)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a numerical hocount in Endpoint Cost Service.");
                    ECSSupportType.add(numericalHopcount);
                }
                if (costTypeNamesList.contains(ordinalRoutingcost)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a ordinal routingcost in Endpoint Cost Service.");
                    ECSSupportType.add(ordinalRoutingcost);
                }
                if (costTypeNamesList.contains(ordinalHopcount)) {
                    System.out.println(
                            "\tThe tested ALTO server does provide a ordinal hocount in Endpoint Cost Service.");
                    ECSSupportType.add(ordinalHopcount);
                }
                if (costConstraints.booleanValue() == true) {
                    System.out.println("\tThe Endpoint Cost Service accepts constraints.");
                } else {
                    System.out.println("\tThe Endpoint Cost Service does not accept constraints.");
                }
            }
        }

        if (bFlag == false) {
            System.out.println("\tError:The tested ALTO server does not provide a Endpoint Cost Service");
        }
        bFlag = false;

        // ------------------------------------------------------------------------------------------------------//
        // Filtered Network Map Tests
        System.out.println();
        System.out.println("4.1 check Filtered Network Map Tests (default network map).");
        if (defNetworkMapFilteredNameUrl != null) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(defNetworkMapFilteredNameUrl, hostNameRequestLine);
            reasonList.clear();
            defaultFilteredNetworkMapTest(hostNameRequestLine, reasonList);
            System.out.println("\tThe tested ALTO server's response for Filtered Network Map Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);
            }
        } else {
            System.out.println(
                    "\tError:The tested ALTO server does not support Filtered Network Map Tests.(default network map)");
        }
        System.out.println();
        System.out.println("4.1 check Filtered Network Map Tests (alternate network map).");
        if (altNetworkMapFilteredNameUrl != null) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(altNetworkMapFilteredNameUrl, hostNameRequestLine);
            reasonList.clear();
            alternateFilteredNetworkMapTest(hostNameRequestLine, reasonList);
            System.out.println("\tThe tested ALTO server's response for Filtered Network Map Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);
            }
        } else {
            System.out.println(
                    "\tError:The tested ALTO server does not support Filtered Network Map Tests.(alternate network map)");
        }
        // ------------------------------------------------------------------------------------------------------//
        System.out.println();
        System.out.println("4.2 Filtered Cost Map Tests Check. Default Network Map. Routingcost Metric");
        if ((defCostMapFilteredNameUrl != null) && (defCostMapFilteredSupportType.contains(numericalRoutingcost))) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
            reasonList.clear();
            filteredCostMapTestDefaultRoutingcost(hostNameRequestLine, reasonList);
            System.out.println("\t4.2 The tested ALTO server's response for Filtered Cost Map Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);
            }
        } else {
            System.out.println("\tError:The tested ALTO server does not support Filtered Cost Map Tests");
        }
        System.out.println();
        System.out.println("4.2 check Filtered Cost Map Tests. Default Network Map. Hopcount Metric");
        if ((defCostMapFilteredNameUrl != null) && (defCostMapFilteredSupportType.contains(numericalHopcount))) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
            reasonList.clear();
            filteredCostMapTestDefaultHopcount(hostNameRequestLine, reasonList);
            System.out.println("\t4.2 The tested ALTO server's response for Filtered Cost Map Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);
            }
        } else {
            System.out.println("\tError:The tested ALTO server does not support Filtered Cost Map Tests");
            System.out.println("\tdefCostMapFilteredSupportType:" + defCostMapFilteredSupportType);
        }

        System.out.println();
        System.out.println("4.2 check Filtered Cost Map Tests. Altrenate Network Map. Routingcost Metric");
        if ((altCostMapFilteredNameUrl != null) && (altCostMapFilteredSupportType.contains(numericalRoutingcost))) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(altCostMapFilteredNameUrl, hostNameRequestLine);
            reasonList.clear();
            filteredCostMapTestAlternateRoutingcost(hostNameRequestLine, reasonList);
            System.out.println("\t4.2 The tested ALTO server's response for Filtered Cost Map Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);
            }
        } else {
            System.out.println("\tError:The tested ALTO server does not support Filtered Cost Map Tests");
        }
        System.out.println();
        System.out.println("4.2 check Filtered Cost Map Tests. Altrenate Network Map.Hopcount Metric");
        if ((altCostMapFilteredNameUrl != null) && (altCostMapFilteredSupportType.contains(numericalHopcount))) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(altCostMapFilteredNameUrl, hostNameRequestLine);
            reasonList.clear();
            filteredCostMapTestAlternateHopcount(hostNameRequestLine, reasonList);
            System.out.println("\t4.2 The tested ALTO server's response for Filtered Cost Map Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);
            }
        } else {
            System.out.println("\tError:The tested ALTO server does not support Filtered Cost Map Tests");
        }
        //

        // ------------------------------------------------------------------------------------------------------//
        System.out.println();
        System.out.println("result 1.1.44----4.3 check  Endpoint Property Service Tests.");
        if ((endPropsUrl != null) && (EPPPSupportTypesList.size() >= 1)) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
            reasonList.clear();
            for (String loop : EPPPSupportTypesList) {
                hostNameRequestLine.add(loop);
            }
            EndPropertiesMapTest(hostNameRequestLine, reasonList);
            System.out.println(hostNameRequestLine);
            System.out.println("\t4.3 Endpoint Property Service Tests.");
            for (String loop : reasonList) {
                System.out.println("\t\t" + loop);

            }
        } else {
            System.out.println("\t Error:The tested ALTO server does not support Endpoint Property Service Tests");
        }

        // ------------------------------------------------------------------------------------------------------//
        System.out.println();
        System.out.println("4.4 check Endpoint Cost Service Tests.");

        if ((EndpointCostMapUrl != null) && (ECSSupportType.size() >= 1)) {
            hostNameRequestLine.clear();
            altoClientConstInternalMethod.methodName(EndpointCostMapUrl, hostNameRequestLine);
            reasonList.clear();
            if (ECSSupportType.contains(numericalRoutingcost)) {
                hostNameRequestLine.add("routingcost");
            } else {
                System.out.println(
                        "\t4.4 Error:The tested ALTO server  does not support  Endpoint Cost Service Tests for numerical routing cost");
            }
            if (ECSSupportType.contains(numericalHopcount)) {
                hostNameRequestLine.add("hopcount");
            } else {
                System.out.println(
                        "\t4.4 Error:The tested ALTO server  does not support  Endpoint Cost Service Tests for numerical hopcount cost");
            }

            ECSMapTest(hostNameRequestLine, reasonList);
            System.out.println("\n\t4.4 Endpoint Cost Service Tests result.");
            for (String loop : reasonList) {
                System.out.println(loop);
            }
        } else {
            System.out.println("\tError:The tested ALTO server  does not support Endpoint Cost Service Tests");
        }

        // ----------------------//
        if (true) {
            System.out.println("\n5. Error Tests.");
            List<String> retStrList;
            String httpStatus = null;
            String httpCode = null;
            String httpFiled = null;
            String httpValue = null;
            JSONObject jsonobj = null;
            JSONObject metaObj = null;
            String ipAddress = "invalid";
            String type = "invalid";
            System.out.println();
            System.out.println("result 1.2.1----5.1. Invalid Field Type---- Related test item 1.2.1");
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "51IFT", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "1", "ipv4:1.2.3.4" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_TYPE"))
                        && (httpFiled.contains("endpoints"))) {
                    System.out.println("\t The tested ALTO server does PASS \"5.1. Invalid Field Type\" test.");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\tError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_TYPE")) {
                        System.out
                                .println("\tError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("endpoints")) {
                        System.out.println(
                                "\tError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    System.out.println("\tError: The tested ALTO server does FAIL \"5.1. Invalid Field Type\" test.");
                }

            } else {
                System.out
                        .println("\tError: The tested ALTO server does not support \"5.1. Invalid Field Type\" test.");
            }

            System.out.println();
            System.out.println("result 1.2.2----5.2. Missing \"properties\" Field");
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "52MPF", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "1", "ipv4:1.2.3.4" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_MISSING_FIELD"))
                        && (httpFiled.contains("properties"))) {
                    System.out
                            .println("\n The tested ALTO server does PASS \"5.2. Missing \"properties\" Field\" test.");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_TYPE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("endpoints")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.2. Missing \"properties\" Field\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.2. Missing \"properties\" Field\" test.");
            }
            System.out.println();
            System.out.println("result 1.2.3----5.3. Invalid Property Name");
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "53IPN", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        "invalid" + EPPPSupportTypesList.get(0), "1", "ipv4:1.2.3.4" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("properties"))
                        && (httpValue.contains("invalid" + EPPPSupportTypesList.get(0)))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.3. Invalid Property Name\" test.");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("properties")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains("invalid" + EPPPSupportTypesList.get(0))) {
                        System.out.println("\nError: The tested ALTO server does return non-expecting value. "
                                + "invalid" + EPPPSupportTypesList.get(0));
                    }
                    System.out
                            .println("\nError: The tested ALTO server does FAIL \"5.3. Invalid Property Name\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.3. Invalid Property Name\" test.");
            }

            ipAddress = "ipv4:1.2.3.256";
            type = "type A";
            System.out.println();
            System.out.println("result 1.2.4----5.4. Invalid Endpoint Addresses" + "--" + type);
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "54IEA", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "1", ipAddress };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("endpoints")) && (httpValue.contains(ipAddress))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.4. Invalid Endpoint Addresses\" test. "
                            + "--" + type);
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("endpoints")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(ipAddress)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.4. Invalid Endpoint Addresses\" test." + "--"
                                    + type);
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.4. Invalid Endpoint Addresses\" test."
                                + "--" + type);
            }

            ipAddress = "ipv6:2001:db800::";
            type = "type B";
            System.out.println();
            System.out.println("result 1.2.5----5.4. Invalid Endpoint Addresses" + "--" + type);
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "54IEA", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "1", ipAddress };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("endpoints")) && (httpValue.contains(ipAddress))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.4. Invalid Endpoint Addresses\" test. "
                            + "--" + type);
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("endpoints")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(ipAddress)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.4. Invalid Endpoint Addresses\" test." + "--"
                                    + type);
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.4. Invalid Endpoint Addresses\" test."
                                + "--" + type);
            }

            ipAddress = "ipv4:2001:db8::";
            type = "type C";
            System.out.println();
            System.out.println("result 1.2.6----5.4. Invalid Endpoint Addresses" + "--" + type);
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "54IEA", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "1", ipAddress };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("endpoints")) && (httpValue.contains(ipAddress))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.4. Invalid Endpoint Addresses\" test. "
                            + "--" + type);
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("endpoints")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(ipAddress)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.4. Invalid Endpoint Addresses\" test." + "--"
                                    + type);
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.4. Invalid Endpoint Addresses\" test."
                                + "--" + type);
            }

            ipAddress = "ipv6:1.2.3.4";
            type = "type D";
            System.out.println();
            System.out.println("result 1.2.7----5.4. Invalid Endpoint Addresses" + "--" + type);
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "54IEA", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "1", ipAddress };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("endpoints")) && (httpValue.contains(ipAddress))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.4. Invalid Endpoint Addresses\" test. "
                            + "--" + type);
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("endpoints")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(ipAddress)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.4. Invalid Endpoint Addresses\" test." + "--"
                                    + type);
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.4. Invalid Endpoint Addresses\" test."
                                + "--" + type);
            }
            System.out.println();
            System.out.println("result 1.2.8----5.5. Invalid numerical Cost Type.");
            if ((defCostMapFilteredNameUrl != null) && defCostMapFilteredSupportType.contains(numericalHopcount)
                    || defCostMapFilteredSupportType.contains(numericalRoutingcost)) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
                String metric = "invalid";
                String mode = "invalid";
                String value = null;
                metric = "no-such-metric";
                mode = "numerical";
                value = metric;

                String argList[] = { "55ICC", hostNameRequestLine.get(0), hostNameRequestLine.get(1), metric, mode,
                        "le 100" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("cost-type/cost-metric")) && (httpValue.contains(value))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.5. Invalid Cost Type\" test. ");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("cost-type/cost-metric")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(value)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println("\nError: The tested ALTO server does FAIL \"5.5. Invalid Cost Type\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.5. Numerical Invalid Cost Type\" test.");
            }

            System.out.println();
            System.out.println("result 1.2.9----5.5. Invalid ordinal Cost Type.");
            if ((defCostMapFilteredNameUrl != null) && (defCostMapFilteredSupportType.contains(ordinalHopcount)
                    || defCostMapFilteredSupportType.contains(ordinalRoutingcost))) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
                String metric = "invalid";
                String mode = "invalid";
                String value = null;
                metric = "no-such-metric";
                mode = "ordinal";
                value = metric;

                String argList[] = { "55ICC", hostNameRequestLine.get(0), hostNameRequestLine.get(1), metric, mode,
                        "le 100" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("cost-type/cost-metric")) && (httpValue.contains(value))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.5. Invalid Cost Type\" test. ");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("cost-type/cost-metric")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(value)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println("\nError: The tested ALTO server does FAIL \"5.5. Invalid Cost Type\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.5. Invalid ordinal Cost Type\" test.");
            }

            System.out.println();
            System.out.println("result 1.2.10----5.6. Invalid numerical Cost Mode.");
            if ((defCostMapFilteredNameUrl != null) && defCostMapFilteredSupportType.contains(numericalRoutingcost)) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
                String metric = "invalid";
                String mode = "invalid";
                String value = null;
                metric = "routingcost";
                mode = "no-such-mode";
                value = mode;

                String argList[] = { "56ICM", hostNameRequestLine.get(0), hostNameRequestLine.get(1), metric, mode,
                        "le 100" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("cost-type/cost-mode")) && (httpValue.contains(value))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.6. Invalid Cost Mode\" test. ");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("cost-type/cost-metric")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting FIELD. " + httpFiled);
                    }
                    if (!httpValue.contains(value)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println("\nError: The tested ALTO server does FAIL \"5.6. Invalid Cost Mode\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.6.  Invalid numerical Cost Mode\" test.");
            }
            System.out.println();
            System.out.println("result 1.2.11----5.6.  Invalid ordinal Cost Mode.");
            if ((defCostMapFilteredNameUrl != null) && (defCostMapFilteredSupportType.contains(ordinalRoutingcost))) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
                String metric = "invalid";
                String mode = "invalid";
                String value = null;
                metric = "routingcost";
                mode = "no-such-mode";
                value = mode;

                String argList[] = { "56ICM", hostNameRequestLine.get(0), hostNameRequestLine.get(1), metric, mode,
                        "le 100" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("cost-type/cost-mode")) && (httpValue.contains(value))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.6. Invalid Cost Mode\" test. ");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("cost-type/cost-mode")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(value)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println("\nError: The tested ALTO server does FAIL \"5.6. Invalid Cost Mode\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.6.  Invalid ordinal Cost Mode\" test.");
            }
            System.out.println();
            System.out.println("result 1.2.12----5.7. Invalid Cost Constraints.");
            if (defCostMapFilteredNameUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(defCostMapFilteredNameUrl, hostNameRequestLine);
                String metric = "invalid";
                String mode = "invalid";
                String value = null;
                metric = "routingcost";
                mode = "numerical";
                value = "ne 100";

                String argList[] = { "57ICC", hostNameRequestLine.get(0), hostNameRequestLine.get(1), metric, mode,
                        "ne 100" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "no-such-mode";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                    httpFiled = metaObj.getString("field");
                    httpValue = metaObj.getString("value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request")) && (httpCode.contains("E_INVALID_FIELD_VALUE"))
                        && (httpFiled.contains("constraints")) && (httpValue.contains(value))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.7. Invalid Cost Constraints\" test. ");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_INVALID_FIELD_VALUE")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    if (!httpFiled.contains("constraints")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpFiled);
                    }
                    if (!httpValue.contains(value)) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting value. " + httpValue);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.7. Invalid Cost Constraints\" test.");
                }

            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.7. Invalid Cost Constraints\" test.");
            }

            System.out.println();
            System.out.println("result 1.2.13----5.8. JSON Syntax Error");
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "58JSE", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "2", "ipv4:1.2.3.4", "ipv4:100.131.39.11" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                try {
                    jsonobj = new JSONObject(retStrList.get(1));
                    metaObj = (JSONObject) (jsonobj.get("meta"));
                    httpCode = metaObj.getString("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ((httpStatus.contains("400 Bad Request"))
                        && ((httpCode.contains("E_SYNTAX")) || (httpCode.contains("E_INVALID_FIELD_TYPE")))) {
                    System.out.println("\n The tested ALTO server does PASS \"5.8. JSON Syntax Error\" test.");
                } else {
                    if (!httpStatus.contains("400 Bad Request")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    if (!httpCode.contains("E_SYNTAX")) {
                        System.out
                                .println("\nError: The tested ALTO server does return non-expecting CODE. " + httpCode);
                    }
                    System.out.println("Error: The tested ALTO server does FAIL \"5.8. JSON Syntax Error\" test.");
                }
            } else {
                System.out.println("\nError: The tested ALTO server does not support \"5.8. JSON Syntax Error\" test.");
            }
            System.out.println();
            System.out.println("result 1.2.14----5.9. Invalid Accept Header In GET Request");
            if (defaultNetworkMapUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(defaultNetworkMapUrl, hostNameRequestLine);
                String argList[] = { "59IAH", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "text/html" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                if (httpStatus.contains("Not Acceptable")) {
                    System.out.println(
                            "\n The tested ALTO server does PASS \"5.9. Invalid Accept Header In GET Request\" test.");
                } else {
                    if (!httpStatus.contains("406 Not Acceptable")) {
                        System.out.println(
                                "\nError: The tested ALTO server does return non-expecting STATUS. " + httpStatus);
                    }
                    System.out.println(
                            "\nError: The tested ALTO server does FAIL \"5.9. Invalid Accept Header In GET Request\" test.");
                }
            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.9. Invalid Accept Header In GET Request\" test.");
            }

            System.out.println();
            System.out.println("result 1.2.15----5.10. Invalid Accept Header In POST Request");
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "510IAH", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "2", "ipv4:1.2.3.4", "ipv4:100.131.39.11" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                if ((httpStatus.contains("406 Not Acceptable"))) {
                    System.out.println(
                            "\n The tested ALTO server does PASS \"5.10. Invalid Accept Header In POST Request\" test.");
                } else {
                    System.out.println(
                            "\n Tip: The tested ALTO server does FAIL \"5.10. Invalid Accept Header In POST Request\" test.");
                    System.out.println("\n Tip: httpStatus " + httpStatus);
                }
            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.10. Invalid Accept Header In POST Request\" test.");
            }
            System.out.println();
            System.out.println("result 1.2.16----5.11. Invalid Content-Type Header In POST Request");
            if (endPropsUrl != null) {
                hostNameRequestLine.clear();
                altoClientConstInternalMethod.methodName(endPropsUrl, hostNameRequestLine);
                String argList[] = { "511ICT", hostNameRequestLine.get(0), hostNameRequestLine.get(1), "1",
                        EPPPSupportTypesList.get(0), "2", "ipv4:1.2.3.4", "ipv4:100.131.39.11" };
                retStrList = Error5Test(argList);
                jsonobj = null;
                metaObj = null;
                httpStatus = "invalid";
                httpCode = "invalid";
                httpFiled = "invalid";
                httpValue = "invalid";
                httpStatus = retStrList.get(0);

                if ((httpStatus.contains("400 Bad Request")) || (httpStatus.contains("404 Not Found"))
                        || (httpStatus.contains("406 Not Acceptable"))
                        || (httpStatus.contains("415 Unsupported Media Type"))) {
                    System.out.println(
                            "\n The tested ALTO server does PASS \"5.11. Invalid Content-Type Header In POST Request\" test.");
                } else {
                    System.out.println(
                            "\n Tips: The tested ALTO server does FAIL \"5.11. Invalid Content-Type Header In POST Request\" test.");
                    System.out.println("\n Tip: httpStatus " + httpStatus);
                }
            } else {
                System.out.println(
                        "\nError: The tested ALTO server does not support \"5.11. Invalid Content-Type Header In POST Request\" test.");
            }

        }

        System.out.println(hostNameRequestLine + " done" + "  ");
    }
}
