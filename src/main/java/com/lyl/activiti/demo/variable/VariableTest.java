package com.lyl.activiti.demo.variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

public class VariableTest {

	ProcessEngine processEngine = null;

	@Before
	public void testInit() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	/**
	 * 部署流程定义
	 */
	@Test
	public void testDeployment() {
		DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
		deploymentBuilder.addClasspathResource("diagram/variable.bpmn");
		deploymentBuilder.addClasspathResource("diagram/variable.png");
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy.getId());
	}

	/**
	 * 设置流程变量方式一：在启动流程实例时设置
	 */
	@Test
	public void testStartProcessInstanceByKey() {
		String processDefinitionKey = "variable";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("key1", "value1");
		variables.put("key2", 2000);
		ProcessInstance startProcessInstanceByKey = this.processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey, variables);
		System.out.println(startProcessInstanceByKey.getId());
	}

	/**
	 * 设置流程变量方式二：在办理任务时设置
	 */
	@Test
	public void testTaskComplete() {
		String taskId = "57505";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("user", new User(1, "小白"));
		processEngine.getTaskService().complete(taskId, variables);

		//
		// Task task =
		// Task task =
		// processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		// String processInstanceId = task.getProcessInstanceId();
		// System.out.println(processInstanceId);

	}

	/**
	 * 设置流程变量方式三：使用runtimeService时设置
	 */
	@Test
	public void testRuntimeServiceVariable() {
		String executionId = "60001";
		String variableName = "key3";
		Object value = "value3";
		processEngine.getRuntimeService().setVariable(executionId, variableName, value);
	}

	/**
	 * 设置流程变量方式四：使用taskService时设置
	 */
	@Test
	public void testTaskServiceVariable() {
		String taskId = "62505";
		String variableName = "key4";
		Object value = "value4";
		processEngine.getTaskService().setVariable(taskId, variableName, value);
	}

	/**
	 * 获取流程变量方式一：使用RuntimeService的方法获取
	 */
	@Test
	public void testTetVariablesRuntimeService() {
		String executionId = "57501";
		Map<String, Object> variables = processEngine.getRuntimeService().getVariables(executionId);
		// System.out.println(variables);
		Set<String> set = variables.keySet();// key2 key1 user
		for (String key : set) {
			Object value = variables.get(key);
			System.out.println(key + " = " + value);
		}

		Object value = processEngine.getRuntimeService().getVariable(executionId, "user");
		System.out.println(value);

		Collection<String> variableNames = new ArrayList<String>();
		variableNames.add("key2");
		variableNames.add("user");
		Map<String, Object> map = processEngine.getRuntimeService().getVariables(executionId, variableNames);
		System.out.println(map);
	}

	/**
	 * 获取流程变量方式二：使用TaskService的方法获取
	 */
	@Test
	public void testTetVariablesTaskService() {
		String taskId = "75005";
		Map<String, Object> variables = processEngine.getTaskService().getVariables(taskId);
		System.out.println(variables);
	}

}
