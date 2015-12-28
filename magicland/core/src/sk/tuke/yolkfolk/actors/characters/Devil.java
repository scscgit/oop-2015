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

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.GameMusic;
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
public class Devil extends AbstractActor
{
	//Constants
	public static final String NAME = "Devil";
	public static final int DAMAGE_ON_DEATH = 20;
	//Vzdialenost v nasobkoch rozmerov Devila, na ktoru si vsimne hraca a povie mu spravu
	private static final float DETECTION_RADIUS = 2.0f;

	//Objects
	private Message message;

	public Devil()
	{
		super(Devil.NAME, "sprites/devil.png", 32, 48);
		this.message = null;
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
		if(isNear(ring, getWidth(), getHeight()))
		{
			if(this.message!=null)
			{
				this.message.remove();
			}
			this.message = new Message("Nice!","Now I own Daisy's soul, forever.\nIt's been a pleasure to trade with you!", this);
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
		if(intersectsAbove(player, player.getHeight() / 2))
		{
			//Devil spravi posledne akcie a zomrie
			killedByPlayer(player);
			return true;
		}
		//Ak sa pri nom hrac nachadza prvy krat, tak mu povie svoju spravu
		else if(isNear(player, getWidth()*Devil.DETECTION_RADIUS, getHeight()*Devil.DETECTION_RADIUS))
		{
			if(this.message == null)
			{
				this.message = new Message(player.getName() + "!", "My job is to keep you in the castle.\nLet us make a deal. Bring me Daisy's ring.",this);
			}
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
		if(this.message!=null)
		{
			this.message.remove();
		}
		this.message = new Message("Dizzy got burnt.", "Burn in flames, cursed " + player.getName() + "!", this);

		//Odstranit actora po jeho nahradeni plamenom
		setPosition(-getWidth(), -getHeight()); //Teleport outside world so wrong collisions won't happen
		onDeath();
		removeFromWorld();
	}

	//Akcie, ktore nastanu po smrti Devila
	protected void onDeath()
	{
		GameMusic.playFinishHim();
	}

	@Override
	public void act()
	{
		for (Actor actor : getWorld())
		{
			if (actor instanceof Player)
			{
				if(actOnPlayer((Player) actor))
				{
					break;
				}
			}

			if(actor instanceof Ring)
			{
				if(actOnRing((Ring)actor))
				{
					break;
				}
			}
		}
	}
}
