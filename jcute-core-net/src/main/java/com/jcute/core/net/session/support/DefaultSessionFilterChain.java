package com.jcute.core.net.session.support;

import com.jcute.core.net.session.Session;
import com.jcute.core.net.session.SessionFilter;

public class DefaultSessionFilterChain extends AbstractSessionFilterChain{

	private final boolean reversed;
	private final SessionFilter[] filters;

	private Session session;

	private SessionFilter dispatchsFilter;
	private SessionFilter operationFilter;
	private SessionFilter decoderFilter;
	private SessionFilter handlerFilter;

	private int current = -1;

	public DefaultSessionFilterChain(Session session,boolean reversed,SessionFilter operationFilter,SessionFilter[] filters){
		this.session = session;
		this.operationFilter = operationFilter;
		this.reversed = reversed;
		this.filters = filters;
		this.current = reversed ? this.filters.length : -1;
	}

	@Override
	protected SessionFilter nextFilter(){
		SessionFilter filter = null;
		if(null != this.dispatchsFilter){
			filter = this.dispatchsFilter;
			this.dispatchsFilter = null;
		}else if((this.reversed && --this.current >= 0) || (!this.reversed && ++this.current < this.filters.length)){
			filter = this.filters[this.current];
		}else if(null != this.operationFilter){
			filter = this.operationFilter;
			this.operationFilter = null;
		}else if(null != this.decoderFilter){
			filter = this.decoderFilter;
			this.decoderFilter = null;
		}else{
			filter = this.handlerFilter;
			this.handlerFilter = null;
		}
		return filter;
	}

	@Override
	public Session getSession(){
		return this.session;
	}

}