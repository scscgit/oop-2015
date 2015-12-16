/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 *
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;
import sk.tuke.yolkfolk.collectables.BackpackImpl;

/**
 * Abstraktna implementacia hraca.
 *
 * Created by Steve on 2.12.2015.
 */
public abstract class AbstractPlayer extends AbstractAnimatedActor implements Player
{
	//Variables
	private boolean isFlyable;
	private float jumpHeight;
	private int diamonds;
	private int energy;

	//Variables for falling purposes
	private float fallPosition;
	private float fallLimit;
	private float fallDamageRatio;

	//Objects
	private BackpackImpl backpack;
	private PlayerState currentState;

	public AbstractPlayer(String name, String animationString, int animationX, int animationY)
	{
		//Player vyzaduje vytvorenie zakladnych parametrov
		super(name,animationString,animationX,animationY);
		this.fallPosition = getY();
		setEnergy(MAX_HP);
		setFlyable(false);

		//Default parametre hraca
		setDiamonds(0);
		setStep(0); //Inicializacia dlzky kroku pohybu hraca na hodnotu 0, kazdy hrac musi tuto hodnotu prepisat ak sa chce pohybovat
		setJumpHeight(0); //Inicializacia vysky skoku

		//Default parametre tykajuce sa padania
		setFallLimit(0);
		setFallDamageRatio(100);

		//Inicializacia objektov patriacich hracovi
		backpack = new BackpackImpl(this);
	}

	@Override
	public void act()
	{
		//Operacie s ostatnymi actormi vo svete
		for(Actor actor: getWorld())
		{
			if (actor != null && actor.intersects(this))
			{
				actOnIntersect(actor);
			}
		}

		//Vykonanie vsetkych standardnych operacii (ktore spusta pouzivatel z klavesnice) na zaklade aktualneho stavu
		getState().act();
	}

	//Operacie s ostatnymi actormi vo svete, ktorych sa Player dotyka. Ocakava sa implementacia funkcie.
	protected void actOnIntersect(Actor actor)
	{
	}

	@Override
	public String toString()
	{
		return super.toString()+" "+getX()+" "+getY()+" "+getNumberOfDiamonds()+" "+ getEnergy();
	}

	//Nastavi aktualnu poziciu suradnice Y hraca ako novu poziciu dopadu a v pripade velkeho rozdielu od minulej hodnoty udeli hracovi zranenie.
	public final void fall()
	{
		float y = getY();
		float fallDistance = this.fallPosition - y;

		if(getFallDamageRatio() > 0 && fallDistance > 0 && fallDistance > getFallLimit())
		{
			decreaseEnergy((int)(fallDistance*getFallDamageRatio()));
		}
		System.out.println(getPlayer().getName()+" has "+getPlayer().getEnergy()+" HP"); //TODO delete
		this.fallPosition = y;
	}
	protected final float getFallLimit()
	{
		return fallLimit;
	}
	public final void setFallLimit(float fallLimit)
	{
		this.fallLimit = fallLimit;
	}
	protected final float getFallDamageRatio()
	{
		return fallDamageRatio;
	}
	public final void setFallDamageRatio(float fallDamageRatio)
	{
		this.fallDamageRatio = fallDamageRatio;
	}

	public void setJumpHeight(float jumpHeight)
	{
		this.jumpHeight = jumpHeight;
	}
	public float getJumpHeight()
	{
		return this.jumpHeight;
	}

	public boolean isFlyable()
	{
		return this.isFlyable;
	}
	public void setFlyable(boolean flying)
	{
		this.isFlyable = flying;
	}

	public int getNumberOfDiamonds()
	{
		return this.diamonds;
	}
	public void setDiamonds(int diamonds)
	{
		this.diamonds = diamonds;
	}

	public int getEnergy()
	{
		return this.energy;
	}
	public void setEnergy(int energy)
	{
		if(energy > MAX_HP)
		{
			this.energy = MAX_HP;
		}
		else if(energy < 0)
		{
			this.energy = 0;
		}
		else
		{
			this.energy = energy;
		}
	}
	public void decreaseEnergy(int energy)
	{
		setEnergy(getEnergy()-energy);
	}

	public boolean addToBackpack(Actor actor)
	{
		return this.backpack.add(actor);
	}
	public void showBackpack()
	{
		this.backpack.show();
	}
	public void hideBackpack()
	{
		this.backpack.hide();
	}

	//Obsahuje operacie, ktore sa maju vykonat po kazdom uspesnom pohybe (vratane skokov) hraca
	public void afterMovement()
	{
		hideBackpack();
		/*if(!isOnGround()) //TODO check if already works isOnGround with new version, but by then ill probably have it done in some other way
		{
			setState(new PlayerJumping(this));
		}
		else
		{
			setState(new PlayerWalking(this));
		}*/
	}

	//Nastavovanie noveho stavu
	public void setState(PlayerState state)
	{
		this.currentState = state;
	}
	public PlayerState getState()
	{
		if(!(this.currentState instanceof PlayerState))
		{
			this.currentState = defaultState();
		}
		return this.currentState;
	}
	public abstract PlayerState defaultState();
}
