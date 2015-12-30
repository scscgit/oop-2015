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

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.characters.ghost.Ghost;
import sk.tuke.yolkfolk.actors.characters.ghost.GhostImpl;
import sk.tuke.yolkfolk.actors.characters.ghost.WhiteGhostDecorator;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.spaces.CinematicZone;
import sk.tuke.yolkfolk.spaces.PrinceSpace;

/**
 * Truly, a good prince. He enslaved Daisy, though.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public class Prince extends AbstractActor implements Item
{
	//Constants
	public static final String NAME = "Prince";
	//Doba medzi jednotlivym duchmi
	public static final int GHOST_INTERVAL = 100;
	//Doba zobrazenia spravy, ze bol Princ porazeny
	public static final int DEFEATED_INTERVAL = 200;
	//Doba zobrazenia uvitacej spravy
	public static final int GREETING_INTERVAL = 200;

	//Variables
	private int ghostTimer;
	private boolean defeated;
	private int defeatedTimer;
	private int greetingTimer;
	private boolean initialization;
	private boolean endOfLife;
	private boolean greetingDone;

	//Slaves
	private Daisy daisy;

	//Objects
	private Message message;
	private Player player;
	private PrinceSpace princeSpace;

	public Prince()
	{
		super(Prince.NAME, "sprites/prince.png", 50, 60);
		this.ghostTimer = 0;
		this.defeated = false;
		this.defeatedTimer = 0;
		this.daisy = null;
		this.message = null;
		this.player = null;
		this.princeSpace = null;
		this.greetingTimer = 0;
		this.initialization = false;
		this.endOfLife = false;
		this.greetingDone = false;
	}

	//Spusti cinematic obraz
	protected void runCinematic()
	{
		CinematicZone cinematic = null;
		for (Actor actor : getWorld())
		{
			if (actor instanceof CinematicZone)
			{
				cinematic = (CinematicZone) actor;
				break;
			}
		}

		//Zoberie ovladanie hracovi a presunie kameru na cinematic oblast
		interruptPlayer(cinematic);
	}

	//This method gets called when the enemy Ghost kills the Prince
	public void defeat()
	{
		this.defeated = true;
		runCinematic();
	}

	//Returns true when he is defeated
	public boolean isDefeated()
	{
		return this.defeated;
	}

	//Frees Daisy, making her run to the Exit //todo put it somewhere else storywise
	public void setDaisyFree()
	{
		if (this.daisy != null)
		{
			this.daisy.goToExit();
		}
	}

	//Vytvorenie noveho ducha
	private void makeGhost()
	{
		Ghost ghost = new WhiteGhostDecorator(new GhostImpl());
		ghost.setPosition(getX(), getY()+ghost.getHeight()/2);
		getWorld().addActor(ghost);
	}

	//Rutina vytvarania novych duchov
	private void ghostRoutine()
	{
		if (ghostTimer >= Prince.GHOST_INTERVAL)
		{
			makeGhost();
			ghostTimer = 0;
		}
		else
		{
			ghostTimer++;
		}
	}

	//Ak nie je najdeny ziaden hrac, tak ho najde a vrati true
	private boolean findFirstPlayer()
	{
		if (this.player == null && this.princeSpace != null)
		{
			for (Actor actor : getWorld())
			{
				if (actor instanceof Player && this.princeSpace.intersects(actor))
				{
					this.player = (Player) actor;
					return true;
				}
			}
		}
		return false;
	}

	//Privita prveho hraca, ktoreho uvidi
	private void greetFirstPlayer()
	{
		if (this.greetingDone)
		{
			return;
		}

		//Ak bola zobrazena sprava, tak chvilu pocka a potom vrati kameru na hraca
		if (this.message != null)
		{
			if (this.greetingTimer >= Prince.GREETING_INTERVAL && this.player != null)
			{
				//Vrati ovladanie hracovi
				unfreezePlayer();
				this.greetingDone = true;
			}
			else
			{
				this.greetingTimer++;
			}
		}
		//Ak este nebol najdeny hrac, tak ho najde a zobrazi spravu
		else if (findFirstPlayer() && this.message == null)
		{
			this.message = new Message("I know why you are here!",
			                           "You want me to free Daisy?\nYou'll have to defeat me first!", this);
			//Zoberie ovladanie hracovi
			interruptPlayer(this);
		}
	}

	//Inicializacia objektov suvisiacich s princom, ktora sa ma vykonat v prvom cykle hry
	private void initIfNeeded()
	{
		if (!this.initialization)
		{
			//Prince finds his Player-detecting space (only first one, assumes no more are in the map)
			for (Actor actor : getWorld())
			{
				if (actor instanceof PrinceSpace)
				{
					this.princeSpace = (PrinceSpace) actor;
					break;
				}
			}

			//Prince has Daisy as a slave
			Actor daisyActor = getActorByName("Daisy");
			if (daisyActor instanceof Daisy)
			{
				this.daisy = (Daisy) daisyActor;
			}

			this.initialization = true;
		}
	}

	//Zoberie ovladanie hracovi
	public void interruptPlayer(Actor focus)
	{
		if (this.player != null && focus != null)
		{
			getWorld().centerOn(focus);
			this.player.interpret("set state frozen");
		}
	}

	//Vrati ovladanie hracovi
	public void unfreezePlayer()
	{
		if (this.player != null)
		{
			getWorld().centerOn(this.player);
			this.player.setState(this.player.defaultState());
		}
	}

	@Override
	public void act()
	{
		if (this.endOfLife)
		{
			return;
		}

		initIfNeeded();

		//Ak je uz porazeny, tak chvilu pocka, kym si hrac precita spravu a potom mu vrati kameru
		if (isDefeated())
		{
			if (defeatedTimer >= Prince.DEFEATED_INTERVAL)
			{
				//Vrati ovladanie hracovi
				unfreezePlayer();
				this.endOfLife = true;
			}
			else
			{
				defeatedTimer++;
			}
		}
		//Kym nie je porazeny, tak iba raz pozdravi hraca a brani sa
		else
		{
			greetFirstPlayer();
			ghostRoutine();
		}
	}
}
