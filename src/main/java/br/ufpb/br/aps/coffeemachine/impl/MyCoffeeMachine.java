package br.ufpb.br.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine{
	
	private int centavos, dolar;
	private ComponentsFactory factory;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin dime) {
		this.centavos += dime.getValue() % 100;
		this.dolar += dime.getValue() / 100;
		this.factory.getDisplay().info("Total: US$ "+ this.dolar+"."+ this.centavos);
				
	}

}
