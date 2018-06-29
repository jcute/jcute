package com.jcute.core.bean;

import com.jcute.core.bean.exception.BeanInstanceCreateException;
import com.jcute.core.bean.exception.BeanInstanceDestoryException;
import com.jcute.core.bean.exception.BeanInstanceInitialException;
import com.jcute.core.bean.exception.BeanInstanceInjectException;
import com.jcute.core.bean.exception.BeanInstanceReleaseException;

public interface BeanInstanceCycle{

	public void onInitial() throws BeanInstanceInitialException;

	public void onDestory() throws BeanInstanceDestoryException;

	public void onRelease() throws BeanInstanceReleaseException;

	public void onCreate() throws BeanInstanceCreateException;

	public void onInject() throws BeanInstanceInjectException;

}