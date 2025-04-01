//package com.dcu.demo.weather;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.DefaultUriBuilderFactory;
//import java.net.URI;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Controller
//public class WeatherController {
//
//    @GetMapping("/weather")
//    public String getWeather(Model model) throws Exception {
//        LocalDateTime now = LocalDateTime.now().minusMinutes(10);
//        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));
//
//        String serviceKey = "0Nf6t6Jssj2XfO591CDqb4/jytWcRqW7elLXQeIqrtZ/g1ipD0Bf/8RohFwsgBNeKZRpGqaOWHRxcjKl+0HqyQ==";
//        String encodedKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
//
//        var builder = new DefaultUriBuilderFactory();
//        builder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
//
//        String uriString = builder.builder()
//                .scheme("https").host("apis.data.go.kr")
//                .path("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
//                .queryParam("serviceKey", encodedKey)
//                .queryParam("numOfRows", "100")
//                .queryParam("pageNo", "1")
//                .queryParam("dataType", "JSON")
//                .queryParam("base_date", baseDate)
//                .queryParam("base_time", baseTime)
//                .queryParam("nx", "89").queryParam("ny", "90")
//                .build().toString();
//
//        URI uri = URI.create(uriString);
//        RestTemplate restTemplate = new RestTemplate();
//        String json = restTemplate.getForObject(uri, String.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode root = objectMapper.readTree(json);
//        JsonNode items = root.path("response").path("body").path("items").path("item");
//
//        String temperature = "";
//        for (JsonNode item : items) {
//            if (item.path("category").asText().equals("T1H")) {
//                temperature = item.path("obsrValue").asText();
//                break;
//            }
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");
//        String formattedDateTime = now.format(formatter);
//
//        model.addAttribute("formattedDateTime", formattedDateTime);
//        model.addAttribute("temperature", temperature);
//        model.addAttribute("baseDate", baseDate);
//        model.addAttribute("baseTime", baseTime);
//
//        return "weather";
//    }
//}
