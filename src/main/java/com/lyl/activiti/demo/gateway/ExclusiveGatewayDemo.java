package com.lyl.activiti.demo.gateway;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

/**
 * 排他网测试
 * 
 * @author liuyulong
 *
 */
public class ExclusiveGatewayDemo {
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
		deploymentBuilder.addClasspathResource("diagram/exclusiveGateway.bpmn");
		deploymentBuilder.addClasspathResource("diagram/exclusiveGateway.png");
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy.getId());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void testStartDeployment() {
		String processDefinitionId = "ExclusiveGateWay:1:5004";
		ProcessInstance processInstanceById = processEngine.getRuntimeService()
				.startProcessInstanceById(processDefinitionId);
		System.out.println(processInstanceById.getId());

		// String processDefinitionKey = "executionListener";
		// ProcessInstance processInstance = processEngine.getRuntimeService()
		// .startProcessInstanceByKey(processDefinitionKey);
		// System.out.println(processInstance.getId());
	}

	/**
	 * 办理个人任务
	 */
	@Test
	public void testTaskComplete() {
		String taskId = "15004";
		TaskService taskService = processEngine.getTaskService();
		Map<String, Object> variables = new HashMap<>();
		variables.put("category", 555);
		taskService.complete(taskId, variables);
	}
}
