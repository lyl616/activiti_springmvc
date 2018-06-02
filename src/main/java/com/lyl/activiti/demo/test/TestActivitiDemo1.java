package com.lyl.activiti.demo.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

public class TestActivitiDemo1 {

	@Test
	public void createTableTest() {
		// 引擎配置
		ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		pec.setJdbcDriver("com.mysql.jdbc.Driver");
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/activiti_demo");
		pec.setJdbcUsername("root");
		pec.setJdbcPassword("root");

		/**
		 * false 不能自动创建表 create-drop 先删除表再创建表 true 自动创建和更新表
		 */
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		// 获取流程引擎对象
		ProcessEngine processEngine = pec.buildProcessEngine();
		System.out.println(processEngine);
	}

	/**
	 * 使用xml配置 简化
	 */
	@Test
	public void testCreateTableWithXml() {
		// 引擎配置
		ProcessEngineConfiguration pec = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		// 获取流程引擎对象
		ProcessEngine processEngine = pec.buildProcessEngine();
		System.out.println(processEngine);
	}

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义(操作数据表：act_re_deployment、act_re_procdef、act_ge_bytearray)
	 */
	@Test
	public void testDeployment() {

		// 获得一个部署构建器对象，用于加载流程定义文件（MyFirstProcess.bpmn,MyFirstProcess.png）完成流程定义的部署
		DeploymentBuilder builder = processEngine.getRepositoryService().createDeployment();
		// 加载流程定义文件
		builder.addClasspathResource("diagram/MyFirstProcess.bpmn");
		builder.addClasspathResource("diagram/MyFirstProcess.png");
		// 部署流程定义
		Deployment deployment = builder.deploy();
		System.out.println(deployment.getId());
	}

	/**
	 * 查询流程定义列表
	 */
	@Test
	public void testProcessDefinitionQuery() {
		/*
		 * processEngine.getRepositoryService().createDeploymentQuery().list();
		 * processEngine
		 * .getRuntimeService().createProcessInstanceQuery().list();
		 * processEngine.getTaskService().createTaskQuery().list();
		 */

		// 流程定义查询对象,用于查询表act_re_procdef
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		// 添加过滤条件
		query.processDefinitionKey("myFirstProcess");
		// 添加排序条件
		// query.orderByProcessDefinitionVersion().asc();
		query.orderByProcessDefinitionVersion().desc();
		// 添加分页查询
		query.listPage(0, 10);
		List<ProcessDefinition> list = query.list();
		for (ProcessDefinition pd : list) {
			System.out.println(pd.getId());
		}
	}

	/**
	 * 根据流程定义的id启动一个流程实例
	 */
	@Test
	public void testProcessInstance() {
		String processDefinitionId = "myFirstProcess:4:7504";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceById(processDefinitionId);
		System.out.println(processInstance.getId());
	}

	/**
	 * 查询任务列表
	 */
	@Test
	public void testTaskQueryList() {
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		String assignee = "张三";
		query.taskAssignee(assignee);
		List<Task> list = query.list();
		for (Task task : list) {
			System.out.println(task.getId() + " " + task.getName());
		}
	}

	/**
	 * 办理任务
	 */
	@Test
	public void testCompleteTask() {
		String taskId = "15002";
		processEngine.getTaskService().complete(taskId);
	}
}
