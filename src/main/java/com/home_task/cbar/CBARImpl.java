package com.home_task.cbar;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home_task.entity.CurrencyEntity;
import com.home_task.enums.CBAR_URL;
import com.home_task.exception.CurrencyException;
import com.home_task.exception.ExceptionConstants;
import com.home_task.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CBARImpl implements CBAR {

    private final CurrencyRepository currencyRepository;

    @Override
    public List<CurrencyEntity> updateCurrencyes() {
        List<CurrencyEntity> returnList = new ArrayList<>();
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = dateFormat.format(today) + ".xml";

        try {
            URL url = new URL(CBAR_URL.URL.value + dateText);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputline;
                StringBuffer responseString = new StringBuffer();

                while ((inputline = bufferedReader.readLine()) != null) {
                    responseString.append(inputline);
                }
                bufferedReader.close();
                JSONObject jsonObject = XML.toJSONObject(String.valueOf(responseString));

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonObject.toString());

                List<CurrencyEntity> currencyEntities = new ArrayList<>();

                for (JsonNode valuteNode : jsonNode.get("ValCurs").get("ValType").get(1).get("Valute")) {
                    CurrencyEntity currencyEntity = new CurrencyEntity();
                    currencyEntity.setCode(valuteNode.get("Code").asText());
                    currencyEntity.setNominal(valuteNode.get("Nominal").asDouble());
                    currencyEntity.setName(valuteNode.get("Name").asText());
                    currencyEntity.setValue(valuteNode.get("Value").decimalValue());

                    currencyEntities.add(currencyEntity);
                }

                if (!currencyEntities.isEmpty()) {
                    currencyEntities.sort(Comparator.comparing(CurrencyEntity::getValue).reversed());
                    currencyRepository.deleteAll();
                    currencyRepository.saveAll(currencyEntities);
                    returnList = currencyEntities;
                }
            } else {
                throw new CurrencyException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }

        } catch (CurrencyException ce) {
            ce.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }
}

