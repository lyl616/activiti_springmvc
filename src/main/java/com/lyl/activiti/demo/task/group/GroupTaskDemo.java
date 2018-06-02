package com.lyl.activiti.demo.task.group;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

public class GroupTaskDemo {
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
		deploymentBuilder.addClasspathResource("diagram/group.bpmn");
		deploymentBuilder.addClasspathResource("diagram/group.png");
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy.getId());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void testStartDeployment() {
		String processDefinitionId = "groupTask:1:77504";
		ProcessInstance processInstanceById = processEngine.getRuntimeService()
				.startProcessInstanceById(processDefinitionId);
		System.out.println(processInstanceById.getId());
	}

	/**
	 * 办理个人任务
	 */
	@Test
	public void testTaskComplete() {
		String taskId = "85002";
		processEngine.getTaskService().complete(taskId);
	}

	/**
	 * 查询公共任务列表
	 */
	@Test
	public void testQueryPublicTask() {
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
		/**
		 * 根据候选人查询
		 */
		taskQuery.taskCandidateUser("王五");
		List<Task> list = taskQuery.list();
		for (Task task : list) {
			System.out.println(task.getId() + "    " + task.getName());
		}
	}

	/**
	 * 拾取任务 公共任务转为 个人任务
	 */
	@Test
	public void testPubicTask() {
		String taskId = "85002";
		processEngine.getTaskService().claim(taskId, "王五");
	}

	/**
	 * 退回任务 将个人任务转换为公共任务
	 */
	@Test
	public void testTaskBack() {
		String taskId = "85002";
		processEngine.getTaskService().setAssignee(taskId, null);
	}

}
