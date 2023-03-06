package com.example.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("setVariablesDelegate")
@Slf4j
public class SetVariablesDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Start - SetVariablesDelegate");
        Map<String, String> variables = new HashMap<>();
        variables.put("1", "camunda_history_1");
        variables.put("2", "camunda_history_2");
        variables.put("3", "camunda_history_3");
        delegateExecution.setVariables(variables);
        log.info("End - SetVariablesDelegate");
    }
}
