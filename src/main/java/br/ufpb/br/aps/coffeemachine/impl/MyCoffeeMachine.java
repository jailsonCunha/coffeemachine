package br.ufpb.br.aps.coffeemachine.impl;

import java.util.ArrayList;
import java.util.List;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
	
	private int centavos, dolar;
	private ComponentsFactory factory;
	private List<Coin> dime = new ArrayList<Coin>();

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin dime) {
		try {
			
			this.dime.add(dime);
			this.centavos += dime.getValue() % 100;
			this.dolar += dime.getValue() / 100;
			this.factory.getDisplay().info("Total: US$ "+ this.dolar+"."+ this.centavos);
			
		} catch (NullPointerException e) {
			throw new CoffeeMachineException("A moeda inserida foi nula");
		}
				
	}

	public void cancel() {
		if (this.dolar == 0 && this.centavos == 0) {
			
			throw new CoffeeMachineException("Moeda não inserida");
			
		}else{
			this.factory.getDisplay().warn(Messages.CANCEL);
			
			for(Coin ordenado : Coin.reverse()){
				for(Coin moeda : this.dime){
					if(ordenado == moeda){
						this.factory.getCashBox().release(moeda);
					}
				}
			}
		}
		
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		
	}

	public void select(Drink drink) {
		
		if(!this.factory.getCupDispenser().contains(1)){
			this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
			this.factory.getCashBox().release(Coin.quarter);
			this.factory.getCashBox().release(Coin.dime);
			this.factory.getDisplay().info(Messages.INSERT_COINS);
			return;
		}
		
		if(!this.factory.getWaterDispenser().contains(0.1)){
			this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
			this.factory.getCashBox().release(Coin.quarter);
			this.factory.getCashBox().release(Coin.dime);
			this.factory.getDisplay().info(Messages.INSERT_COINS);
			return;
			
		}
				
		if(!this.factory.getCoffeePowderDispenser().contains(0.1)){
			this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			this.factory.getCashBox().release(Coin.quarter);
			this.factory.getCashBox().release(Coin.dime);
			this.factory.getDisplay().info(Messages.INSERT_COINS);
		}else{
			
			if(drink == Drink.BLACK_SUGAR){
				if(!this.factory.getSugarDispenser().contains(0.1)){
					this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
					this.factory.getCashBox().release(Coin.halfDollar);
					this.factory.getDisplay().info(Messages.INSERT_COINS);
					return;
				}
			}
			
			this.factory.getDisplay().info(Messages.MIXING);
			this.factory.getCoffeePowderDispenser().release(0.1);
			this.factory.getWaterDispenser().release(0.1);
			
			if(drink == Drink.BLACK_SUGAR){
				this.factory.getSugarDispenser().release(0.1);
			}
			
			this.factory.getDisplay().info(Messages.RELEASING);
			this.factory.getCupDispenser().release(1);
			this.factory.getDrinkDispenser().release(0.1);
			
			this.factory.getDisplay().info(Messages.TAKE_DRINK);
			this.factory.getDisplay().info(Messages.INSERT_COINS);
			this.dime.clear();			
		}
		
	}


}

