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

package sk.tuke.yolkfolk.actors.characters.monkey;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.State;
import sk.tuke.yolkfolk.actors.player.Player;
import sk.tuke.yolkfolk.actors.player.players.dizzy.Dizzy;

/**
 * Template pre stav opice.
 * <p/>
 * Created by Steve on 28.12.2015.
 */
public abstract class AbstractMonkeyState implements MonkeyState
{
	private Monkey monkey;

	public AbstractMonkeyState(Monkey monkey)
	{
		this.monkey = monkey;
	}

	//Metoda, ktorou na baze retazca zodpovednosti kazdy stav moze nastavit nasledujuci stav
	public abstract State setNextState();

	protected final Monkey getMonkey()
	{
		return this.monkey;
	}

	protected final Dizzy getDizzy()
	{
		return getMonkey().getDizzy();
	}
	protected final void setDizzy(Dizzy firstPlayer)
	{
		getMonkey().setDizzy(firstPlayer);
	}

	protected boolean isNear(Player player)
	{
		return getMonkey().isNear(player, getMonkey().getWidth() * Monkey.DETECTION_RADIUS,
		                          getMonkey().getHeight() * Monkey.DETECTION_RADIUS);
	}

	public void removeMessage()
	{
		getMonkey().removeMessage();
	}
	public Message newMessage(String title, String message, Actor actor)
	{
		return getMonkey().newMessage(title, message, actor);
	}
	public Message newMessage(String title, String message)
	{
		return getMonkey().newMessage(title, message);
	}
}