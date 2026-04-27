package com.example.paymentService.service;

import com.example.paymentService.Model.AppUser;
import com.example.paymentService.Model.PaymentPaystack;
import com.example.paymentService.Wrapper.ModelWrappers;
import com.example.paymentService.dto.AppUserDto;
import com.example.paymentService.dto.CreatePlanDto;
import com.example.paymentService.dto.InitializePaymentDto;
import com.example.paymentService.dto.OrderDto;
import com.example.paymentService.repository.AppUserRepository;
import com.example.paymentService.repository.PaymentRepository;
import com.example.paymentService.repository.OrderRepository;
import com.example.paymentService.response.CreatePlanResponse;
import com.example.paymentService.response.InitializePaymentResponse;
import com.example.paymentService.response.PaymentVerificationResponse;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import static com.example.paymentService.Constant.APIConstant.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private OrderRepository planRepository;

    @Autowired
    private OrderService orderService;

    @Value("${applyforme.paystack.secret.key}")
    private String paystackSecretKey;
    //this variable for creating logs during payment verification
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    /***
     * This function creates a plan on the paystack for subscription
     * @param plan
     * @return
     * @throws Exception
     */
    public CreatePlanResponse createPlan(OrderDto plan) throws Exception {
        CreatePlanResponse createPlanResponse  = null;
        CreatePlanDto createPlanDto = CreatePlanDto.builder()
                .name("purchase")
                .interval(plan.getInterval())
                .amount(plan.getAmount())
                .build();
        try{
            Gson gson = new Gson();
            StringEntity stringEntity = new StringEntity(gson.toJson(createPlanDto));
            HttpPost httpPost = new HttpPost(PAYSTACK_INIT);
            CloseableHttpClient client = HttpClients.createDefault();
            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Authorization", "Bearer "+ paystackSecretKey);
            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println(response);
            StringBuilder result = new StringBuilder();

            if(response.getStatusLine().getStatusCode() == 201){
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                System.out.println(result.toString());
            }
            else{
                throw new Exception("Failed : HTTP error code : " + response.getStatusLine().getStatusCode() +
                        "paystack cannot process this request");

            }
            ObjectMapper objectMapper = new ObjectMapper();
            createPlanResponse = objectMapper.readValue(result.toString(), CreatePlanResponse.class);
            System.out.println(createPlanResponse.getStatus());


        }
        catch (Throwable e){
            e.printStackTrace();
        }
        planRepository.save(ModelWrappers.MapperToPlan(plan));
        return createPlanResponse;

    }

    /***
     *
     * @param order
     *  the order recieved from the user or front end stores the user other to verify payment so
     *  reciept can be generated
     *  cloudfare url changes but best for testing environment to create tunnels for testing webhooks
     * @return
     * @throws Exception
     */
    public InitializePaymentResponse initializePayment(OrderDto order) throws Exception {
        InitializePaymentResponse initializePaymentResponse = null;
        InitializePaymentDto initializePaymentDto = InitializePaymentDto.builder()
                .amount(order.getAmount()).
                email(order.getEmail()).
                currency("NGN").
                channel("bank").
                callbackUrl("https://yesterday-realize-drainage-essentially.trycloudflare.com/verify").
                build();
        try{
            Gson gson = new Gson();
            StringEntity stringEntity = new StringEntity(gson.toJson(initializePaymentDto));
            HttpPost httpPost = new HttpPost(PAYSTACK_INITIALIZE);
            CloseableHttpClient client = HttpClients.createDefault();
            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Authorization", "Bearer "+ paystackSecretKey);
            CloseableHttpResponse response = client.execute(httpPost);
            StringBuilder result = new StringBuilder();


            if(response.getStatusLine().getStatusCode() == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                br.close();
                System.out.println(result.toString());

            }
            else{
                throw new Exception( response.getStatusLine().getStatusCode()+" "+
                        response.getStatusLine().getReasonPhrase() + " :paystack cannot process this request");

            }
            ObjectMapper objectMapper = new ObjectMapper();
            initializePaymentResponse = objectMapper.readValue(result.toString(), InitializePaymentResponse.class);
            order.setReference(initializePaymentResponse.getData().getReference());
            orderService.saveOrder(order);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
        return initializePaymentResponse;

    }

    /***
     *
     * @param paymentVerificationResponse received from paystack to verify the payment
     *                                   so webhooks is reference from paystack call back url should be used for
     *                                    front end paystack while webhooks should communicate with server
     * @return
     * @throws Exception
     */
    public PaymentVerificationResponse verifyPayment(PaymentVerificationResponse paymentVerificationResponse) throws Exception {
        logger.info("payment verification started");
        PaymentPaystack paymentPaystack =null;
        logger.info(paymentVerificationResponse.toString());

        try{

            if(!(paymentVerificationResponse.getEvent().equals("charge.success"))){
                throw new Exception("Payment Verification Failed");
            }
            else if(paymentVerificationResponse.getEvent().equals("charge.success")){
                AppUser appUser = appUserRepository.findById((long) 1).orElse(null);
                paymentPaystack = PaymentPaystack.builder()
                        .appUser(appUser)
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .createdOn(new Date())
                        .build();
            }
        } catch (Exception e) {
            throw new Exception("paystack");
        }
        paymentRepository.save(paymentPaystack);
        logger.info("payment verification finished");
        return paymentVerificationResponse;
    }

    public void createUser(AppUserDto appUser){
        appUserRepository.save(AppUser.builder()
                .creationDate(new Date())
                .email(appUser.getEmail())
                .address(appUser.getAddress())
                .username(appUser.getUsername())
                .build());
    }



}
