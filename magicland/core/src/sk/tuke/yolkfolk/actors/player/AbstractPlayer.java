/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.PhysicsHelper;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.collectables.BackpackImpl;
import sk.tuke.yolkfolk.input.CustomInput;

/**
 * Abstraktna implementacia hraca.
 * <p/>
 * Created by Steve on 2.12.2015.
 */
public abstract class AbstractPlayer extends AbstractStatefulPlayer
{
	//Variables
	private int diamonds;
	private int energy;
	private float lastX;
	private boolean lastXequals;

	//Objects
	private BackpackImpl backpack;

	public AbstractPlayer(String name, String animationString, int animationX, int animationY)
	{
		//Player vyzaduje vytvorenie zakladnych parametrov
		super(name, animationString, animationX, animationY);
		setEnergy(MAX_HP);

		//Default parametre hraca
		setDiamonds(0);
		//Inicializacia dlzky kroku pohybu hraca na hodnotu 0, kazdy hrac musi tuto hodnotu prepisat, ak sa chce pohybovat
		setStep(0);
		this.lastX = getX();
		this.lastXequals = false;

		//Inicializacia objektov patriacich hracovi
		this.backpack = new BackpackImpl(this);
		setPlayerInput(new CustomInput());
	}

	@Override
	public void act()
	{
		//Osetrenie chyby kniznice, prvy act sa nema vykonat
		if (getX() != 0 && getY() != 0)
		{
			//Operacie s ostatnymi actormi vo svete
			for (Actor actor : getWorld())
			{
				if (actor != null && actor.intersects(this) && actor != this && actOnIntersect(actor))
				{
					break;
				}
			}

			//Osetrovanie zistovania, ci sa hrac hybe horizontalne
			this.lastXequals = Math.abs(this.lastX - getX()) < AbstractPlayer.DELTA_X_IS_ZERO;
			this.lastX = getX();

			//Pravidelne operacie nadradenej triedy sa vykonaju tiez
			super.act();

			//Vykonanie vsetkych standardnych operacii (ktore spusta pouzivatel z klavesnice) na zaklade aktualneho stavu
			getState().act();
		}
	}

	//Hotfix for GameLib inability to distinguish between standing and falling
	public final boolean noHorizontalChange()
	{
		return this.lastXequals;
	}

	@Override
	public String toString()
	{
		return super.toString() + " " + getX() + " " + getY() + " " + getNumberOfDiamonds() + " " + getEnergy();
	}

	//Nastavi pocet diamantov
	public void setDiamonds(int diamonds)
	{
		this.diamonds = diamonds;
	}
	public int getNumberOfDiamonds()
	{
		return this.diamonds;
	}

	//Nastavi zivot hraca, ktory je maximalne MAX_HP a minimalne 0, pricom zabezpeci tieto podmienky
	public void setEnergy(int energy)
	{
		if (energy > Player.MAX_HP)
		{
			this.energy = Player.MAX_HP;
		}
		else if (energy < 0)
		{
			this.energy = 0;
		}
		else
		{
			this.energy = energy;
		}

		//Stav zivota hraca bude vypisovany cez konzolu
		System.out.println(getName() + " has " + getEnergy() + " HP.");
	}
	public void decreaseEnergy(int energy)
	{
		if (energy > 0)
		{
			setEnergy(getEnergy() - energy);
		}
	}
	public int getEnergy()
	{
		return this.energy;
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

	//Obsahuje operacie, ktore sa maju vykonat po kazdom uspesnom pohybe (vratane priebehu skokov) hraca.
	public void afterMovement()
	{
		//Po kazdom pohybe sa zatvori backpack ako hotfix problemu, ze backpack sa nepohybuje spolu s hracom
		hideBackpack();

		/*
		Stara verzia: zmena stavu podla toho, ci je na zemi
		if(!isOnGround())
		{
			setState(new PlayerJumping(this));
		}
		else
		{
			setState(new PlayerWalking(this));
		}
		*/
	}

	//Checks whether the actor stands on a solid object/ground
	public final boolean standsOnSolid()
	{
		if (noVerticalChange() /*isOnGround()*/ &&
		    Math.abs(PhysicsHelper.getLinearVelocity(this)[1]) < Player.SPEED_Y_IS_ON_GND)
		{
			return true;
		}
		for (Actor actor : getWorld())
		{
			if (actor instanceof AbstractActor && !(actor.equals(this)) && !(actor instanceof Item) && standsOn(actor))
			{
				return true;
			}
		}
		return false;
	}

	public abstract void onDeath();
	public abstract void wonTheGame();

	/*public final boolean enoughTimeNoSolid()
	{
		return this.timeNoSolid>=Player.MAX_TIME_NO_SOLID;
	}*/
}
