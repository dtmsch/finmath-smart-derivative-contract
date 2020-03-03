package net.finmath.smartcontract.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ContractValuation {
	
	private LocalDateTime marketDataTime;
	private HashMap<String,String> externalReferences;
	private HashMap<String,HashMap<String,String>> counterpartyNames;
	private double value_t1;
	private double value_t2;
	private double margin;
	
	
	public  ContractValuation() {
		super();
	}
	
	
	
	/**
	 * @param marketDataTime time of evaluation
	 * @param externalReferences External references of the counterparts
	 * @param counterpartyNames Counterparty names
	 * @param value_t1 Trade value at t1
	 * @param value_t2 Trade value at t2
	 * @param margin Margin
	 */
	public ContractValuation(LocalDateTime marketDataTime, HashMap<String, String> externalReferences, HashMap<String, HashMap<String,String>> counterpartyNames,double value_t1, double value_t2, double margin) {
		this.externalReferences = externalReferences;
		this.counterpartyNames = counterpartyNames;
		this.value_t1 = value_t1;
		this.value_t2 = value_t2;
		this.margin = margin;
		this.marketDataTime = marketDataTime;
	}
	
	

	/**
	 * @return the externalReferences
	 */
	public HashMap<String, String> getExternalReferences() {
		return externalReferences;
	}
	/**
	 * @param externalReferences the externalReferences to set
	 */
	public void setExternalReferences(HashMap<String, String> externalReferences) {
		this.externalReferences = externalReferences;
	}
	/**
	 * @return the counterpartyNames
	 */
	public HashMap<String, HashMap<String,String>> getCounterpartyNames() {
		return counterpartyNames;
	}
	/**
	 * @param counterpartyNames the counterpartyNames to set
	 */
	public void setCounterpartyNames(HashMap<String, HashMap<String,String>> counterpartyNames) {
		this.counterpartyNames = counterpartyNames;
	}
	/**
	 * @return the value_t1
	 */
	public double getValue_t1() {
		return value_t1;
	}
	/**
	 * @param value_t1 the value_t1 to set
	 */
	public void setValue_t1(double value_t1) {
		this.value_t1 = value_t1;
	}
	/**
	 * @return the value_t2
	 */
	public double getValue_t2() {
		return value_t2;
	}
	/**
	 * @param value_t2 the value_t2 to set
	 */
	public void setValue_t2(double value_t2) {
		this.value_t2 = value_t2;
	}
	/**
	 * @return the margin
	 */
	public double getMargin() {
		return margin;
	}
	/**
	 * @param margin the margin to set
	 */
	public void setMargin(double margin) {
		this.margin = margin;
	}



	/**
	 * @return the marketDataTime
	 */
	public LocalDateTime getMarketDataTime() {
		return marketDataTime;
	}



	/**
	 * @param marketDataTime the marketDataTime to set
	 */
	public void setMarketDataTime(LocalDateTime marketDataTime) {
		this.marketDataTime = marketDataTime;
	}
	
	
	
		
	

}
