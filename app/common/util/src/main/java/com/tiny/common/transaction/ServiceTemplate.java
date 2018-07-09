/**
 * AbstractTransactionTemplate.java
 *
 *
 */
package com.tiny.common.transaction;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tiny.common.base.BaseResult;
import com.tiny.common.base.CommonResult;
import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class ServiceTemplate {
	/**
	 * logger
	 */
	private static final Logger				logger	= Logger.getLogger(ServiceTemplate.class);

	/**
	 * txManager
	 */
	private DataSourceTransactionManager	txManager;

	/**
	 * @param baseResult
	 * @param action
	 */
	public <T> void executeWithoutTransaction(CommonResult<T> baseResult, ServiceCheckCallback action) {
		try {
			action.check();
			action.doTransaction();
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeWithoutTransaction error");
			baseResult.marketFail(e.getMessage());
		}
	}

	/**
	 * @param baseResult
	 * @param action
	 */
	public <T> void executeWithoutTransaction(CommonResult<T> baseResult, ServiceCallback action) {
		try {
			action.doTransaction();
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeWithoutTransaction error");
			baseResult.marketFail(e.getMessage());
		}
	}

	/**
	 * @param action
	 * @return
	 */
	public BaseResult executeWithoutTransaction(ServiceCheckCallback action) {
		BaseResult baseResult = new BaseResult(true);
		try {
			action.check();
			action.doTransaction();
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeWithoutTransaction error");
			baseResult.marketFail(e.getMessage());
		}
		return baseResult;
	}

	/**
	 * @param action
	 * @return
	 */
	public BaseResult executeWithoutTransaction(ServiceCallback action) {
		BaseResult baseResult = new BaseResult(true);
		try {
			action.doTransaction();
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeWithoutTransaction error");
			baseResult.marketFail(e.getMessage());
		}
		return baseResult;
	}

	/********************************************************with_transaction*************************************************************************************/
	/**
	 * @param baseResult
	 * @param action
	 */
	public <T> void executeTransaction(CommonResult<T> baseResult, ServiceCheckCallback action) {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus txnStatus = txManager.getTransaction(definition);
		boolean isCommited = false;
		LogUtil.debug(logger, "begin transaction txnStatus={0}", txnStatus);
		try {
			action.check();
			action.doTransaction();
			isCommited = true;
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeTransaction error");
			baseResult.marketFail(e.getMessage());
		} finally {
			if (isCommited) {
				txManager.commit(txnStatus);
				LogUtil.debug(logger, "commit transaction txnStatus={0}", txnStatus);
			} else {
				LogUtil.error(logger, "Rollback transaction txnStatus={0}", txnStatus);
				txManager.rollback(txnStatus);
			}
		}
	}

	/**
	 * @param baseResult
	 * @param action
	 */
	public <T> void executeTransaction(CommonResult<T> baseResult, ServiceCallback action) {
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus txnStatus = txManager.getTransaction(definition);
		boolean isCommited = false;
		LogUtil.debug(logger, "begin transaction txnStatus={0}", txnStatus);
		try {
			action.doTransaction();
			isCommited = true;
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeTransaction error");
			baseResult.marketFail(e.getMessage());
		} finally {
			if (isCommited) {
				txManager.commit(txnStatus);
				LogUtil.debug(logger, "commit transaction txnStatus={0}", txnStatus);
			} else {
				LogUtil.error(logger, "Rollback transaction txnStatus={0}", txnStatus);
				txManager.rollback(txnStatus);
			}
		}
	}

	/**
	 * @param action
	 * @return
	 */
	public BaseResult executeTransaction(ServiceCheckCallback action) {
		BaseResult baseResult = new BaseResult(true);
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus txnStatus = txManager.getTransaction(definition);
		boolean isCommited = false;
		LogUtil.debug(logger, "begin transaction txnStatus={0}", txnStatus);
		try {
			action.check();
			action.doTransaction();
			isCommited = true;
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeTransaction error");
			baseResult.marketFail(e.getMessage());
		} finally {
			if (isCommited) {
				txManager.commit(txnStatus);
				LogUtil.debug(logger, "commit transaction txnStatus={0}", txnStatus);
			} else {
				LogUtil.error(logger, "Rollback transaction txnStatus={0}", txnStatus);
				txManager.rollback(txnStatus);
			}
		}
		return baseResult;
	}

	/**
	 * @param action
	 * @return
	 */
	public BaseResult executeTransaction(ServiceCallback action) {
		BaseResult baseResult = new BaseResult(true);
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus txnStatus = txManager.getTransaction(definition);
		boolean isCommited = false;
		LogUtil.debug(logger, "begin transaction txnStatus={0}", txnStatus);
		try {
			action.doTransaction();
			isCommited = true;
		} catch (Exception e) {
			LogUtil.error(logger, e, "executeTransaction error");
			baseResult.marketFail(e.getMessage());
		} finally {
			if (isCommited) {
				txManager.commit(txnStatus);
				LogUtil.debug(logger, "commit transaction txnStatus={0}", txnStatus);
			} else {
				LogUtil.error(logger, "Rollback transaction txnStatus={0}", txnStatus);
				txManager.rollback(txnStatus);
			}
		}
		return baseResult;
	}

	/**
	 * @param txManager the txManager to set
	 */
//	public void setTxManager(DataSourceTransactionManager txManager) {
//		this.txManager = txManager;
//	}

}
