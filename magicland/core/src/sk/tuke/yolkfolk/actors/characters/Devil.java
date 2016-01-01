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
import sk.tuke.gamelib2.Message;
import sk.tuke.gamelib2.NoGravity;
import sk.tuke.yolkfolk.GameMusic;
import sk.tuke.yolkfolk.NewWorldOrder;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Cursable;
import sk.tuke.yolkfolk.actors.items.Splash;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.collectables.Ring;

/**
 * Enemy, that curses a player after dying. His goal is to block exit path and offer temptations.
 * Death style is inspired by Mario game.
 * <p/>
 * Created by Steve on 3.12.2015.
 */
public class Devil extends AbstractActor implements NoGravity
{
	//Constants
	public static final String NAME = "Devil";
	//Zranenie, ktore udeli hracovi po svojej smrti
	public static final int DAMAGE_ON_DEATH = 20;
	//Vzdialenost v nasobkoch rozmerov Devila, na ktoru si vsimne hraca a povie mu spravu
	private static final float DETECTION_RADIUS = 2f;
	//Doba, po ktoru musi stat hrac na jeho hlave aby zomrel
	private static final int ON_HEAD_TIME = 15;

	//Variables
	private int onHeadCounter;
	private boolean deleted;

	//Objects
	private Message message;

	public Devil()
	{
		super(Devil.NAME, "sprites/devil.png", 32, 48);
		this.message = null;
		this.onHeadCounter = 0;
		this.deleted = false;
	}

	//Puts an instance of Splash below the Devil
	protected void putSplash()
	{
		Splash splash = new Splash(getName());
		splash.setPosition(getX(), getY());
		getWorld().addActor(splash);
	}

	//Akcie vykonane v suvislosti s kazdym prstenom. Vracia true ak nastane zmena actora.
	protected boolean actOnRing(Ring ring)
	{
		if (isNear(ring, Devil.DETECTION_RADIUS * getWidth(), Devil.DETECTION_RADIUS * getHeight()))
		{
			if (this.message != null)
			{
				this.message.remove();
			}
			this.message = new Message("Nice!",
			                           "Now I own Daisy's soul, forever.\nIt's been a pleasure to trade with you!",
			                           this);
			ring.stealByDevil(this);

			//Devil goes away
			putSplash();
			getWorld().removeActor(this);
			return true;
		}
		return false;
	}

	//Akcie vykonane v suvislosti s kazdym hracom. Vracia true ak nastane zmena actora.
	protected boolean actOnPlayer(Player player)
	{
		//Ak na neho skoci hrac, tak ma za ulohu zomriet a pustit hraca dalej.
		if (intersectsAbove(player, player.getHeight() / 2))
		{
			if (this.onHeadCounter >= Devil.ON_HEAD_TIME)
			{
				//Devil spravi posledne akcie a zomrie
				killedByPlayer(player);
				return true;
			}
			else
			{
				this.onHeadCounter++;
			}
		}
		//Ak sa pri nom hrac nachadza prvy krat, tak mu povie svoju spravu
		else if (this.message == null &&
		         isNear(player, getWidth() * Devil.DETECTION_RADIUS, getHeight() * Devil.DETECTION_RADIUS))
		{
			this.message = new Message(player.getName() + "!",
			                           "My job is to keep you in the castle.\nLet us make a deal. Bring me Daisy's ring.",
			                           this);
		}
		return false;
	}

	//Hrac uspesne zabil diabla
	protected void killedByPlayer(Player player)
	{
		//Po smrti sa objavia pekelne plamene (respektive krv, zalezi na uhle pohladu hraca)
		putSplash();

		//Hrac bude prekliaty (ak nema imunitu voci prekliatiu)
		if (player instanceof Cursable)
		{
			((Cursable) player).beCursed(this);
		}

		//Dizzy sa popalil
		player.decreaseEnergy(DAMAGE_ON_DEATH);
		if (this.message != null)
		{
			this.message.remove();
		}
		this.message = new Message("Dizzy got burnt.", "Burn in flames, cursed " + player.getName() + "!", this);

		//Odstranit actora po jeho nahradeni plamenom
		onDeath();
		safeDeleteStopAct();
	}

	//Akcie, ktore nastanu po smrti Devila
	protected void onDeath()
	{
		GameMusic.playFinishHim();
	}

	//Hotfix proti crashu - teleport outside world so wrong collisions won't happen
	public final void safeDeleteStopAct()
	{
		this.deleted = true;
		NewWorldOrder.teleportOutside(this);
		//removeFromWorld();
	}

	@Override
	public void act()
	{
		if (this.deleted)
		{
			return;
		}

		for (Actor actor : getWorld())
		{
			//Vykona pozadovanu akciu nad hracom alebo prstenom
			if
				(
				(actor instanceof Player && actOnPlayer((Player) actor))
				||
				(actor instanceof Ring && actOnRing((Ring) actor))
				)
			{
				break;
			}
		}
	}

	//After the game ends, he visits Daisy
	public void daisyEnd()
	{
		if (this.message != null)
		{
			this.message.remove();
		}
		this.message = new Message("You think you won?", "I now truly own Daisy\nfor the eternity!", this);
	}
}
