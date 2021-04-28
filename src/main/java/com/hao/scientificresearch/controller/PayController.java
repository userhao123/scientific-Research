package com.hao.scientificresearch.controller;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hao.scientificresearch.config.AlipayConfig;
import com.hao.scientificresearch.entity.CollectInformation;
import com.hao.scientificresearch.entity.Project;
import com.hao.scientificresearch.entity.ProjectFunds;
import com.hao.scientificresearch.service.IProjectFundsService;
import com.hao.scientificresearch.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/Alipay")
public class PayController {

    @Autowired
    private IProjectFundsService projectFundsService;

    @Autowired
    private IProjectService projectService;

    private Integer projectId;
    private String projectName;

    @RequestMapping("/pay")
    @ResponseBody
    public void payController(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);


        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        String str = new String(request.getParameter("projectId").getBytes("ISO-8859-1"), "UTF-8");
        projectId = Integer.valueOf(str);
        projectName = request.getParameter("projectName");
        System.out.println("项目名:"+projectName);

        //商户订单号，商户网站订单系统中唯一订单号，必填，时间戳+4位随机数
        String out_trade_no = System.currentTimeMillis()+RandomUtil.randomNumbers(4);

        //付款金额，必填
        String total_amount = new String(request.getParameter("payMoney").getBytes("ISO-8859-1"), "UTF-8");

        //订单名称，必填
        String subject = RandomUtil.randomString(6);

        //商品描述，可空
        String body = new String("项目经费支付");

        //设置支付宝的请求参数
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","

                + "\"total_amount\":\"" + total_amount + "\","

                + "\"subject\":\"" + subject + "\","

                + "\"body\":\"" + body + "\","

                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //      + "\"total_amount\":\""+ total_amount +"\","
        //      + "\"subject\":\""+ subject +"\","
        //      + "\"body\":\""+ body +"\","
        //      + "\"timeout_express\":\"10m\","
        //      + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
        //请求
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用支付宝SDK生成表单
        } catch (AlipayApiException e) {
            System.out.println("请求接口发送异常");
            e.printStackTrace();
        }
        System.out.println("表单+++++++++++++++:"+form);

        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().print(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }



    //处理用户付款成功后的异步回调业务代码
    @RequestMapping("/notifyUrl")
    public void notifyUrl(HttpServletRequest request)throws Exception {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if (signVerified) {//验证成功
            //商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            //支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            //交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
//
//            if (trade_status.equals("TRADE_FINISHED")) {
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//                System.out.println("是否已完成交易");
//            } else if (trade_status.equals("TRADE_SUCCESS")) {
//                //判断该笔订单是否在商户网站中已经做过处理
//                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                //如果有做过处理，不执行商户的业务程序
//
//                //注意：
//                //付款完成后，支付宝系统发送该交易状态通知
//                System.out.println("交易成功");
//            }

            System.out.println("服务端的验证成功success");

        } else {//验证失败
            System.out.println("服务端验证失败fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

    }





    //处理用户支付成功后的同步回调业务代码
    @RequestMapping("/returnUrl")
    public String  returnUrl(HttpServletRequest request,RedirectAttributes redirectAttributes)throws Exception{
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values =requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //公钥验证
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.sign_type); //调用SDK验证签名

        if(signVerified) {
            System.out.println("支付成功后的回调逻辑处理");
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易金额
            String trade_status = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //收款账号
            String sellerid = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"), "UTF-8");

            ProjectFunds funds = new ProjectFunds();
            funds.setOrderNumber(out_trade_no);
            funds.setPayNumber(trade_no);
            funds.setPayStatus("支付成功");
            funds.setPayTime(LocalDateTime.now());
            funds.setProjectId(projectId);
            funds.setProjectName(projectName);
            funds.setSellerId(sellerid);
            funds.setTotalAmount(trade_status);
            projectFundsService.save(funds);
            //修改项目的ispay字段
            Project project = projectService.getById(projectId);
            project.setIsPay(true);
            projectService.updateById(project);

            System.out.println("订单号:"+out_trade_no);
            System.out.println("支付宝交易号:"+trade_no);
            System.out.println("交易金额"+trade_status);
            System.out.println("收款账号:"+sellerid);
            //使用重定向走接口，直接走页面不会渲染页面
            return "redirect:/index";
        }else {
            String result = "验签失败";
            System.out.println("验签失败后的输出");
            return "/page/welcome";
        }
    }


}
