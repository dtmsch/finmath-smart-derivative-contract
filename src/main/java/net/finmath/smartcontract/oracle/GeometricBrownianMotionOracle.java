/*
 * (c) Copyright Christian P. Fries, Germany. Contact: email@christianfries.com.
 *
 * Created on 6 Oct 2018
 */

package net.finmath.smartcontract.oracle;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.BrownianMotionLazyInit;
import net.finmath.montecarlo.assetderivativevaluation.BlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
import net.finmath.montecarlo.process.ProcessEulerScheme;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.FloatingpointDate;
import net.finmath.time.TimeDiscretizationFromArray;
import net.finmath.time.TimeDiscretization;

/**
 * A dummy oracle which generates values using a geometric Brownian motion.
 *
 * @author Christian Fries
 */
public class GeometricBrownianMotionOracle implements StochasticValuationOracle {

	private final TimeDiscretization timeDiscretization;

	private final LocalDateTime initialTime;
	private final double initialValue;
	private final double riskFreeRate;
	private final double volatility;
	private final int numberOfPaths;

	private transient MonteCarloAssetModel simulation;
	private final Object simulationLazyInitLock = new Object();

	/**
	 * A dummy oracle which generates values using a geometric Brownian motion.
	 *
	 * Using default parameters.
	 *
	 * Caution: The object is initialized with LocalDateTime.now(). This will result in different
	 * Oracles each time the object is instantiated.
	 */
	public GeometricBrownianMotionOracle() {
		this(LocalDateTime.now());
	}

	/**
	 * A dummy oracle which generates values using a geometric Brownian motion.
	 *
	 * Using a given initial time and default parameters.
	 *
	 * @param initialTime The date corresponding to the initial time of the oracle. Valuation prior this time is not provided.
	 */
	public GeometricBrownianMotionOracle(LocalDateTime initialTime) {
		this(initialTime,
				1.0 /* initialValue */,
				20.0 /* timeHorizon */,
				0.02 /* riskFreeRate */,
				0.10 /* volatility */,
				1000 /* numberOfPaths */);
	}

	/**
	 * A dummy oracle which generates values using a geometric Brownian motion.
	 *
	 * Using a given initial time and default parameters.
	 *
	 * @param initialTime The date corresponding to the initial time of the oracle. Valuation prior this time is not provided.
	 * @param initialValue The initial value.
	 * @param timeHorizon The time horizon in ACT/365 from initialTime.
	 * @param riskFreeRate The drift.
	 * @param volatility The volatility.
	 * @param numberOfPaths The number of simulation path to generate.
	 */
	public GeometricBrownianMotionOracle(LocalDateTime initialTime, double initialValue, double timeHorizon, double riskFreeRate, double volatility, int numberOfPaths) {
		this(new TimeDiscretizationFromArray(0.0, timeHorizon, 1.0/365.0, TimeDiscretizationFromArray.ShortPeriodLocation.SHORT_PERIOD_AT_END),
				initialTime,
				initialValue,
				riskFreeRate,
				volatility,
				numberOfPaths);
	}

	public GeometricBrownianMotionOracle(TimeDiscretization timeDiscretization, LocalDateTime initialTime,
			double initialValue, double riskFreeRate, double volatility, int numberOfPaths) {
		super();
		this.timeDiscretization = timeDiscretization;
		this.initialTime = initialTime;
		this.initialValue = initialValue;
		this.riskFreeRate = riskFreeRate;
		this.volatility = volatility;
		this.numberOfPaths = numberOfPaths;
	}

	private void init() {
		final int numberOfFactors = 1;
		final int seed = 31415;

		simulation = new MonteCarloAssetModel(
				new BlackScholesModel(initialValue, riskFreeRate, volatility),
				new ProcessEulerScheme(new BrownianMotionLazyInit(timeDiscretization, numberOfFactors, numberOfPaths, seed)));
	}

	@Override
	public RandomVariable getValue(LocalDateTime evaluationTime) {
		synchronized (simulationLazyInitLock) {
			if(simulation == null) {
				init();
			}
		}

		double time = FloatingpointDate.getFloatingPointDateFromDate(initialTime, evaluationTime);

		int timeIndexOfLastFixing = timeDiscretization.getTimeIndexNearestLessOrEqual(time);
		double timeOfLastFixing = timeDiscretization.getTime(timeIndexOfLastFixing);

		RandomVariable value = null;
		try {
			value = simulation.getAssetValue(timeOfLastFixing, 0);
		}
		catch(CalculationException e) {
			Logger.getLogger("net.finmath.smartcontract").warning("Oracle valuation failed with " + e.getCause());
		}

		return value;
	}
}
