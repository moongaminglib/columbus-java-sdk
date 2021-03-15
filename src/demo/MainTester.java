package demo;

import com.columbus.utils.RsaUtil;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MainTester {

    public static void main(String[] args) {
        RsaUtil rsaUtil = new RsaUtil();
        String signature = URLDecoder.decode(
        "5%2FZ2H7pNkRmt8X1vDYah6E8acdQ8%2BOUVkSMBtx7oEqDnb1043yQTtOkgKelUyHdrNpnRmmJJdvN3JQXtlCSeJfkxLxi0QWeVIJ%2Fbm8e2PetnnplvUntirR8c3366R%2FiiV%2B%2BnVnw4RRnAvbnJ0wmELeVF77Z53mRZvwkaZooQ9B8%3D");
        System.out.println("signature :" + signature);
        // post
        Map<String, String> params = new HashMap<>();
        params.put("app_id", "38");
        params.put("out_trade_no", "491000893418741760");
        params.put("openid", "68tehkf9W2tCEEGnPBZwYGPMKQuahmkng");
        params.put("status", "3");
        params.put("amount", "99");
        params.put("real_amount", "99");
        params.put("createtime", "1568641335");
        params.put("confirm_time", "1568700605");

        String original_str = rsaUtil.mapToString(params);
        System.out.println("original_str:" + original_str);
        System.out.println("sig Result：" + signature);
        if (rsaUtil.verifyByPublicKey(signature, original_str)) {
            System.out.println("success");
        } else {
            System.out.println("failure");
        }
    }
}
