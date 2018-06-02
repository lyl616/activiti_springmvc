package com.lyl.activiti.demo.listener.task;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

public class TaskListernerDemo {

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
		deploymentBuilder.addClasspathResource("diagram/taskListener.bpmn");
		deploymentBuilder.addClasspathResource("diagram/taskListener.png");
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy.getId());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void testStartDeployment() {
		// String processDefinitionId = "executionListener:1:102504";
		// ProcessInstance processInstanceById =
		// processEngine.getRuntimeService()
		// .startProcessInstanceById(processDefinitionId);
		// System.out.println(processInstanceById.getId());

		String processDefinitionKey = "taskListener";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("key", "value1");
		variables.put("key2", "value2");
		variables.put("key3", 2000);
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println(processInstance.getId());
	}

	/**
	 * 办理个人任务
	 */
	@Test
	public void testTaskComplete() {
		String taskId = "107502";
		processEngine.getTaskService().complete(taskId);
	}
}
