package com.lyl.activiti.demo.task.receive;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

public class ReveiveTaskDemo {
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
		deploymentBuilder.addClasspathResource("diagram/receive.bpmn");
		deploymentBuilder.addClasspathResource("diagram/receive.png");
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy.getId());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void testStartDeployment() {
		String processDefinitionId = "receiveTask:1:90004";
		ProcessInstance processInstanceById = processEngine.getRuntimeService()
				.startProcessInstanceById(processDefinitionId);
		System.out.println(processInstanceById.getId());
	}

	/**
	 * 办理个人任务
	 */
	@Test
	public void testTaskComplete() {
		String taskId = "97502";
		processEngine.getTaskService().complete(taskId);
	}
	
	
	/**
	 * 处理接收任务
	 */
	@Test
	public void testTaskSignal() { 
		String executionId = "92501";
		processEngine.getRuntimeService().signal(executionId);
	}
}
