package br.ufpb.br.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
	
	private int centavos, dolar;
	private ComponentsFactory factory;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin dime) {
		try {
			
			this.centavos += dime.getValue() % 100;
			this.dolar += dime.getValue() / 100;
			this.factory.getDisplay().info("Total: US$ "+ this.dolar+"."+ this.centavos);
			
		} catch (NullPointerException e) {
			throw new CoffeeMachineException("A moeda inserida foi nula");
		}
				
	}

	public void cancel() {
		if(this.dolar == 0 && this.centavos == 0){
			throw new CoffeeMachineException("Moeda não inserida");
		}else{
			this.factory.getDisplay().warn(Messages.CANCEL_MESSAGE);
			this.factory.getCashBox().release(Coin.halfDollar);
			this.factory.getCashBox().release(Coin.nickel);
			this.factory.getCashBox().release(Coin.penny);
			this.factory.getCashBox().release(Coin.quarter);
			this.factory.getCashBox().release(Coin.quarter);
			this.factory.getDisplay().info(Messages.INSERT_COINS_MESSAGE);
		}
	}

}
