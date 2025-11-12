package app.controllers;

import java.math.BigDecimal;
import java.util.*;

class Data {
    private String st_code;
    private String component_code;
    private String component_name;
    private BigDecimal expenditure;

    public Data(String st_code, String component_code, String component_name, BigDecimal expenditure) {
        this.st_code = st_code;
        this.component_code = component_code;
        this.component_name = component_name;
        this.expenditure = expenditure;
    }

    public String getSt_code() { return st_code; }
    public String getComponent_code() { return component_code; }
    public String getComponent_name() { return component_name; }
    public BigDecimal getExpenditure() { return expenditure; }
}

public class TestNew {
    public static void main(String[] args) {
        List<Data> list = Arrays.asList(
            new Data("KA", "C1", "Health", new BigDecimal("100")),
            new Data("KA", "C1", "Health", new BigDecimal("150")),
            new Data("MH", "C2", "Education", new BigDecimal("200"))
        );

        Map<String, BigDecimal> stcmpMap = new HashMap<>();
        Map<String, String> cmpntMap = new HashMap<>();

        list.forEach(s -> {
            String key = s.getSt_code() + "," + s.getComponent_code();

            // Aggregate expenditures by (state, component)
            if (!stcmpMap.containsKey(key)) {
                stcmpMap.put(key, s.getExpenditure());
            } else {
                stcmpMap.put(key, stcmpMap.get(key).add(s.getExpenditure()));
            }

            // Save component code → component name mapping
            if (!cmpntMap.containsKey(s.getComponent_code())) {
                cmpntMap.put(s.getComponent_code(), s.getComponent_name() + ".");
            }
        });

        System.out.println("State-Component Expenditures: " + stcmpMap);
        System.out.println("Component Names: " + cmpntMap);
    }
}

