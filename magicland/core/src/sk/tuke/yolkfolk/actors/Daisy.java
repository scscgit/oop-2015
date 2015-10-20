/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 *
 * scsc
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

package sk.tuke.yolkfolk.actors;

import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.player.Player;

/**
 * A generic Greeter example. Can be possibly saved by our Hero.
 *
 * Created by Steve on 10.11.2015.
 */
public class Daisy extends AbstractActor implements Greeter, Item //TODO Daisy is not an item, but needs to intersect
{
	private boolean didIGreetPlayerYet;

	public Daisy()
	{
		super("Daisy","sprites/daisy.png",25,25);
		this.didIGreetPlayerYet = false;
	}

	public boolean greetPlayer(Player player)
	{
		if(!this.didIGreetPlayerYet)
		{
			new Message("Greetings", "Hi " + player.getName() + "!", this);
			this.didIGreetPlayerYet = true;
			return true;
		}
		else
		{
			return false;
		}
	}
}
