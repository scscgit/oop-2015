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
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Cursable;
import sk.tuke.yolkfolk.actors.items.Splash;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * Enemy that curses a player after dying.
 * <p/>
 * Created by Steve on 3.12.2015.
 */
public class Devil extends AbstractActor
{
	//Constants
	public static final String name = "Devil";
	public static final int DAMAGE_ON_DEATH = 20;

	public Devil()
	{
		super(Devil.name, "sprites/devil.png", 32, 48);
	}

	@Override
	public void act()
	{
		for (Actor actor : getWorld())
		{
			//Ak na neho skoci hrac, tak ma za ulohu zomriet a pustit hraca dalej.
			if (actor instanceof Player && intersectsAbove(actor, actor.getHeight() / 2))
			{
				Player player = (Player) actor;

				//Po smrti sa objavia pekelne plamene (respektive krv, zalezi na uhle pohladu hraca)
				Splash splash = new Splash(getName());
				splash.setPosition(getX(), getY());
				getWorld().addActor(splash);

				//Hrac bude prekliaty (ak nema imunitu voci prekliatiu)
				if (player instanceof Cursable)
				{
					((Cursable) player).beCursed(this);
				}

				//Dizzy sa popalil
				player.setEnergy(player.getEnergy() - DAMAGE_ON_DEATH);
				new Message("Dizzy got burnt.", "Burn in flames, cursed " + player.getName() + "!", splash);

				//Odstranit actora po jeho nahradeni plamenom
				setPosition(-getWidth(), -getHeight()); //Teleport outside world so wrong collisions won't happen
				getWorld().removeActor(this);
				break;
			}
		}
	}
}
