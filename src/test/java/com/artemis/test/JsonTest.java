package com.artemis.test;

import com.artemis.football.common.JsonTools;
import com.artemis.football.model.DataPack;

/**
 * @author zhengenshen
 * @date 2018-06-25 11:43
 */

public class JsonTest {

    public static void main(String[] args) throws Exception {
        String a = "{\"id\":381469995,\"roomId\":381469995,\"fromUid\":3,\"toUid\":4,\"footballX\":0.0500000007450581,\"footballY\":-0.699999988079071,\"footballZ\":-0.5,\"count\":null,\"timestamp\":null,\"fromUnits\":{\"unit1\":{\"x\":-2.99993824958801,\"y\":-3.0},\"unit4\":{\"x\":1.98000037670136,\"y\":-3.97000002861023},\"unit2\":{\"x\":-2.99993824958801,\"y\":-6.0},\"unit3\":{\"x\":3.00006175041199,\"y\":-6.0},\"unit5\":{\"x\":0.0117133017629385,\"y\":-4.76050519943237}},\"toUnits\":{\"unit2\":{\"x\":3.0,\"y\":6.0},\"unit1\":{\"x\":5.57653766009025E-06,\"y\":6.0},\"unit5\":{\"x\":-2.11999988555908,\"y\":3.34999990463257},\"unit3\":{\"x\":-1.9099999666214,\"y\":4.32000017166138},\"unit4\":{\"x\":-3.0,\"y\":6.0}}}";


        DataPack dp = JsonTools.toBean(a, DataPack.class);

        System.out.println(dp);
    }
}
