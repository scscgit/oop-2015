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

import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;

/**
 * He robs in hood. This is bad neighborhood.
 * <p/>
 * Created by Steve on 27.12.2015.
 */
public class RobinHood extends AbstractActor implements Item, HoodedVisitor
{
	//Constants
	public static final String NAME = "RobinHood";
	public static final int BIRD_CHARISMA = 150;
	public static final int DIZZY_CHARISMA = 350;

	public RobinHood()
	{
		super(RobinHood.NAME, "sprites/robinhood.png", 32, 48);
	}

	@Override
	public void act()
	{
	}

	//Makes birds be full of joy
	@Override
	public void visit(Bird bird)
	{
		if (bird != null && isNear(bird, getWidth() * 2, getHeight() * 2))
		{
			bird.doChirp(RobinHood.BIRD_CHARISMA);
		}
	}
	//Dizzy, our hero of this chapter has special purpose
	@Override
	public void visit(Dizzy dizzy)
	{
		if (dizzy != null && isNear(dizzy, getWidth() * 2, getHeight() * 2))
		{
			dizzy.beSpooked(RobinHood.DIZZY_CHARISMA);
		}
	}

	//Greets a first generic player (my map does not plan to activate this method)
	@Override
	@Deprecated
	public void visit(Player player)
	{
		if (player != null && isNear(player, getWidth() * 2, getHeight() * 2))
		{
			new Message("A new hero?", "Welcome to my home.", this);
		}
	}
}
