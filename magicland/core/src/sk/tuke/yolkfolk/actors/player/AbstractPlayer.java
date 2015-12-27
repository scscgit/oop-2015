/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
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
import sk.tuke.gamelib2.World;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.player.states.PlayerState;
import sk.tuke.yolkfolk.actors.player.states.PlayerStates;
import sk.tuke.yolkfolk.collectables.BackpackImpl;
import sk.tuke.yolkfolk.input.CustomInput;
import sk.tuke.yolkfolk.interpreter.PlayerInterpreter;

/**
 * Abstraktna implementacia hraca.
 * <p/>
 * Created by Steve on 2.12.2015.
 */
public abstract class AbstractPlayer extends AbstractAnimatedActor implements Player
{
	//Variables
	private boolean flyable;
	private float jumpHeight;
	private int diamonds;
	private int energy;
	private float lastY;
	private boolean lastYequals;
	private float lastX;
	private boolean lastXequals;

	//Variables for falling purposes
	private float fallSpeed;
	private float fallLimit;
	private float fallDamageRatio;

	//Objects
	private BackpackImpl backpack;
	private PlayerState currentState;
	private CustomInput playerInput;

	public AbstractPlayer(String name, String animationString, int animationX, int animationY)
	{
		//Player vyzaduje vytvorenie zakladnych parametrov
		super(name, animationString, animationX, animationY);
		setEnergy(MAX_HP);
		setFlyable(false);

		//Default parametre hraca
		setDiamonds(0);
		setStep(
			0); //Inicializacia dlzky kroku pohybu hraca na hodnotu 0, kazdy hrac musi tuto hodnotu prepisat, ak sa chce pohybovat
		setJumpHeight(0); //Inicializacia vysky skoku.
		this.lastY = getY();
		this.lastYequals = false;
		this.lastX = getX();
		this.lastXequals = false;

		//Default parametre tykajuce sa padania
		setFallLimit(0);
		setFallDamageRatio(20);

		//Inicializacia objektov patriacich hracovi
		this.backpack = new BackpackImpl(this);
		setPlayerInput(new CustomInput());
	}

	@Override
	public void act()
	{
		//Operacie s ostatnymi actormi vo svete
		for (Actor actor : getWorld())
		{
			if (actor != null && actor.intersects(this))
			{
				actOnIntersect(actor);
			}
		}

		//Osetrenie zistovania, ci je hrac na zemi
		this.lastYequals = Math.abs(this.lastY - getY()) < AbstractPlayer.DELTA_Y_IS_ZERO;
		this.lastY = getY();
		this.lastXequals = Math.abs(this.lastX - getX()) < AbstractPlayer.DELTA_X_IS_ZERO;
		this.lastX = getX();

		//Vykonanie vsetkych standardnych operacii (ktore spusta pouzivatel z klavesnice) na zaklade aktualneho stavu
		getState().act();
	}

	//Operacie s ostatnymi actormi vo svete, ktorych sa Player dotyka. Ocakava sa implementacia funkcie.
	protected abstract void actOnIntersect(Actor actor);

	//Each actor must implement his default state
	public abstract PlayerState defaultState();

	@Override
	public String toString()
	{
		return super.toString() + " " + getX() + " " + getY() + " " + getNumberOfDiamonds() + " " + getEnergy();
	}

	//Oznamuje hracovi rychlost padania a po dopade na zem urcitou rychlostou (linear velocity v smere y) udeli hracovi relevantne zranenie.
	public final void fall(float ySpeed)
	{
		//Ak pada, tak sa najvacsia hodnota rychlosti zapamata. Ak prestal padat, povodna hodnota urcuje zranenie.
		if (/*ySpeed < 0 &&*/ -this.fallSpeed > ySpeed)
		{
			this.fallSpeed = -ySpeed;
		}
		else
		{
			float speed = this.fallSpeed - getFallLimit();
			this.fallSpeed = 0f;

			//Zranenie dostane iba ak pretal padat a nelieta
			if (speed > 0 && !isFlyable())
			{
				decreaseEnergy((int) (speed * getFallDamageRatio()));
				System.out.println("Dizzy has " + getPlayer().getEnergy() + " HP");
			}
		}

		/*
		Stary kod: verzia ktora riesila rozdiel suradnic
		Nastavi aktualnu poziciu suradnice Y hraca ako novu poziciu dopadu a v pripade velkeho rozdielu od minulej hodnoty udeli hracovi zranenie.
		float y = getY();
		float fallDistance = this.fallPosition - y;
		if (getFallDamageRatio() > 0 && fallDistance > 0 && fallDistance > getFallLimit())
		{
			decreaseEnergy((int) ((fallDistance - getFallLimit()) * getFallDamageRatio()));
		}
		this.fallPosition = y;
		*/
	}
	//Kriticka rychlost, akou musi hrac padat, aby mu bolo udelene poskodenie z padu.
	public final void setFallLimit(float fallLimit)
	{
		this.fallLimit = fallLimit;
	}
	protected final float getFallLimit()
	{
		return fallLimit;
	}
	//Koeficient, ktorym sa vynasobi rozdiel rychlosti padania a kritickej rychlosti pred udelenim zranenia o rovnakej hodnote.
	public final void setFallDamageRatio(float fallDamageRatio)
	{
		this.fallDamageRatio = fallDamageRatio;
	}
	protected final float getFallDamageRatio()
	{
		return fallDamageRatio;
	}
	//Vyska skoku
	public void setJumpHeight(float jumpHeight)
	{
		this.jumpHeight = jumpHeight;
	}
	public float getJumpHeight()
	{
		//V pripade lietania je potrebne redukovat rychlost "skakania po vzduchu"
		if (isFlyable())
		{
			return this.jumpHeight * 0.72f;
		}
		else
		{
			return this.jumpHeight;
		}
	}

	//Nastavi schopnost hraca lietat namiesto skakania
	public void setFlyable(boolean flying)
	{
		this.flyable = flying;

		//Ak prave padal z velkej vysky, tak bude zachraneny a po dopade na zem nezomrie
		fall(0f);
	}
	public boolean isFlyable()
	{
		return this.flyable;
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

	//Nastavovanie noveho stavu
	public void setState(PlayerState state)
	{
		this.currentState = state;
	}
	public PlayerState getState()
	{
		if (this.currentState == null)
		{
			this.currentState = defaultState();
		}
		return this.currentState;
	}
	//Access to the list of all available states
	public PlayerStates changeState()
	{
		return new PlayerStates(this);
	}

	//Hotfix for GameLib inability to distinguish between standing and falling
	public final boolean noVerticalChange()
	{
		return this.lastYequals;
	}

	//Hotfix for GameLib inability to distinguish between standing and falling
	public final boolean noHorizontalChange()
	{
		return this.lastXequals;
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

	//Interprets command for this Player
	@Override
	public void interpret(String commands)
	{
		new PlayerInterpreter(this).interpret(commands);
	}

	//Each player is egocentric - he thinks the World, literally, revolves around him.
	@Override
	public void addedToWorld(World world)
	{
		super.addedToWorld(world);
		world.centerOn(this);
	}

	//Keyboard input of the player
	public CustomInput getPlayerInput()
	{
		return this.playerInput;
	}
	public void setPlayerInput(CustomInput playerInput)
	{
		this.playerInput = playerInput;
	}

	public abstract void onDeath();
}
