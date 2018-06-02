package com.lyl.activiti.demo.listener.execution;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 自定义监听器
 * 
 * @author liuyulong
 *
 */
public class MyExecutionListenerStart implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 当监听的事件发送时触发
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("自定义的监听开始方法执行了。。。。。。 ");
	}

}
