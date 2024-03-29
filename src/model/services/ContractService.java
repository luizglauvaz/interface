package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, int months) {
	
		double valorBase = contract.getTotalValue()/months;
			
		for (int i = 1; i <= months; i++) {
			 Date date = addMonths(contract.getDate(), i);
			double valorAtualizado = valorBase + onlinePaymentService.interest(valorBase, i);
	        double valorTotal =  valorAtualizado + onlinePaymentService.paymentFee(valorAtualizado);
	        contract.addInstallment(new Installment(date, valorTotal));	
		}
	}
	
	private Date addMonths(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}
}
