package com.lyl.activiti.demo.history;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

/**
 * 查询历史数据
 * 
 * @author liuyulong
 *
 */
public class HistoryTaskDemo {

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
		deploymentBuilder.addClasspathResource("diagram/history.bpmn");
		deploymentBuilder.addClasspathResource("diagram/history.png");
		Deployment deploy = deploymentBuilder.deploy();
		System.out.println(deploy.getId());
	}

	/**
	 * 通过流程key 启动流程实例
	 */
	@Test
	public void testStarProcessInstance() {
		ProcessInstance processInstance = this.processEngine.getRuntimeService().startProcessInstanceByKey("history");
		System.out.println(processInstance.getId());
	}

	/**
	 * 查询任务列表
	 */
	@Test
	public void testQUeryTaskList() {
		TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery();
		List<Task> list = taskQuery.list();
		for (Task task : list) {
			System.out.println("id:" + task.getId() + "  name:" + task.getName());
		}
	}

	/**
	 * 查询历史活动数据列表
	 */
	@Test
	public void testHistoricActivityInstance() {
		HistoricActivityInstanceQuery historicActivityInstanceQuery = this.processEngine.getHistoryService()
				.createHistoricActivityInstanceQuery();

		historicActivityInstanceQuery.orderByActivityId().desc();
		historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().asc();
		List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
		for (HistoricActivityInstance hi : list) {
			System.out.println(hi.getActivityId() + " " + hi.getActivityName() + " " + hi.getActivityType());
		}
	}

	/**
	 * 查询历史任务数据列表
	 */
	@Test
	public void testHistoricTaskInstance() {
		HistoricTaskInstanceQuery query = this.processEngine.getHistoryService().createHistoricTaskInstanceQuery();
		query.orderByProcessInstanceId().asc();
		query.orderByHistoricTaskInstanceEndTime().desc();
		List<HistoricTaskInstance> list = query.list();
		for (HistoricTaskInstance hi : list) {
			System.out.println(hi.getAssignee() + " " + hi.getName() + " " + hi.getStartTime());
		}
	}

}
