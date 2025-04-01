package com.dcu.demo.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalWeatherAdvice {

    @ModelAttribute("currentTemperature")
    public String provideCurrentTemperature() {
        try {
            int nx = 89;
            int ny = 90;

            LocalDateTime now = LocalDateTime.now().minusMinutes(10);
            String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String baseTime = now.format(DateTimeFormatter.ofPattern("HH00"));

            String serviceKey = "0Nf6t6Jssj2XfO591CDqb4/jytWcRqW7elLXQeIqrtZ/g1ipD0Bf/8RohFwsgBNeKZRpGqaOWHRxcjKl+0HqyQ==";
            String encodedKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);

            DefaultUriBuilderFactory builder = new DefaultUriBuilderFactory();
            builder.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

            String uriString = builder.builder()
                    .scheme("https").host("apis.data.go.kr")
                    .path("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
                    .queryParam("serviceKey", encodedKey)
                    .queryParam("numOfRows", "100")
                    .queryParam("pageNo", "1")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", baseDate)
                    .queryParam("base_time", baseTime)
                    .queryParam("nx", nx)
                    .queryParam("ny", ny)
                    .build().toString();

            URI uri = URI.create(uriString);
            RestTemplate rt = new RestTemplate();
            String json = rt.getForObject(uri, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode items = objectMapper.readTree(json)
                    .path("response").path("body").path("items").path("item");

            String temperature = null;
            String pty = null;

            for (JsonNode item : items) {
                String category = item.path("category").asText();
                String value = item.path("obsrValue").asText();

                if (category.equals("T1H")) {
                    temperature = value;
                } else if (category.equals("PTY")) {
                    pty = value;
                }
            }

            if (temperature != null && pty != null) {
                String weatherDesc;
                String icon;

                switch (pty) {
                    case "0" -> {
                        weatherDesc = "맑음";
                        icon = "☀️";
                    }
                    case "1" -> {
                        weatherDesc = "비";
                        icon = "🌧️";
                    }
                    case "2" -> {
                        weatherDesc = "비/눈";
                        icon = "🌨️";
                    }
                    case "3" -> {
                        weatherDesc = "눈";
                        icon = "❄️";
                    }
                    case "4" -> {
                        weatherDesc = "소나기";
                        icon = "🌦️";
                    }
                    default -> {
                        weatherDesc = "알 수 없음";
                        icon = "🌈";
                    }
                }

                return temperature + "℃ / " + icon + " " + weatherDesc;
            }

        } catch (Exception e) {
            return "N/A";
        }

        return "N/A";
    }
}
