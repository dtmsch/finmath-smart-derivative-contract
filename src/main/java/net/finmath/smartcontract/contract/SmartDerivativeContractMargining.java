/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 7 Oct 2018
 */

package net.finmath.smartcontract.contract;

import java.time.LocalDateTime;

import net.finmath.smartcontract.oracle.ValuationOracle;

/**
 * The margin agreement of a smart derivative contract.
 * The agreement consists of
 * <ul>
 * <li>a valuation oracle, implementing <code>net.finmath.smartcontract.oracle.ValuationOracle</code></li>
 * </ul>
 *
 * The accrual of the collateral is assumed to e consistent with the valuation, hence, the accrued collateral
 * can be determined from calling <code>getValue(marginPeriodEnd, marginPeriodStart)</code>.
 *
 * @author Christian Fries
 * @see net.finmath.smartcontract.oracle.ValuationOracle
 */
public class SmartDerivativeContractMargining {

	private final ValuationOracle derivativeValuationOracle;

	public SmartDerivativeContractMargining(final ValuationOracle derivativeValuationOracle) {
		super();
		this.derivativeValuationOracle = derivativeValuationOracle;
	}

	/**
	 * Get the margin of the contract based on the valuation oracles.
	 *
	 * @param marginPeriodStart Period start time of the margin period.
	 * @param marginPeriodEnd Period end time of the margin period.
	 * @return The margin.
	 */
	public Double getMargin(final LocalDateTime marginPeriodStart, final LocalDateTime marginPeriodEnd) {

		final double valueDerivativeCurrent = derivativeValuationOracle.getValue(marginPeriodEnd, marginPeriodEnd);
		final double valueDerivativePrevious = derivativeValuationOracle.getValue(marginPeriodEnd, marginPeriodStart);

		final double valuationChange = valueDerivativeCurrent - valueDerivativePrevious;

		final double margin = valuationChange;

		return margin;
	}
}
