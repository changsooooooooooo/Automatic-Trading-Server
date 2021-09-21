package com.autotradeserver.dto.coinsector;

import com.autotradeserver.repository.CoinDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class CoinSendDTO {

    private final CoinDBRepository coinDBRepository;

    @Value("${coin.msg.ticket}")
    private JSONObject ticket;

    @Value("${coin.msg.body}")
    private JSONObject body;

    @Value("${coin.msg.format}")
    private JSONObject format;

    public JSONArray makeJsonArray(String candidate){
        List<JSONObject> jsonList = new ArrayList<>();
        body.put("codes", List.of(candidate));
        jsonList.add(new JSONObject(ticket.toString()));
        jsonList.add(new JSONObject(body.toString()));
        jsonList.add(new JSONObject(format.toString()));
        return new JSONArray(jsonList);
    }

    public List<JSONArray> makeMsgList(String theme) {
        List<JSONArray> msgList = new ArrayList<>();
        List<String> candidates = coinDBRepository.findCoinCadidatesByTheme(theme);
        for(String candidate : candidates){
            JSONArray jsonArray = makeJsonArray(candidate);
            msgList.add(jsonArray);
        }
        msgList.stream()
                .forEach(x-> System.out.println(x));
        return msgList;
    }
}
