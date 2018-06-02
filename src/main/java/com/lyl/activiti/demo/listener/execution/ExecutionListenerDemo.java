package com.lyl.activiti.demo.listener.execution;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lyl.activiti.service.UserService;

public class ExecutionListenerDemo {

	ProcessEngine processEngine = null;

	@Before
	public void testInit() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
	}

	@Test
	public void testGetBean() {
		ApplicationContext act = new ClassPathXmlApplicationContext(
				new String[] { "spring.xml", "spring-mybatis.xml" });
		UserService bean = act.getBean(UserService.class);
		System.out.println(bean);
	}

	/**
	 * 部署流程定义
	 */
	@Test
	public void testDeployment() {
		DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
		deploymentBuilder.addClasspathResource("diagram/executionTask.bpmn");
		deploymentBuilder.addClasspathResource("diagram/executionTask.png");
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

		String processDefinitionKey = "executionListener";
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
