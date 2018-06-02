package com.lyl.activiti.demo.listener.task;

import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 自定义任务监听
 * 
 * @author liuyulong
 *
 */
public class MyTaskListenerComplete implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 监听任务的事件
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		String name = delegateTask.getName();
		String assignee = delegateTask.getAssignee();
		String processInstanceId = delegateTask.getProcessInstanceId();
		Set<String> variableNames = delegateTask.getVariableNames();
		for (String key : variableNames) {
			Object variable = delegateTask.getVariable(key);
			System.out.println(key + ":" + variable);
		}
		System.out.println("一个任务【" + processInstanceId + ":" + name + "】被创建了，由【" + assignee + "】负责办理");

	}

}
